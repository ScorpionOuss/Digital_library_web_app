package servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
import dao.ParagrapheDAO;
import forms.InscriptionForm;
import forms.WriteParagraphForm;

/**
 * Servlet implementation class LireParagraph
 */
@WebServlet("/writeParagraph")
public class WriteParagraph extends HttpServlet {
	@Resource(name = "users")
    private DataSource dataSource;
	
    public static final String ATT_USER         = "utilisateur";
	public static final String ATT_ID_CHOICE = "idChoice";
    public static final String ATT_FORM = "form";
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

		 /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        
        Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
		int idChoice = (int) Integer. parseInt(request.getParameter(ATT_ID_CHOICE));
		PrintWriter out = response.getWriter();	
		/* To be able to find the id of the choice after */
		request.setAttribute(ATT_ID_CHOICE, idChoice);
//		ChoixDAO choixDAO = new ChoixDAO(dataSource);
//		choixDAO.lockChoice(idChoice, user.getUserName()); 
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        /* Préparation de l'objet formulaire */
        WriteParagraphForm form = new WriteParagraphForm();

        /*Récupération du userName de l'utilisateur*/
	   	 HttpSession session = request.getSession();
	   	 Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */

        Paragraphe paragraph= form.creerParagraphe(request, dataSource, user.getUserName());
        /* Add the paragraph to the dataBase */
        ParagrapheDAO parDAO = new ParagrapheDAO(dataSource);
        int idPar = parDAO.addParagraphe(paragraph, true, true);
        
		/*Après la validation du choix*/
		int idChoice = Integer.parseInt(request.getParameter(ATT_ID_CHOICE));
        ChoixDAO choixDAO = new ChoixDAO(dataSource);
		choixDAO.unlockChoice(idChoice);
		//la il faut update le contenu du choix.
		

		
		/* add the association */
        choixDAO.associateParagraph(idChoice, idPar, paragraph.getStory());
        
        /* Since we do the submit : validate */
        parDAO.validateParagraphe(paragraph.getStory(), idPar);

        
        /* Stockage du formulaire dans l'objet request */
        request.setAttribute( ATT_FORM, form );
		
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}
