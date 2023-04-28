
public class Bit {
	
    private boolean value;
        
//  Task 4.1
    public Bit(boolean value){
    	this.value=value;
    }
    
    //Task 4.2
    //return 0 if false and 1 if true
    public int toInt(){ 
        int ans = 0;
        if(this.value)
        	ans=1;
        return ans;

    }
    
    //Task 4.3
    //return "0" if false and "1" if true
    public String toString(){
        return (""+toInt());
    }
}

