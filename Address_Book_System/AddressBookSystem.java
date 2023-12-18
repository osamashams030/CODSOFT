package AddressBookSystem;

import java.util.List;
import java.util.Scanner;

public class AddressBookSystem {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();

        while (true) {
            System.out.println("1. Add Contact");
            System.out.println("2. Remove Contact");
            System.out.println("3. Search Contact");
            System.out.println("4. Display All Contacts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Add Contact--------------------

                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email address: ");
                    String emailAddress = scanner.nextLine();

                    Contact newContact = new Contact(name, phoneNumber, emailAddress);
                    addressBook.addContact(newContact);
                    break;

                case 2:
                    // Remove Contact-------------------
                    System.out.print("Enter Contact Name to remove: ");
                    String removeName = scanner.nextLine();
                    Contact contactToRemove = addressBook.searchContact(removeName);
                    if (contactToRemove != null) {
                        addressBook.removeContact(contactToRemove);
                        System.out.println("Contact removed successfully.");
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;

                case 3:
                    // Search Contact---------------------
                    System.out.print("Enter Contact Name to Search: ");
                    String searchName = scanner.nextLine();
                    Contact foundContact = addressBook.searchContact(searchName);
                    if (foundContact != null) {
                        System.out.println(
                                "Contact found: " + foundContact.getName() + " - " + foundContact.getPhoneNumber()
                                        + " - "
                                        + foundContact.getEmailAddress());

                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;

                case 4:
                    // Display All Contacts---------------
                    List<Contact> allContacts = addressBook.getAllContacts();
                    System.out.println("All Contacts:");
                    for (Contact contact : allContacts) {
                        System.out.println(contact.getName() + " - " + contact.getPhoneNumber() + " - "
                                + contact.getEmailAddress());
                    }
                    break;

                case 5:
                    // Code to Exit---------------
                    System.out.println("Exiting Address Book System. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
