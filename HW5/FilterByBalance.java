public class FilterByBalance implements Filter<BankAccount>{
	private int balanceThreshold;
	public FilterByBalance(int balanceThreshold) {
		// task 5c
		this.balanceThreshold=balanceThreshold;
	}
	@Override
	/**
	 * @param elem Bank account 
	 * @return true if the balance of the account is bigger than the defined balance 
	 */
	public boolean accept(BankAccount elem) {
		// task 5c
		return (elem.getBalance()>=balanceThreshold);
	}
}
