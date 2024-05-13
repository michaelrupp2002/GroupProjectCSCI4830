import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BalancedDietServlet")
public class BalancedDietServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the request
        int fruitServings = Integer.parseInt(request.getParameter("fruit"));
        int vegetableServings = Integer.parseInt(request.getParameter("vegetable"));
        int proteinServings = Integer.parseInt(request.getParameter("protein"));
        int grainsServings = Integer.parseInt(request.getParameter("grains"));
        int dairyServings = Integer.parseInt(request.getParameter("dairy"));

        // Calculate total calories
        int totalCalories = calculateTotalCalories(fruitServings, vegetableServings, proteinServings, grainsServings, dairyServings);

        // Check if the diet is balanced
        boolean isBalanced = isDietBalanced(totalCalories);

        // Output the result
        response.setContentType("text/plain");
        response.getWriter().println("Total Calories: " + totalCalories);
        response.getWriter().println("Is Diet Balanced: " + (isBalanced ? "Yes" : "No"));
    }

    private int calculateTotalCalories(int fruitServings, int vegetableServings, int proteinServings, int grainsServings, int dairyServings) {
        int fruitCalories = fruitServings * 50;
        int vegetableCalories = vegetableServings * 30;
        int proteinCalories = proteinServings * 100;
        int grainsCalories = grainsServings * 70;
        int dairyCalories = dairyServings * 80;
        return fruitCalories + vegetableCalories + proteinCalories + grainsCalories + dairyCalories;
    }

    private boolean isDietBalanced(int totalCalories) {
        return totalCalories >= 2000 && totalCalories <= 2500;
    }
}
