package foo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class TestPage extends HttpServlet {

    public void doGet(HttpServletRequest request,
                     HttpServletResponse response)
                throws IOException, ServletException
    {
	System.out.println("get the request from TestPage class");
        PrintWriter out = response.getWriter();
	Date now = new Date(); // Date & Time
        String page = "<html><body><p>" + now +"</p></body></html>";
        out.println(page);
    }
}
