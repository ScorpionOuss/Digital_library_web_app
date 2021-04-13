package servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.Paragraphe;
import dao.ChoixDAO;
import dao.ParagrapheDAO;

/**
 * Servlet implementation class LireParagraph
 */
@WebServlet("/writeParagraph")
public class WriteParagraph extends HttpServlet {
	@Resource(name = "users")
    private DataSource dataSource;
	

    public static final String VUE  = "/WEB-INF/writeParagraph.jsp";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteParagraph() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
