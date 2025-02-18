

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class doget
 */
@WebServlet("/doget")
public class doget extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public doget() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Biến request lưu các thông tin yêu cầu
		//Biến response đại diện kết quả trả về cho người dùng
		PrintWriter dau_ghi_kq = response.getWriter();
		
		//Lấy dữ liệu từ tham số URL
		// Gia sử có dạng /doget?ten=PhanAnhNhat
		
		String value = request.getParameter("ten");
		dau_ghi_kq.println("<h1>Xin chào</h1>");
		dau_ghi_kq.println(value);
	}

}
