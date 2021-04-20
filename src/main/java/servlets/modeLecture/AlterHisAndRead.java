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
import beans.Historique;
import beans.Utilisateur;
import dao.HistoriqueDAO;
import servlets.Connexion;

/**
 * handle the history changing in case if the user chose to get back to a certain previous choice 
 */
@WebServlet("/AlterHisAndRead")
public class AlterHisAndRead extends HttpServlet {
	@Resource(name = "users")
    private DataSource dataSource;
	private static final long serialVersionUID = 1L;
	public static final String nextPar = "idPar";
	public static final String Choice = "idChoice";
	public static final String secServlet = "/LireParagraph";
	public static final String forReinitialize = "/LireUneHistoire";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlterHisAndRead() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* We will just alter the history and redirect toward the servlet that will show the paragraph */
		Historique his = (Historique) request.getSession().getAttribute(LireUneHistoire.HISTORY);
		
		/* Not the perfect choice */
		int breakAt = Integer.parseInt(request.getParameter(Choice));
		/* if the parameter is -1 : we'll delete all the history and start from the begining */
		if (breakAt < 0 ) {
			Utilisateur user = (Utilisateur) request.getSession().getAttribute(Connexion.ATT_USER);
			if (user != null) {
				/* delete from the data base */
				HistoriqueDAO hisDAO = new HistoriqueDAO(dataSource);
				hisDAO.deleteHistory(his.getStory(), user.getUserName());
				
			}
			/* Just re-initialize the history */
			Historique history = new Historique("default", his.getStory());
			request.getSession().setAttribute(LireUneHistoire.HISTORY, history);
			/* Now redirect to the servlet that displays the story */
			this.getServletContext().getRequestDispatcher(forReinitialize+"?titre="+ history.getStory()).forward( request, response );
			return;
		}
		
		LinkedList<Choix> newHis = new LinkedList<Choix>();
		for (Choix ch : his.getHisChoices()) {
			if (ch.getIdChoice() == breakAt) {
				break;
			}
			newHis.add(ch);
		}
		((Historique) request.getSession().getAttribute(LireUneHistoire.HISTORY)).setHisChoices(newHis);
		/* Redirect to the paragraph reading Servlet */
		this.getServletContext().getRequestDispatcher( secServlet).forward( request, response );
	}
}
