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

@WebServlet(name = "restriction", urlPatterns = {"/restriction"})
public class Restriction extends HttpServlet {
	

	@Resource(name = "users")
    private DataSource dataSource;
    public static final String VUE = "/WEB-INF/restriction.jsp";


    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de restriction */
        this.getServletContext().getRequestDispatcher( VUE).forward( request, response );
    }
}