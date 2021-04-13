package servlets.modeLecture;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.Choix;
import beans.Historique;
import beans.Paragraphe;
import beans.Utilisateur;
import dao.ChoixDAO;
import dao.HistoriqueDAO;
import dao.ParagrapheDAO;
import servlets.Connexion;

/**
 * Servlet implementation class LireParagraph
 */
@WebServlet("/LireParagraph")
public class LireParagraph extends HttpServlet {
	@Resource(name = "users")
    private DataSource dataSource;
	
	private static final long serialVersionUID = 1L;
    private static final String forNextPar = "idPar";
    private static final String story = "titleStory";
    private static final String forChoice = "idChoice";
    private static final String par = "paragraph";
    public static final String VUE  = "/WEB-INF/jspModeLecture/lireParagraph.jsp";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LireParagraph() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* See if it corresponds to a next paragraph */
		Paragraphe paragraph = null; 
		if (request.getParameter(forNextPar) != null) {
			ParagrapheDAO parDAO = new ParagrapheDAO(dataSource);
			paragraph = parDAO.getParagraphe(request.getParameter(story), Integer.parseInt(request.getParameter(forNextPar)));
		/* Or to a choice */
		} else if (request.getParameter(forChoice) != null) {
			ChoixDAO choixDAO = new ChoixDAO(dataSource);
			int idChoice = Integer.parseInt(request.getParameter(forChoice));
			paragraph = choixDAO.retreiveCorrPar(idChoice);
			/* ALter the history */
			Historique history = (Historique) request.getSession().getAttribute(LireUneHistoire.HISTORY);
			Choix choiceToAdd = choixDAO.getChoice(idChoice);
			history.addChoiceToHis(choiceToAdd);
			/* See if the user is connected alter the DB */
			Utilisateur user = (Utilisateur) request.getSession().getAttribute(Connexion.ATT_SESSION_USER);
			if (user != null) {
				HistoriqueDAO historyDAO = new HistoriqueDAO(dataSource);
				historyDAO.addHistoryToDB(history);
			}
			request.getSession().setAttribute(LireUneHistoire.HISTORY, history);
		} else {
			return;
		}
		/* No other case in fact :/ */
		/* Since we are in lecture mode : we have to analyze if the choices are masked or no : 
		 * we do it here because it has a significant complexity so : done only if it's really necessary */
		ChoixDAO choixDAO2 = new ChoixDAO(dataSource);
		if (paragraph.getChoices() != null) {
			for (Choix choice : paragraph.getChoices()) {
				/* A choice is masked if doesn't lead to a conclusion or because of an access condition */
				boolean masked = choixDAO2.isMasked(choice.getIdChoice());
				/* See if there is a condition */
				Integer condition = choixDAO2.accessCondition(choice.getIdChoice());
				/* If there is a condition : verify that it is in the history */
				if (condition != null) {
					Historique history = (Historique) request.getSession().getAttribute(LireUneHistoire.HISTORY);
					masked = masked || history.hasBeenRead(condition, dataSource);
					
				}
				choice.setIsMasked(masked);
			}
		}
		request.setAttribute(par, paragraph);
		request.setAttribute(LireUneHistoire.HISTORY, request.getSession().getAttribute(LireUneHistoire.HISTORY));
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
