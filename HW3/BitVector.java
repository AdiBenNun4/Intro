public class BitVector {
    private Bit[] bits;
    
    //Task 4.4
    public BitVector(String s) {
    	if(NumericalString.legalNumericString(s,2)) { //checks if its a legal string
    		bits=new Bit [s.length()];
    		for(int i=0 ; i<bits.length ; i++) {
    			if(s.charAt(i)=='0')
    				bits[i]=new Bit(false); //false if 0
    			else
    				bits[i]=new Bit(true); //true if 1
    		}
    			
    	}
    }
    
    //Task 4.5
    public String toString() {
        String ans = "";
        for (int i = 0; i < bits.length; i++) //insert to ans the binary number that bits has
			ans=ans+bits[i].toInt();
		
        ans=NumericalString.binary2Decimal(ans); //turn the binary number to decimal
        String flipped="";
        for (int i = ans.length()-1 ; i >= 0 ; i--)  //flip the decimal string
			flipped=flipped+ans.charAt(i);
		        
        return flipped;
    }

}
