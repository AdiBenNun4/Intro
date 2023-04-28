
import java.util.Scanner;

public class Task4b {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int ans = 0;

        //---------------write your code BELOW this line only!--------------

        int n= scanner.nextInt();
        boolean flag=true; // true if its a prime number
                
        for (int i=n ; n>1 ; i--) //run all the numbers from n down
        {
        	flag=true;
        	 for (int j=2 ; j*j<=n & flag==true; j++) //check is prime
             {
             	if (n%j ==0)
             		flag=false;
             }
            if (flag==true) 
            	ans++;
            n--; 
        }
       
        //---------------write your code ABOVE this line only!--------------

        System.out.println(ans);    
		scanner.close();    
    }
}