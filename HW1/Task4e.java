
import java.util.Scanner;

public class Task4e {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean ans1 = true;
        int ans2 = 0;

        //---------------write your code BELOW this line only!--------------

        int n=scanner.nextInt();
        int b=scanner.nextInt();
        int s=scanner.nextInt();
        int d=scanner.nextInt();
        ans1=false; // the conditions happens
        int x=b%n; //if d=1
        
        for (int j=1 ; j<d ; j++) //check the first condition except if d=1
        {
        	x=(x*(b%n))%n;
        }
        //in the end of the loop x=(b^d)%n
        if (x==1 | x==n-1) //check the first condition and the second if s=1
        	ans1=true;
        

        for (int i=1 ; i<s & ans1==false; i++) //check the second condition
        {
        	if (((x%n)*(x%n))%n==n-1)
        		ans1=true;
        	else
        		x=((x%n)*(x%n))%n; //to check i
        }
                 
        if(ans1 == false)
        	ans2=b;
        else
        	ans2=-1;
        //---------------write your code ABOVE this line only!--------------

        System.out.println(ans1);
        System.out.println(ans2);
        scanner.close();
    }
}