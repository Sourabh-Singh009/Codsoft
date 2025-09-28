import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BankAccount {
    private double balance;
    private List<String> transactionHistory;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
        transactionHistory = new ArrayList<>();
        transactionHistory.add(String.format("Account opened with balance: Rs. %.2f", balance));
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
        transactionHistory.add(String.format("Deposited: Rs. %.2f", amount));
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(String.format("Withdrew: Rs. %.2f", amount));
            return true;
        }
        return false;
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public boolean transferTo(BankAccount targetAccount, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return false;
        }
        if (amount <= balance) {
            balance -= amount;
            targetAccount.deposit(amount);
            transactionHistory.add(String.format("Transferred: Rs. %.2f to another account", amount));
            return true;
        }
        return false;
    }
}

class ATM {
    private BankAccount account;
    private Scanner scanner;

    public ATM(BankAccount bankAccount) {
        account = bankAccount;
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Funds");
        System.out.println("5. Transaction History");
        System.out.println("6. Exit");
    }

    private double getPositiveAmount(String prompt) {
        double amount = -1;
        while (amount <= 0) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Amount must be positive. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next(); // consume invalid input
            }
        }
        return amount;
    }

    public void run() {
        while (true) {
            displayMenu();
            System.out.print("Select an option: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                scanner.next(); // consume invalid input
                continue;
            }
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.printf("Your balance: Rs. %.2f%n", account.getBalance());
                    break;
                case 2:
                    double depositAmount = getPositiveAmount("Enter amount to deposit: Rs. ");
                    account.deposit(depositAmount);
                    System.out.printf("Deposit successful. Your balance: Rs. %.2f%n", account.getBalance());
                    break;
                case 3:
                    double withdrawAmount = getPositiveAmount("Enter amount to withdraw: Rs. ");
                    if (account.withdraw(withdrawAmount)) {
                        System.out.printf("Withdrawal successful. Your balance: Rs. %.2f%n", account.getBalance());
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;
                case 4:
                    double transferAmount = getPositiveAmount("Enter amount to transfer: Rs. ");
                    // For demonstration, create a dummy target account
                    BankAccount targetAccount = new BankAccount(0);
                    if (account.transferTo(targetAccount, transferAmount)) {
                        System.out.printf("Transfer successful. Your balance: Rs. %.2f%n", account.getBalance());
                        System.out.printf("Recipient's balance: Rs. %.2f%n", targetAccount.getBalance());
                    } else {
                        System.out.println("Insufficient balance for transfer.");
                    }
                    break;
                case 5:
                    account.printTransactionHistory();
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(0); // Initial balance set to 0
        ATM atm = new ATM(userAccount);
        atm.run();
    }
}
