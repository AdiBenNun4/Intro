
import java.util.Scanner;

public class Task4f {

    public static void main(String[] args) {
        
		Scanner scanner = new Scanner(System.in);
        boolean ans = true;

        //---------------write your code BELOW this line only!--------------

        int n=scanner.nextInt();
        int s=scanner.nextInt();
        int d=scanner.nextInt();
        int k=scanner.nextInt();
        
        int b= (int)((n-1-2+1)* Math.random()+2);

        //check if b with n satisfied the condition (*)

        boolean ans1=false; // the conditions happens
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
        		x=((x%n)*(x%n))%n; //to check the next i
        }

        if(ans1 == false)
        	ans = false;
        else
        	ans=true;

        //---------------write your code ABOVE this line only!--------------

        System.out.println(ans);
		scanner.close();
    }
}