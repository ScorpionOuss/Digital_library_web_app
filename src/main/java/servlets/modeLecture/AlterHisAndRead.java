package servlets.modeLecture;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Choix;
import beans.Historique;

/**
 * Servlet implementation class AlterHisAndRead
 */
@WebServlet("/AlterHisAndRead")
public class AlterHisAndRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String nextPar = "idPar";
	public static final String Choice = "idChoice";
	public static final String secServlet = "/LireParagraph";

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
