package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import beans.Histoire;
import beans.Paragraphe;
import beans.Utilisateur;
import dao.HistoireDAO;
import dao.ParagrapheDAO;
import dao.UtilisateurDAO;
import forms.AutorisationForm;
import forms.ConnexionForm;

@WebServlet("/autoriserAcces")
public class AutoriserAcces extends HttpServlet {
	
	@Resource(name = "users")
    private DataSource dataSource;
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_TITLE      = "titre";

    public static final String VUE       = "/WEB-INF/autorisation.jsp";
    public static final String ATT_HISTOIRE = "histoire";
    public static final String ATT_FORM = "form";
    public static final String VUE_Post = "/espacePersonnel";


    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	
        String title = request.getParameter(ATT_TITLE);

        HttpSession session = request.getSession();
        session.setAttribute("titre", title);
        
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	
    AutorisationForm form = new AutorisationForm();
    
    /*Récupération de l'histoire à partir du titre*/
    HttpSession session = request.getSession();
    String title = (String) session.getAttribute("titre");
	 
	 form.autoriserAcces(request, dataSource, title);
	 
     request.setAttribute( ATT_FORM, form );
     
     PrintWriter out = response.getWriter();
     
//     out.print("Paaaaas possible!");
//     for (String inv:invites) {
//    	 out.println(inv);
//     }
//     
//     out.println(title);
//     if (form.getErreurs().isEmpty()) {
//    	 out.println("Yeeees");
//     } else {
////    	 out.print("Noooo");
//     }
     
     this.getServletContext().getRequestDispatcher( VUE_Post ).forward( request, response );

    }    
}