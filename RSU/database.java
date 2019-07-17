import com.ibm.aglet.*;
import java.lang.*;
import java.util.*;
import java.io.*;

public class database extends Aglet{

public boolean handleMessage(Message msg){

if(msg.sameKind("database")){
System.out.println("received message=============================");
String type = msg.getArg("type").toString();
//determinig the file extension for generating file

String file_dest ="/home/pi/Desktop/";
if(type.equals("html")){
file_dest = file_dest+"map13.html";

}else{

file_dest = file_dest+"speed_data.txt";

}
System.out.println(file_dest);
try{
File file = new File(file_dest);
PrintWriter writer = new PrintWriter(file);
writer.println(msg.getArg("data").toString());
writer.close();
}catch(Exception gh){
System.out.println(gh.toString());
}

return true;
}else{return false;}

}

}
