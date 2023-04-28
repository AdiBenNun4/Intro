import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BankAccountsBinarySearchTree extends BinarySearchTree<BankAccount>{

	public BankAccountsBinarySearchTree(Comparator<BankAccount> myComparator) {
		super(myComparator);
	}

	/**
	 * This method make the tree to be a balanced tree
	 * @throws if the tree is empty
	 */
	public void balance(){
		// task 5b
		if(this.root==null)
			throw new NoSuchElementException();
		Iterator <BankAccount> iter=this.iterator();
		DynamicArray<BankAccount> bankAccountList = new DynamicArray<BankAccount>() ;
		while(iter.hasNext())
			bankAccountList.add(iter.next());
		this.root=null;

		buildBalancedTree(bankAccountList, 0, bankAccountList.size()-1);
	}
	/**
	 * 
	 * @param list a sorted DynamicArray of Bank accounts by in order of the tree  
	 * @param low the smaller index to calculate the middle
	 * @param high the higher index to calculate the middle
	 */
	private void buildBalancedTree(List<BankAccount> list, int low, int high){
		// task 5b
		int middle=(high+low)/2;

		if(low<=high) { // < if there is another middle to go to or = if low==high and this is the last one 
			BinarySearchNode<BankAccount> node=new BinarySearchNode<BankAccount>(list.get(middle), comparator);
			if(root==null) //initial the root
				root=node;
			else //insert the value of the middle node of the list
				root.insert(list.get(middle)); 
			buildBalancedTree(list,low,middle-1); // do it on the left
			buildBalancedTree(list,middle+1,high);  // do it on the right

		}

	}
}
