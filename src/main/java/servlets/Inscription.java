package servlets;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.Utilisateur;
import forms.InscriptionForm;

/**
 * servlet that handles the sign up 
 * @author mounsit kaddami yan perez 
 *
 */
public class Inscription extends HttpServlet {
	
	@Resource(name = "users")
    private DataSource dataSource;
	public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";
    public static final String VUE = "/WEB-INF/Inscription.jsp";
		
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        /* Affichage de la page d'inscription */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
	
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        /* Préparation de l'objet formulaire */
        InscriptionForm form = new InscriptionForm();
		
        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        Utilisateur utilisateur = form.inscrireUtilisateur(request, dataSource);
		
        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );


        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}