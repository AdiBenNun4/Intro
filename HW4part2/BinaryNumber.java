/*
I, <ADI BEN NUN> (<207257486>), assert that the work I submitted is entirely my own.
I have not received any part from any other student in the class,
nor did I give parts of it for use to others.
I realize that if my work is found to contain code that is not originally my own,
 a formal case will be opened against me with the BGU disciplinary committee.
 */

import java.util.Iterator;import java.util.LinkedList;

import javax.swing.DefaultRowSorter;

public class BinaryNumber implements Comparable<BinaryNumber>{
	private static final BinaryNumber ZERO = new BinaryNumber(0);
	private static final BinaryNumber ONE  = new BinaryNumber(1);
	private BitList bits;

	// Copy constructor
	//Do not change this constructor
	public BinaryNumber(BinaryNumber number) {
		bits = new BitList(number.bits);
	}

	//Do not change this constructor
	private BinaryNumber(int i) {
		bits = new BitList();
		bits.addFirst(Bit.ZERO);
		if (i == 1)
			bits.addFirst(Bit.ONE);
		else if (i != 0)
			throw new IllegalArgumentException("This Constructor may only get either zero or one.");
	}

	//Do not change this method
	public int length() {
		return bits.size();
	}

	//Do not change this method
	public boolean isLegal() {
		return bits.isNumber() & bits.isReduced();
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.1 ================================================
	//constructor by char
	//throw IllegalArgumentException if c is not 0<=c<10
	public BinaryNumber(char c) {
		int num=toInt(c);
		if(num==-1) //if c ! 0<=c<=9
			throw new IllegalArgumentException("the char need to be decimal digit");  
		bits = new BitList();
		while(num>0) {
			if(num%2==0) 
				bits.addLast(Bit.ZERO);
			else
				bits.addLast(Bit.ONE);
			num=num/2;
		}
		bits.addLast(Bit.ZERO); //the 0 digit of positive sign

	}

	//assisting function, returns -1 if ! 0<=c<=9
	public static int toInt(char c) {
		return "0123456789".indexOf(c);
	}
	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.2 ================================================
	//return string that represent this Binary Number
	//throw RuntimeException if this is illegal
	public String toString() {
		// Do not remove or change the next two lines
		if (!isLegal()) // Do not change this line
			throw new RuntimeException("I am illegal.");// Do not change this line
		//
		String stringMSB="",output="";

		Iterator <Bit> iter = bits.iterator();
		while (iter.hasNext()) 
			stringMSB+=iter.next();

		for (int i = 0; i < stringMSB.length(); i++) //flip the string
			output=stringMSB.charAt(i)+output;

		return output;   
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.3 ================================================
	//return the BitList Bits of this
	private BitList GetBits() {
		return bits;
	}

	//checks and return true\false is this is equal to other
	public boolean equals(Object other) {
		boolean isEqual=true;
		if(!(other instanceof BinaryNumber))//other isn't binary number
			isEqual=false;
		else if (!(((BinaryNumber)other).isLegal()) | ((BinaryNumber)other).length()!=this.length()) //other isn't illegal or don't have the same length
			isEqual=false;
		else { //checks every Bit in the BitList if equal
			Iterator <Bit> iterthis = bits.iterator();
			Iterator <Bit> iterother =((BinaryNumber)other).GetBits().iterator();

			while (iterthis.hasNext() & isEqual) 
				if(!(iterthis.next()).equals( iterother.next()))
					isEqual=false;      		
		}
		return isEqual;
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.4 ================================================
	//add the element value to this
	//throw IllegalArgumentException if this or addMe is illegal
	public BinaryNumber add(BinaryNumber addMe) {
		if(!this.isLegal() | !addMe.isLegal())
			throw new IllegalArgumentException("illegal BinaryNumber");
		BinaryNumber addmetempBinaryNumber=new BinaryNumber(addMe);//copy addme
		BitList listsum=new BitList();
		//padding the shorter list if there is one
		if(this.length()>addmetempBinaryNumber.length())
			addmetempBinaryNumber.GetBits().padding(this.length());
		else if(this.length()<addmetempBinaryNumber.length())
			bits.padding(addmetempBinaryNumber.length());
		//Summaries the Binary numbers
		Iterator <Bit> iterthis = bits.iterator();
		Iterator <Bit> iteraddme =addmetempBinaryNumber.GetBits().iterator();
		Bit Cin=Bit.ZERO; //initially Cin
		while (iterthis.hasNext()) {
			Bit thisnext=iterthis.next();
			Bit addmenext=iteraddme.next();

			listsum.addLast(Bit.fullAdderSum(thisnext, addmenext, Cin));
			Cin=Bit.fullAdderCarry(thisnext, addmenext, Cin);
		}
		//treat the remain carry if both have same sign
		if(bits.getLast().equals(addMe.GetBits().getLast())) {
			if(Cin.equals(Bit.ONE))//add the cin
				listsum.addLast(Bit.ONE);
			listsum.addLast(bits.getLast());
		}

		listsum.reduce(); //reduce if needed
		BinaryNumber BNsum=new BinaryNumber(listsum);
		return BNsum;
	}
	//Constructor by bitList
	private BinaryNumber (BitList b) {
		bits=b;
	}
	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.5 ================================================
	//return the negative value of this by complete to 2 method
	//throw RuntimeException if this is illegal Binary Number
	public BinaryNumber negate() {
		if (!this.isLegal()) 
			throw new RuntimeException("illegal Binary number");
		if(this.equals(BinaryNumber.ZERO))
			return this;
		Bit signbit= bits.getLast();
		BinaryNumber BNComplement = new BinaryNumber(bits.complement());

		if(signbit.equals(Bit.ZERO)) //if it positive add 0 to the sign digit- the number turn to legal and we can add 1
			BNComplement.GetBits().addLast(Bit.ZERO); //duplicate the sign bit

		BNComplement.GetBits().reduce();
		BNComplement = BNComplement.add(BinaryNumber.ONE);
		if(signbit.equals(Bit.ZERO)) //if it positive remove the last 0 (which we added)
			BNComplement.GetBits().removeLast();

		return BNComplement;
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.6 ================================================
	//return the subtracted Binary Number (this-subtractMe) 
	//throw runtimeException if this or subtractMe element is illegal Binary Numbers
	public BinaryNumber subtract(BinaryNumber subtractMe) {
		if (!this.isLegal()|!subtractMe.isLegal()) //
			throw new RuntimeException("illegal Binary number");
		BinaryNumber tempsuBinaryNumber=new BinaryNumber(subtractMe);
		BinaryNumber tempthisBinaryNumber = new BinaryNumber(this);
		//add the negative number of subtractMe
		BinaryNumber subtracted= tempthisBinaryNumber.add(tempsuBinaryNumber.negate());
		return subtracted;
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.7 ================================================
	//returns -1 if this is negative, 1 if positive and 0 if ==0
	//throw runtimeException if this is illegal Binary Number
	public int signum() {
		if (!this.isLegal()) 
			throw new RuntimeException("illegal Binary number");
		int sign=-1; //initial as negative
		Bit last=bits.getLast(); //the last bit represent the sign (1=positive, 0=negative)
		if(this.length()==1 & last.equals(Bit.ZERO)) //if it 0
			sign=0;
		else if(last.equals(Bit.ZERO))//positive
			sign=1;

		return sign;
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.8 ================================================
	//returns -1 if this<other, 0 if equals, 1 if this>other
	//throw runtimeException if this or other is illegal Binary Numbers
	public int compareTo(BinaryNumber other) {
		if(!this.isLegal() | !other.isLegal())
			throw new IllegalArgumentException("illegal Binary Number");
		int compareans=-1,thissign=this.signum(), othersign=other.signum(); //initialized than this<other
		if(this.equals(other)) //if equals ans=0
			compareans=0;
		else if(thissign > othersign) //this is positive/0 other is negative
			compareans=1;
		else if(thissign<othersign)//this is negative/0 other is positive
			compareans=-1;
		else{ //both positive/negative 
			int thislength=this.length(),otherlength=other.length();
			if(thislength>otherlength)
				compareans=thissign; //if positive, this longer than it bigger.if negative and this longer, it smaller
			else if(thislength<otherlength)
				compareans=(-1)*thissign; //if positive, this shorter than it smaller.if negative and this shorter, it bigger
			else { //have the same length and the same sign

				Iterator<Bit> iterthis=bits.flipBitList().iterator();
				Iterator<Bit> iterother=other.GetBits().flipBitList().iterator();

				boolean foundbiggerdigit=false;
				while(iterthis.hasNext() & !foundbiggerdigit) {
					Bit thisbit=iterthis.next();
					Bit otherBit=iterother.next();
					if(!thisbit.equals(otherBit)) {
						if(thisbit.equals(Bit.ONE))
							compareans=1;
						else
							compareans=-1;
						foundbiggerdigit=true;
					}
				}
			}
		}	
		return compareans;
	}


	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.9 ================================================
	//return the int value of this BInary Number
	//throw runtimeException if this is illegal Binary Number
	public int toInt() {
		// Do not remove or change the next two lines
		if (!isLegal()) // Do not change this line
			throw new RuntimeException("I am illegal.");// Do not change this line
		int signnum=this.signum(), ans;
		Iterator<Bit> iter;
		if(signnum==-1) //if the number is negative take his positive value
			iter=this.negate().GetBits().flipBitList().iterator();
		else 
			iter=bits.flipBitList().iterator();

		ans=toIntAssist(0, iter);
		if(signnum==-1)
			ans=ans*(-1);
		return ans;
	}

	//assisting function to Task 3.9
	//using the algorithm of doubling method that is show in the website: https://www.cuemath.com/numbers/binary-to-decimal/ 
	private int toIntAssist(int sum, Iterator<Bit> iter) {
		int output;
		Bit thisBit=iter.next();
		if (!iter.hasNext())
			if(thisBit.equals(Bit.ONE))
				output=sum*2+1;
			else
				output=sum*2;
		else if(thisBit.equals(Bit.ONE)) //duplicate the sum and add the digit 1, continue with the continue with the next bit
			output= toIntAssist(sum*2+1,iter);
		else //duplicate the sum, continue with the next bit
			output= toIntAssist(sum*2,iter);
		if(output>Integer.MAX_VALUE| output<0)
			throw new RuntimeException("not in int range");

		return output;
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.10 ================================================
	//throw IllegalArgumentException if this or multiplyMe are illegal Binary Numbers
	public BinaryNumber multiply(BinaryNumber multiplyMe) {
		if(!this.isLegal() | !multiplyMe.isLegal())
			throw new IllegalArgumentException("illegal Binary Number");
		BinaryNumber copymultiplyme=new BinaryNumber(multiplyMe);
		BinaryNumber copythis=new BinaryNumber(this);
		int thissign=copythis.signum(),multsign=copymultiplyme.signum();
		BinaryNumber ans;
		if(thissign==-1) {
			if(multsign==1)  //this is negative MultMe is positive
				ans= (copythis.negate().multiplyPositive(copymultiplyme)).negate();
			else  //both negative
				ans= copythis.negate().multiplyPositive(copymultiplyme.negate());
		}
		else if (multsign==-1)//this is positive MultMe is negative
			ans=copythis.multiplyPositive(copymultiplyme.negate()).negate();
		else //both positive
			ans=copythis.multiplyPositive(copymultiplyme);
		return ans;
	}
	//use the method of long multiplying
	private BinaryNumber multiplyPositive(BinaryNumber multiplyMe) {
		BinaryNumber copymultme = new BinaryNumber(multiplyMe);
		BinaryNumber sumBitList=new BinaryNumber(ZERO);
		BinaryNumber longerBinaryNumber=this;

		Iterator<Bit> iter; //go over the shorter BinaryNumber
		if(this.length()>copymultme.length()) 
			iter=copymultme.GetBits().iterator();
		else {
			iter=bits.iterator();
			longerBinaryNumber=copymultme;
		}    	
		while(iter.hasNext()) {//if the bit is 1 add the longer number with extra bits in the first according to its place
			if(iter.next().equals(Bit.ONE)) 
				sumBitList=	sumBitList.add(longerBinaryNumber);
			longerBinaryNumber.GetBits().shiftLeft();
		}
		return sumBitList;
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.11 ================================================
	//return the divided Binary Number of this\divisor
	//throw IllegalArgumentException if this or divisor are illegal Binary Numbers
	public BinaryNumber divide(BinaryNumber divisor) {
		// Do not remove or change the next two lines
		if (divisor.equals(ZERO)) // Do not change this line
			throw new RuntimeException("Cannot divide by zero."); // Do not change this line
		if(!this.isLegal() | !divisor.isLegal())
			throw new IllegalArgumentException("illegal Binary Number");
		BinaryNumber copythis=new BinaryNumber(this);
		BinaryNumber copydivisor=new BinaryNumber(divisor);
		int thissign=copythis.signum(),divisorsign=copydivisor.signum();
		BinaryNumber flippedthis=new BinaryNumber(bits.flipBitList()), ans;
		if(thissign==-1) {
			flippedthis=new BinaryNumber(copythis.negate().GetBits().flipBitList());  
			if(divisorsign==1)  //this is negative divisor is positive
				ans= (flippedthis.dividePositive(copydivisor)).negate();
			else  //both negative
				ans= flippedthis.dividePositive(copydivisor.negate());
		}
		else if (divisorsign==-1)//this is positive divisor is negative
			ans=(flippedthis.dividePositive(copydivisor.negate())).negate();
		else //both positive
			ans=flippedthis.dividePositive(copydivisor);
		return ans;
	}

	private BinaryNumber dividePositive(BinaryNumber divisor) {
		BinaryNumber ans=new BinaryNumber(BinaryNumber.ZERO);
		BinaryNumber partialnum=new BinaryNumber(BinaryNumber.ZERO);
		Iterator<Bit> iter=bits.iterator();
		boolean founflast=false;
		while(!founflast) {
			if(!iter.hasNext())
				founflast=true;
			//reduce the number to be able to compare
			partialnum.GetBits().reduce();
			ans.GetBits().reduce();

			if(partialnum.compareTo(divisor)==1 | partialnum.equals(divisor)){//this is bigger or equal than divisor 
				ans.GetBits().addFirst (Bit.ONE); //add the bit 1 to the answer
				partialnum =partialnum.subtract(divisor);
			}
			else
				ans.GetBits().addFirst(Bit.ZERO); //add the bit 0 to the answer
			if(!founflast) //if its not the last loop
				partialnum.GetBits().addFirst(iter.next()); //add to partial number the next digit

		}    	
		return ans;


	}
	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.12 ================================================
	//constructor by string
	//throw IllegalArgumentException if the string is empty 
	public BinaryNumber(String s) {
		if(s==null | s.length()==0)
			throw new IllegalArgumentException("the string can't be null or empty");

		bits=new BitList();
		boolean isnegative=false;
		BinaryNumber BN10=BinaryNumber10();
		BinaryNumber indexformult=new BinaryNumber(1);
		BinaryNumber thisBinaryNumber=new BinaryNumber(0);

		if(s.charAt(0)=='-'){//if the number is negative
			isnegative=true;
			s=s.substring(1);
		}

		for(int i=s.length()-1; i>=0 ; i-- ) { //go over each digit, turn it to the binary number, multiply to the 2^place, and add it to this 
			BinaryNumber BNchar=new BinaryNumber(s.charAt(i));
			thisBinaryNumber= thisBinaryNumber.add(BNchar.multiply(indexformult));
			indexformult=indexformult.multiply(BN10);
		}
		if(isnegative)//the number is negative, turn it back to negative
			bits=thisBinaryNumber.negate().GetBits();
		else
			bits=thisBinaryNumber.GetBits();
	}
	//build The BN 10 (01010)
	private BinaryNumber BinaryNumber10() {
		//built the BN 10
		BitList BT10=new BitList();
		BT10.addFirst(Bit.ZERO);
		BT10.addFirst(Bit.ONE);
		BT10.addFirst(Bit.ZERO);
		BT10.addFirst(Bit.ONE);
		BT10.addFirst(Bit.ZERO);
		BinaryNumber BN10=new BinaryNumber(BT10);
		return BN10;
	}
	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.13 ================================================
	//return string that represents the int value of the Binary Number
	//throw RuntimeException if this is illegal Binary Number
	public String toIntString() {
		// Do not remove or change the next two lines
		if (!isLegal()) // Do not change this line
			throw new RuntimeException("I am illegal.");// Do not change this line
		//
		BinaryNumber thisBinaryNumber= new BinaryNumber(this);
		boolean isnegative=false;
		if(thisBinaryNumber.signum()==-1) {
			isnegative=true;
			thisBinaryNumber=thisBinaryNumber.negate();
		}
		Iterator<Bit>iter=thisBinaryNumber.GetBits().iterator(); 
		String thisstring="";
		while(iter.hasNext())
			thisstring=thisstring+iter.next();
		String flippedans=binary2Decimalh(thisstring.substring(0,thisstring.length()-1), "0");
		//flip the answer
		String ans="";
		for(int i=0; i<flippedans.length(); i++)
			ans= flippedans.charAt(i)+ans;
		if(isnegative)
			ans="-"+ans;
		return ans;
	}
	//=================================================================================================================

	private String decimalDouble(String s) {    	
		return decimalDoubleH(s) ;
	}
	//assumes s is legal string
	private String decimalDoubleH(String s) {
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
			output=(""+(toInt(s.charAt(0))*2)).charAt(1)+decimalIncrementh(decimalDoubleH(s.substring(1))); //multiply the LSB digit, take the LSB of it and add one to the multiply of the remain string
		return output;
	}
	//=================================================================================================================

	private String decimalIncrementh(String s) {
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
	//=================================================================================================================
	private String binary2Decimalh(String s,String acc) {
		String output;
		if (s.length()==0)
			output=acc;
		else if(toInt(s.charAt(s.length()-1))==1) //duplicate the digit and add the digit 1, continue with the remain string
			output= binary2Decimalh(s.substring(0,s.length()-1),decimalIncrementh(decimalDouble(acc)));
		else //duplicate the digit, continue with the remain string
			output= binary2Decimalh(s.substring(0,s.length()-1),decimalDouble(acc));

		return output;
	}
	//	private String binary2Decimalh(BitList BL,String acc) {
	//		String output;
	//		if (BL.size()==0)
	//			output=acc;
	//		else if(BL.getFirst().equals(Bit.ONE)) //duplicate the digit and add the digit 1, continue with the remain string
	//			output= binary2Decimalh(BL.removeFirst(),decimalIncrementh(decimalDouble(acc)));
	//		else //duplicate the digit, continue with the remain string
	//			output= binary2Decimalh(BL.shiftRight(),decimalDouble(acc));
	//
	//		return output;
	//	}
	//=================================================================================================================


	// Returns this * 2
	public BinaryNumber multiplyBy2() {
		BinaryNumber output = new BinaryNumber(this);
		output.bits.shiftLeft();
		output.bits.reduce();
		return output;
	}

	// Returns this / 2;
	public BinaryNumber divideBy2() {
		BinaryNumber output = new BinaryNumber(this);
		if (!equals(ZERO)) {
			if (signum() == -1) {
				output.negate();
				output.bits.shiftRight();
				output.negate();
			} else output.bits.shiftRight();
		}
		return output;
	}

}
