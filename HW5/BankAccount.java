public class BankAccount {

	private String name;
	private int accountNumber;
	private int balance;

	public BankAccount(String name, int accountNumber, int balance) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException();
		}
		if (accountNumber <= 0) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	/**
	 * A getter of the field name.
	 *
	 * @return returns the field name..
	 */
	public String getName() {
		return name;
	}

	/**
	 * A getter of the field balance.
	 *
	 * @return returns the field balance..
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * A getter of the field accountNumbername.
	 *
	 * @return returns the field accountNumber..
	 */
	public int getAccountNumber() {
		return accountNumber;
	}

	/**
	 * toString method of the following format: "Name: "+name+", AccountNumber: "+accountNumber.
	 *
	 * @return returns a String with the current state.
	 */
	public String toString() {
		return "Name: " + name + ", AccountNumber: " + accountNumber;
	}

	// Complete the functions from here on

	/**
	 * This method deposit money into the bank account by increasing the balance.
	 *
	 * @param amount The amount of money to deposit.
	 * @return true if amount >= 0 else if amount is negative return false without changing the balance.
	 */
	public boolean depositMoney(int amount) {
		// task 1a
		boolean isilegalamount=true;
		if(amount<0) //if the amount is negative it's illegal input, return false
			isilegalamount=false;
		else 
			this.balance=getBalance()+amount; //update the balance  	

		return isilegalamount;	
	}

	/**
	 * This method withdraw money from the bank account by deceasing the balance.
	 *
	 * @param amount The amount of money to withdraw.
	 * @return true if amount >= 0 and by decreasing balance it doesn't turns negative, else if amount is negative or
	 * if balance < 0 after the withdraw return false without changing the balance.
	 */
	public boolean withdrawMoney(int amount) {
		// task 1b
		boolean isilegalamount=true;
		if(amount<0 | amount>this.getBalance())
			isilegalamount=false;
		else
			this.balance=getBalance()-amount;

		return isilegalamount;	 
	}
}
