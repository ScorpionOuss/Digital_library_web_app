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

import beans.Histoire;
import beans.Paragraphe;
import beans.Utilisateur;
import dao.HistoireDAO;
import dao.ParagrapheDAO;

public class AuthorParFilter implements Filter {
    
	@Resource(name = "users")
    private DataSource dataSource;
	public static final String ACCES_RESTREINT    = "/restriction";
    public static final String ATT_USER = "utilisateur";
	public static final String donneeHistoire = "donneeHis";

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
        if ( session.getAttribute( ATT_USER ) == null ||
        		session.getAttribute(donneeHistoire) == null) {
            /* Redirection vers la page publique */
            response.sendRedirect( request.getContextPath() + ACCES_RESTREINT );
        } else {
        	if (request.getMethod().contentEquals("GET")) {
	        	Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
	        	Histoire story = (Histoire) session.getAttribute(donneeHistoire);
	        	int idP = Integer.parseInt(request.getParameter("idP"));
	        	ParagrapheDAO paragDao = new ParagrapheDAO(dataSource);
	        	Paragraphe paragraph = paragDao.getParagraphe(story.getTitle(), idP);
	        	
	    		boolean droits = user.getUserName().contentEquals(paragraph.getAuthor());
	
	            if (!droits) {
	                response.sendRedirect( request.getContextPath() + ACCES_RESTREINT );
	            }
	            else {
		        	/* Affichage de la page restreinte */
		            chain.doFilter( request, response );
	            	}
        	}else {
	        	/* Affichage de la page restreinte */
	            chain.doFilter( request, response );
            	}
        	}
        }
    
    

	public void destroy() {
    }
}