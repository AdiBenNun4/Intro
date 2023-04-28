
import java.util.Scanner;

public class Task4d {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int ans1 = 0;
        int ans2 = 0;

        //---------------write your code BELOW this line only!--------------
        int n= scanner.nextInt();
        n--;
        boolean flag=true;
        while (flag==true) //as long as n spite by 2
        {
        	if(n%2 ==0) // if n divided by 2
        	{
        		n=n/2; 
        		ans1++; // the power get bigger one more
        	}
        	else // if n had left without mult by 2
        		flag=false;
        }
        ans2=n; //the left number after minus 2 mult
        //---------------write your code ABOVE this line only!--------------

        System.out.println(ans1);
        System.out.println(ans2);
		scanner.close();
    }
}