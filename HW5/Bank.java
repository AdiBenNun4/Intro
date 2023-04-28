public class Bank {

	private BankAccountsBinarySearchTree namesTree;
	private BankAccountsBinarySearchTree accountNumbersTree;
	
	public Bank() {
		namesTree = new BankAccountsBinarySearchTree(new AccountComparatorByName());
		accountNumbersTree = new BankAccountsBinarySearchTree(new AccountComparatorByNumber());
	}

	public BankAccount lookUp(String name){
		// create an Entry with the given name, a "dummy" accountNumber (1) and zero balance
		// This "dummy" accountNumber will be ignored when executing getData
		BankAccount lookFor = new BankAccount(name, 1, 0);
		return (BankAccount)namesTree.findData(lookFor);
	}
	
	public BankAccount lookUp(int accountNumber){
		// create an Entry with a "dummy" name, zero balance and the given accountNumber
		// This "dummy" name will be ignored when executing getData
		BankAccount lookFor = new BankAccount("dummy", accountNumber,0);
		return (BankAccount)accountNumbersTree.findData(lookFor);
	}
	
	// END OF Given code -----------------------------------
	
	// Complete the methods from here on
	/**
	 * 
	 * @param newAccount to add both trees if it's name doesn't exist in the trees
	 * @return true if new account was legal and was added
	 */
	public boolean add(BankAccount newAccount) {
		// task 6a
		boolean hasadded=true;
		if(lookUp(newAccount.getName())!=null || lookUp(newAccount.getAccountNumber())!=null) //add if the name and number account don't appear already in the trees
			hasadded=false;
		else {
			namesTree.insert(newAccount);
			accountNumbersTree.insert(newAccount);
		}
			return hasadded;
	}
	
	/**
	 * 
	 * @param name of the bank account to be deleted
	 * @return true if the name exist and was the bank account of it was deleted
	 */
	public boolean delete(String name){
		// this first line is given in the assignment file
		BankAccount toRemove = lookUp(name);
		// complete this:
		// task 6b
		boolean hasdeleted=true;
		if(toRemove!=null) { //if the name exist in the trees
			namesTree.remove(toRemove);
			accountNumbersTree.remove(toRemove);
		}
		else
			hasdeleted=false;

		return hasdeleted;
	}
	
	/**
	 * 
	 * @param accountNumber of the bank account to be removed
	 * @return true if the number exist and was the bank account of it was deleted
	 */
	public boolean delete(int accountNumber){
		// this first line is given in the assignment file
		BankAccount toRemove = lookUp(accountNumber);
		// complete this:
		// task 6c
		boolean wasdeleted=false;
		if(toRemove!= null)
			wasdeleted=delete(toRemove.getName());
		return wasdeleted;
	}

	/**
	 * 
	 * @param amount to deposit to the account
	 * @param accountNumber to deposit the money to it
	 * @return true if was deposit, false if in was illegal amount to deposit
	 */
	public boolean depositMoney(int amount, int accountNumber){
		// task 6d
		return lookUp(accountNumber).depositMoney(amount);	
	}

	/**
	 * 
	 * @param amount to withdraw to the account
	 * @param accountNumber to withdraw the money to it
	 * @return true if was withdraw, false if in was illegal amount to withdraw
	 */
	public boolean withdrawMoney(int amount, int accountNumber){
		// task 6e
		return lookUp(accountNumber).withdrawMoney(amount);	
	}	
	
	/**
	 * 
	 * @param amount to withdraw from account number 1 and deposit in account number 2
	 * @param accountNumber1 to withdraw the amount from it
	 * @param accountNumber2 to deposit the amount in it
	 * @return true if the transfer was legal and was done, otherwise false
	 */
	public boolean transferMoney(int amount, int accountNumber1, int accountNumber2) {
		// task 6f
		BankAccount account1=lookUp(accountNumber1);
		BankAccount account2=lookUp(accountNumber2);
		Filter<BankAccount> filterbalance=new FilterByBalance(amount);
		boolean islegaltransfer=filterbalance.accept(account1);
		if(islegaltransfer) {
			account1.withdrawMoney(amount);
			account2.depositMoney(amount);
		}

		return islegaltransfer;
	}	
	
	/**
	 * 
	 * @param amount to withdraw from account number 1 and deposit in the account of the name given
	 * @param accountNumber to withdraw the amount from it
	 * @param name of the bank account to deposit the amount in it
	 * @return if the transfer was legal and was done, otherwise false
	 */
	public boolean transferMoney(int amount, int accountNumber, String name) {
		// task 6g
		BankAccount account2=lookUp(name);
		boolean wastranfered=false;
		if(account2!=null)
			wastranfered=transferMoney(amount, accountNumber, account2.getAccountNumber());
		
		return wastranfered;
	}	
}
