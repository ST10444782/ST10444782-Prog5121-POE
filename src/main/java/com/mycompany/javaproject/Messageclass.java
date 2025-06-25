/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.javaproject;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Messageclass {
    
    private String messageId;    // Unique identifier for the message
    private String recipient;    // Recipient's phone number
    private String message;      // The message content

    public Messageclass(String messageId, String recipient, String message) {
        this.messageId = messageId;
        this.recipient = recipient;
        this.message = message;
    }

    // Check if the message ID is not longer than 10 characters
    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }

    // Verify if the recipient's phone number is valid (starts with + and is <= 10 characters)
    public int checkRecipientCell() {
        if (recipient == null || recipient.length() > 10 || !recipient.startsWith("+")) {
            return -1; // Invalid
        }
        return 0; // Valid
    }

    // Generate a hash based on the first and last words of the message
    public String createMessageHash() {
        if (message == null || message.isEmpty()) return "00:EMPTY";
        String[] words = message.split("\\s+");
        String hash = "00:" + (words.length > 0 ? words[0].toUpperCase() : "") + 
                     (words.length > 1 ? words[words.length - 1].toUpperCase() : "");
        return hash;
    }

    // Lets the user decide whether to send, store, or disregard the message
    public String sendMessage() {
        String[] options = {"Send Message", "Store Message", "Disregard Message"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an option for the message:", "Send Message",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        String hash = createMessageHash();
        String messageDetail = "Recipient: " + recipient + ", Message: " + message; // Format for Messages array
        String flag; // To determine the message category

        if (choice == 0) { 
            flag = "Sent";
        } else if (choice == 1) { 
            flag = "Stored";
        } else if (choice == 2) { 
            flag = "Disregarded";
        } else {
            return "No action taken";
        }

        // Add to parallel arrays in JavaProject
        JavaProject.MessageID.add(messageId);
        JavaProject.MessageHash.add(hash);
        JavaProject.Messages.add(messageDetail);
        JavaProject.MessageFlags.add(flag);
        storeMessage(); // Save to JSON
        return "Message " + flag.toLowerCase() + (flag.equals("Sent") ? "" : " for later");
    }

    // Display a list of all messages categorized by type
    public String printMessages() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < JavaProject.Messages.size(); i++) {
            if (JavaProject.MessageFlags.get(i).equals("Sent")) {
                result.append("Sent: ").append(JavaProject.Messages.get(i)).append("\n");
            } else if (JavaProject.MessageFlags.get(i).equals("Stored")) {
                result.append("Stored: ").append(JavaProject.Messages.get(i)).append("\n");
            } else if (JavaProject.MessageFlags.get(i).equals("Disregarded")) {
                result.append("Disregarded: ").append(JavaProject.Messages.get(i)).append("\n");
            }
        }
        return result.toString();
    }

    // Count the total number of messages across all categories
    public int returnTotalMessages() {
        int sentCount = (int) JavaProject.MessageFlags.stream().filter(f -> f.equals("Sent")).count();
        int storedCount = (int) JavaProject.MessageFlags.stream().filter(f -> f.equals("Stored")).count();
        int disregardedCount = (int) JavaProject.MessageFlags.stream().filter(f -> f.equals("Disregarded")).count();
        return sentCount + storedCount + disregardedCount;
    }

    // Save all messages to a JSON format for display
    public void storeMessage() {
        String json = "{\n  \"messages\": [\n";
        for (int i = 0; i < JavaProject.Messages.size(); i++) {
            json += "    {\n";
            String[] parts = JavaProject.Messages.get(i).split(", ");
            for (String part : parts) {
                String[] keyValue = part.split(": ", 2);
                if (keyValue.length == 2) {
                    json += "      \"" + keyValue[0].replace("Recipient", "recipient").replace("Message", "content") + "\": \"" + keyValue[1] + "\"";
                    if (!part.equals(parts[parts.length - 1])) json += ",";
                    json += "\n";
                }
            }
            json += "      \"id\": \"" + JavaProject.MessageID.get(i) + "\",\n";
            json += "      \"hash\": \"" + JavaProject.MessageHash.get(i) + "\",\n";
            json += "      \"flag\": \"" + JavaProject.MessageFlags.get(i) + "\"\n";
            json += "    }" + (i < JavaProject.Messages.size() - 1 ? "," : "") + "\n";
        }
        json += "  ]\n}";
        JOptionPane.showMessageDialog(null, "JSON Stored:\n" + json);
    }
}