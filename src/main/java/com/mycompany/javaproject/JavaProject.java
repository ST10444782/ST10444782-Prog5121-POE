/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javaproject;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class JavaProject {
    
    static String savedUsername;        
    static String savedPassword;        
    static String savedPhoneNumber;     
    static int maxMessages;             
    static int messagesSent = 0;        

    // Parallel arrays to manage message data consistently
    static ArrayList<String> MessageID = new ArrayList<>();         // Stores unique identifiers for each message
    static ArrayList<String> MessageHash = new ArrayList<>();       // Stores hash values for message identification
    static ArrayList<String> Messages = new ArrayList<>();          // Stores message details (recipient and content)
    static ArrayList<String> MessageFlags = new ArrayList<>();      // Indicates the status of each message (Sent, Stored, Disregarded)

    public static boolean checkUsername(String username) {
        
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        // Checks if the password meets the following (8+ chars, capital, number, special character)
        if (password == null) return false;
        boolean hasLength = password.length() >= 8;
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            if (Character.isDigit(c)) hasNumber = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasLength && hasCapital && hasNumber && hasSpecial;
    }

    public static boolean checkCellPhoneNumber(String phoneNumber) {
        // Checks if the phone number format (starts with +, up to 14 characters total)
        if (phoneNumber == null) return false;
        String regex = "^\\+\\d{1,3}\\d{1,10}$";
        return phoneNumber.matches(regex) && phoneNumber.length() <= 14;
    }

    public static String registerUser() {
        
        String username;
        do {
            username = JOptionPane.showInputDialog("Enter your username (must contain an underscore and be no more than 5 characters): ");
            if (username == null) return "Registration cancelled.";
            if (!checkUsername(username)) {
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.");
            }
        } while (!checkUsername(username));

        String password;
        do {
            password = JOptionPane.showInputDialog("Enter your password (at least 8 characters, with a capital letter, a number, and a special character): ");
            if (password == null) return "Registration cancelled.";
            if (!checkPasswordComplexity(password)) {
                JOptionPane.showMessageDialog(null, "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        } while (!checkPasswordComplexity(password));

        String phoneNumber;
        do {
            phoneNumber = JOptionPane.showInputDialog("Enter your phone number (format: international code like +27 followed by up to 10 digits): ");
            if (phoneNumber == null) return "Registration cancelled.";
            if (!checkCellPhoneNumber(phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Cell phone number incorrectly formatted or does not contain international code, please correct the number and try again.");
            }
        } while (!checkCellPhoneNumber(phoneNumber));

        savedUsername = username;
        savedPassword = password;
        savedPhoneNumber = phoneNumber;
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }

    public static boolean loginUser(String username, String password) {
        // checks if the entered details match up to the stored ones
        return savedUsername != null && savedPassword != null &&
               username.equals(savedUsername) && password.equals(savedPassword);
    }

    public static String returnLoginStatus(String username, String password) {
     
        if (loginUser(username, password)) {
            String firstName = username.split("_")[0];
            String lastName = username.split("_")[1];
            return "Welcome <" + firstName + ">, <" + lastName + "> it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Shows the sender and recipient for all sent messages
    public static void displaySendersRecipients() {
        StringBuilder sb = new StringBuilder("Sent Messages:\n");
        for (int i = 0; i < Messages.size(); i++) {
            if (MessageFlags.get(i).equals("Sent")) {
                String[] parts = Messages.get(i).split(", ");
                sb.append("Sender: ").append(savedPhoneNumber).append(", Recipient: ").append(parts[0].replace("Recipient: ", "")).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    
    public static void displayLongestMessage() {
        String longest = "No messages";
        int maxLength = -1;
        for (int i = 0; i < Messages.size(); i++) {
            if (MessageFlags.get(i).equals("Sent") && Messages.get(i).length() > maxLength) {
                maxLength = Messages.get(i).length();
                longest = Messages.get(i);
            }
        }
        JOptionPane.showMessageDialog(null, "Longest Message: " + longest);
    }

    // Searches for a message by its ID and shows the recipient and message
    public static void searchByMessageID(String id) {
        for (int i = 0; i < MessageID.size(); i++) {
            if (MessageID.get(i).equals(id)) {
                String msg = Messages.get(i);
                JOptionPane.showMessageDialog(null, "Recipient: " + msg.split(", ")[0].replace("Recipient: ", "") + "\nMessage: " + msg.split(", ")[1].replace("Message: ", ""));
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }

    // Looks up all messages for a specific recipient
    public static void searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder("Messages for " + recipient + ":\n");
        boolean found = false;
        for (int i = 0; i < Messages.size(); i++) {
            if (Messages.get(i).contains("Recipient: " + recipient)) {
                sb.append("ID: ").append(MessageID.get(i)).append(", ").append(Messages.get(i)).append(" (").append(MessageFlags.get(i)).append(")\n");
                found = true;
            }
        }
        if (found) JOptionPane.showMessageDialog(null, sb.toString());
        else JOptionPane.showMessageDialog(null, "No messages found for " + recipient);
    }

    // Removes a message based on its hash value
    public static void deleteMessage(String hash) {
        for (int i = 0; i < MessageHash.size(); i++) {
            if (MessageHash.get(i).equals(hash)) {
                MessageID.remove(i);
                MessageHash.remove(i);
                Messages.remove(i);
                MessageFlags.remove(i);
                JOptionPane.showMessageDialog(null, "Message deleted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Hash not found.");
    }

    // This creates a report for all the messages that are sent
    public static void displayReport() {
        StringBuilder sb = new StringBuilder("Full Report of Sent Messages:\n");
        for (int i = 0; i < Messages.size(); i++) {
            if (MessageFlags.get(i).equals("Sent")) {
                sb.append("ID: ").append(MessageID.get(i)).append(", Hash: ").append(MessageHash.get(i)).append(", ").append(Messages.get(i)).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static void displayMessages() {
        // Lists all recently sent messages
        StringBuilder sb = new StringBuilder("Recently Sent Messages:\n");
        for (int i = 0; i < Messages.size(); i++) {
            if (MessageFlags.get(i).equals("Sent")) {
                sb.append("ID: ").append(MessageID.get(i)).append(", ").append(Messages.get(i)).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static void main(String[] args) {
        
        JOptionPane.showMessageDialog(null, "\tWelcome to the TechFest RSVP System!");
        String registrationMessage = registerUser();
        if (registrationMessage.contains("cancelled")) {
            JOptionPane.showMessageDialog(null, "Registration failed.");
            return;
        }
        JOptionPane.showMessageDialog(null, "--- REGISTRATION ---\nUsername = " + savedUsername + ", Password = " + "****", "--- Check-In ---" + registrationMessage, JOptionPane.INFORMATION_MESSAGE);

        while (true) {
            
            String loginUsername = JOptionPane.showInputDialog("--- LOGIN ---\nEnter username for login: ");
            if (loginUsername == null) {
                JOptionPane.showMessageDialog(null, "Login cancelled.");
                return;
            }
            String loginPassword = JOptionPane.showInputDialog("Enter password for login: ");
            if (loginPassword == null) {
                JOptionPane.showMessageDialog(null, "Login cancelled.");
                return;
            }

            String loginStatus = returnLoginStatus(loginUsername, loginPassword);
            JOptionPane.showMessageDialog(null, "--- Login Status ---\n" + loginStatus);

            if (loginStatus.startsWith("Welcome")) {
                // Set the message limit if not already defined
                if (maxMessages == 0) {
                    String maxMsgInput = JOptionPane.showInputDialog("How many messages would you like to enter? ");
                    if (maxMsgInput == null) {
                        JOptionPane.showMessageDialog(null, "Message limit cancelled.");
                        return;
                    }
                    try {
                        maxMessages = Integer.parseInt(maxMsgInput);
                        if (maxMessages <= 0) {
                            JOptionPane.showMessageDialog(null, "Please enter a positive number of messages.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
                        continue;
                    }
                }

                JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
                while (messagesSent < maxMessages) {
                    // Present the user with a menu of options
                    String choiceInput = JOptionPane.showInputDialog("\nMenu:\n1. Send Messages\n2. Show recently sent messages\n3. Display senders/recipients\n4. Display longest message\n5. Search by Message ID\n6. Search by Recipient\n7. Delete Message\n8. Display Report\n9. Quit\nEnter your choice (1-9): ");
                    if (choiceInput == null) {
                        JOptionPane.showMessageDialog(null, "Menu cancelled.");
                        return;
                    }
                    int choice;
                    try {
                        choice = Integer.parseInt(choiceInput);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 9.");
                        continue;
                    }

                    switch (choice) {
                        case 1:
                            
                            String recipient = JOptionPane.showInputDialog("Enter recipient phone number: ");
                            if (recipient == null) continue;
                            String message = JOptionPane.showInputDialog("Enter message: ");
                            if (message == null) continue;

                            String messageId = String.format("%010d", messagesSent + 1);
                            Messageclass msg = new Messageclass(messageId, recipient, message);
                            if (!msg.checkMessageID() || msg.checkRecipientCell() == -1) {
                                JOptionPane.showMessageDialog(null, "Invalid Message ID or Recipient format.");
                                break;
                            }

                            // Use Messageclass to process the message
                            String result = msg.sendMessage();
                            JOptionPane.showMessageDialog(null, result);
                            if (result.toLowerCase().contains("sent")) {
                                messagesSent++;
                            }
                            break;
                        case 2:
                            displayMessages();
                            break;
                        case 3:
                            displaySendersRecipients();
                            break;
                        case 4:
                            displayLongestMessage();
                            break;
                        case 5:
                            String id = JOptionPane.showInputDialog("Enter Message ID: ");
                            if (id != null) searchByMessageID(id);
                            break;
                        case 6:
                            String rec = JOptionPane.showInputDialog("Enter Recipient: ");
                            if (rec != null) searchByRecipient(rec);
                            break;
                        case 7:
                            String hash = JOptionPane.showInputDialog("Enter Message Hash: ");
                            if (hash != null) deleteMessage(hash);
                            break;
                        case 8:
                            displayReport();
                            break;
                        case 9:
                            JOptionPane.showMessageDialog(null, "Total number of messages sent: " + messagesSent);
                            return;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid choice, please enter 1-9.");
                    }
                }
                JOptionPane.showMessageDialog(null, "Total number of messages sent: " + messagesSent);
                break;
            }
        }
    }
}
/*
 * The regular expression in checkCellPhoneNumber() was developed with assistance from Grok, an AI tool created by xAI.
 * Citation: Grok by xAI. (n.d.). Assistance with regular expression for phone number validation. Retrieved April 15, 2025.
 * Reference format based on: https://apastyle.apa.org/blog/how-to-cite-chatgpt
 */
