package ListManager;


import java.util.Scanner;

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class BankAccount {
    protected String name;
    protected double balance;

    public BankAccount(String name, double openingDeposit) {
        this.name = name;
        this.balance = openingDeposit;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Deposited: $%,.2f\n", amount);
        } else {
            System.out.println("Amount must be greater than zero.");
        }
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Error: Not enough funds in the account.");
        }
        if (amount > 0) {
            balance -= amount;
            System.out.printf("Withdrew: $%,.2f\n", amount);
        } else {
            System.out.println("Amount must be greater than zero.");
        }
    }

    public void displayBalance() {
        System.out.printf("Current Balance: $%,.2f\n", balance);
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String name, double openingDeposit, double interestRate) {
        super(name, openingDeposit);
        this.interestRate = interestRate;
    }

    public void addInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.printf("Interest added: $%,.2f\n", interest);
    }
}

public class BankApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        double openingDeposit = 0;
        while (true) {
            System.out.print("Enter opening deposit amount: ");
            try {
                openingDeposit = Double.parseDouble(scanner.nextLine());
                if (openingDeposit >= 0) {
                    break;
                }
                System.out.println("Deposit cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        SavingsAccount account = new SavingsAccount(name, openingDeposit, 0.03);

        int choice = 0;
        while (choice != 4) {
            System.out.println("\n--- Bank Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance & Add Interest");
            System.out.println("4. Quit");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to deposit: ");
                        double depAmount = Double.parseDouble(scanner.nextLine());
                        account.deposit(depAmount);
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double witAmount = Double.parseDouble(scanner.nextLine());
                        account.withdraw(witAmount);
                        break;
                    case 3:
                        account.addInterest();
                        account.displayBalance();
                        break;
                    case 4:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Choose a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (InsufficientFundsException e) {
                System.out.println(e.getMessage());
            }
        }
        
        scanner.close();
    }
}
