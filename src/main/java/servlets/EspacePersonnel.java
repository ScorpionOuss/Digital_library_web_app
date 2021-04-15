package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import beans.Utilisateur;
import dao.UtilisateurDAO;
import forms.ConnexionForm;

public class EspacePersonnel extends HttpServlet {
	
	@Resource(name = "users")
    private DataSource dataSource;
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_STORIES        = "stories";
    public static final String ATT_PAR        = "paragraphs";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String VUE_SUCCES       = "/WEB-INF/index.jsp";
    public static final String VUE_FAILURE      = "/WEB-INF/connexion.jsp";

    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
    	HttpSession session = request.getSession();
    	Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
    	
    	/*Récupérer les histoires dont il est auteur*/
    	
    	/*Récupérer les paragraphes dont il est auteur*/
        this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
    }
}