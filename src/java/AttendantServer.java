import java.net.*;
import java.util.Date;
import java.io.*;
import java.sql.*;
import java.text.*;
public class AttendantServer {
    private PrintWriter out;
    private BufferedReader in;
    Socket clientSocket;
    String transaction[], transactions[][];
    int position = 0, commission =0, newFloat = 0,newCash =0;
    String amount = null, number =null,airtelFloat = null, airtelCash = null, mtnFloat =null, mtnCash =null, mtnNumber =null, airtelNumber =null;
    String responseNo = null,requestAmount = null, customerNo = null;
    public static void main (String args[])throws IOException,ClassNotFoundException, SQLException{
        AttendantServer server = new AttendantServer();
        server.connect();
        
    }
    public void connect() throws IOException, ClassNotFoundException, SQLException{
        ServerSocket serverSocket = new ServerSocket(7777, 4);

        while(true){
        System.out.println("Listening.... ");
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        transaction = in.readLine().split("/");
        transactions = new String[transaction.length][6];
        System.out.println(transaction.length);
        for (int i =0; i<transaction.length; i++){
            transactions[i] = transaction[i].split(",");
        }
         connectDatabase();
    }
    }
        //Database connection
    public void connectDatabase() throws IOException, ClassNotFoundException, SQLException{
       Class.forName("com.mysql.jdbc.Driver");
       String url = "jdbc:mysql://localhost/mobilemo";
       Connection conn = DriverManager.getConnection(url, "root", "");
       Statement st = conn.createStatement();
       ResultSet result = null, req = null, login =null, status = null;
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date date =new Date();
       if(transactions[0].length == 2){
           login = st.executeQuery("Select * from kiosk where kioskAttendant='"+transactions[0][0]+"' and password = '"+transactions[0][1]+"'");
           if(login.next()== false){
               System.out.println("Log in failed");
           }
           else{
               do{
                   out.println("Success");
                   mtnNumber = login.getString("mtnSimCard");
                   out.println(mtnNumber);
                   airtelNumber = login.getString("airtelSimCard");
                   out.println(airtelNumber);
                   out.println(login.getString("kioskID"));
                   }while(login.next());
           }
                   
                   status = st.executeQuery("Select * from requests where status = 'pending'");
                   if(status.next()!=false){
                       if(status.getString("agentNoResponse").equals(mtnNumber) || status.getString("agentNoResponse").equals(airtelNumber)){
                        do{
                            out.println(status.getString("requestID")+" "+status.getString("agentNoRequest")+" "+status.getString("customerNumber")+" "+status.getString("amountForRequest"));
                
                           }while(status.next());
                   }
                       else
                            out.println("No pending requests");
                    }
                   else 
                       out.println("No pending requests");
       }
       else if (transactions[0].length == 3){
           req = st.executeQuery("Select transactions.*, kiosk.* from transactions inner join kiosk on transactions.kioskId = kiosk.kioskId where transactions.agentNo <> '"+transactions[0][0]+"'and transactions.totalFloat >'"+transactions[0][1]+"' and transactions.transactionId in "
                   + "(Select max(transactions.transactionId) from transactions group by agentNo) order by transactions.agentNo Desc limit 1");
           if(req.next()== false){
               out.println("no request");
           }
           else{
               System.out.println(req.getString("agentNo"));
               do{
                   out.println("available");
                   out.println(req.getString("kioskName"));
                   out.println(req.getString("agentNo"));
                   out.println(req.getInt("totalFloat"));
                   out.println(transactions[0][2]);//send customer number back to client
                   out.println(transactions[0][0]);
               }while(req.next());
           
        }
       }
       else if(transactions[0].length == 4){
           ResultSet kiosk = st.executeQuery("Select kiosk.*, requests.* from kiosk inner join requests on kiosk.kioskID = requests.kioskID"
                   + " where requests.requestID = '"+transactions[0][0]+"'");
            while(kiosk.next()){
                responseNo = kiosk.getString("agentNoResponse");
                requestAmount = String.valueOf(kiosk.getInt("amountForRequest"));
                customerNo = kiosk.getString("customerNumber");
                System.out.println(requestAmount);
               if(responseNo.equals(kiosk.getString("mtnSimCard"))){
                   mtnFloat = result.getString("mtnFloat");
                   mtnCash = result.getString("mtnCash");
               }
               else if(responseNo.equals(kiosk.getString("airtelSimCard"))){
                   airtelFloat = result.getString("airtelFloat");
                   airtelCash = result.getString("airtelCash");
               }
           }
           if(customerNo.startsWith("077") || customerNo.startsWith("078")){
               int amount2 = Integer.parseInt(requestAmount);
               System.out.println(amount2);
               ResultSet resp = st.executeQuery("Select * from transactions where agentNo = '"+responseNo+"' order by transactionID Desc LIMIT 1");
                    if(resp.next()== false){
                        newFloat = Integer.parseInt(mtnFloat)-amount2;
                        newCash = Integer.parseInt(mtnCash)+amount2;
                    }
                    else{
                        do{
                            newFloat = resp.getInt("totalFloat")-amount2;
                            newCash = resp.getInt("cash")+amount2;
                    }while(resp.next());
                    }
                    mtnDepositCommission(amount2);
           }
           else if(customerNo.startsWith("070") || customerNo.startsWith("075")){
               int amount2 = Integer.parseInt(requestAmount);
               System.out.println(amount2);
               ResultSet resp = st.executeQuery("Select * from transactions where agentNo = '"+responseNo+"' order by transactionID Desc LIMIT 1");
                    if(resp.next()== false){
                        newFloat = Integer.parseInt(mtnFloat)-amount2;
                        newCash = Integer.parseInt(mtnCash)+amount2;
                    }
                    else{
                        do{
                            newFloat = resp.getInt("totalFloat")-amount2;
                            newCash = resp.getInt("cash")+amount2;
                    }while(resp.next());
                    }
                    airtelDepositCommission(amount2);
           }
           st.executeUpdate("Insert into transactions (kioskID, agentNo, serviceProvider, typeOfTransaction, customerNo, amount, date, commission, totalFloat, cash)"
                   + "values('"+transactions[0][3]+"','"+responseNo+"','airtel',"
                   + "'deposit','"+customerNo+"', '"+requestAmount+"', '"+dateFormat.format(date)+"',"
                   + "'"+String.valueOf(commission)+"', '"+String.valueOf(newFloat)+"', '"+String.valueOf(newCash)+"')");
           st.executeUpdate("Update requests set status = 'served' where requestId = '"+transactions[0][0]+"'");
       }
       else if(transactions[0].length == 5){
           st.executeUpdate("Insert into requests (kioskID, agentNoRequest, agentNoResponse,customerNumber, amountForRequest, status)values"
                   + "('"+transactions[0][4]+"','"+transactions[0][1]+"','"+transactions[0][0]+"', '"+transactions[0][3]+"' ,'"+transactions[0][2]+"', 'pending')");
       }
       else{
       while(position<transaction.length){  
           result = st.executeQuery("Select * from kiosk");
           while(result.next()){
               if(result.getString("mtnSimCard").equals(transactions[position][0])){
                   mtnFloat = result.getString("mtnFloat");
                   mtnCash = result.getString("mtnCash");
               }
               else if(result.getString("airtelSimCard").equals(transactions[position][0])){
                   airtelFloat = result.getString("airtelFloat");
                   airtelCash = result.getString("airtelCash");
               }
           }
           amount = transactions[position][3];
           int amount1 = Integer.parseInt(amount);
           number = transactions[position][1];
           if(number.startsWith("077") ||number.startsWith("078")){
                if(transactions[position][2].equals("deposit")){
                    ResultSet res = st.executeQuery("Select * from transactions where agentNo = '"+transactions[position][0]+"' order by transactionID Desc LIMIT 1");
                    if(res.next()== false){
                        newFloat = Integer.parseInt(mtnFloat)-amount1;
                        newCash = Integer.parseInt(mtnCash)+amount1;
                    }
                    else{
                        do{
                            newFloat = res.getInt("totalFloat")-amount1;
                            newCash = res.getInt("cash")+amount1;
                    }while(res.next());
                    }
                    mtnDepositCommission(amount1);
                }
                else if(transactions[position][2].equals("withdraw")){
                    ResultSet res = st.executeQuery("Select * from transactions where agentNo = '"+transactions[position][0]+"' order by transactionID Desc LIMIT 1");
                    if(res.next()== false){
                        newFloat = Integer.parseInt(mtnFloat)+amount1;
                        newCash = Integer.parseInt(mtnCash)-amount1;
                    }
                    else{
                        do{
                            newFloat = res.getInt("totalFloat")+amount1;
                            newCash = res.getInt("cash")-amount1;
                        }while(res.next());
                    }
                    mtnWithdrawCommission(amount1);
                }
           }
           else if (number.startsWith("070") ||number.startsWith("075")){
               if(transactions[position][2].equals("deposit")){
                   ResultSet res = st.executeQuery("Select * from transactions where agentNo = '"+transactions[position][0]+"' order by transactionID Desc LIMIT 1");
                    if(res.next()== false){
                        newFloat = Integer.parseInt(airtelFloat)-amount1;
                        newCash = Integer.parseInt(airtelCash)+amount1;
                    }
                    else{
                        do{
                            newFloat = res.getInt("totalFloat")-amount1;
                            newCash = res.getInt("cash")+amount1;
                    }while(res.next());
                    }
                   airtelDepositCommission(amount1);
               }
               else if(transactions[position][2].equals("withdraw")){
                   ResultSet res = st.executeQuery("Select * from transactions where agentNo = '"+transactions[position][0]+"' order by transactionID Desc LIMIT 1");
                    if(res.next()== false){
                        newFloat = Integer.parseInt(airtelFloat)+amount1;
                        newCash = Integer.parseInt(airtelCash)-amount1;
                    }
                    else{
                        do{
                            newFloat = res.getInt("totalFloat")+amount1;
                            newCash = res.getInt("cash")-amount1;
                    }while(res.next());
                    }
                   airtelWithdrawCommission(amount1); 
                }
           }
           st.executeUpdate("Insert into transactions (kioskID, agentNo, serviceProvider, typeOfTransaction, customerNo, amount, date, commission, totalFloat, cash)"
                   + "values('"+transactions[position][4]+"','"+transactions[position][0]+"','"+transactions[position][5]+"',"
                   + "'"+transactions[position][2]+"','"+transactions[position][1]+"', '"+transactions[position][3]+"', '"+dateFormat.format(date)+"',"
                   + "'"+String.valueOf(commission)+"', '"+String.valueOf(newFloat)+"', '"+String.valueOf(newCash)+"')");
           ++position;
       }
        
        }
    }
    public int mtnDepositCommission(int amount1){
        if(amount1>= 500 && amount1 <=25000)
                        commission =0;
                    else if(amount1<=5000)
                          commission = 0;
                    else if(amount1<=15000)
                          commission = 285;
                    else if(amount1<=30000)
                          commission = 285;
                    else if(amount1<=45000)
                          commission = 285;
                    else if(amount1<=60000)
                          commission = 285;
                    else if(amount1<=125000)
                          commission = 440;
                    else if(amount1<=250000)
                          commission = 520;
                    else if(amount1<=500000)
                          commission = 850;
                    else if(amount1<=1000000)
                          commission = 2500;
                    else if(amount1<=2000000)
                          commission = 4500;
                    else if(amount1<=4000000)
                          commission = 8000;
        return commission;
    }
    public int mtnWithdrawCommission(int amount1){
        if(amount1>= 500 && amount1 <=25000)
                        commission =100;
                    else if(amount1<=5000)
                          commission = 125;
                    else if(amount1<=15000)
                          commission = 450;
                    else if(amount1<=30000)
                          commission = 500;
                    else if(amount1<=45000)
                          commission = 525;
                    else if(amount1<=60000)
                          commission = 575;
                    else if(amount1<=125000)
                          commission = 700;
                    else if(amount1<=250000)
                          commission = 1300;
                    else if(amount1<=500000)
                          commission = 2600;
                    else if(amount1<=1000000)
                          commission = 5000;
                    else if(amount1<=2000000)
                          commission = 7500;
                    else if(amount1<=4000000)
                          commission = 12500;
        return commission;
    }
    //calculate airtel commission
    public int airtelDepositCommission(int amount1){
        if(amount1>= 500 && amount1 <=25000)
                        commission =150;
                    else if(amount1<=5000)
                          commission = 150;
                    else if(amount1<=15000)
                          commission = 285;
                    else if(amount1<=30000)
                          commission = 285;
                    else if(amount1<=45000)
                          commission = 285;
                    else if(amount1<=60000)
                          commission = 285;
                    else if(amount1<=125000)
                          commission = 440;
                    else if(amount1<=250000)
                          commission = 520;
                    else if(amount1<=500000)
                          commission = 850;
                    else if(amount1<=1000000)
                          commission = 2500;
                    else if(amount1<=2000000)
                          commission = 4500;
                    else if(amount1<=3000000)
                          commission = 8000;
                   else if(amount1<=4000000)
                          commission = 8000;
                   else if(amount1<=5000000)
                          commission = 9000;
        return commission;
    }
    public int airtelWithdrawCommission(int amount1){
        if(amount1>= 500 && amount1 <=25000)
                        commission =100;
                    else if(amount1<=5000)
                          commission = 125;
                    else if(amount1<=15000)
                          commission = 450;
                    else if(amount1<=30000)
                          commission = 500;
                    else if(amount1<=45000)
                          commission = 525;
                    else if(amount1<=60000)
                          commission = 575;
                    else if(amount1<=125000)
                          commission = 700;
                    else if(amount1<=250000)
                          commission = 1300;
                    else if(amount1<=500000)
                          commission = 2600;
                    else if(amount1<=1000000)
                          commission = 5000;
                    else if(amount1<=2000000)
                          commission = 7500;
                    else if(amount1<=3000000)
                          commission = 12500;
                    else if(amount1<=4000000)
                          commission = 12500;
                    else if(amount1<=5000000)
                          commission = 15000;
        return commission;
    }
}