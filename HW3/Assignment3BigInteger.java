
import java.math.BigInteger;
import java.util.Random;


class Assignment3BigInteger{


	   public static void main(String[] args) {
	   }
	
	   //assumes n!=null
	   //Task 1.1
    public static BigInteger sumSmaller(BigInteger n){
        BigInteger sum =  new BigInteger("0");
              for (int i = 0; i < n.intValue() ; i++) {
        	String tempi=""+i+""; //'tempi' gets the string value of i
        	BigInteger num=new BigInteger(tempi);
			sum=sum.add(num);
		}
        return sum;
    }

    //assumes n>=0
    //Task 1.2
    public static void printRandoms(int n){
         	Random p=new Random();
    	for (int i = 0; i < n; i++) {
			System.out.println(p.nextInt());
		}
    }

    //assumes n!=null and n>=0 
    //Task 1.3
    public static boolean isPrime(BigInteger n){
    	boolean ans= true;
    	if(n.intValue()<2)
    		ans=false;
    	else 
    		for (int i = 2; i*i<=n.intValue() & ans; i++) {
    			String tempi=""+i+""; //'tempi' gets the string value of i
    			BigInteger iindex=new BigInteger(tempi);
    			if((n.mod(iindex)).equals(BigInteger.ZERO)) //checks if there is a number that divides n 
    				ans=false;
    		}
    	return ans;
    }
    //assumes n>1
    //Task 1.4
    public static BigInteger randomPrime(int n){
    	Random p=new Random();
    	BigInteger randBig = new BigInteger(n, p);
    	while(!isPrime(randBig)) // lottery bigintegers until their value is prime
    		 randBig = new BigInteger(n, p);
    		return randBig;
    }

}