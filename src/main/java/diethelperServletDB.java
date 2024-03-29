

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class diethelperServletDB
 */
@WebServlet("/diethelperServletDB")
public class diethelperServletDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String url = "jdbc:mysql://ec2-3-16-149-43.us-east-2.compute.amazonaws.com:3306/";
	static String user = "diethelper_remote";
	static String password = "diethelper";
	static Connection connection = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public diethelperServletDB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	      response.setContentType("text/html;charset=UTF-8");
	      response.getWriter().println("------------ MySQL JDBC Connection Testing ------------<br><br>");
	      try {
	         Class.forName("com.mysql.cj.jdbc.Driver"); //old:com.mysql.jdbc.Driver
	      } catch (ClassNotFoundException e) {
	         System.out.println("Where is your MySQL JDBC Driver?");
	         e.printStackTrace();
	         return;
	      }
	      response.getWriter().println("MySQL JDBC Driver Registered!<br>");
	      connection = null;
	      try {
	         connection = DriverManager.getConnection(url, user, password);
	      } catch (SQLException e) {
	         System.out.println("Connection Failed! Check output console");
	         e.printStackTrace();
	         return;
	      }
	      if (connection != null) {
	         response.getWriter().println("You made it, take control your database now!<br>");
	      } else {
	         System.out.println("Failed to make connection!");
	      }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
