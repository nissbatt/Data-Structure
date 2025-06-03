import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;
import java.text.*;
import javax.swing.JOptionPane;
public class LinkedListPlan{
    public static void main(String args[])throws Exception{
        try{
            Scanner sc = new Scanner(System.in);
            FileReader fr = new FileReader("planList.txt");
            BufferedReader br = new BufferedReader(fr);
            sc.useDelimiter("\n");
            String indata;
            DecimalFormat df = new DecimalFormat("0.00");
            String name, id, email, phoneNum, type;
            double postCost, preCost, bill;
            int pType, postPeriod;
            
            JOptionPane.showMessageDialog(null,"Hi, Welcome to U-mobile Service Centre!","U-mobile",1);
            
            //insert data from the front and back
            LinkedList <Plan> planLL = new LinkedList();
            while((indata = br.readLine()) != null){
                StringTokenizer st = new StringTokenizer(indata,";");
    
                name = st.nextToken();
                id = st.nextToken();
                email = st.nextToken();
                phoneNum = st.nextToken();
                type = st.nextToken();
                postPeriod = Integer.parseInt(st.nextToken());
                bill = Double.parseDouble(st.nextToken());
                preCost = Double.parseDouble(st.nextToken());
                postCost = Double.parseDouble(st.nextToken());
                pType = Integer.parseInt(st.nextToken());
                
                Customer c = new Customer(name, id, email, phoneNum);
                Plan p = new Plan(c, type, postPeriod, bill, preCost, postCost, pType);
                planLL.addFirst(p);
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
                    System.out.print("Insert pospaid cost for the past months: RM ");
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
                planLL.addLast(p);
            }
            
            //split cust prepaid postpaid
            LinkedList <Plan> postpaidLL = new LinkedList();
            LinkedList <Plan> prepaidLL =new LinkedList();
            Plan p = planLL.getFirst();
            while(p!=null)
            {
                if(p.getPlanType().equalsIgnoreCase("postpaid")){
                    postpaidLL.addLast(p);
                }else if(p.getPlanType().equalsIgnoreCase("prepaid")){
                    prepaidLL.addLast(p);
                }
                p = planLL.getNext();
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
            p = postpaidLL.getFirst();
            while(p!=null)
            {
                if(p.getPlanType().equalsIgnoreCase("postpaid")){
                    countPO++;
                    System.out.println(p.toString());
                }
                p = postpaidLL.getNext();
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

            int countPR = 0;
            p = prepaidLL.getFirst();
            while(p!=null)
            {
                if(p.getPlanType().equalsIgnoreCase("prepaid")){
                    countPR++;
                    System.out.println(p.toString());
                }
                p = prepaidLL.getNext();
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("**Total Prepaid Customer: " + countPR);
            System.out.println();
            
            //search for customer name and display customer detail who didn't pay the bill for this month
            //search customer name
            System.out.print("\nEnter customer phone number to search (e.g; 01x-xxxxxxxx):");
            String search = sc.next();
                
            p = planLL.getFirst();
            boolean found = false;
            String detailSearch = null;
            while(p!=null)
            {
                Customer c = p.getCustomer();
                if(c.getCustomerPhoneNum().equalsIgnoreCase(search))
                {
                    found = true;
                    detailSearch = p.toString();
                    p = null;
                }else{
                    p = planLL.getNext();
                }
            }
            if(!found)
                System.out.println("Customer's phone number not found");
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
            p = planLL.getFirst();
            while(p!=null)
            {
                if(p.getBillCost() > 0.0){
                    System.out.println(p.toString());
                }
                p = planLL.getNext();
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            //remove inactive acc for postpaid after 3 months 
            LinkedList <Plan> inactive = new LinkedList();
            p = postpaidLL.getFirst();
            while(p!=null){
                if(p.getBillCost() >= 100.00 && p.getPostPeriod() >= 3){
                    inactive.addLast(p);
                }
                p = postpaidLL.getNext();
            }
            
            p = inactive.getFirst();
            if(p!=null){
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.print("====================================================================================================================================================================================");
                System.out.print("\n\t\t\t\t\tList of customer with inactive number");
                System.out.println("\n====================================================================================================================================================================================");
                System.out.println(String.format("%-20s %-15s %-25s %-15s %-12s %-22s %-16s %-16s %-16s %-5s",
                                                 "Name","ID","Email","Phone Number","Plan Type","Postpaid Period","Bill Cost","Prepaid Cost","Postpaid Cost","Payment Type"));
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");      
                while(p!=null){
                    System.out.println(p.toString());
                    p = inactive.getNext();
                }
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            }else
                System.out.println("\nThere's no customer with inactive number.\n*** Inactive List is Empty");
            
            //calculate both profit
            p = postpaidLL.getFirst();
            double totalBills = 0.0;
            double totalPostpaid = 0.0;
            double totPostCost = 0.0;
            int countPostpaid = 0;
            while(p!=null){
                countPostpaid++;
                totalBills += p.getBillCost();
                totPostCost += p.getPostCost();
                p = postpaidLL.getNext();
            }
            totalPostpaid = (countPostpaid * 10) + totPostCost;
            
            //total bcs org beli no slalu rm 10
            p = prepaidLL.getFirst();
            double totalPrepaid = 0.0;
            double totPreCost = 0.0;
            int countPrepaid = 0;
            while(p!=null){
                countPrepaid++;
                totPreCost += p.getPreCost();
                p = prepaidLL.getNext();
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