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

import beans.Paragraphe;
import beans.Utilisateur;
import dao.ChoixDAO;

/**
 * servlet that handles the display of paragraphs in editing mode giving the id of the associated choice  
 */
@WebServlet("/editParagraph")
public class EditParagraph extends HttpServlet {
	@Resource(name = "users")
    private DataSource dataSource;
	
	private static final long serialVersionUID = 1L;
    private static final String forChoice = "idChoice";
    private static final String par = "paragraph";
    public static final String ATT_DAO         = "choiceDAO";
    public static final String VUE  = "/WEB-INF/editParagraph.jsp";
    public static final String ATT_MS         = "droits";
    public static final String ATT_USER         = "utilisateur";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditParagraph() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Paragraphe paragraph = null; 
		HttpSession session =  request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
		
		/* to a choice */
		if (request.getParameter(forChoice) != null) {
			ChoixDAO choixDAO = new ChoixDAO(dataSource);
			paragraph = choixDAO.retreiveCorrPar(Integer.parseInt(request.getParameter(forChoice))); 
		} else {
			return;
		}
		
		/*set droit à la modif et la supression*/
		request.setAttribute(ATT_MS, user.getUserName().contentEquals(paragraph.getAuthor()));
		
		request.setAttribute(par, paragraph);
		ChoixDAO choixDao = new ChoixDAO(dataSource);
		request.setAttribute(ATT_DAO, choixDao);
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
