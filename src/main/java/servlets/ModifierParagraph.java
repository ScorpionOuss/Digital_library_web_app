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
import forms.ModifyForm;
import forms.WriteParagraphForm;

/**
 * Servlet implementation class LireParagraph
 */
@WebServlet("/modifier")
public class ModifierParagraph extends HttpServlet {
	@Resource(name = "users")
    private DataSource dataSource;
	
    public static final String ATT_USER         = "utilisateur";
	public static final String ATT_ID_CHOICE = "idChoice";
    public static final String ATT_FORM = "form";
    public static final String ATT_PAR = "paragraph";
    public static final String VUE  = "/WEB-INF/modifierParagraph.jsp";
    public static final String VUEPost  = "/editStory";
    
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
		/*récupération du titre de l'histoire*/
		HttpSession session =  request.getSession();
		Histoire story = (Histoire) session.getAttribute(donneeHistoire);
		
		/*récupération du paragraphe*/
		
		Paragraphe paragraphe = paraDAO.getParagraphe(story.getTitle(), idP);

		session.setAttribute(ATT_PAR, paragraphe);
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

	}
	

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
    	/*Créer un form pour update de contenu*/
    	ModifyForm form = new ModifyForm();
    	
    	/*Récupérer le titre*/
		HttpSession session =  request.getSession();
    	Histoire story = (Histoire) session.getAttribute(donneeHistoire);
    	Paragraphe par = (Paragraphe) session.getAttribute(ATT_PAR);

    	
        String submit = request.getParameter("button")==null? "": request.getParameter("button");
        String annul =  request.getParameter("annuler") == null?"": request.getParameter("annuler");
        String action = submit + annul;
    	
        switch(action) {
        	case "modify":
        		/*Execution*/
            	form.modifierParagraph(request, dataSource, story.getTitle(), par.getIdParagraph());
            	break;
        	case "annuler":
        		/*rien à priori*/
        		break;
        }

    	request.setAttribute(ATT_FORM, form);
    	response.sendRedirect(request.getContextPath() +  VUEPost + "?" + "titre=" + story.getTitle());    	
    	
    }
}
