/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.javaproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lab_services_student
 */
public class JavaProjectTest {
    
    private JavaProject proj = new JavaProject();
    
    public JavaProjectTest() {
        
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
        boolean result =proj.checkPasswordComplexity(password);
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
        String username = "kyl_1";
        String password = "Ch&sec@ke99!";
        String phoneNumber = "+27838968976";
        String expectedResult = "Username successfully captured.\n" +
                               "Password successfully captured.\n" +
                               "Cell number successfully captured.\n";
        String result = proj.registerUser(username, password, phoneNumber);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testLoginUserSuccessful() {
       proj.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976");
        String username = "kyl_1";
        String password = "Ch&sec@ke99!";
        boolean expectedResult = true;
        boolean result = proj.loginUser(username, password);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testLoginUserFailed() {
        proj.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976");
        String username = "kyl_1";
        String password = "wrongPassword";
        boolean expectedResult = false;
        boolean result = proj.loginUser(username, password);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testReturnLoginStatusSuccessful() {
        proj.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976");
        String username = "kyl_1";
        String password = "Ch&sec@ke99!";
        String expectedResult = "Welcome <kyl>, <1> it is great to see you again.";
        String result = proj.returnLoginStatus(username, password);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testReturnLoginStatusFailed() {
        proj.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976");
        String username = "kyl_1";
        String password = "wrongPassword";
        String expectedResult = "Username or password incorrect, please try again.";
        String result = proj.returnLoginStatus(username, password);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testMain() {
        
    }
}



   