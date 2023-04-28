import java.util.Iterator;

public class NumericalString {


	   public static void main(String[] args) {
		 
	   }
	
	//Task 3.1
	public static boolean legalNumericString(String s, int b) {
		boolean ans = true;
		if (b<2 | b>10) //checks for correct b input
			throw new IllegalArgumentException("illegal input");
		if(s==null || s.length()==0) //if s is null don't check the length
			ans=false;
		else if(s.length()!=1 & s.charAt(s.length()-1)=='0')// if the last digit is 0 and the string is not "0"
			ans=false;

		for (int i = 0; i < s.length() & ans; i++) { //checks that every digit belongs to the base
			if(!(toInt(s.charAt(i))<=b) | !(toInt(s.charAt(i))>=0))
				ans=false;
		}
		return ans;
	}
	//assisting function
	public static int toInt(char c) {
		return "0123456789".indexOf(c);
	}

	//Task 3.2
	public static String decimalIncrement(String s) {
		if(!legalNumericString(s, 10))
			throw new IllegalArgumentException("you need to enter number of base 10");

		return decimalIncrementh(s) ;
	}

	//assisting function to Task 3.2
	public static String decimalIncrementh(String s) {
		String output;
		if (s.length()==0)//length is 0
			output="";
		if(s.charAt(0)=='9') { //if LSB is 9 we change it and add one to the next digit
			if(s.length()==1) //if the MSB is 9 we change it to 01
				output="01" ;
			else 
				output= "0"+decimalIncrementh(s.substring(1)) ; //0 instead of 9 and add 1 to the next digit
		}
		else 
			if(s.length()==1) //if 9 is not LSB, add 1 to the LSB and copy the other digits
				output=""+(toInt(s.charAt(0))+1) ;
			else 
				output= decimalIncrementh(s.substring(0,s.length()-1)) + toInt(s.charAt(s.length()-1));

		return output;

	}


	//Task 3.3
	public static String decimalDouble(String s) {
		if(!legalNumericString(s, 10))
			throw new IllegalArgumentException("you need to enter number of base 10");
		//    	
		return decimalDoubleH(s) ;
	}

	//assisting function to Task 3.3
	//assumes s is legal string
	public static String decimalDoubleH(String s) {
		String output;
		if(s.length()==0)
			output="";
		else if(s.length()==1) {
			if(toInt(s.charAt(0))<5) //if the digit smaller than 5 we the mult is 1 digit
				output=""+toInt(s.charAt(0))*2;
			else //switch the digits if the last digit bigger than 4
				output= ""+(""+toInt(s.charAt(0))*2).charAt(1)+(""+toInt(s.charAt(0))*2).charAt(0); 
		}
		else if(toInt(s.charAt(0))<5) 
			output=toInt(s.charAt(0))*2+decimalDoubleH(s.substring(1)); //multiply the LSB digit and do the function on the remain string
		else
			output=(""+(toInt(s.charAt(0))*2)).charAt(1)+decimalIncrement(decimalDoubleH(s.substring(1))); //multiply the LSB digit, take the LSB of it and add one to the multiply of the remain string
		return output;
	}

	//Task 3.4
	public static String binary2Decimal(String s) {
		if(!legalNumericString(s, 2))
			throw new IllegalArgumentException("you need to enter number of base 2");

		return binary2Decimalh(s,"0");
	}

	//assisting function to Task 3.4
	//assumes s is legal string
	//using the algorithm of doubling method that is show in the website: https://www.cuemath.com/numbers/binary-to-decimal/ 
	public static String binary2Decimalh(String s,String acc) {
		String output;
		if (s.length()==0)
			output=acc;
		else if(toInt(s.charAt(s.length()-1))==1) //duplicate the digit and add the digit 1, continue with the remain string
			output= binary2Decimalh(s.substring(0,s.length()-1),decimalIncrement(decimalDouble(acc)));
		else //duplicate the digit, continue with the remain string
			output= binary2Decimalh(s.substring(0,s.length()-1),decimalDouble(acc));

		return output;
	}


}
