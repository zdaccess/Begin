package src.ex05;

import java.util.Scanner;
import java.util.UUID;

public class Menu {
    TransactionsService vtb;
    public Menu (String profile) {
        try {
            vtb = new TransactionsService(3);
            helpMessage(profile, 0);
            process(profile);
        } catch (ArrayIndexOutOfBoundsException |
                 TransactionNotFoundException | IllegalTransactionException e) {}
    }

    public void helpMessage(String profile, Integer flag) {
        if (flag == 1)
            System.out.println("---------------------------------------------------------");
        if (profile == "dev") {
            System.out.println("1. Add a user\n" +
                    "2. View user balances\n" +
                    "3. Perform a transfer\n" +
                    "4. View all transactions for a specific user\n" +
                    "5. DEV – remove a transfer by ID\n" +
                    "6. DEV – check transfer validity\n" +
                    "7. Finish execution");

        }
        else if (profile == "production")
            System.out.println("1. Add a user\n" +
                    "2. View user balances\n" +
                    "3. Perform a transfer\n" +
                    "4. View all transactions for a specific user\n" +
                    "7. Finish execution");
    }

    public String[] viewUserBalances(String profile, Scanner scanner) {
        System.out.println("Enter a user ID");
        String[] userArray = scanner.nextLine().split(" ");
        try {
            Integer id = Integer.parseInt(userArray[0]);
            System.out.println(vtb.getListUser().getUsersId(id).getName()
                    + " - " + vtb.getListUser().getUsersId(id).getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Error! Enter only numbers without spaces!");
        } catch (NullPointerException | UserNotFoundException e) {
            System.out.println("Error! There is no user with this id!");
        }
        helpMessage(profile, 1);
        userArray = scanner.nextLine().split(" ");
        return userArray;
    }

    public String[] addUsers(String profile, Scanner scanner) {
        System.out.println("Enter a user name and a balance");
        Integer balance = 0;
        String name = null;
        String[] userArray = scanner.nextLine().split(" ");
        if (userArray.length != 2)
            System.out.println("Error! Enter only two values separated by a space!");
        else if (!userArray[0].matches("[a-zA-Z]+"))
            System.out.println("Error! The client's name must " +
                    "consist of English letters only!");
        else if (Integer.parseInt(userArray[1]) < 0) {
            System.out.println("Error! " +
                    "The new user's balance is less than zero!");
        }
        else {
            try {
                name = userArray[0];
                balance = Integer.parseInt(userArray[1]);
                User user = new User(name, balance);
                vtb.add(user);
                System.out.println("User with id = " + user.getId() + " is added");
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Error! Other symbols have been entered " +
                        "instead of numbers in the second value!");
            }
        }
        helpMessage(profile, 1);
        userArray = scanner.nextLine().split(" ");
        return userArray;
    }

    public String[] reAddCommand(String profile, Scanner scanner) {
        System.out.println("Enter a number from the list of commands");
        helpMessage(profile, 1);
        String[]  userArray = scanner.nextLine().split(" ");
        return userArray;
    }

