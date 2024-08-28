import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
class Bank{
    private static Scanner sc = new Scanner(System.in);
    private List <BankAccount> accounts = new ArrayList<>();
    private BankAccount currentAccount;

    class BankAccount {
        private String accHolder_Name;
        private long accNumber;
        private float accBal;

        BankAccount(String accHolder_Name, long accNumber, float accBal) {
            this.accHolder_Name = accHolder_Name;
            this.accNumber = accNumber;
            this.accBal = accBal;
        }

        String getAccHolder_Name() {
            return accHolder_Name;
        }

        long getAccNumber() {
            return accNumber;
        }

        float getAccBal() {
            return accBal;
        }

        void setAccBal(float accBal) {
            this.accBal = accBal;
        }
    }

    void addAccDetails(){
         try {
            System.out.println("Enter Account Holder's Name:");
            String input = sc.nextLine();
            if (input.trim().isEmpty()) {
                throw new InputMismatchException();
            }
            String accHolderName = input;

            long accNumber;
            while (true) {
                System.out.println("Enter Account Number:");
                accNumber = sc.nextLong();
                sc.nextLine(); // Consume newline

                if (isAccountNumberUnique(accNumber)) {
                    break;
                } else {
                    System.out.println("Account number already exists. Please enter a different account number");
                }
            }
            System.out.println("Enter Account Balance:");
            float accBal = sc.nextFloat();
            sc.nextLine(); // Consume newline

            BankAccount account = new BankAccount(accHolderName, accNumber, accBal);
            accounts.add(account);
            currentAccount = account;
            System.out.println("Account added successfully!\n");
            } catch (InputMismatchException e) {
            System.out.println("Invalid input..Please enter a valid value");
            sc.nextLine(); // Clear the invalid input
            addAccDetails(); // Prompt again for input
        }
    }

