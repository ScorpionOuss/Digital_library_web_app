package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "accueil", urlPatterns = {"/accueil"})
public class Accueil extends HttpServlet {

    public static final String VUE = "/WEB-INF/index.jsp";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
        String action = request.getParameter("action");

    	/* Affichage de la page d'accueil */
    	if (action == null){
    		/* Affichage de la page d'accueil */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    	} else if(action.contentEquals("")) {}
    }
    
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    }
}