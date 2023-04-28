import java.util.Comparator;

public class AccountComparatorByName implements Comparator<BankAccount>{

	@Override
	public int compare(BankAccount account1, BankAccount account2) {
		// task 2a
		return account1.getName().compareTo(account2.getName());
	}

}
