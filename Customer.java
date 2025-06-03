public class Customer
{
    private String customerName;
    private String customerID;
    private String customerEmail;
    private String customerPhoneNum;
    
    public Customer(String name, String id, String email, String phoneNum)
    {
        customerName = name;
        customerID = id;
        customerEmail = email;
        customerPhoneNum = phoneNum;
    }
    
    public String getCustomerName(){return customerName;}
    public String getCustomerID(){return customerID;}
    public String getCustomerEmail(){return customerEmail;}
    public String getCustomerPhoneNum(){return customerPhoneNum;}
    
    public String toString()
    {
        return String.format("%-20s %-15s %-25s %-17s ",customerName, customerID, customerEmail, customerPhoneNum);
    }
}
