/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javaproject;






import java.util.Scanner;

public class JavaProject {
    // Stored data
    static String savedUsername;
    static String savedPassword;
    static String savedPhoneNumber;

    // Check username contains underscore and is no more than 5 characters
    public static boolean checkUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Check password meets complexity rules
    public static boolean checkPasswordComplexity(String password) {
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

    // Check phone number using regex (international code + number <= 10 digits)
    public static boolean checkCellPhoneNumber(String phoneNumber) {
        // Regex: Starts with "+" followed by 1-3 digits (country code), then up to 10 digits
        String regex = "^\\+\\d{1,3}\\d{1,10}$";
        return phoneNumber.matches(regex) && phoneNumber.length() <= 14; // Total length: +XXX + 10 digits = max 14
    }

    // Register user
    public static String registerUser(String username, String password, String phoneNumber) {
        String message = "";

        if (checkUsername(username)) {
            savedUsername = username;
            message += "Username successfully captured.\n";
        } else {
            message += "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.\n";
        }

        if (checkPasswordComplexity(password)) {
            savedPassword = password;
            message += "Password successfully captured.\n";
        } else {
            message += "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n";
        }

        if (checkCellPhoneNumber(phoneNumber)) {
            savedPhoneNumber = phoneNumber;
            message += "Cell phone number successfully added.\n";
        } else {
            message += "Cell phone number incorrectly formatted or does not contain international code, please correct the number and try again.\n";
        }

        return message;
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

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Registration Phase
        System.out.println("\tWelcome to the TechFest RSVP System!");
        System.out.println("Enter your username (must contain an underscore and be no more than 5 characters): ");
        String username = input.nextLine();

        System.out.println("Enter your password (at least 8 characters, with a capital letter, a number, and a special character): ");
        String password = input.nextLine();

        System.out.println("Enter your phone number (format: international code like +27 followed by up to 10 digits): ");
        String phoneNumber = input.nextLine();

        System.out.println("\n--- REGISTRATION ---");
        System.out.println("Username = " + username + ", Password = " + password + ", Phone Number = " + phoneNumber);
        System.out.println("--- Check-In ---");
        System.out.println(registerUser(username, password, phoneNumber));

        // Login Phase
        System.out.println("\n--- LOGIN ---");
        System.out.println("Enter username for login: ");
        String loginUsername = input.nextLine();

        System.out.println("Enter password for login: ");
        String loginPassword = input.nextLine();

        System.out.println("\n--- Login Status ---");
        System.out.println(returnLoginStatus(loginUsername, loginPassword));
    }
}

/*
 * The regular expression in checkCellPhoneNumber() was developed with assistance from Grok, an AI tool created by xAI.
 * Citation: Grok by xAI. (n.d.). Assistance with regular expression for phone number validation. Retrieved April 15, 2025.
 * Reference format based on: https://apastyle.apa.org/blog/how-to-cite-chatgpt
 */