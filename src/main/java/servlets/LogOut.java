/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *  for the user to log out 
 * 
 * @author mounsit kaddami yan perez 
 */
@WebServlet(name = "LogOut", urlPatterns = {"/logout"})
public class LogOut extends HttpServlet {
    
    @Resource(name = "users")
    private DataSource ds;
    public static final String ATT_USER         = "utilisateur";
    public static final String VUE              = "/accueil";




    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	/* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        session.invalidate();

        response.sendRedirect(request.getContextPath() + VUE);

    }



}
