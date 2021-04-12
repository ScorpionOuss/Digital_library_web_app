package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.Histoire;
import forms.CreationForm;

@WebServlet(name = "creation", urlPatterns = {"/creation"})
public class Creation extends HttpServlet {
	

	@Resource(name = "users")
    private DataSource dataSource;
    public static final String VUE = "/WEB-INF/creation.jsp";
    public static final String VUEP = "/WEB-INF/creationVisualis.jsp";

    public static final String ATT_HISTOIRE = "histoire";
    public static final String ATT_FORM = "form";

    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	/* Préparation de l'objet formulaire */
    	CreationForm form = new CreationForm();
    	
    	/*Traitement et validation de la requête*/
    	Histoire histoire = form.creerHistoire(request, dataSource);
 
    	
        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_HISTOIRE, histoire );
        

        this.getServletContext().getRequestDispatcher( VUEP ).forward( request, response );


    }
}