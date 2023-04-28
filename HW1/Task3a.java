
import java.util.Scanner;

public class Task3a {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int ans = 0;

        //---------------write your code BELOW this line only!--------------
        ans=1;
        int n=scanner.nextInt();
        for (int i=0 ; i<n ; i++)
        {
        	ans= ans*2;
        }
      

        //---------------write your code ABOVE this line only!--------------
        System.out.println(ans);
		scanner.close();
    }
}