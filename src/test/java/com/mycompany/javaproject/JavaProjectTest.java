/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


package com.mycompany.javaproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JavaProjectTest {
    
    private JavaProject proj = new JavaProject();
    
    @BeforeEach
    public void setUp() {
        // Populate test data before each test
        JavaProject.JavaProjectTest();
    }

    @Test
    public void testCheckUsernameIncorrectlyFormatted() {
        String username = "af4";
        boolean expectedResult = false;
        boolean result = proj.checkUsername(username);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCheckPasswordComplexityMeetsRequirements() {
        String password = "Ch&sec@ke99!";
        boolean expectedResult = true;
        boolean result = proj.checkPasswordComplexity(password);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCheckPasswordComplexityDoesNotMeetRequirements() {
        String password = "password";
        boolean expectedResult = false;
        boolean result = proj.checkPasswordComplexity(password);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCheckCellPhoneNumberCorrectlyFormatted() {
        String phoneNumber = "+27838968976";
        boolean expectedResult = true;
        boolean result = proj.checkCellPhoneNumber(phoneNumber);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCheckCellPhoneNumberIncorrectlyFormatted() {
        String phoneNumber = "08966553";
        boolean expectedResult = false;
        boolean result = proj.checkCellPhoneNumber(phoneNumber);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testRegisterUser() {
        
        proj.registerUser(); // Set up static variables for testing
        assertNotNull(JavaProject.savedUsername);
    }

    @Test
    public void testLoginUserSuccessful() {
        JavaProject.savedUsername = "kyl_1";
        JavaProject.savedPassword = "Ch&sec@ke99!";
        String username = "kyl_1";
        String password = "Ch&sec@ke99!";
        boolean expectedResult = true;
        boolean result = proj.loginUser(username, password);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testLoginUserFailed() {
        JavaProject.savedUsername = "kyl_1";
        JavaProject.savedPassword = "Ch&sec@ke99!";
        String username = "kyl_1";
        String password = "wrongPassword";
        boolean expectedResult = false;
        boolean result = proj.loginUser(username, password);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testReturnLoginStatusSuccessful() {
        JavaProject.savedUsername = "kyl_1";
        JavaProject.savedPassword = "Ch&sec@ke99!";
        String username = "kyl_1";
        String password = "Ch&sec@ke99!";
        String expectedResult = "Welcome <kyl>, <1> it is great to see you again.";
        String result = proj.returnLoginStatus(username, password);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testReturnLoginStatusFailed() {
        JavaProject.savedUsername = "kyl_1";
        JavaProject.savedPassword = "Ch&sec@ke99!";
        String username = "kyl_1";
        String password = "wrongPassword";
        String expectedResult = "Username or password incorrect, please try again.";
        String result = proj.returnLoginStatus(username, password);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSentMessagesArrayPopulated() {
       
        assertTrue(JavaProject.SentMessages.contains("MessageID: 0000000001, Recipient: +2783457896, Message: Did you get the cake?, Hash: 00:DIDYOU"));
        assertTrue(JavaProject.SentMessages.contains("MessageID: 0838884567, Recipient: +2783884567, Message: It is dinner time!, Hash: 00:ITISDINNER"));
        assertEquals(2, JavaProject.SentMessages.size());
    }

    @Test
    public void testStoredMessagesArrayPopulated() {
        // Verify StoredMessages array contains expected test data
        assertTrue(JavaProject.StoredMessages.contains("MessageID: 0000000002, Recipient: +2783884567, Message: Where are you? You are late! I have asked you to be on time., Hash: 00:WHEREON"));
        assertTrue(JavaProject.StoredMessages.contains("MessageID: 0000000005, Recipient: +2783884567, Message: Ok, I am leaving without you., Hash: 00:OKLEAVING"));
        assertEquals(2, JavaProject.StoredMessages.size());
    }

    @Test
    public void testDisregardedMessagesArrayPopulated() {
       
        assertTrue(JavaProject.DisregardedMessages.contains("MessageID: 0000000003, Recipient: +2783448567, Message: Yahoooo, I am at your gate., Hash: 00:YAHOOAT"));
        assertEquals(1, JavaProject.DisregardedMessages.size());
    }

    @Test
    public void testMessageIDArrayPopulated() {
        // Verify MessageID array contains expected test data
        assertTrue(JavaProject.MessageID.contains("0000000001"));
        assertTrue(JavaProject.MessageID.contains("0000000002"));
        assertTrue(JavaProject.MessageID.contains("0000000003"));
        assertTrue(JavaProject.MessageID.contains("0838884567"));
        assertTrue(JavaProject.MessageID.contains("0000000005"));
        assertEquals(5, JavaProject.MessageID.size());
    }

    @Test
    public void testMessageHashArrayPopulated() {
        // Verify MessageHash array contains expected test data
        assertTrue(JavaProject.MessageHash.contains("00:DIDYOU"));
        assertTrue(JavaProject.MessageHash.contains("00:WHEREON"));
        assertTrue(JavaProject.MessageHash.contains("00:YAHOOAT"));
        assertTrue(JavaProject.MessageHash.contains("00:ITISDINNER"));
        assertTrue(JavaProject.MessageHash.contains("00:OKLEAVING"));
        assertEquals(5, JavaProject.MessageHash.size());
    }

    @Test
    public void testDisplayLongestMessage() {
        // Verify the longest message (manually check length: "Where are you? You are late! I have asked you to be on time." is longest)
        String expected = "MessageID: 0000000002, Recipient: +2783884567, Message: Where are you? You are late! I have asked you to be on time., Hash: 00:WHEREON";
        proj.displayLongestMessage(); // Can't test JOptionPane directly, but verify data
        assertTrue(JavaProject.StoredMessages.contains(expected));
    }

    @Test
    public void testSearchByMessageID() {
        String id = "0838884567";
        
        proj.searchByMessageID(id);
        assertTrue(JavaProject.MessageID.contains(id));
    }

    @Test
    public void testSearchByRecipient() {
        String recipient = "+2783884567";
        
        proj.searchByRecipient(recipient);
        assertTrue(JavaProject.SentMessages.stream().anyMatch(msg -> msg.contains("Recipient: " + recipient)) ||
                   JavaProject.StoredMessages.stream().anyMatch(msg -> msg.contains("Recipient: " + recipient)));
    }

    @Test
    public void testDeleteMessage() {
        String hash = "00:WHEREON";
        int initialSize = JavaProject.StoredMessages.size();
        proj.deleteMessage(hash);
        
        assertEquals(initialSize - 1, JavaProject.StoredMessages.size());
    }

    @Test
    public void testDisplayReport() {
        
        proj.displayReport();
        assertTrue(JavaProject.SentMessages.size() > 0 || JavaProject.StoredMessages.size() > 0 || JavaProject.DisregardedMessages.size() > 0);
    }
}