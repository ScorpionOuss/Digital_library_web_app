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
import beans.Histoire;
import beans.Paragraphe;
import beans.Utilisateur;
import dao.ChoixDAO;
import dao.HistoireDAO;
import dao.ParagrapheDAO;

/**
 * Servlet implementation class LireUneHistoire
 */
@WebServlet("/editStory")
public class EditStory extends HttpServlet {
       
	@Resource(name = "users")
    private DataSource dataSource;
	private static final long serialVersionUID = 1L;
	public static final String titreHis = "titre";
	public static final String donneeHistoire = "donneeHis";
	public static final String donneepar = "donneePar";
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_DAO         = "choiceDAO";

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

		ParagrapheDAO parDAO = new ParagrapheDAO(dataSource);
		Paragraphe assocPar; 
		assocPar = parDAO.getParagraphe(titre, story.getFirstParagraph());

		/*Mettre en place les attributs pour la gestion dynamique en JSP*/
		request.setAttribute(donneeHistoire, story);
		request.setAttribute(donneepar, assocPar);
		
		HttpSession session =  request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
		request.setAttribute(ATT_USER, user);
		/*set strory in session attributes; use in case of edition*/
		session.setAttribute(donneeHistoire, story);
		
		ChoixDAO choixDao = new ChoixDAO(dataSource);
		request.setAttribute(ATT_DAO, choixDao);
		
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
}
