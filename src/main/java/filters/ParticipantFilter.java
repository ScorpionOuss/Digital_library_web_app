package filters;

import java.io.IOException;
import java.io.PrintWriter;
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
import beans.Utilisateur;
import dao.HistoireDAO;

public class ParticipantFilter implements Filter {
    
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
    	Utilisateur user = (Utilisateur) session.getAttribute(ATT_USER);
    	Histoire story = (Histoire) session.getAttribute(donneeHistoire);

        if ( user== null ||
        		story == null) {
            /* Redirection vers la page publique */
            response.sendRedirect( request.getContextPath() + ACCES_RESTREINT );
        } else {
        	
        	HistoireDAO stDao = new HistoireDAO(dataSource);
        	LinkedList<String> participants = stDao.getInvited(story.getTitle());
        	        	
            boolean participe = containsStr(user.getUserName(), participants) || story.getPublicEc();
            
            if (!participe) {
                response.sendRedirect( request.getContextPath() + ACCES_RESTREINT );
            }
            else {
	        	/* Affichage de la page restreinte */
	            chain.doFilter( request, response );
            	}
            
        	}
        }
    
    

    private boolean containsStr(String userName, LinkedList<String> participants) {
		
    	for (String participant : participants) {
    		if (participant.contentEquals(userName)){
    			return true;
    		}
    	}
		return false;
	}

	public void destroy() {
    }
}