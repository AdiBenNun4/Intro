import java.security.DomainCombiner;

import javax.sound.midi.VoiceStatus;

class Change{
	public static void main (String[] args) {
	}
	
	//assumes coins!=null and sorted, n>=0
    //Task 2.1
    public static boolean change(int [] coins, int n){
        boolean ans = false;
        int lastindex=-1; //the index of the last value in the array that is smaller than n
        for (int i = 0; i < coins.length; i++)
			if(coins[i]<=n)
				lastindex++;
		
        ans= change(coins, n, lastindex);
       	
        return ans;
    }
    //assisted function to Task 2.1
	//assumes coins!=null and sorted, n>=0, lastindex>=0 
    public static boolean change(int [] coins, int n, int lastindex) {
    	boolean output;

    	if(lastindex<0 | (lastindex==0 & n<coins[0] & n!=0)) //false if:1- we went on all the array 
    		output=false;//2-we got to the last value and it's bigger than the left n (and the n isn't 0)
    	else if (n==0) //true if n got to 0 after subtract the coins in the array
    		output=true;
    	else if(coins[lastindex]>n) //if the lastindex is bigger than n it must not divide by it, so we move to the next index
    		output=change(coins, n, lastindex-1);
    	else //subtract the last coin from n
    		output=change(coins, n-coins[lastindex], lastindex);

    	return output;
    }

    //assumes coins!=null and sorted, n>=0, numofCoinsToUse>=0 
    //Task 2.2
    public static boolean changeLimited(int[] coins, int n, int numOfCoinsToUse){
    	boolean ans ;
    	 int lastindex=-1; //the index of the last value in the array that is smaller than n
         for (int i = 0; i < coins.length; i++)
 			if(coins[i]<=n)
 				lastindex++;
         ans=changeLimited(coins,n, lastindex, numOfCoinsToUse);
         
    	return ans;
    }
    
    //assumes coins!=null and sorted, n>=0, numofCoinsToUse>=0 
    //assisted function to Task 2.2
    public static boolean changeLimited(int[] coins, int n,int lastindex, int numOfCoinsToUse){
    	    	boolean output;
    
    	if(lastindex<0 | (lastindex==0 & n<coins[0] & n!=0) | numOfCoinsToUse<0) //the terms as Task 2.1+if we don't have more coins
    		output=false;
    	else if (n==0) 
    		output=true;
    	else if(coins[lastindex]>n) 
    		output=changeLimited(coins, n, lastindex-1,numOfCoinsToUse);
    	else 
    		output=changeLimited(coins, n-coins[lastindex], lastindex,numOfCoinsToUse-1); //for every coin use we subtract one coin
    		
    	return output;
    }
    
    //Task 2.3
    //assumes coins!=null and sorted, n>=0, numofCoinsToUse>=0 
    public static void printChangeLimited(int[] coins, int n, int numOfCoinsToUse){
    	 int lastindex=-1;  //the index of the last value in the array that is smaller than n
         for (int i = 0; i < coins.length; i++)
 			if(coins[i]<=n)
 				lastindex++;
         
       String ans=  printChangeLimited(coins,n, lastindex, numOfCoinsToUse,"");
       if(ans.length()>0) //if there is an answer - print it without the last char ','
    	   System.out.println(ans.substring(0, ans.length()-1));
       else
    	   System.out.println(ans);
    }

    //assisted function to Task 2.3
    //assumes coins!=null and sorted, n>=0, numofCoinsToUse>=0 
    public static String printChangeLimited(int[] coins, int n,int lastindex, int numOfCoinsToUse, String s){    

    	String output;
    	if(lastindex<0 | (lastindex==0 & n<coins[0] & n!=0) | numOfCoinsToUse<0) 
    		output=""; //if there is no solution
    	else if (n==0) //if there is a solution' save the s in output
    		output= s;
    	else if(coins[lastindex]>n) 
    		output=printChangeLimited(coins, n, lastindex-1,numOfCoinsToUse,s);
    	else 
    		output= printChangeLimited(coins, n-coins[lastindex], lastindex,numOfCoinsToUse-1,coins[lastindex]+","+s); 
    	
    	return output;
   }
    
    
    //Task 2.4
    //assumes coins!=null and sorted, n>=0, numofCoinsToUse>=0 
    public static int countChangeLimited(int[] coins, int n, int numOfCoinsToUse){ 	
   	     
    	return countChangeLimited(coins,n,numOfCoinsToUse,0,0);
       
    }
    //assisted function to Task 2.4
    public static int countChangeLimited(int[] coins, int n, int numOfCoinsToUse ,int index,int sum){
    	int output=0;
    	if(n==0 & numOfCoinsToUse>=0 ) {//if there is solution
    		if(sum==0) 
    			output=0;
    		else 
    			output=1;
    	}
    	else if((numOfCoinsToUse<=0 & n>0) | (index==coins.length)) //if there isn't solution
    		output=0;
    	else {
    		if(coins[index]<=n & numOfCoinsToUse>0) {
    			output=countChangeLimited(coins, n-coins[index], numOfCoinsToUse-1, index, sum=sum+1)+ //use the coin
    					countChangeLimited(coins, n,numOfCoinsToUse,index=index+1, sum); //don't we use the coin
    		}
    	}
    	return output;
    }

  //Task 2.5
    //assumes coins!=null and sorted, n>=0, numofCoinsToUse>=0 
    public static void printAllChangeLimited(int[] coins, int n, int numOfCoinsToUse) {
    	printAllChangeLimited(coins, n, 0, numOfCoinsToUse, "") ; 
    }
    //assisted function to Task 2.5
    //assumes coins!=null and sorted, n>=0, numofCoinsToUse>=0 ,s=""
    public static void printAllChangeLimited(int[] coins, int n,int index, int numOfCoinsToUse, String s){  
    	if(n==0 & numOfCoinsToUse>=0 ) {//if there is solution
    		if(s.length()==0) 
    			System.out.print(s);
    		else 
    			System.out.println(s.substring(0, s.length()-1));
    	}
    	else if((numOfCoinsToUse<=0 & n>0) | (index==coins.length)) //if there isn't solution
    		System.out.print("");
    	else {
    		if(coins[index]<=n & numOfCoinsToUse>0) {
    			printAllChangeLimited(coins, n-coins[index], index, numOfCoinsToUse-1, s+coins[index]+","); //we use the coin
    			printAllChangeLimited(coins, n, index=index+1, numOfCoinsToUse, s); //don't we use the coin
    		}
    	}
    }

    

}
