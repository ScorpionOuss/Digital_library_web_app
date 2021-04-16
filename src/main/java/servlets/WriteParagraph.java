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
public class WriteParagraph extends HttpServlet {
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
    public WriteParagraph() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 /* retrieve the request session and its user */
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
        
		int idChoice = (int) Integer. parseInt(request.getParameter(ATT_ID_CHOICE));
		/* To be able to find the id of the choice after */
		request.setAttribute(ATT_ID_CHOICE, idChoice);
		
		/* choice treatment */
		ChoixDAO choixDAO = new ChoixDAO(dataSource);
		/* Here we lock the choice : So be aware here the paragraph is created as a conclusion */
		Integer idPar = choixDAO.lockChoice(idChoice, user.getUserName());
		if( idPar != null){ 
			/* Forward the text to display */
			ParagrapheDAO parDAO = new ParagrapheDAO(dataSource);
			String title =  ((Histoire) session.getAttribute(donneeHistoire)).getTitle();
			/* Not really efficient */
			String text = parDAO.getParagraphe(title, idPar).getText();
			request.setAttribute(textTodisplay, text);
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		}
		
		else {
			/****Afficher vous avez déjà un paragraphe non validé****/
			this.getServletContext().getRequestDispatcher( VUE_ERREUR ).forward( request, response );

		}
	}
	
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        /* prepare the form  */
        WriteParagraphForm form = new WriteParagraphForm();
        /* get the user name */
	   	 HttpSession session = request.getSession();
	   	 Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
        /* request treatment and validation, retrieve the resulting beans  */
        Paragraphe paragraph= form.creerParagraphe(request, dataSource, user.getUserName());
        
        /* Modify the data base */
        /* Be aware that the choice is already locked , has a paragraph that needs only to be modified */
        /* Here for some security we'll use the lockChoice() method since it creates a new paragraph if there is not */
        int idChoice = Integer.parseInt(request.getParameter(ATT_ID_CHOICE));
        ChoixDAO choixDAO = new ChoixDAO(dataSource);
        /* Retrieve the paragraph needed to be modified */
        int idPar = choixDAO.lockChoice(idChoice, user.getUserName());
        /* We modify the text */
        ParagrapheDAO parDAO = new ParagrapheDAO(dataSource);
        parDAO.modifyText(paragraph.getStory(), idPar, paragraph.getText());

        String submit = request.getParameter("button")==null? "": request.getParameter("button");
        String enreg = request.getParameter("enregistrer") == null?"": request.getParameter("enregistrer");
        String annul =  request.getParameter("annuler") == null?"": request.getParameter("annuler");
        
        String action = submit + enreg + annul;
        /*Manage the locked attribute of choice*/
        switch(action) {
        case "submit":
        	/* Since we do the submit : validate : this validates the paragraph and the choice  */
            parDAO.validateParagraphe(paragraph.getStory(), idPar);
            break;
        case "enregistrer":
        	/* nothing */
            break;
        case "annuler":
        	/*we unlock : and delete the paragraph */
    		choixDAO.unlockChoice(idChoice);
            break;
        }

        /* Add the inserted choices to the database */
        if (paragraph.getChoices() != null) {
        	parDAO.declareAsBodyParagraph(idPar, paragraph.getStory());
        	for (Choix choice : paragraph.getChoices()) {
        		choixDAO.addChoice(paragraph.getStory(), idPar, choice.getText());
        	}
        }
        
        /* Stockage du formulaire dans l'objet request */
        request.setAttribute( ATT_FORM, form );
		
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}
