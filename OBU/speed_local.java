import com.ibm.aglet.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class speed_local extends Aglet{
String speed_data="";
public boolean handleMessage(Message msg){
if(msg.sameKind("read")){
String speed_limits = msg.getArg().toString();
System.out.println(speed_limits);
try{
Process process = Runtime.getRuntime().exec("python gps_test.py");
process.waitFor();
Process process2 = Runtime.getRuntime().exec("python gps_speed.py");
process2.waitFor();
BufferedReader reader = new BufferedReader(new InputStreamReader(process2.getInputStream()));
String data = reader.readLine();
if(data!=null){
System.out.println(data);

float limit = Float.parseFloat(speed_limits.split(":")[1]);
float current_speed = Float.parseFloat(data.split(";")[6]);
System.out.println(limit);
System.out.println(current_speed);
if(limit>current_speed){
data= data+";"+"fine";
}else{
data=data+";"+"violated";
}
System.out.println(data);


speed_data=speed_data+data+"\n";
}

}catch(Exception e){System.out.println(e.toString());}
return true;
}else if(msg.sameKind("send")){
System.out.println("speed data from =================");
System.out.println(speed_data);
msg.sendReply(speed_data);
speed_data ="";
return true;
}else{
return false;
}

}



}

