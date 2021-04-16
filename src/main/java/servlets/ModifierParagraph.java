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

import beans.Choix;
import beans.Histoire;
import beans.Paragraphe;
import beans.Utilisateur;
import dao.ChoixDAO;
import dao.ParagrapheDAO;
import forms.InscriptionForm;
import forms.WriteParagraphForm;

/**
 * Servlet implementation class LireParagraph
 */
@WebServlet("/writeParagraph")
public class ModifierParagraph extends HttpServlet {
	@Resource(name = "users")
    private DataSource dataSource;
	
    public static final String ATT_USER         = "utilisateur";
	public static final String ATT_ID_CHOICE = "idChoice";
    public static final String ATT_FORM = "form";
    public static final String VUE  = "/WEB-INF/writeParagraph.jsp";
    public static final String VUE_ERREUR  = "/WEB-INF/erreur.jsp";
    private static final String donneeHistoire = "donneeHis";
    private static final String textTodisplay = "textAff";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierParagraph() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*Récupération de l'histoire à partir du titre*/
		int idP = Integer.parseInt(request.getParameter("idP"));
		
		ParagrapheDAO paraDAO = new ParagrapheDAO(dataSource);
		/*Il faut le titre*/
		//Paragraphe paragraphe = paraDAO.getParagraphe(titleStory, idPar)
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

	}
	
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
    	/*Créer un form pour update de contenu*/
    }
}
