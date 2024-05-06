package reminders;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.annotation.WebServlet;

@WebServlet("/ResetReminder")
public class ResetReminder implements Job {

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://ec2-3-16-149-43.us-east-2.compute.amazonaws.com:3306/UserData";
    private static final String JDBC_USER = "diethelper_remote";
    private static final String JDBC_PASSWORD = "diethelper";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            // Get the timestamp for the first input of the specific username
            String username = "specific_username"; // Replace with the username you want to target
            LocalDateTime timestamp = getFirstInputTimestamp(connection, username);

            if (timestamp != null) {
                LocalDateTime now = LocalDateTime.now();
                // Check if 24 hours have passed since the first input
                if (Duration.between(timestamp, now).toHours() >= 24) {
                    resetRemindersForUsername(connection, username);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private LocalDateTime getFirstInputTimestamp(Connection connection, String username) throws SQLException {
        // Get the timestamp of the first input for the specified username
        String selectQuery = "SELECT timestamp FROM remindersData WHERE username = ? ORDER BY timestamp ASC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getTimestamp("timestamp").toLocalDateTime();
            }
        }
        return null;
    }

    private void resetRemindersForUsername(Connection connection, String username) throws SQLException {
        // Reset the remindersData table entries for the specified username
        String resetQuery = "DELETE FROM remindersData WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(resetQuery)) {
            statement.setString(1, username);
            statement.executeUpdate();
        }
    }

    public static void main(String[] args) {
        try {
            // Create a Quartz Scheduler instance
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // Define a trigger that runs every hour
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("hourlyTrigger", "group1")
                    .withSchedule(SimpleScheduleBuilder.repeatHourlyForever())
                    .build();

            // Define the job detail
            JobDetail job = JobBuilder.newJob(ResetReminder.class)
                    .withIdentity("resetRemindersJob", "group1")
                    .build();

            // Schedule the job with the trigger
            scheduler.scheduleJob(job, trigger);

            // Start the scheduler
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
}