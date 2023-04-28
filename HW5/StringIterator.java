import java.util.Iterator;

public class StringIterator implements Iterator<String> {

	// -------------------- fields --------------------
	private DynamicArray<Character> charDynamicArray;

	// -------------------- constructors --------------------
	public StringIterator(String start){
		int size=start.length();

		if(size!=0) {
			charDynamicArray=new DynamicArray<Character>(size);
			for (int i = 0; i < size; i++) { //enter every character in the string to the Dynamic array
				charDynamicArray.add(start.charAt(i));
			}
		}
		else //size==0
			charDynamicArray=new DynamicArray<Character>();

	}

	// -------------------- methods -------------------- 

	//Returns true if the iteration has more elements
	public boolean hasNext(){
		return (charDynamicArray.size()<=Integer.MAX_VALUE);
	}

	//Returns the next String in the iteration
	public String next(){
		String output=DAtoString();
		if(charDynamicArray.isEmpty()) {
			charDynamicArray.add('a');
		}
		else
			SetNext(charDynamicArray.size()-1);
		return output;
	}

	//Returns the string that represent the chars in charDynamicArray
	private String DAtoString() {
		String output="";
		Iterator<Character>iter=charDynamicArray.iterator();
		while(iter.hasNext())
			output+=iter.next();
		return output;
	}

	//Set the char in the index in charDynamicArray to the next char
	private void SetNext(int index) {
		char value=charDynamicArray.get(index);
		if(value=='z')
			charDynamicArray.set(index, 'A');
		else if(value=='Z') {
			charDynamicArray.set(index, 'a');
			if(index==0)//if the char is the left letter add 'a'
				charDynamicArray.add('a');
			else 
				SetNext(index-1); //change the left letter near 'Z'
		}
		else 	//value is between a<value<z or A<value<Y
			charDynamicArray.set(index, (char)(value+1));
	}

}
