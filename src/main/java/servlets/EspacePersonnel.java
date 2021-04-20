package servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import beans.Choix;
import beans.Histoire;
import beans.Paragraphe;
import beans.Utilisateur;
import dao.ChoixDAO;
import dao.HistoireDAO;
import dao.ParagrapheDAO;

/**
 * servlet that handle display of the stories owned by the logged in user also 
 * as the choice he's currently writing 
 * @author mounsit
 *
 */
@WebServlet("/espacePersonnel")
public class EspacePersonnel extends HttpServlet {
	
	@Resource(name = "users")
    private DataSource dataSource;
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_STORIES        = "stories";
    public static final String ATT_PAR        = "paragraphs";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String VUE       = "/WEB-INF/espacePersonnel.jsp";
    public static final String ATT_TEXT = "text";
    public static final String ATT_IDCHOICE = "idChoice";


    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 /* Affichage de la page de connexion */
    	HttpSession session = request.getSession();
    	Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
    	
    	/*Récupérer les histoires dont il est auteur*/
    	HistoireDAO storyDAO = new HistoireDAO(dataSource);
    	LinkedList<Histoire> stories = storyDAO.storiesCreatedBy(user.getUserName());
    	request.setAttribute(ATT_STORIES, stories);
    	
    	/*Récupérer les paragraphes dont il est auteur*/
    	ParagrapheDAO parDao = new ParagrapheDAO(dataSource);
    	LinkedList<Paragraphe> paragraphs = parDao.parWroteBy(user.getUserName());
    	request.setAttribute(ATT_PAR, paragraphs);
    	
    	/*Récupérer les choix écrits mais pas encore validés*/
    	ChoixDAO chDao = new ChoixDAO(dataSource);
    	Integer idChoice = chDao.getChoiceLockedBy(user.getUserName());
    	String text = null;
    	if (idChoice != null) {
    		Choix choix = chDao.getChoice(idChoice);
    		text = choix.getText();
    	}
    	
    	request.setAttribute(ATT_TEXT, text);
    	request.setAttribute(ATT_IDCHOICE, idChoice);
    	
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    
    

}