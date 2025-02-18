

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Bai4BMI
 */
@WebServlet("/Bai4BMI")
public class Bai4BMI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bai4BMI() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>BMI Calculator</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; text-align: center; background-color: #f4f4f4; padding: 50px; }");
            out.println(".container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); display: inline-block; }");
            out.println("input, button { margin: 10px; padding: 10px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h1>BMI Calculator</h1>");
            out.println("<form action='Bai4BMI' method='POST'>");
            out.println("<label>Height (m): <input type='text' name='height' required></label><br>");
            out.println("<label>Weight (kg): <input type='text' name='weight' required></label><br>");
            out.println("<button type='submit'>Calculate BMI</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            double height = Double.parseDouble(request.getParameter("height"));
            double weight = Double.parseDouble(request.getParameter("weight"));
            double bmi = weight / (height * height);
            
            String category;
            if (bmi < 18.5) {
                category = "Underweight";
            } else if (bmi < 24.9) {
                category = "Normal weight";
            } else if (bmi < 29.9) {
                category = "Overweight";
            } else {
                category = "Obese";
            }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>BMI Result</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; text-align: center; background-color: #f4f4f4; padding: 50px; }");
            out.println(".container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); display: inline-block; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h1>Your BMI Result</h1>");
            out.println("<p>Your BMI is: " + String.format("%.2f", bmi) + "</p>");
            out.println("<p>Category: " + category + "</p>");
            out.println("<a href='Bai4BMI'>Back to Calculator</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
	}

}
