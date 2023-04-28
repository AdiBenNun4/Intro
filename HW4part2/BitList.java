/*
I, <ADI BEN NUN> (<207257486>), assert that the work I submitted is entirely my own.
I have not received any part from any other student in the class,
nor did I give parts of it for use to others.
I realize that if my work is found to contain code that is not originally my own,
 a formal case will be opened against me with the BGU disciplinary committee.
 */

import java.util.LinkedList;

import javax.swing.text.AbstractDocument.LeafElement;

import java.security.cert.TrustAnchor;
import java.util.Iterator;

public class BitList extends LinkedList<Bit> {
	private int numberOfOnes;

	// Do not change the constructor
	public BitList() {
		numberOfOnes = 0;
	}

	// Do not change the method
	public int getNumberOfOnes() {
		return numberOfOnes;
	}


	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.1 ================================================

	// throws IllegalArgumentException - if the specified element is null
	public void addLast(Bit element) {
		if(element==null)
			throw new IllegalArgumentException("need to put inpute, can't contain null");
		super.addLast(element);
		numberOfOnes+=element.toInt();

	}
	//Add element first to this
	// throws IllegalArgumentException - if the specified element is null
	public void addFirst(Bit element) {
		if(element==null)
			throw new IllegalArgumentException("need to put inpute, can't contain null");
		super.addFirst(element);
		numberOfOnes+=element.toInt();
	}
	//Removes and returns the Last element from this
	// throws IllegalArgumentException - if the specified element is null
	public Bit removeLast() {
		if(this.getLast()==null)
			throw new IllegalArgumentException("can't remove null");
		Bit last=super.removeLast();
		numberOfOnes-=last.toInt();
		return last;
	}
	//Removes and returns the first element from this
	// throws IllegalArgumentException - if the specified element is null
	public Bit removeFirst() {
		if(this.getFirst()==null)
			throw new IllegalArgumentException("can't remove null");
		Bit first=super.removeFirst();
		numberOfOnes-=first.toInt();
		return first;
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.2 ================================================
	//Return this as string <this>
	public String toString() {
		String stringMSB="",output="";

		Iterator <Bit> iter = this.iterator();
		while (iter.hasNext()) 
			stringMSB+=iter.next();

		for (int i = 0; i < stringMSB.length(); i++) //flip the string
			output=stringMSB.charAt(i)+output;

		return ("<"+ output +">" );
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.3 ================================================
	//copy constructor
	// throws IllegalArgumentException - if the specified element is null
	public BitList(BitList other) {
		if(other==null)
			throw new IllegalArgumentException("need to put inpute, can't contain null");   
		Iterator <Bit> iter = other.iterator();
		while (iter.hasNext()) //insert to this every bit like other
			this.addLast(iter.next());

		this.numberOfOnes=other.numberOfOnes;
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.4 ================================================
	//checks if the BitList is legal BitList, return true\false 
	public boolean isNumber() {
		boolean output=false;
		//if this is null don't check the size (error)
		if(this==null || this.size()<1)//if the length is 0 
			output=false;
		else if (this.getNumberOfOnes()>1 | this.getLast().equals(Bit.ZERO) )//MSB is 0 or there and there is just one time 1
			output=true;

		return output;
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.5 ================================================
	//check if the BitList is reduced, return true\false 
	public boolean isReduced() {
		boolean isreduced=true, condition1=false,condition2=false,condition3=false;
		if(!this.isNumber())
			isreduced=false;
		else {
			String bitliststring=this.toString();
			//check condition 1
			if(bitliststring.equals("<0>")|bitliststring.equals("<01>")|bitliststring.equals("<11>"))
				condition1=true;
			//check condition 2
			else if(!condition1 & isreduced) 
				if(this.size()>2 & (bitliststring.substring(1,3).equals("01"))|bitliststring.substring(1,3).equals("10"))
					condition2=true;
			//check condition 3
				else if (this.size()>2 & this.getNumberOfOnes()==2 & bitliststring.substring(1,3).equals("11"))
					condition3=true;
		}
		//if the bitList don't make at least on condition return false
		if(!condition1 & !condition2 &!condition3)
			isreduced=false;

		return isreduced;
	}
	//Reduce the BitList if it is legal
	public void reduce() {  
		if(this.isNumber())
			while(!this.isReduced())
				this.removeLast();
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.6 ================================================
	//Creates and return the complement BitList-0 turn to 1 and opposite
	// throws IllegalArgumentException - if this is not legal BitList
	public BitList complement() {
		if(!this.isNumber())
			throw new IllegalArgumentException("Illegal BitList");
		BitList comp=new BitList();
		Iterator <Bit> iter = this.iterator();
		while(iter.hasNext())
			comp.addLast(iter.next().negate());//insert the negative digit

		return comp;
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.7 ================================================
	//Remove the first Bit (the Right)
	// throws IllegalArgumentException - if the specified First Bit is null
	public Bit shiftRight() {
		if(this.getFirst()==null)
			throw new IllegalArgumentException("can't shift null list");
		Bit firstbit=null;
		if(this.size()>0) {    	
			firstbit=this.getFirst();
			this.removeFirst();
		}
		return firstbit;


	}
	//add 0 to be the first(right)
	public void shiftLeft() {
		this.addFirst(Bit.ZERO);
	}

	//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.8 ================================================
	//duplicate the last Bit (left) until the whole number has the new length
	public void padding(int newLength) {
		if(this.getFirst()==null)
			throw new IllegalArgumentException("can't padding null list");
		Bit last=this.getLast();
		while(this.size()<newLength) //add the last bit until get new length
			this.addLast(last);
	}
	//=========================== Intro2CS 2021, ASSIGNMENT 4, assisting function ================================================
	//the function creates and return the flip BitList
	// throws IllegalArgumentException - if the specified First Bit is null
	public BitList flipBitList() {
		if(this.getFirst()==null)
			throw new IllegalArgumentException("can't flip null list");
		BitList flippedBitList=new BitList();
		Iterator <Bit> iter=this.iterator();
		while(iter.hasNext())
			flippedBitList.addFirst(iter.next());
		return flippedBitList;

	}


	//----------------------------------------------------------------------------------------------------------
	// The following overriding methods must not be changed.
	//----------------------------------------------------------------------------------------------------------
	public boolean add(Bit e) {
		throw new UnsupportedOperationException("Do not use this method!");
	}

	public void add(int index, Bit element) {
		throw new UnsupportedOperationException("Do not use this method!");
	}

	public Bit remove(int index) {
		throw new UnsupportedOperationException("Do not use this method!");
	}

	public boolean offer(Bit e) {
		throw new UnsupportedOperationException("Do not use this method!");
	}

	public boolean offerFirst(Bit e) {
		throw new UnsupportedOperationException("Do not use this method!");
	}

	public boolean offerLast(Bit e) {
		throw new UnsupportedOperationException("Do not use this method!");
	}

	public Bit set(int index, Bit element) {
		throw new UnsupportedOperationException("Do not use this method!");
	}

	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Do not use this method!");
	}
}
