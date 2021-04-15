package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import beans.Histoire;
import beans.Utilisateur;
import dao.ChoixDAO;
import dao.HistoireDAO;
import forms.CreationForm;

@WebServlet(name = "creation", urlPatterns = {"/creation"})
public class Creation extends HttpServlet {
	

	@Resource(name = "users")
    private DataSource dataSource;
    public static final String VUE = "/WEB-INF/creation.jsp";
    public static final String VUEP = "/WEB-INF/creationVisualis.jsp";

    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_HISTOIRE = "histoire";
    public static final String ATT_FORM = "form";

    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	/* Préparation de l'objet formulaire */
    	CreationForm form = new CreationForm();
    	
    	/*Récupération du userName de l'utilisateur*/
    	 HttpSession session = request.getSession();
    	 Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
    	/*Traitement et validation de la requête*/ 
    	 String[] histoire = form.creerHistoire(request, dataSource, user.getUserName());
    	
        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_HISTOIRE, histoire);
        
    	 PrintWriter out = response.getWriter();
    	 out.println(histoire[0]);
    	 
//    	 for (String erreur: form.getErreurs().values()) {
//    		 out.println(erreur);
//    	 }
    	 
//    	 out.println(form.getResultat());
//    	 if(histoire != null) {
//    	 for (String str: histoire) {
//    		 out.println(str);
//    	 }
//    	 }
//
        //this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );


    }
    
//    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
//    
////    	ChoixDAO choiceDAO= new ChoixDAO(dataSource);
////    	
////    	String title = "titre de l'histoire";
////    	int idP = 24;
////    	String choice = "ouiii";
////    	
////		choiceDAO.addChoice(title, idP, choice);
//    	
////    	CreationForm form = new CreationForm();
////    	boolean b = form.validationTitle("Little Red Riding", dataSource);
////   	 	PrintWriter out = response.getWriter();
////   	 	if (b) {
////   	 		out.print("Yeees");
////   	 	}
////   	 	else {
////   	 		out.print("NOOO");
////   	 	}
//
//    	HistoireDAO st = new HistoireDAO(dataSource);
//    	LinkedList<String> ll = st.verifyTitle("Little Red Riding");
//    	PrintWriter out = response.getWriter();
//    	if (ll == null) {
//    		out.print("viiiiiiiiide");
//    	}
//    	else {
//    		
//    	
//    	for (String ss:ll) {
//    		out.println(ss);
//    	}
//    }
//    }
}