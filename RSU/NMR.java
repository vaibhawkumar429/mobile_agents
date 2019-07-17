import com.ibm.aglet.*;
import java.util.*;
import java.lang.*;
import java.net.*;
//RSU 2
public class NMR extends Aglet{
String current_dest ="RSU_2";
String dests="";

private static final HashMap<String, String> map = new HashMap<String,String>();
private static final List agents = new ArrayList();

public void onCreation(Object o){
 


map.put("RSU_1","RSU_2;RSU_3");
map.put("RSU_2","RSU_3;RSU_1");
map.put("RSU_3","RSU_1;RSU_2");

}

public boolean handleMessage(Message msg){
if(msg.sameKind("register")){

AgletID aid_received = (AgletID)msg.getArg();

msg.sendReply(map.get(current_dest));
agents.add(aid_received);
System.out.println(agents.size());
System.out.println("========");


return true;
}else if(msg.sameKind("obu")){
System.out.println(msg.getArg()+"received by NMR");
AgletProxy proxy = getAgletContext().getAgletProxy((AgletID)agents.get(0));
try{
String destination_mobile = "atp://"+msg.getArg().toString()+":4434";
proxy.dispatch(new URL(destination_mobile));
agents.remove(0);
}catch(Exception ghk){}
return true;
}else{
return false;
}
}
}
