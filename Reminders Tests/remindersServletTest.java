package Tests;

import static org.junit.Assert.assertEquals;

import java.io.*;

import java.util.HashMap;

import java.util.Map;


import javax.servlet.ServletException;


import org.junit.Test;

import reminders.RemindersServlet;

public class remindersServletTest {

    @Test
    public void testDoGet() throws ServletException, IOException {
        HttpServletRequestStub request = new HttpServletRequestStub();
        HttpServletResponseStub response = new HttpServletResponseStub();

        request.setAttribute("name", "Test1");

        // Stub database data
        Map<String, Integer> remindersMap = new HashMap<>();
        remindersMap.put("DrinkWater", 1);
        remindersMap.put("Eatmeal", 0);
        remindersMap.put("BrushTeeth", 1);
        remindersMap.put("Steps", 0);
        remindersMap.put("Exercise", 1);

        RemindersServlet servlet = new RemindersServlet();
        servlet.doGet(request, response);

        // Verify that the request attributes are set correctly
        assertEquals(1, request.getAttribute("DrinkWater"));
        assertEquals(0, request.getAttribute("Eatmeal"));
        assertEquals(1, request.getAttribute("BrushTeeth"));
        assertEquals(0, request.getAttribute("Steps"));
        assertEquals(1, request.getAttribute("Exercise"));
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        HttpServletRequestStub request = new HttpServletRequestStub();
        HttpServletResponseStub response = new HttpServletResponseStub();

        // Set session attribute
        request.setAttribute("name", "testUser");

        // Set parameters for form submission
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("DrinkWater", new String[]{"1"});
        parameters.put("Eatmeal", new String[]{"0"});
        parameters.put("BrushTeeth", new String[]{"1"});
        parameters.put("Steps", new String[]{"0"});
        parameters.put("Exercise", new String[]{"1"});
        request.setParameters(parameters);

        RemindersServlet servlet = new RemindersServlet();
        servlet.doPost(request, response);

        // Add your assertions to validate the behavior after doPost is called
    }
}