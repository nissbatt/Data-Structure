import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;
import java.text.*;
import javax.swing.JOptionPane;
public class QueuePlan
{
    public static void main(String [] args) throws Exception 
    {
        try
        {
            Scanner sc = new Scanner(System.in);
            FileReader fr = new FileReader("planList.txt");
            BufferedReader br = new BufferedReader(fr);
            sc.useDelimiter("\n");
            String indata;
            DecimalFormat df = new DecimalFormat("0.00");
            
            
            JOptionPane.showMessageDialog(null,"Hi, Welcome to U-mobile Service Centre!","U-mobile",1);
            
            //insert data
            Queue <Plan> planQ = new Queue();
            Queue <Plan> tempQ = new Queue();
            while((indata = br.readLine()) != null){
                StringTokenizer st = new StringTokenizer(indata,";");

                String name = st.nextToken();
                String id = st.nextToken();
                String email = st.nextToken();
                String phoneNum = st.nextToken();
                String type = st.nextToken();
                int postPeriod = Integer.parseInt(st.nextToken());
                double bill = Double.parseDouble(st.nextToken());
                double preCost = Double.parseDouble(st.nextToken());
                double postCost = Double.parseDouble(st.nextToken());
                int pType = Integer.parseInt(st.nextToken());

                Customer c = new Customer(name, id, email, phoneNum);
                Plan p = new Plan(c, type, postPeriod, bill, preCost, postCost, pType);
                planQ.enqueue(p);
            }

            String response = JOptionPane.showInputDialog("Enter number of customer for this day");
            int num = Integer.parseInt(response);
            for(int i = 0; i < num; i++){
                double newBill = 0.0;
                double newPostCost = 0.0;
                int newPostPeriod = 0;
                double newPreCost = 0.0;

                System.out.println();
                System.out.print("\nInsert customer's name: ");
                String newName = sc.next();
                System.out.print("Insert customer's ID: ");
                String newID = sc.next();
                System.out.print("Insert customer's email (e.g; abc@gmail.com): ");
                String newEmail = sc.next();
                System.out.print("Insert customer's phone number (e.g; 01x-xxxxxxxx): ");
                String newPhoneNum = sc.next();
                System.out.println("postpaid || prepaid");                
                System.out.print("Insert customer's plan type: ");
                String newType = sc.next();
                if(newType.equalsIgnoreCase("postpaid")){
                    System.out.print("Insert bill cost for this month: RM ");
                    newBill = sc.nextDouble();
                    System.out.print("Insert postpaid cost for the past months: RM ");
                    newPostCost = sc.nextDouble();
                    System.out.print("Insert bill's period: ");
                    newPostPeriod = sc.nextInt();
                }else{
                    System.out.print("Insert prepaid cost for the past months: RM ");
                    newPreCost = sc.nextDouble();
                }
                System.out.println("1-eWallet || 2-credit card || 3-online banking");
                System.out.print("Insert payment method: ");
                int newPType = sc.nextInt();

                Customer c = new Customer(newName, newID, newEmail, newPhoneNum);
                Plan p = new Plan(c, newType, newPostPeriod, newBill, newPreCost, newPostCost, newPType);
                planQ.enqueue(p);
            }
            
            //split cust prepaid postpaid
            Queue <Plan> postpaidQ = new Queue();
            Queue <Plan> prepaidQ =new Queue();
            while(!planQ.isEmpty())
            {
                Plan p = planQ.dequeue();
                if(p.getPlanType().equalsIgnoreCase("postpaid")){
                    postpaidQ.enqueue(p);
                }else if(p.getPlanType().equalsIgnoreCase("prepaid")){
                    prepaidQ.enqueue(p);
                }
                tempQ.enqueue(p);
            }
            
            System.out.println(prepaidQ.toString());
            
            //restore data
            while(!tempQ.isEmpty())
            {
                planQ.enqueue(tempQ.dequeue());
            }

            //show status (show total of customer in list)
            System.out.println();
            System.out.print("====================================================================================================================================================================================");
            System.out.println("\n\t\t\t\t\t\t\tPostpaid Customer");
            System.out.println("====================================================================================================================================================================================");
            System.out.println(String.format("%-20s %-15s %-25s %-15s %-12s %-22s %-16s %-16s %-16s %-5s",
                    "Name","ID","Email","Phone Number","Plan Type","Postpaid Period","Bill Cost","Prepaid Cost","Postpaid Cost","Payment Type"));
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            int countPO = 0;
            int countPR = 0;
            Plan p;
            while(!postpaidQ.isEmpty())
            {
                p = postpaidQ.dequeue();
                if(p.getPlanType().equalsIgnoreCase("postpaid"))
                {
                    countPO++;
                    System.out.println(p.toString());
                }
                tempQ.enqueue(p);
            }
            //restore data
            while(!tempQ.isEmpty())
            {
                postpaidQ.enqueue(tempQ.dequeue());
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("**Total Postpaid Customer: " + countPO);
            System.out.println();
            System.out.println();

            System.out.print("====================================================================================================================================================================================");
            System.out.println("\n\t\t\t\t\t\t\tPrepaid Customer");
            System.out.println("====================================================================================================================================================================================");
            System.out.println(String.format("%-20s %-15s %-25s %-15s %-12s %-22s %-16s %-16s %-16s %-5s",
                    "Name","ID","Email","Phone Number","Plan Type","Postpaid Period","Bill Cost","Prepaid Cost","Postpaid Cost","Payment Type"));
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while(!prepaidQ.isEmpty())
            {
                p = prepaidQ.dequeue();
                if(p.getPlanType().equalsIgnoreCase("prepaid"))
                {
                    countPR++;
                    System.out.println(p.toString());
                }
                tempQ.enqueue(p);
            }
            //restore data
            while(!tempQ.isEmpty())
            {
                prepaidQ.enqueue(tempQ.dequeue());
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("**Total Prepaid Customer: " + countPR);
            System.out.println();

            //search for customer name and display customer detail who didn't pay the bill for this month
            //search customer name
            System.out.print("\nEnter customer phone number to search (e.g; 01x-xxxxxxxx):");
            String search = sc.next();

            boolean found = false;
            String detailSearch = null;
            while(!planQ.isEmpty())
            {
                p = planQ.dequeue();
                Customer c = p.getCustomer();
                if(c.getCustomerPhoneNum().equalsIgnoreCase(search))
                {
                    found = true;
                    detailSearch = p.toString();
                    p = null;
                }else{
                    tempQ.enqueue(p);
                }
            }
            //restore data
            while(!tempQ.isEmpty())
            {
                planQ.enqueue(tempQ.dequeue());
            }
            if(!found)
                System.out.println("Customer's Phone Number not found");
            else
                System.out.println(detailSearch);

            //display cust who did'nt pay the bills
            System.out.println();

            System.out.print("====================================================================================================================================================================================");
            System.out.print("\n\t\t\t\t\tList of customer who didn't pay the bills for this month");
            System.out.println("\n====================================================================================================================================================================================");
            System.out.println(String.format("%-20s %-15s %-25s %-15s %-12s %-22s %-16s %-16s %-16s %-5s",
                    "Name","ID","Email","Phone Number","Plan Type","Postpaid Period","Bill Cost","Prepaid Cost","Postpaid Cost","Payment Type"));
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");            

            while(!planQ.isEmpty())
            {
                p = planQ.dequeue();
                if(p.getBillCost() > 0.0)
                    System.out.println(p.toString());
                tempQ.enqueue(p);
            }
            //restore data
            while(!tempQ.isEmpty())
            {
                planQ.enqueue(tempQ.dequeue());
            }

            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");            
            
            //remove inactive acc for postpaid after 3 months 
            Queue <Plan> inactiveQ = new Queue();
            while(!postpaidQ.isEmpty())
            {
                p = postpaidQ.dequeue();
                if(p.getBillCost() >= 100.00 && p.getPostPeriod() >= 3){
                    inactiveQ.enqueue(p);
                }
                tempQ.enqueue(p);
            }
            //restore data
            while(!tempQ.isEmpty())
            {
                postpaidQ.enqueue(tempQ.dequeue());
            }

            if(!inactiveQ.isEmpty()){
                
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.print("====================================================================================================================================================================================");
                System.out.print("\n\t\t\t\t\tList of customer with inactive number");
                System.out.println("\n====================================================================================================================================================================================");
                System.out.println(String.format("%-20s %-15s %-25s %-15s %-12s %-22s %-16s %-16s %-16s %-5s",
                        "Name","ID","Email","Phone Number","Plan Type","Postpaid Period","Bill Cost","Prepaid Cost","Postpaid Cost","Payment Type"));
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");          
                while(!inactiveQ.isEmpty()){
                    p = inactiveQ.dequeue();
                    System.out.println(p.toString());
                    tempQ.enqueue(p);
                }
                //restore data
                while(!tempQ.isEmpty())
                {
                    inactiveQ.enqueue(tempQ.dequeue());
                }
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");                            
            }else
                System.out.println("\nThere's no customer with inactive number.\n*** Inactive List is Empty");

            //restore data
            while(!tempQ.isEmpty())
            {
                inactiveQ.enqueue(tempQ.dequeue());
            }

            //calculate both profit
            double totalBills = 0.0;
            double totalPostpaid = 0.0;
            double totPostCost = 0.0;
            int countPostpaid = 0;
            while(!postpaidQ.isEmpty()){
                p = postpaidQ.dequeue();
                countPostpaid++;
                totalBills += p.getBillCost();
                totPostCost += p.getPostCost();
                tempQ.enqueue(p);
            }
            //restore data
            while(!tempQ.isEmpty())
            {
                postpaidQ.enqueue(tempQ.dequeue());
            }
            totalPostpaid = (countPostpaid * 10) + totPostCost;

            //total bcs org beli no slalu rm 10
            double totalPrepaid = 0.0;
            double totPreCost = 0.0;
            int countPrepaid = 0;
            while(!prepaidQ.isEmpty()){
                p = prepaidQ.dequeue();
                countPrepaid++;
                totPreCost += p.getPreCost();
                tempQ.enqueue(p);
            }
            //restore data
            while(!tempQ.isEmpty())
            {
                prepaidQ.enqueue(tempQ.dequeue());
            }
            totalPrepaid = (countPrepaid * 10) + totPreCost;

            double totalProfit = totalPostpaid + totalPrepaid;
            System.out.println();
            System.out.println("************************************************************************************************************************************************************************************");
            System.out.println("Total prepaid customer: " + countPrepaid +
                "\nTotal postpaid customer: " + countPostpaid +
                "\nTotal Bills that customers don't pay: RM " + df.format(totalBills) +
                "\nTotal profit: RM " + df.format(totalProfit));
            br.close();
        }
        catch (FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        }
        catch (IOException io){
            System.out.println(io.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
