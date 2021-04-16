package servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dao.HistoireDAO;

@WebServlet(name = "publier", urlPatterns = {"/publier"})
public class Publier extends HttpServlet {
	
	@Resource(name = "users")
    private DataSource dataSource;
    public static final String VUE = "/espacePersonnel";
    public static final String ATT_TITLE      = "titre";
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    
    	
    	/*Récupération de l'histoire à partir du titre*/
        String title = request.getParameter(ATT_TITLE);
        
        HistoireDAO storyDAO = new HistoireDAO(dataSource);
        storyDAO.publish_story(title);
        
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        
    }
}
