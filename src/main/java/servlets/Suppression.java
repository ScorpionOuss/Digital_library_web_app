package servlets;

import java.io.IOException;

import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "supprimer", urlPatterns = {"/creation"})
public class Suppression extends HttpServlet {
	
	@Resource(name = "users")
    private DataSource dataSource;
    public static final String VUE = "/WEB-INF/supprimer.jsp";
   
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    }
}
