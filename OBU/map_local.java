import com.ibm.aglet.*;
import java.lang.*;
import java.io.*;

public class map_local extends Aglet{

public boolean handleMessage(Message msg){
if(msg.sameKind("send")){
try{
Process process =Runtime.getRuntime().exec("python gps_map.py");
process.waitFor();
File file = new File("/home/pi/Desktop/practice/map13.html");
String stem="";
BufferedReader br = new BufferedReader(new FileReader(file));
String st;
while((st=br.readLine())!=null){
stem =stem+st+"\n";
}
msg.sendReply(stem);
}catch(Exception as){}
return true;
}else{
return false;
}
}

}
