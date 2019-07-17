import com.ibm.aglet.*;
import java.io.*;
import java.util.*;

public class Manager extends Aglet{
private static final HashMap<String,AgletID> agents = new HashMap<String,AgletID>();

public void onCreation(Object inti){
try{
Enumeration e = getAgletContext().getAgletProxies();
while(e.hasMoreElements()){
AgletProxy proxy = (AgletProxy)e.nextElement();
String class_name = proxy.getAgletClassName();
if(class_name.equals("NMR")){
proxy.sendMessage(new Message("Manager",getAgletID()));
System.out.println("Sent message to nmr");
}
if(class_name.equals("NMR") || class_name.equals("example") || class_name.equals("Manager")){

}else{

agents.put(proxy.getAgletClassName().toString(),(AgletID)proxy.getAgletID());
}

}
}catch(Exception lp){}
System.out.println(agents);
}
public boolean handleMessage(Message msg){
if(msg.sameKind("info")){
String agent = msg.getArg().toString();
AgletID aid = (AgletID)agents.get(agent);
msg.sendReply((AgletID)aid);

return true;
}else{
return false;
}
}
}
