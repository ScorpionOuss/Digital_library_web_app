package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dao.ChoixDAO;
import dao.ParagrapheDAO;

/**
 * Servlet implementation class addAChoice
 */
@WebServlet("/addAChoice")
public class addAChoice extends HttpServlet {
	
	@Resource(name = "users")
    private DataSource dataSource;
	
	private static final long serialVersionUID = 1L;
	private static final String CHAMP_Histoire    = "prevStory";
	private static final String CHAMP_CHOIX = "choix";
	private static final String CHAMP_PAR= "prevPar";
	public static final String SERV1= "/editStory";
	public static final String SERV2  = "/WEB-INF/editParagraph.jsp";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addAChoice() {
        super();

    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* There is nothing to verify : what's givven by the user is just a text */
		String prevStory = request.getParameter(CHAMP_Histoire);
		int prevPar = Integer.parseInt(request.getParameter(CHAMP_PAR));
		
		String[] choix = request.getParameterValues(CHAMP_CHOIX);
		if (choix != null && choix.length != 0) {
			/* Add the choices to the database */
			ChoixDAO choixDAO = new ChoixDAO(dataSource);
			for (String ch : choix) {
				choixDAO.addChoice(prevStory, prevPar, ch);
			}
			/* Since we add a choice just to make sure that the paragraph is a body paragraph */
			ParagrapheDAO parDAO = new ParagrapheDAO(dataSource);
			parDAO.declareAsBodyParagraph(prevPar, prevStory);
		}
		
		PrintWriter out = response.getWriter();
		out.print("Votre choix est créé");
	}

}
