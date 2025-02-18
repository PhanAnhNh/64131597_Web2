package ntu.edu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaiAboutMe
 */
@WebServlet("/BaiAboutMe")
public class BaiAboutMe extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public BaiAboutMe() {
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
            out.println("<title>About Me</title>");
            out.println("<style>");
            //css
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            out.println(".container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); text-align: left; }");
            out.println("h1 { color: #333; text-align: center; }");
            out.println("p { color: #666; font-size: 18px; margin-left: 20px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h1>About Me</h1>");
            out.println("<p><strong>Name:</strong> Phan Anh Nhất</p>");
            out.println("<p><strong>Age:</strong> 21</p>");
            out.println("<p><strong>University:</strong> Nha Trang University</p>");
            out.println("<p><strong>Hobbies:</strong> Traveling, playing badminton, drinking bubble tea</p>");
            out.println("<p><strong>Email:</strong> anhnhat1322004@gmail.com</p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
		} finally {
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