    public String[] performTransfer(String profile, Scanner scanner) {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String[] userArray = scanner.nextLine().split(" ");
        if (userArray.length == 3) {
            try {
                if (!userArray[0].matches("\\d+")) {
                    System.out.println("Error! The first argument does not contain numbers!");
                } else if (!userArray[1].matches("\\d+")) {
                    System.out.println("Error! The second argument does not contain numbers!");
                } else if (!userArray[2].matches("\\d+")) {
                    System.out.println("Error! The third argument does not contain numbers!");
                } else {
                    Integer senderId = Integer.parseInt(userArray[0]);
                    Integer recipientId = Integer.parseInt(userArray[1]);
                    Integer amount = Integer.parseInt(userArray[2]);
                    if (senderId != recipientId) {
                        vtb.addTransactions(senderId, recipientId, amount);
                        System.out.println("The transfer is completed");
                    } else
                        System.out.println("Error! The two client IDs are the same!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Incorrect number format!");
            } catch (UserNotFoundException e) {
                System.out.println("Error! A user with this id does " +
                        "not exist or the data has been entered incorrectly!");
            } catch (IllegalTransactionException e) {
                System.out.println("Error! The amount exceeds the " +
                        "sender's remaining balance!");
            }
        } else {
            System.out.println("Error! You must enter three numeric " +
                    "values separated by a space!");
        }
        helpMessage(profile, 1);
        userArray = scanner.nextLine().split(" ");
        return userArray;
    }

    public String[] viewAllTransactions(String profile, Scanner scanner) {
        System.out.println("Enter a user ID");
        String[] userArray = scanner.nextLine().split(" ");
        if (userArray.length == 1) {
            try {
                Integer id = Integer.parseInt(userArray[0]);
                printOperations(id);
            } catch (NumberFormatException e) {
                System.out.println("Error! Enter only a number!");
            } catch (UserNotFoundException e) {
            System.out.println("Error! A user with this id does " +
                    "not exist or the data has been entered incorrectly!");
        }
        } else
            System.out.println("Error! You must enter numbers without spaces!");
        helpMessage(profile, 1);
        userArray = scanner.nextLine().split(" ");
        return userArray;
    }

    public String[] removeTransfer(String profile, Scanner scanner) {
        Integer amount;
        System.out.println("Enter a user ID and a transfer ID");
        String[] userArray = scanner.nextLine().split(" ");
        if (userArray.length == 2) {
            try {
                if (!userArray[0].matches("\\d+"))
                    System.out.println("Error! The first argument " +
                            "does not contain numbers!");
                else if (!userArray[1].matches("[0-9a-fA-F\\\\-]+")) {
                    System.out.println("Error! The second argument " +
                            "contains characters that should not be " +
                            "in the identifier!");
                }
                Integer id = Integer.parseInt(userArray[0]);
                UUID identifier = UUID.fromString(userArray[1]);
                Transaction operation = vtb.deleteTransaction(id, identifier);
                if (operation.getTransferAmount() < 0)
                    amount = -1 * operation.getTransferAmount();
                else
                    amount = operation.getTransferAmount();
                if (operation.getNameOperation().equals("credit"))
                    System.out.println("Transfer To " +
                            operation.getRecipient().getName() + "(id = " +
                            operation.getRecipient().getId() + ") " +
                            amount + " removed");
                else
                    System.out.println("Transfer From " +
                            operation.getRecipient().getName() +
                            "(id = " + operation.getRecipient().getId() +
                            ") " + amount + " removed");
            } catch (NumberFormatException e) {
                System.out.println("Error! Incorrect number format!");
            } catch (UserNotFoundException e) {
                System.out.println("Error! A user with this id does " +
                        "not exist or the data has been entered incorrectly!");
            } catch (IllegalArgumentException e) {
                System.out.println("Error! This ID does not exist!");
            } catch (TransactionNotFoundException | NullPointerException e) {
                System.out.println("Error! No transaction found by ID!");
            }
        } else
            System.out.println("Error! You must enter two arguments " +
                    "separated by a space!");
        helpMessage(profile, 1);
        userArray = scanner.nextLine().split(" ");
        return userArray;
    }

    public String[] checkTransferValidity(String profile, Scanner scanner) throws UserNotFoundException {
        Transaction[] transactions = vtb.checkValidityTransactions();
        if (transactions.length == 0) {
            System.out.println("No unpaired transactions found!");
        } else {
            System.out.println("Check results:");
            for (Integer i = 0; i < transactions.length; i++) {
                if (transactions[i].getNameOperation().equals("credit")) {
                    System.out.println(transactions[i].getRecipient().getName()
                            + "(id = " + transactions[i].getRecipient().getId()
                            + ") has an unacknowledged transfer id = "
                            + transactions[i].getIdentifier() + " to " +
                            transactions[i].getSender().getName() + "(id = "
                            + transactions[i].getSender().getId() + ") for "
                            + transactions[i].getTransferAmount());
                } else if (transactions[i].getNameOperation().equals("debit")) {
                    System.out.println(transactions[i].getSender().getName()
                            + "(id = " + transactions[i].getSender().getId()
                            + ") has an unacknowledged transfer id = "
                            + transactions[i].getIdentifier() + " from " +
                            transactions[i].getRecipient().getName() + "(id" +
                            " = " + transactions[i].getRecipient().getId() +
                            ") for " + transactions[i].getTransferAmount());
                }
            }
        }
        helpMessage(profile, 1);
        String[] userArray = scanner.nextLine().split(" ");
        return userArray;
    }
    public void process (String profile) throws TransactionNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String[] userArray = scanner.nextLine().split(" ");
        while (true) {
            try {
                if (userArray[0].equals("1") && userArray.length == 1)
                    userArray = addUsers(profile, scanner);
                else if (userArray[0].equals("2") && userArray.length == 1)
                    userArray = viewUserBalances(profile, scanner);
                else if (userArray[0].equals("3") && userArray.length == 1)
                    userArray = performTransfer(profile, scanner);
                else if (userArray[0].equals("4") && userArray.length == 1)
                    userArray = viewAllTransactions(profile, scanner);
                else if (userArray[0].equals("5") && profile.equals("dev") && userArray.length == 1)
                    userArray = removeTransfer(profile, scanner);
                else if (userArray[0].equals("6") && profile.equals("dev") && userArray.length == 1)
                    userArray = checkTransferValidity(profile, scanner);
                else if (userArray[0].equals("7") && userArray.length == 1) {
                    System.out.println("The program has completed its work!");
                    break;
                } else
                    userArray = reAddCommand(profile, scanner);
            } catch (ArrayIndexOutOfBoundsException | UserNotFoundException e) {
                userArray = reAddCommand(profile, scanner);
            }
        }
    }

    public void printOperations(Integer id) throws UserNotFoundException {
        for (Transaction a: vtb.setArrayTransaction(id)) {
            if (a.getSender().getId() == id) {
                if (a.getNameOperation().equals("credit"))
                    System.out.println("To " + a.getRecipient().getName() + "(id = " + a.getRecipient().getId() + ") " + a.getTransferAmount() + " with id = " + a.getIdentifier());
                else if (a.getNameOperation().equals("debit"))
                    System.out.println("From " + a.getRecipient().getName() + "(id = " + a.getRecipient().getId() + ") " + a.getTransferAmount() + " with id = " + a.getIdentifier());
            }
        }
    }
}
