package me.border.bankapplication.account;

public class AccountComparator {

    private Account account;
    private int size;

    protected AccountComparator(Account account){
        this.account = account;
        this.size = account.getTransactions().size();
    }

    public int compare(Account account){
        if (account.equals(this.account)) {
            boolean sizeEquals = account.getTransactions().size() == size;
            if (!sizeEquals){
                return 2;
            }
            return 1;
        }

        return 0;
    }
}
