package foo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class TestPage extends HttpServlet {

    public void doGet(HttpServletRequest request,
                     HttpServletResponse response)
                throws IOException, ServletException
    {
	System.out.println("get the request from TestPage class");
        PrintWriter out = response.getWriter();
        out.println("<html><body>" +
           "<p>Hello World from TestPage class!</p>" + "</body></html>");
    }
}
