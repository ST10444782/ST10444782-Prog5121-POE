/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.javaproject;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class JavaProject {
    // Stored data for user credentials and messages
    static String savedUsername;
    static String savedPassword;
    static String savedPhoneNumber;
    static int maxMessages;
    static int messagesSent = 0;

    // Check username: must contain underscore and be <= 5 characters
    public static boolean checkUsername(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // Check password: must be >= 8 characters with a capital, number, and special character
    public static boolean checkPasswordComplexity(String password) {
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

    // Check phone number: must match regex with international code
    public static boolean checkCellPhoneNumber(String phoneNumber) {
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

    // Verify login credentials
    public static boolean loginUser(String username, String password) {
        return savedUsername != null && savedPassword != null &&
               username.equals(savedUsername) && password.equals(savedPassword);
    }

    // Return login status message
    public static String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            String firstName = username.split("_")[0];
            String lastName = username.split("_")[1];
            return "Welcome <" + firstName + ">, <" + lastName + "> it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    
    public static void displayMessages() {
        JOptionPane.showMessageDialog(null, "Coming Soon.");
    }

    public static void main(String[] args) {
        // Collect and validate user credentials
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
                // Initialize max messages 
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
                // loop: Will continue until max messages are sent
                while (messagesSent < maxMessages) {
                    String choiceInput = JOptionPane.showInputDialog("\nMenu:\n1. Send Messages\n2. Show recently sent messages\n3. Quit\nEnter your choice (1-3): ");
                    if (choiceInput == null) {
                        JOptionPane.showMessageDialog(null, "Menu cancelled.");
                        return;
                    }
                    int choice;
                    try {
                        choice = Integer.parseInt(choiceInput);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 3.");
                        continue;
                    }

                    switch (choice) {
                        case 1:
                            String recipient = JOptionPane.showInputDialog("Enter recipient phone number: ");
                            if (recipient == null) continue;
                            String message = JOptionPane.showInputDialog("Enter message: ");
                            if (message == null) continue;

                            
                            String messageId = String.format("%010d", messagesSent + 1); // Use loop counter
                            Messageclass msg = new Messageclass(messageId, recipient, message);
                            if (!msg.checkMessageID() || msg.checkRecipientCell() == -1) {
                                JOptionPane.showMessageDialog(null, "Invalid Message ID or Recipient format.");
                                break;
                            }

                            // Sends message and stores it
                            String result = msg.sendMessage();
                            JOptionPane.showMessageDialog(null, result);
                            if (result.equals("Message sent")) {
                                messagesSent++;
                                msg.storeMessage(); // Store as JSON (attributed in Message class)
                            }
                            break;
                        case 2:
                            displayMessages();
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(null, "Total number of messages sent: " + messagesSent + "\n" + new Messageclass("", "", "").printMessages());
                            return;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid choice, please enter 1, 2, or 3.");
                    }
                }
                JOptionPane.showMessageDialog(null, "Total number of messages sent: " + messagesSent + "\n" + new Messageclass("", "", "").printMessages());
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