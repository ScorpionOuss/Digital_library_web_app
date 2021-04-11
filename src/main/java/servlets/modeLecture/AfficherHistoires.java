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

import beans.Histoire;
import dao.HistoireDAO;

/**
 * Servlet implementation class AfficherHistoires
 */
@WebServlet("/afficherHistoires")
public class AfficherHistoires extends HttpServlet {
	
	@Resource(name = "users")
    private DataSource dataSource;
	private static final long serialVersionUID = 1L;
	public static final String HISTOIRES = "histoires";
	public static final String SESSION_USER = "sessionUtilisateur";
	public static final String CONNECTED = "isConnected";
	public static final String VUE  = "/WEB-INF/jspModeLecture/afficherHistoires.jsp";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherHistoires() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* Restore all the stories in the data base for read */
		HistoireDAO storiesDAO = new HistoireDAO(dataSource);
		LinkedList<Histoire> stories = storiesDAO.listOfStoriesToRead();
		/* See if the user is connected or not */
		boolean isConnected = false; 
		if (request.getSession().getAttribute(SESSION_USER) != null) {
			isConnected = true; 
		}
		request.setAttribute(CONNECTED, isConnected);
		request.setAttribute(HISTOIRES, stories);
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
}
