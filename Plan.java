import java.text.*;
public class Plan
{
    DecimalFormat df = new DecimalFormat("0.00");
    private Customer customer;
    private String planType;           //prepaid || postpaid
    private int postPeriod;            //means bape bulan dia tak bayar bill
    private double billCost;           //bill cost for postpaid means yg belum bayar
    private double postpaidCost;       //postpaid cost for postpaid means yg dh bayar
    private double prepaidCost;        //prepaid cost for prepaid means yg dh bayar
    private int paymentType;           //1-eWallet || 2-credit card || 3-online banking 
    
    public Plan(Customer c, String type, int pPo, double bCost, double preCost,double postCost, int pType)
    {
        customer = c;
        planType = type;
        postPeriod = pPo;
        billCost = bCost;
        prepaidCost = preCost;
        postpaidCost = postCost;
        paymentType = pType;
    }
    
    public Customer getCustomer(){return customer;}
    public String getPlanType(){return planType;}
    public int getPostPeriod(){return postPeriod;}
    public double getBillCost(){return billCost;}
    public double getPreCost(){return prepaidCost;}
    public double getPostCost(){return postpaidCost;}
    public int getPaymentType(){return paymentType;}
    
    public String toString()
    {
        return customer.toString() +
               String.format("%-18s %-16s %-16s %-16s %-16s %-8s", 
               planType, postPeriod, df.format(billCost), df.format(prepaidCost), df.format(postpaidCost), paymentType);
    }
}
