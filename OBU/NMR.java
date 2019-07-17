import com.ibm.aglet.*;
import java.util.*;
import java.lang.*;
import java.net.*;

public class NMR extends Aglet{

private static final List agent_dests =new ArrayList();
private static final List agent_aid = new ArrayList();
boolean migrate = false;
String destination;
AgletID aid_manager;
public void onCreation(Object o){
 

}

public boolean handleMessage(Message msg){
if(msg.sameKind("register")){

AgletID aid_received = (AgletID)msg.getArg("aid");
agent_aid.add(aid_received);
System.out.println(agent_aid.size());
String dests=(String)msg.getArg("dest");
//System.out.println(dests);
String[] ip_dests = dests.split(";");

for(int i =0;i<ip_dests.length;i++){
agent_dests.add(ip_dests[i]);
}
System.out.println(agent_dests);
msg.sendReply(aid_manager);
return true;
}else if(msg.sameKind("rsu")){
System.out.println(msg.getArg("rsu_name")+" is the rsu found");
System.out.println(msg.getArg("ip")+" is the ip found");

if(agent_dests.contains(msg.getArg("rsu_name").toString())){
System.out.println("migrating");
try{


//AgletProxy mobile = getAgletContext().getAgletProxy((AgletID)agent_aid.get(0));
destination= "atp://"+msg.getArg("ip").toString()+":4434";
migrate= true;
//mobile.dispatch(new URL(destination));
agent_dests.clear();
agent_aid.clear();
System.out.println(agent_dests);
//migrating
}catch(Exception uh){}
}else{
System.out.println("not migrating");
}

return true;
}else if(msg.sameKind("Manager")){
aid_manager = (AgletID)msg.getArg();
System.out.println(aid_manager);
return true;
}else if(msg.sameKind("migrate")){
if(!migrate){
msg.sendReply("no");
}else{
msg.sendReply(destination);
destination="";
migrate = false;
}

return true;
}else{
return false;
}
}
}
