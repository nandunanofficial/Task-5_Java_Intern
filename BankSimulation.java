package Task_5;

import java.util.ArrayList;
import java.util.List;

class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        this.transactionHistory.add("Account created with initial balance: $" + initialBalance);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposit: +$" + amount);
            System.out.println("Deposit successful. New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }


    public void withdraw(double amount) {
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                transactionHistory.add("Withdrawal: -$" + amount);
                System.out.println("Withdrawal successful. New balance: $" + balance);
            } else {
                System.out.println("Insufficient funds for withdrawal.");
            }
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }


    public void transfer(Account recipient, double amount) {
        if (amount > 0) {
            if (amount <= balance) {
                this.balance -= amount;
                recipient.balance += amount;
                this.transactionHistory.add("Transfer to " + recipient.accountNumber + ": -$" + amount);
                recipient.transactionHistory.add("Transfer from " + this.accountNumber + ": +$" + amount);
                System.out.println("Transfer successful. New balance: $" + balance);
            } else {
                System.out.println("Insufficient funds for transfer.");
            }
        } else {
            System.out.println("Invalid transfer amount.");
        }
    }


    public void displayBalance() {
        System.out.println("Account Balance for " + accountHolder + " (" + accountNumber + "): $" + balance);
    }


    public void displayTransactionHistory() {
        System.out.println("\nTransaction History for " + accountHolder + " (" + accountNumber + "):");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }
}


class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double initialBalance, double interestRate) {
        super(accountNumber, accountHolder, initialBalance);
        this.interestRate = interestRate;
    }


    public void applyInterest() {
        double interest = getBalance() * interestRate / 100;
        deposit(interest);
        System.out.println("Interest of $" + interest + " applied to savings account.");
    }


    @Override
    public void withdraw(double amount) {

        double minimumBalance = 100.0;
        if (getBalance() - amount >= minimumBalance) {
            super.withdraw(amount);
        } else {
            System.out.println("Withdrawal denied. Must maintain minimum balance of $" + minimumBalance);
        }
    }
}


public class BankSimulation {
    public static void main(String[] args) {

        Account johnAccount = new Account("1001", "John Doe", 500.0);


        SavingsAccount janeAccount = new SavingsAccount("2001", "Jane Smith", 1000.0, 2.5);


        System.out.println("----- Initial Setup -----");
        johnAccount.displayBalance();
        janeAccount.displayBalance();

        System.out.println("\n----- Transaction 1: Deposits -----");
        johnAccount.deposit(200.0);
        janeAccount.deposit(300.0);

        System.out.println("\n----- Transaction 2: Withdrawals -----");
        johnAccount.withdraw(100.0);
        janeAccount.withdraw(950.0); // This should be allowed
        janeAccount.withdraw(100.0); // This should be denied (minimum balance)

        System.out.println("\n----- Transaction 3: Transfer -----");
        johnAccount.transfer(janeAccount, 150.0);

        System.out.println("\n----- Transaction 4: Apply Interest -----");
        janeAccount.applyInterest();

        System.out.println("\n----- Final Balances -----");
        johnAccount.displayBalance();
        janeAccount.displayBalance();

        System.out.println("\n----- Transaction History -----");
        johnAccount.displayTransactionHistory();
        janeAccount.displayTransactionHistory();
    }
}
