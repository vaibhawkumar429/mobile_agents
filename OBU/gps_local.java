import com.ibm.aglet.*;
import java.io.*;
import java.util.*;

public class gps_local extends Aglet{

public boolean handleMessage(Message msg){

if(msg.sameKind("read")){
try{
Process process = Runtime.getRuntime().exec("python gps_test.py");
process.waitFor();
}catch(Exception kj){}

return true;
}else if(msg.sameKind("send")){
try{

File file = new File("/home/pi/Desktop/practice/gps_data.txt");
String stem="";

BufferedReader br = new BufferedReader(new FileReader(file));
String st;
while((st = br.readLine())!=null){
stem = stem+st+"@";
}
msg.sendReply(stem);

}catch(Exception as){}
return true;
}else{
return false;
}
}

}
