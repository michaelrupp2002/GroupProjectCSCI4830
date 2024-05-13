package registrationPage;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;

@WebServlet("/caloriesData")
public class caloriesData extends HttpServlet {
    private ArrayList<String[]> caloriesData;
    private int currentCaloriesIndex;

    @Override
    public void init() throws ServletException {
        String caloriesFile = getServletContext().getRealPath("/WEB-INF/lib/caloriesData.csv");

        caloriesData = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(caloriesFile))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                line[1] = line[1].replaceAll("cal", "");
                caloriesData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String meal = request.getParameter("meal");
        out.println("<!DOCTYPE html>\r\n"
    			+ "<html lang=\"en\">\r\n"
    			+ "<head>\r\n"
    			+ "<meta charset=\"utf-8\" />\r\n"
    			+ "<meta name=\"viewport\"\r\n"
    			+ "	content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />\r\n"
    			+ "<meta name=\"description\" content=\"\" />\r\n"
    			+ "<meta name=\"author\" content=\"\" />\r\n"
    			+ "<title>Diet Helpers Web</title>\r\n"
    			+ "\r\n"
    			+ "<!-- Favicon-->\r\n"
    			+ "<link rel=\"icon\" type=\"image/x-icon\" href=\"assets/favicon.ico\" />\r\n"
    			+ "\r\n"
    			+ "	\r\n"
    			+ "<!-- Core theme CSS (includes Bootstrap)-->\r\n"
    			+ "<link href=\"css/index-styles.css\" rel=\"stylesheet\" />\r\n"
    			+ "</head>\r\n"
    			+ "<body id=\"page-top\">\r\n"
    			+ "	<!-- Navigation-->\r\n"
    			+ "	<nav\r\n"
    			+ "		class=\"navbar navbar-expand-lg bg-secondary text-uppercase fixed-top\"\r\n"
    			+ "		id=\"mainNav\">\r\n"
    			+ "		<div class=\"container\">\r\n"
    			+ "			<a class=\"navbar-brand\" href=\"#page-top\">Diet Helpers</a>\r\n"
    			+ "			<button\r\n"
    			+ "				class=\"navbar-toggler text-uppercase font-weight-bold bg-primary text-white rounded\"\r\n"
    			+ "				type=\"button\" data-bs-toggle=\"collapse\"\r\n"
    			+ "				data-bs-target=\"#navbarResponsive\" aria-controls=\"navbarResponsive\"\r\n"
    			+ "				aria-expanded=\"false\" aria-label=\"Toggle navigation\">\r\n"
    			+ "				Menu <i class=\"fas fa-bars\"></i>\r\n"
    			+ "			</button>\r\n"
    			+ "			<div class=\"collapse navbar-collapse\" id=\"navbarResponsive\">\r\n"
    			+ "				<ul class=\"navbar-nav ms-auto\">\r\n"
    			+ "					<li class=\"nav-item mx-0 mx-lg-1\"><a\r\n"

    			+ "						class=\"nav-link py-3 px-0 px-lg-3 rounded\" href=\"reminders\">Reminders</a></li>\r\n"
    			+ "<li class=\"nav-item mx-0 mx-lg-1\"><a\r\n"
    			+ "					class=\"nav-link py-3 px-0 px-lg-3 rounded\" href=\"calorieIntake.html\">Recomendations</a></li>"
    			+ "					<li class=\"nav-item mx-0 mx-lg-1\"><a\r\n"
    			+ "						class=\"nav-link py-3 px-0 px-lg-3 rounded\" href=\"dailyTracking.html\">Daily Tracking</a></li>\r\n"
    			+ "					<li class=\"nav-item mx-0 mx-lg-1\"><a\r\n"
    			+ "						class=\"nav-link py-3 px-0 px-lg-3 rounded\" href=\"Logout\">Logout</a></li>\r\n"
    			+ "					<li class=\"nav-item mx-0 mx-lg-1 bg-danger\"><a\r\n"
    			+ "						class=\"nav-link py-3 px-0 px-lg-3 rounded\" href=\"index.jsp\">Home</a><li>\r\n"
    			+ "					\r\n"
    			+ "				</ul>\r\n"
    			+ "			</div>\r\n"
    			+ "		</div>\r\n"
    			+ "	</nav>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        
        if (meal != null) {
        	out.println("<div style='background-color: green; padding: 10px; border: 2px solid black; text-align: center;'>");
        	out.println("<h2 style='color: white;'>" + meal + " Meal Options:</h2>");
        	out.println("</div>");
 
            int lowerBound = 1;
            int upperBound = Integer.MAX_VALUE;

            if (meal.equals("Low-calorie")) {
                upperBound = 500;
            } else if (meal.equals("Moderate-calorie")) {
                lowerBound = 500;
                upperBound = 800;
            } else if (meal.equals("High-calorie")) {
                lowerBound = 800;
            } else {
                out.println("Invalid meal choice.");
                return;
            }

            for (String[] data : caloriesData) {
                try {
                	out.println("<body style='background-color: #1abc9c; margin-top: 10px;'>");
                    int calories = Integer.parseInt(data[1].trim().replaceAll("[^0-9]", "0"));
                    if (calories >= lowerBound && calories <= upperBound) {
                        out.println("<li>" + data[0] + " - " + calories + " calories</li>");
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid calorie format
                    out.println("<li>Invalid calorie format for: " + data[0] + "</li>");
                }
            }

            out.println("</ul>");
        } else {
            out.println("No meal choice provided.");
        }
    }
    
		

    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);

    }


}