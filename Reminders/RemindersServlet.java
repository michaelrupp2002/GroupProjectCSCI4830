package reminders;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

@WebServlet("/Reminders")
public class RemindersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://ec2-3-16-149-43.us-east-2.compute.amazonaws.com:3306/UserData";
    private static final String JDBC_USER = "diethelper_remote";
    private static final String JDBC_PASSWORD = "diethelper";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in
        String username = (String) request.getSession().getAttribute("name");
        if (username == null) {
            // If not logged in, redirect to the login page
            response.sendRedirect("login.jsp");
            return;
        }

        // Process form submission
        String[] remindersKeys = {"DrinkWater", "Eatmeal", "BrushTeeth", "Steps", "Exercise"};
        Map<String, Integer> remindersMap = new HashMap<>();
        for (String key : remindersKeys) {
            String value = request.getParameter(key);
            if (value != null && value.equals("1")) {
                remindersMap.put(key, 1);
            } else {
                remindersMap.put(key, 0); // Mark as unchecked
            }
        }

        // Store data in the database
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String insertQuery = "INSERT INTO remindersData (username, DrinkWater, Eatmeal, BrushTeeth, Steps, Exercise) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, username);
                for (int i = 0; i < remindersKeys.length; i++) {
                    statement.setInt(i + 2, remindersMap.get(remindersKeys[i]));
                }
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        // Redirect back to the reminders page
        response.sendRedirect(request.getContextPath() + "/Reminders");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("name");
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }
       
        Map<String, Integer> remindersMap = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String selectQuery = "SELECT * FROM remindersData WHERE username = ? ORDER BY id DESC LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    
                    remindersMap.put("DrinkWater", resultSet.getInt("DrinkWater"));
                    remindersMap.put("Eatmeal", resultSet.getInt("Eatmeal"));
                    remindersMap.put("BrushTeeth", resultSet.getInt("BrushTeeth"));
                    remindersMap.put("Steps", resultSet.getInt("Steps"));
                    remindersMap.put("Exercise", resultSet.getInt("Exercise"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        // Set attributes for the form fields in the request
        request.setAttribute("DrinkWater", remindersMap.getOrDefault("DrinkWater", 0));
        request.setAttribute("Eatmeal", remindersMap.getOrDefault("Eatmeal", 0));
        request.setAttribute("BrushTeeth", remindersMap.getOrDefault("BrushTeeth", 0));
        request.setAttribute("Steps", remindersMap.getOrDefault("Steps", 0));
        request.setAttribute("Exercise", remindersMap.getOrDefault("Exercise", 0));

        // Forward the request to reminders.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/reminders.jsp");
        dispatcher.forward(request, response);
    }
}