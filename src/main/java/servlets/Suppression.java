package servlets;

import java.io.IOException;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import beans.Histoire;
import dao.ParagrapheDAO;

@WebServlet(name = "supprimer", urlPatterns = {"/supprimer"})
public class Suppression extends HttpServlet {
	
	@Resource(name = "users")
    private DataSource dataSource;
    public static final String VUE = "/editStory";
    private static final String donneeHistoire = "donneeHis";
    private static final String ATT_SUPP = "suppReussie";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	
    	/*Récupération de l'histoire*/
    	HttpSession session =  request.getSession();
		Histoire story = (Histoire) session.getAttribute(donneeHistoire);
		
    	/*récupération de l'identifiant*/
    	int id = Integer.parseInt(request.getParameter("idP"));
    	
    	ParagrapheDAO paraDao = new ParagrapheDAO(dataSource);
    	
    	boolean yn = paraDao.deleteParagraphe(story.getTitle(), id);
    	
    	/*Pour la gestion des erreurs*/
    	
    	request.setAttribute(ATT_SUPP, yn);
    	/*Redirection*/
    	response.sendRedirect(request.getContextPath() +  VUE + "?" + "titre=" + story.getTitle());    	

    }
}
