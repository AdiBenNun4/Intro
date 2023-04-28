import java.util.Iterator;
import java.util.NoSuchElementException;


public class FilteredBankAccountsIterator implements Iterator<BankAccount> {

	private BinaryTreeInOrderIterator<BankAccount> iterator;
	private BinaryTreeInOrderIterator<BankAccount> hasNextiterator;
	private Filter<BankAccount> filter;
	private BankAccount current;

	public FilteredBankAccountsIterator(BankAccountsBinarySearchTree bankAccountsTree, Filter<BankAccount> filter) {
		// task 5c
		this.filter=filter;
		iterator=  new BinaryTreeInOrderIterator<BankAccount>(bankAccountsTree.root);
		current=iterator.next();
		hasNextiterator=  new BinaryTreeInOrderIterator<BankAccount>(bankAccountsTree.root);

	}

	/**
	 * @return true if there a bank account that stand in the filter 
	 */
	@Override
	public boolean hasNext() {
		// task 5c
		boolean isexist = false;
		while(!isexist & hasNextiterator.hasNext() )  //search in the tree for bank account with the condition of the filter
			isexist= filter.accept(hasNextiterator.next());

		return isexist;
	}



	/**
	 * @throws NoSuchElementException if there isn't another bank account that stand the filter
	 * @return the bank account in the tree that stand in the filter
	 */
	@Override
	public BankAccount next() {
		// task 5c
		BankAccount filtered=current;
		boolean hasfound=false;
		while(!hasfound ) {
			if(filter.accept(current)) {//if it stands in the filter update the bank accout filtered 
				hasfound=true;
				filtered=current;
			}
			if(iterator.hasNext())
				current=iterator.next();
			else
				throw new NoSuchElementException();
		}
		return filtered;
	}
}
