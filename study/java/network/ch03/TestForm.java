package foo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class TestForm extends HttpServlet {

    public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
                    throws IOException, ServletException
    {
	System.out.println("do post get the request");
        String username = request.getParameter("user"); // get "user" input
	
	System.out.println("username="+username);
        PrintWriter out = response.getWriter();
        String page = "<html><body><p>Submitted: " + username +"</p></body></html>";
        out.println(page);    }
}
