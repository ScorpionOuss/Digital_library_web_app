package servlets;

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
import beans.Paragraphe;
import dao.ChoixDAO;
import dao.HistoireDAO;
import dao.ParagrapheDAO;

/**
 * Servlet implementation class LireUneHistoire
 */
@WebServlet("/EditStory")
public class EditStory extends HttpServlet {
       
	@Resource(name = "users")
    private DataSource dataSource;
	private static final long serialVersionUID = 1L;
	public static final String titreHis = "titre";
	public static final String donneeHistoire = "donneeHis";
	public static final String donneepar = "donneePar";
	/* 1 = a unique display / 0 = not unique */
	public static final String displayUnique = "displayUnique";
	public static final String VUE  = "/WEB-INF/editStory.jsp";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditStory() {
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
		if (storieDAO.uniqueDisplay(titre, paragraphsId)) {
			assocPar = parDAO.turnIntoOneParagraph(titre, paragraphsId);
		}
		else {
			assocPar = parDAO.getParagraphe(titre, story.getFirstParagraph());
		}
//		/* Since we are in lecture mode : we have to analyze if the choices are masked or no : 
//		 * we do it here because it has a significant complexity so : done only if it's really necessary */
//		ChoixDAO choixDAO = new ChoixDAO(dataSource);
//		if (assocPar.getChoices() != null) {
//			for (Choix choice : assocPar.getChoices()) {
//				choice.setIsMasked(choixDAO.isMasked(choice.getIdChoice()));
//			}
//		}
		request.setAttribute(donneeHistoire, story);
		request.setAttribute(donneepar, assocPar);
		
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
}
