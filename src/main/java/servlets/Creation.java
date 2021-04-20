package servlets;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import beans.Utilisateur;
import forms.CreationForm;

/**
 * To handle the creation of a new story
 * @author mounsit kaddami yan perez 
 *
 */
@WebServlet(name = "creation", urlPatterns = {"/creation"})
public class Creation extends HttpServlet {
	

	@Resource(name = "users")
    private DataSource dataSource;
    public static final String VUE_Succes = "/espacePersonnel";
    public static final String VUE_Failure = "/WEB-INF/creation.jsp";

    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_HISTOIRE = "histoire";
    public static final String ATT_FORM = "form";

    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE_Failure).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	/* Préparation de l'objet formulaire */
    	CreationForm form = new CreationForm();
    	
    	/*Récupération du userName de l'utilisateur*/
    	 HttpSession session = request.getSession();
    	 Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
    	/*Traitement et validation de la requête*/ 
    	 String[] histoire = form.creerHistoire(request, dataSource, user.getUserName());
    	
        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_HISTOIRE, histoire);
        
//    	 PrintWriter out = response.getWriter();
//    	 
//    	 for (String erreur: form.getErreurs().values()) {
//    		 out.println(erreur);
//    	 }

        if (form.getErreurs().isEmpty()) {
        	this.getServletContext().getRequestDispatcher( VUE_Succes ).forward( request, response );
        } else {
        	this.getServletContext().getRequestDispatcher( VUE_Failure ).forward( request, response);
        }

    }

}