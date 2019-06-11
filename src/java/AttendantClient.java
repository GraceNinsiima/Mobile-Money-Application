import java.net.*;
import java.io.*;

public class AttendantClient {
    private  BufferedReader reader, in, readFromServer, read;
    private  PrintWriter out, writer, sendToServer, outToServer,send, sendTo;
    private Socket socket;
    String amountForRequest= null, choose, username, password, mtnNumber, airtelNumber, transactionNumber,customerNumber,statusChoice;
    String agentNo, customerNo, amount, typeOfTransaction, transaction, kiosk, availableFloat, kioskNumber, kioskName, kioskID, requestId;
    String request = null;
    String customerNum =null;
    public static void main(String args[]) throws IOException{
        
        AttendantClient client = new AttendantClient();
        client.initiate(); 
    }
    public void initiate() throws IOException{
        try{
            socket = new Socket("127.0.0.1", 7777);
            out = new PrintWriter(socket.getOutputStream(), true);
            writer = new PrintWriter(socket.getOutputStream(), true);
            send = new PrintWriter(socket.getOutputStream(), true);
            sendToServer = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            reader = new BufferedReader(new InputStreamReader(System.in));
            agentLogin();
        }
        catch(UnknownHostException e){
             System.err.println("Dont know about the host. ");
                System.exit(1);
        }
    }
    //Attendant login
    public void agentLogin()throws IOException{
        System.out.println("Enter username:");
        username = reader.readLine();
        System.out.println("Enter password");
        password = reader.readLine();
        sendToServer.println(username+","+password);
        sendToServer.flush();
        Thread login = new Thread(new anotherThread());
        login.start();
    }
    //Thread fro login
    public class anotherThread implements Runnable {
        @Override
        public void run() {
            try {
                if(in.readLine().equals("Success")){
                mtnNumber = in.readLine();
                airtelNumber = in.readLine();
                kioskID= in.readLine();
                System.out.println("Mtn number is "+mtnNumber+" and airtel is "+airtelNumber);
                String receive = in.readLine();
                System.out.println(receive);
                 
                sendToServer.close();
                commitOrRequest();
            }
                else{
                    System.out.println("Incorrect username or password");
                }
          } catch (IOException ex) {
                ex.printStackTrace();
          }
        }
    }
   //commit request or check status
    public void commitOrRequest() throws IOException{
        
        System.out.println("Type 'commit' or 'request' or 'serveRequest'");
        choose = reader.readLine();
        if(choose.equals("commit"))
            transact();
        else if (choose.equals("request"))
            request();
        else if(choose.equals("serveRequest")){
            serveRequest();
            }
            else{
                System.out.println("Invalid");
            }
        }
            
    
    //carrying out requests
    public void request()throws IOException{
        System.out.println("Enter customer number");
        customerNumber = reader.readLine();
        System.out.println("Amount of float");
        amountForRequest = reader.readLine();
        System.out.println("Choose number for transaction type 'mtn' or 'airtel'");
        transactionNumber = reader.readLine();
        if(transactionNumber.equals("mtn")){
            request = mtnNumber+","+ amountForRequest+","+customerNumber;
        }
        else if(transactionNumber.equals("airtel")){
            request = airtelNumber+","+ amountForRequest+","+customerNumber;
        }
        else{
            System.out.println("Invalid");
        }
        socket = new Socket("127.0.0.1", 7777);
        out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println(request);
        out.println(request);
        out.flush();
        Thread bg = new Thread(new myThread());
        bg.start();
    }
    
    //Thread for requests
    public class myThread implements Runnable {
        @Override
        public void run() {
            try {
                readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if(readFromServer.readLine().equals("available")){
                    kioskName = readFromServer.readLine();
                    kiosk = readFromServer.readLine();
                    availableFloat = readFromServer.readLine();
                    customerNumber =readFromServer.readLine();
                    agentNo = readFromServer.readLine();
                    out.close();
                    System.out.println("kiosks availabe "+kioskName);
                    System.out.println("kiosk number availabe "+kiosk);
                    System.out.println("Amount of float available "+availableFloat);
                    sendRequest();
            }
                else{
                    System.out.println("No request can be made");
                    commitOrRequest();
                }
          } catch (IOException ex) {
                ex.printStackTrace();
          }
        }
    }
    //send the request to the server
    public void sendRequest() throws IOException{
        System.out.println("Enter kiosk number to send request to");
        kioskNumber = reader.readLine();
        request = kioskNumber+","+ agentNo+","+amountForRequest+ ","+customerNumber+","+kioskID;
        socket = new Socket("127.0.0.1", 7777);
        writer= new PrintWriter(socket.getOutputStream(), true);
        writer.println(request);
    }
    public void transact()throws IOException{
        String totalTransaction = null;
        System.out.println("You can now commit your transactions");
            for(int i=1; i<=3; i++){
                if(i==1){
                    totalTransaction = enter();
                }
                else{
                    totalTransaction =totalTransaction + enter();
                }
                if(i==3){
                    System.out.println("You can only enter 3 transactions at a time");
                    break;
                }
                System.out.println("To continue enter \"1\" or any value to stop");
                String choice = reader.readLine();
                if(choice.equals("1"))
                    totalTransaction =totalTransaction+"/";
                else
                    break;
            }
            socket = new Socket("127.0.0.1", 7777);
            outToServer = new PrintWriter(socket.getOutputStream(), true);
            outToServer.println(totalTransaction);
    }
    
    public  String enter() throws IOException{
        System.out.println("Choose mtn or airtel. Type 'mtn' or 'airtel'");
        agentNo = reader.readLine();
        System.out.println("Enter customer number");
        customerNo = reader.readLine();
        System.out.println("Enter type of Transaction");
        typeOfTransaction = reader.readLine();
        System.out.println("Enter amount");
        amount = reader.readLine();
        if(agentNo.equals("mtn") && Integer.parseInt(amount)<=4000000)
            transaction = mtnNumber+ ","+ customerNo+"," + typeOfTransaction+","+ amount+","+kioskID+","+agentNo;
        else if(agentNo.equals("airtel")&& Integer.parseInt(amount)<=5000000)
            transaction = airtelNumber+ ","+ customerNo+"," + typeOfTransaction+","+ amount+","+kioskID+","+agentNo;
        else
            System.out.println("Invalid choice");
        return transaction;
    }
    public void serveRequest()throws IOException{
        System.out.println("To server a request: Type 'serve' or 'no'");
        statusChoice = reader.readLine();
        if(statusChoice.equals("serve")){
            System.out.println("Enter request Id to be serviced");
            requestId = reader.readLine();
            socket = new Socket("127.0.0.1", 7777);
            sendTo= new PrintWriter(socket.getOutputStream(), true);
            sendTo.println(requestId+","+customerNum+","+amountForRequest+","+kioskID);
      }
        else if(statusChoice.equals("no")){
            commitOrRequest();
        }
    }
    
}
