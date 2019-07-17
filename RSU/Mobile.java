import com.ibm.aglet.*;
import java.lang.*;
import java.util.*;
import com.ibm.aglet.event.*;
import java.net.*;

public class Mobile extends Aglet{
boolean state=true;
String destinations="";


public List<String> read = new ArrayList<String>();
public List<String> send = new ArrayList<String>();


public List<String> send_data = new ArrayList<String>();

public void onCreation(Object o){
System.out.println("Hello Master");
addMobilityListener(new MobilityAdapter(){

public void onArrival(MobilityEvent q){
state= !state;

if(state){


//sending to database agent
try{
Enumeration f =getAgletContext().getAgletProxies();
while(f.hasMoreElements()){
AgletProxy proxy =(AgletProxy)f.nextElement();
if(proxy.getAgletClassName().equals("database")){
for(int j =0;j<send_data.size();j++){
String type ;
if(j==1){
type ="html";
}else{
type = "txt";
}
Message hello = new Message("database");
hello.setArg("data",send_data.get(j));
hello.setArg("type",type);

proxy.sendMessage(hello);

}

break;
}
}
}catch(Exception fg){}

//printing the data received
System.out.println("on arrival starts");
for(int i =0;i<send_data.size();i++){
System.out.println(send_data.get(i));
System.out.println("==========================================================================");
System.out.println("\n \n");
}
send_data.clear();

//clear the agents work list
read.clear();
send.clear();
}
}
});


}
public void run(){

if(state){
//RSU WORK
try{
AgletID aid = getAgletID();
Enumeration e =getAgletContext().getAgletProxies();
while(e.hasMoreElements()){
AgletProxy proxy =(AgletProxy)e.nextElement();
if(proxy.getAgletClassName().equals("Manager")){

Object details = proxy.sendMessage(new Message("details"));
tasks(details.toString());
System.out.println(details.toString());
}
if(proxy.getAgletClassName().equals("NMR")){

Object nmr_reply = proxy.sendMessage(new Message("register",aid));
destinations=nmr_reply.toString();
}
}
System.out.println("printing dests");
System.out.println(destinations);
// REGISTRATION DONE ABOVE

System.out.println(read);
System.out.println(send);
System.out.println("work finished");


}catch(Exception p){}







}else{
//OBU WORK

try{
 AgletID aid_manager = getAgletID();
AgletID aid_nmr = getAgletID();
AgletID aid_ = getAgletID();
Enumeration e_ =getAgletContext().getAgletProxies();
while(e_.hasMoreElements()){
AgletProxy proxy_ =(AgletProxy)e_.nextElement();


if(proxy_.getAgletClassName().equals("NMR")){
Message msg=new Message("register");
msg.setArg("aid",aid_);
msg.setArg("dest",destinations);
Object nmc_reply = proxy_.sendMessage(msg);
aid_manager=(AgletID)nmc_reply;
aid_nmr = proxy_.getAgletID();
destinations = "";
}
}
System.out.println("Registration done");

System.out.println(read);
System.out.println(send);
System.out.println("work finished");

AgletProxy proxy_manager = getAgletContext().getAgletProxy((AgletID)aid_manager);
AgletProxy proxy_nmr = getAgletContext().getAgletProxy((AgletID)aid_nmr);

List<AgletProxy> read_proxy_list= new ArrayList<AgletProxy>();
List<AgletProxy> send_proxy_list= new ArrayList<AgletProxy>();

for(String a : read){
a=a.split(":")[0];
Object reply =proxy_manager.sendMessage(new Message("info",a.toString()));
read_proxy_list.add(getAgletContext().getAgletProxy((AgletID)reply));
}

for(String b : send){
b=b.split(":")[0];
Object reply_ =proxy_manager.sendMessage(new Message("info",b.toString()));
send_proxy_list.add(getAgletContext().getAgletProxy((AgletID)reply_));
}

System.out.println("Size of read proxy list is : ");
System.out.println(read_proxy_list.size());
System.out.println("Size of send proxy list is : ");
System.out.println(send_proxy_list.size());

while(true){
int i =0;
//for work is gonna be done
for(AgletProxy z : read_proxy_list){

z.sendMessage(new Message("read",read.get(i)));
i++;
}
Object nmr_reply =proxy_nmr.sendMessage(new Message("migrate"));

if(nmr_reply.toString().equals("no")){
Thread.sleep(1000);
}else{
System.out.println("dispatching myself");
//send work is gonna be done
for(AgletProxy p : send_proxy_list){
Object send_reply = p.sendMessage(new Message("send"));
System.out.println("send data exhibition");

send_data.add(send_reply.toString());

}
System.out.println(send_data.get(1));
dispatch(new URL(nmr_reply.toString()));
}
}

}catch(Exception p){
System.out.println(p.toString());
}

}

}

public void tasks(String rule){

String read_ = rule.split("@")[0];
String send_ = rule.split("@")[1];

read_ = read_.split("=")[1];
send_ = send_.split("=")[1];
String[] reads = read_.split(";");
String[] sends = send_.split(";");
for(String a : reads){
read.add(a);
}

for(String b : sends){
send.add(b);
}

}




}
