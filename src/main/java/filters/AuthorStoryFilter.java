package filters;

import java.io.IOException;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.catalina.User;

import beans.Histoire;
import beans.Paragraphe;
import beans.Utilisateur;
import dao.HistoireDAO;
import dao.ParagrapheDAO;

public class AuthorStoryFilter implements Filter {
    
	@Resource(name = "users")
    private DataSource dataSource;
	public static final String ACCES_RESTREINT    = "/restriction";
    public static final String ATT_USER = "utilisateur";

    public void init( FilterConfig config ) throws ServletException {
    }

    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException,
            ServletException {
        /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /**
         * On récupère l'histoire
         */
        if ( session.getAttribute( ATT_USER ) == null) {
            /* Redirection vers la page publique */
            response.sendRedirect( request.getContextPath() + ACCES_RESTREINT );
    	} else {
    		Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
        	String titre = request.getParameter("titre");
        	HistoireDAO stDao = new HistoireDAO(dataSource);
        	
        	Histoire story = stDao.getHistoire(titre);
    		boolean droits = user.getUserName().contentEquals(story.getCreator());

            if (!droits) {
                response.sendRedirect( request.getContextPath() + ACCES_RESTREINT );
            }
            else {
	        	/* Affichage de la page restreinte */
	            chain.doFilter( request, response );
            	}
        	}
        }
    
    


	public void destroy() {
    }
}