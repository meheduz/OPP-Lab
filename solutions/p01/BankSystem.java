// Problem 1: Bank Account Management System

abstract class BankAccount {
    private String accountNumber;
    private String holderName;
    private double balance;
    public static int totalAccounts = 0;

    public BankAccount(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        totalAccounts++;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    protected void deductBalance(double amount) { balance -= amount; }
    protected void addBalance(double amount) { balance += amount; }

    public abstract void displayInfo();
}

abstract class DepositAccount extends BankAccount {
    private double depositLimit;

    public DepositAccount(String accountNumber, String holderName, double balance, double depositLimit) {
        super(accountNumber, holderName, balance);
        this.depositLimit = depositLimit;
    }

    public void setDepositLimit(double limit) { this.depositLimit = limit; }
    public double getDepositLimit() { return depositLimit; }
}

class SavingsAccount extends DepositAccount {
    private double monthlyInterestRate;
    private double lastInterestApplied;

    public SavingsAccount(String accountNumber, String holderName, double balance, double monthlyInterestRate) {
        super(accountNumber, holderName, balance, 100000.0);
        this.monthlyInterestRate = monthlyInterestRate;
    }

    public void applyInterest() {
        InterestPolicy ip = new InterestPolicy();
        lastInterestApplied = ip.compute(getBalance(), monthlyInterestRate);
        addBalance(lastInterestApplied);
    }

    public void displayInfo() {
        System.out.println("=== Savings Account ===");
        System.out.println("Account No : " + getAccountNumber());
        System.out.println("Holder       : " + getHolderName());
        System.out.println("Balance      : " + getBalance());
        System.out.println("Monthly Interest Applied: " + lastInterestApplied + " (via InterestPolicy)");
        System.out.println();
    }

    public static class InterestPolicy {
        public double compute(double balance, double rate) {
            return balance * rate;
        }
    }
}

class CurrentAccount extends DepositAccount {
    private double overdraftLimit;
    private boolean lastWithdrawalApproved;

    public CurrentAccount(String accountNumber, String holderName, double balance, double overdraftLimit) {
        super(accountNumber, holderName, balance, 100000.0);
        this.overdraftLimit = overdraftLimit;
        this.lastWithdrawalApproved = true;
    }

    public void withdraw(double amount) {
        OverdraftPolicy op = new OverdraftPolicy();
        if (op.isAllowed(getBalance(), amount, overdraftLimit)) {
            deductBalance(amount);
            lastWithdrawalApproved = true;
        } else {
            lastWithdrawalApproved = false;
        }
    }

    public void displayInfo() {
        System.out.println("=== Current Account ===");
        System.out.println("Account No : " + getAccountNumber());
        System.out.println("Holder       : " + getHolderName());
        System.out.println("Balance      : " + getBalance());
        System.out.println("Overdraft    : " + overdraftLimit);
        if (!lastWithdrawalApproved) {
            System.out.println("Withdrawal DENIED - exceeds overdraft limit (OverdraftPolicy)");
        }
        System.out.println();
    }

    public static class OverdraftPolicy {
        public boolean isAllowed(double balance, double amount, double limit) {
            return (balance - amount) >= -limit;
        }
    }
}

class InterestCalculator {
    public double calculate(double principal, double rate) {
        return principal * rate / 100.0;
    }
    public double calculate(double principal, double rate, int years) {
        return principal * rate * years / 100.0;
    }
}

public class BankSystem {
    public static void main(String[] args) {
        SavingsAccount sa = new SavingsAccount("SA001", "Alice", 50000.0, 0.005);
        CurrentAccount ca = new CurrentAccount("CA001", "Bob", 3000.0, 1000.0);

        sa.deposit(2000.0);
        sa.applyInterest();
        sa.displayInfo();

        ca.withdraw(3500.0);
        ca.withdraw(5000.0);
        ca.displayInfo();

        InterestCalculator ic = new InterestCalculator();

        System.out.println();
        System.out.println("Interest (1 yr) : " + ic.calculate(50000, 6));
        System.out.println("Interest (3 yr) : " + ic.calculate(50000, 6, 3));

        System.out.println("Total Accounts Created : " + BankAccount.totalAccounts);
    }
}
