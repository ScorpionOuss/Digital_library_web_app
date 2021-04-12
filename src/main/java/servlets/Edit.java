package servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.Histoire;
import dao.HistoireDAO;

@WebServlet(name = "edit", urlPatterns = {"/edit"})
public class Edit extends HttpServlet {
	

	@Resource(name = "users")
    private DataSource dataSource;
    public static final String VUE = "/WEB-INF/edit.jsp";
	public static final String HISTOIRES = "histoiresEdit";
	

    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	/* Restore all the stories in the data base for read */
		HistoireDAO storiesDAO = new HistoireDAO(dataSource);
		LinkedList<Histoire> stories = storiesDAO.listOfStoriesToEdit();
		/*Set attributes*/
		request.setAttribute(HISTOIRES, stories);
    	/* Vérification de la connection et forward.*/
        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    }
}