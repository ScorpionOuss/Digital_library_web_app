package servlets;

import java.io.IOException;

import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Histoire;

@WebServlet(name = "supprimer", urlPatterns = {"/supprimer"})
public class Suppression extends HttpServlet {
	
	@Resource(name = "users")
    private DataSource dataSource;
    public static final String VUE = "/WEB-INF/supprimer.jsp";
    private static final String donneeHistoire = "donneeHis";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	
    	/*Récupération de l'histoire*/
    	HttpSession session =  request.getSession();
		Histoire story = (Histoire) session.getAttribute(donneeHistoire);
		
    	/*récupération de l'identifiant*/
    	int id = Integer.parseInt(request.getParameter("idP"));
    }
}
