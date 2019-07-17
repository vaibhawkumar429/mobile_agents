import com.ibm.aglet.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class example extends Aglet{
private static final HashMap<String,String> map =new HashMap<String,String>();
public static AgletProxy nmr;
public void onCreation(Object init){
map.put("161.3.197.118","RSU_1");
map.put("161.3.197.106","RSU_2");

try{
System.out.println("On creation started");
Enumeration e = getAgletContext().getAgletProxies();
while(e.hasMoreElements()){
AgletProxy proxy = (AgletProxy)e.nextElement();
if(proxy.getAgletClassName().equals("NMR")){
System.out.println("NMR found");
nmr = proxy;
break;
}
}}catch(Exception qw){}

}

public void run(){
System.out.println("Run started");
try{
System.out.println(nmr.getAgletClassName()+" found in run");
}catch(Exception gh){}

try{

while(true){
String ip_got=net_check();

if(!ip_got.equals("")){
System.out.println(ip_got);
Message msg = new Message("rsu");
msg.setArg("rsu_name",map.get(ip_got));
msg.setArg("ip",ip_got);
Object reply = nmr.sendMessage(msg);
System.out.println("Message sent");

}
	Thread.sleep(4000);}
}catch(Exception kj){}
//System.out.println("connection established");


}

public String net_check(){
String ip_found="";
try{
Process process =Runtime.getRuntime().exec("arp -a");
process.waitFor();
BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
String ip;

while((ip=reader.readLine())!=null){

//System.out.println(ip);

ip = ip.split(" ")[1];
ip =ip.substring(1,ip.length()-1);

try{
Socket socket =new Socket();
socket.connect(new InetSocketAddress(ip,4434),300);

ip_found=ip;
break;
//System.out.println(ip + ": aglet running");
}catch(Exception df){
//System.out.println(df.toString());
}

}

}catch(Exception a){}
return ip_found;
}
} 

