import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;
import java.text.*;
import javax.swing.JOptionPane;
public class testing
{
    public static void main(String [] args) throws Exception 
    {
        Queue <Plan> planQ = new Queue();
        Customer c = new Customer("kamal","1234","kamal123","010111");
        Plan p = new Plan(c,"prepaid",0,24, 23,0,1);
        planQ.enqueue(p);
        System.out.println(planQ);
    }
}