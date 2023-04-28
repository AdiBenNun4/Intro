
import java.util.Scanner;

public class Task4a {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean ans = true;

        //---------------write your code BELOW this line only!--------------

        int n= scanner.nextInt();
        for (int i=2 ; i*i<=n & ans==true ; i++)
        {
        	if (n%i ==0)
        		ans=false;
        }
        
        //---------------write your code ABOVE this line only!--------------

        System.out.println(ans);
		scanner.close();
    }
}