    boolean isAccountNumberUnique(long accNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccNumber() == accNumber) {
                return false;
            }
        }
        return true;
    }

    void printAccDetails(){
        if (currentAccount != null) {
            System.out.println("Account Holder's Name: " + currentAccount.getAccHolder_Name());
            System.out.println("Account Number: " + currentAccount.getAccNumber());
            System.out.println("Account Balance: " + currentAccount.getAccBal() + "\n\n");
        } else {
            System.out.println("No account selected\n");
        }
        methods();
    }

    void transactions(){
        if (currentAccount == null) {
            System.out.println("No account selected.");
            methods();
            return;
        }
        System.out.println("Enter d for Debit and c for Credit:");
        char transactionType = sc.next().charAt(0);
        switch (transactionType) {
            case 'd':
                System.out.println("Enter amount to be Debited:");
                float debitAmt = sc.nextFloat();
                currentAccount.setAccBal(currentAccount.getAccBal() + debitAmt);
                break;
            case 'c':
                System.out.println("Enter amount to be Credited:");
                float creditAmt = sc.nextFloat();
                if (currentAccount.getAccBal() < creditAmt)
                    System.out.println("Insufficient Balance");
                else
                    currentAccount.setAccBal(currentAccount.getAccBal() - creditAmt);
                break;
            default:
                System.out.println("Invalid!!");
        }
        System.out.println("New Balance: " + currentAccount.getAccBal() + "\n\n");
        methods();
    }

    void interest(){
        if (currentAccount != null) {
            float in = currentAccount.getAccBal() * 5 / 100;
            System.out.println("Interest received: " + in + "\n\n");
        } else {
            System.out.println("No account selected \n\n");
        }
        methods();
    }

    void transferMoney() {
        if (accounts.size() < 2) {
            System.out.println("Need at least two accounts to transfer money\n");
            methods();
            return;
        }

        System.out.println("Select the source account:");
        BankAccount sourceAccount = selectAccount();
        if (sourceAccount == null) {
            System.out.println("Invalid source account selection\n");
            methods();
            return;
        }

        System.out.println("Select the destination account:");
        BankAccount destinationAccount = selectAccount();
        if (destinationAccount == null || destinationAccount == sourceAccount) {
            System.out.println("Invalid destination account selection\n");
            methods();
            return;
        }

        try {
            System.out.println("Enter the amount to transfer:");
            float amount = sc.nextFloat();
            if (sourceAccount.getAccBal() < amount) {
                System.out.println("Insufficient balance in source account\n");
            } else {
                sourceAccount.setAccBal(sourceAccount.getAccBal() - amount);
                destinationAccount.setAccBal(destinationAccount.getAccBal() + amount);
                System.out.println("Transfer successful. New balances:");
                System.out.println("Source Account (" + sourceAccount.getAccHolder_Name() + "): " + sourceAccount.getAccBal());
                System.out.println("Destination Account (" + destinationAccount.getAccHolder_Name() + "): " + destinationAccount.getAccBal() + "\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input..Please enter a valid amount");
            sc.nextLine(); // Clear the invalid input
        }
        methods();
    }

    BankAccount selectAccount() {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).getAccHolder_Name() + " - " + accounts.get(i).getAccNumber());
        }

        try {
            int index = sc.nextInt() - 1;
            sc.nextLine(); // Consume newline
            if (index >= 0 && index < accounts.size()) {
                return accounts.get(index);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input..Please enter a valid value");
            sc.nextLine(); // Clear the invalid input
        }
        return null;
    }

    void closeAcc(){
        if (currentAccount != null) {
            System.out.println("Are you sure you want to close the account? (Enter 'yes' to confirm)");
            String confirm = sc.next().toLowerCase(); // Convert input to lowercase for case-insensitive check
            if (confirm.equals("yes")) {
                accounts.remove(currentAccount);
                currentAccount = null;
                System.out.println("Account closed!");
                System.out.println("Thank you for banking with us \n\n");
            } else {
                System.out.println("Account closure canceled");
            }
        } else {
            System.out.println("No account selected");
        }
        methods();
    }

    void switchAccount() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available. Please add an account first\n");
            methods();
            return;
        }
         System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).getAccHolder_Name() + " - " + accounts.get(i).getAccNumber());
        }

        try {
            System.out.println("Enter the number of the account you want to switch to:");
            int index = sc.nextInt() - 1;
            sc.nextLine(); // Consume newline
            if (index >= 0 && index < accounts.size()) {
                currentAccount = accounts.get(index);
                System.out.println("Switched to account: " + currentAccount.getAccHolder_Name() + "\n");
            } else {
                System.out.println("Invalid selection!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input..Please enter a valid value");
            sc.nextLine(); // Clear the invalid input
        }
        methods();
    }

    void methods(){
        System.out.println("Enter 1 to view account details");
        System.out.println("Enter 2 to do some transactions");
        System.out.println("Enter 3 to calculate interest");
        System.out.println("Enter 4 to transfer money");
        System.out.println("Enter 5 to close account");
        System.out.println("Enter 6 to add a new account");
        System.out.println("Enter 7 to switch account");
        System.out.println("Enter 8 to exit");
        try {
            int n = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (n) {
                case 1: //Account details
                    printAccDetails();
                    break;
                case 2: //do some transaction
                    transactions();
                    break;
                case 3: //Check Interest
                    interest();
                    break;
                case 4: //Transfer money
                    transferMoney();
                    break;
                case 5: //Close account
                    closeAcc();
                    break;
                case 6: //Add account
                    addAccDetails();
                    methods();
                    break;
                case 7: //Switch account
                    switchAccount();
                    break;
                case 8: //Exit
                    System.out.println("Exiting. Thank you for using the bank system");
                    sc.close(); // Close the scanner
                    System.exit(0);
                default:
                    System.out.println("Invalid Input!");
                    methods();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input..Please enter a valid integer");
            sc.nextLine(); // Clear the invalid input
            methods(); // Prompt again for input
        }
    }
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.addAccDetails();
        bank.methods();
        sc.close(); // Close the scanner at the end
    }
}