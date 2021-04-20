package servlets.modeLecture;

import java.io.IOException;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.Choix;
import beans.Histoire;
import beans.Historique;
import beans.Paragraphe;
import beans.Utilisateur;
import dao.ChoixDAO;
import dao.HistoireDAO;
import dao.HistoriqueDAO;
import dao.ParagrapheDAO;
import servlets.Connexion;

/**
 * handle the display of a story in reading mode 
 */
@WebServlet("/LireUneHistoire")
public class LireUneHistoire extends HttpServlet {
       
	@Resource(name = "users")
    private DataSource dataSource;
	private static final long serialVersionUID = 1L;
	public static final String titreHis = "titre";
	public static final String donneeHistoire = "donneeHis";
	public static final String donneepar = "donneePar";
	/* 1 = a unique display / 0 = not unique */
	public static final String displayUnique = "displayUnique";
	public static final String VUE  = "/WEB-INF/jspModeLecture/lireUneHistoire.jsp";
	public static final String HISTORY = "history";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LireUneHistoire() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HistoireDAO storieDAO = new HistoireDAO(dataSource);
		String titre = (String) request.getParameter(titreHis);
		Histoire story = storieDAO.getHistoire(titre);
		/* See if we have to do a unique display for the story */
		LinkedList<Integer> paragraphsId = new LinkedList<Integer>();
		 /* It will be a paragraph containing all the story if there is 
		  * a unique display or the first story in case not */
		ParagrapheDAO parDAO = new ParagrapheDAO(dataSource);
		Paragraphe assocPar; 
		/* If the display is unique */
		if (storieDAO.uniqueDisplay(titre, paragraphsId)) {
			assocPar = parDAO.turnIntoOneParagraph(titre, paragraphsId);
			request.setAttribute(donneeHistoire, story);
			request.setAttribute(donneepar, assocPar);
			
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
			return; 
		}
		
		/* Otherwise */
		/* Case One : the user is not connected */
		if (request.getSession().getAttribute(Connexion.ATT_USER) == null) {
			/* Just Get the first paragraph of story */
			assocPar = parDAO.getParagraphe(titre, story.getFirstParagraph());
			/* Establish a history */
			Historique history = new Historique("default", titre);
			request.getSession().setAttribute(HISTORY, history);
		}
		else { /* The user is connected : get the last paragraph of the history */
			String userName = ((Utilisateur) request.getSession().getAttribute(Connexion.ATT_USER)).getUserName();
			HistoriqueDAO historyDAO = new HistoriqueDAO(dataSource);
			Historique history = historyDAO.GetHistoryFromDB(titre, userName);
			/* If there is no history : create one for the session */
			if (history == null) {
				history = new Historique(userName, titre);
				request.getSession().setAttribute(HISTORY, history);
				/* Get the first paragraph */
				assocPar = parDAO.getParagraphe(titre, story.getFirstParagraph());
			} else {
				
				/* Get the last paragraph on the history */
				request.getSession().setAttribute(HISTORY, history);
				/* We'll just redirect */
				int idChoice = history.removeLastChoice(); /* We remove it because it re-added by the next command */
				this.getServletContext().getRequestDispatcher("/LireParagraph?idChoice="+idChoice).forward( request, response );
				return;
			}
		}
		/* Since we are in lecture mode : we have to analyze if the choices are masked or no : 
		 * we do it here because it has a significant complexity so : done only if it's really necessary */
		ChoixDAO choixDAO = new ChoixDAO(dataSource);
		if (assocPar.getChoices() != null) {
			for (Choix choice : assocPar.getChoices()) {
				/* A choice is masked if doesn't lead to a conclusion or because of an access condition */
				boolean masked = choixDAO.isMasked(choice.getIdChoice());
				/* See if there is a condition */
				Integer condition = choixDAO.getAccessCondition(choice.getIdChoice());
				/* If there is a condition : verify that it is in the history */
				if (condition != null) {
					Historique history = (Historique) request.getSession().getAttribute(HISTORY);
					masked = masked || history.hasBeenRead(condition, dataSource);
					
				}
				choice.setIsMasked(masked);
			}
		}
		request.setAttribute(donneeHistoire, story);
		request.setAttribute(donneepar, assocPar);
		
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
}
