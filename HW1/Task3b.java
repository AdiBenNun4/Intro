
import java.util.Scanner;


public class Task3b {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int ans = 0;

        //---------------write your code BELOW this line only!--------------

        int n= scanner.nextInt();
        int k= scanner.nextInt();
        ans=1;
        for (int i=0 ; i<n ; i++)
        {
        	ans= ((ans*(2%k)) % k);
        }
        
        //---------------write your code ABOVE this line only!--------------

        System.out.println(ans);
		scanner.close();
    }
}