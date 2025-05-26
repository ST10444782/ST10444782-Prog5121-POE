/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaproject;



import javax.swing.JOptionPane;
import java.util.ArrayList;
/**
 *
 * @author lab_services_student
 */
public class Messageclass {
    
  
    private String messageId;
    private String recipient;
    private String message;
    private static ArrayList<String> sentMessages = new ArrayList<>();

    public Messageclass(String messageId, String recipient, String message) {
        this.messageId = messageId;
        this.recipient = recipient;
        this.message = message;
    }

    // Check if message ID is not more than 10 characters
    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }

    // Check if recipient cell number is no more than 10 characters and starts with +
    public int checkRecipientCell() {
        if (recipient == null || recipient.length() > 10 || !recipient.startsWith("+")) {
            return -1; // Invalid
        }
        return 0; // Valid
    }

    // Create and return the Message Hash
    public String createMessageHash() {
        if (message == null || message.isEmpty()) return "00:EMPTY";
        String[] words = message.split("\\s+");
        String hash = "00:" + (words.length > 0 ? words[0].toUpperCase() : "") + 
                     (words.length > 1 ? words[words.length - 1].toUpperCase() : "");
        return hash;
    }

    // Allow user to choose to send, store, or disregard the message
    public String sendMessage() {
        String[] options = {"Send Message", "Store Message", "Disregard Message"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an option for the message:", "Send Message",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        if (choice == 0) { // Send
            sentMessages.add("MessageID: " + messageId + ", Recipient: " + recipient + ", Message: " + message + ", Hash: " + createMessageHash());
            return "Message sent";
        } else if (choice == 1) { // Store
            return "Message stored for later";
        } else if (choice == 2) { // Disregard
            return "Message disregarded";
        }
        return "No action taken";
    }

    // Return a list of all messages sent
    public String printMessages() {
        return String.join("\n", sentMessages);
    }

    // Return the total number of messages sent
    public int returnTotalMessages() {
        return sentMessages.size();
    }

    // Custom method to store messages in JSON (using ChatGPT suggestion)
    public void storeMessage() {
        String json = "{\n";
        json += "  \"messages\": [\n";
        for (int i = 0; i < sentMessages.size(); i++) {
            json += "    {\n";
            String[] parts = sentMessages.get(i).split(", ");
            for (String part : parts) {
                String[] keyValue = part.split(": ", 2);
                if (keyValue.length == 2) {
                    json += "      \"" + keyValue[0].replace("MessageID", "id").replace("Recipient", "recipient").replace("Message", "content").replace("Hash", "hash") + "\": \"" + keyValue[1] + "\"";
                    if (!part.equals(parts[parts.length - 1])) json += ",";
                    json += "\n";
                }
            }
            json += "    }" + (i < sentMessages.size() - 1 ? "," : "") + "\n";
        }
        json += "  ]\n}";
        JOptionPane.showMessageDialog(null, "JSON Stored:\n" + json);
        
    }

    
    
}
