import com.ibm.aglet.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Manager extends Aglet{

public boolean handleMessage(Message msg){

if(msg.sameKind("aid")){

msg.sendReply(getAgletID());
return true;
}else if(msg.sameKind("details")){
try{
File file =new File("/home/vaibhav/Desktop/hell/manager.txt");
String stem = "";

BufferedReader br = new BufferedReader(new FileReader(file));
String st;
while((st = br.readLine())!=null){
stem = stem+st+"@";
}
System.out.println(stem);
msg.sendReply(stem);
}catch(Exception gh){}
return true;
}else{
return false;
}
}
}
