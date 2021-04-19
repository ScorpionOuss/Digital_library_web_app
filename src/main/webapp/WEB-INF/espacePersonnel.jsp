<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>votre hero histoire</title>
    <link rel="stylesheet" type="text/css" href="../css/normalize.css">
	<link rel="stylesheet" type="text/css" href="../css/simplegrid.css">
	<link rel="stylesheet" type="text/css" href="../css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Amatic+SC" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Fredoka+One" rel="stylesheet">
</head>
<body >
				<jsp:include page="header.jsp" />
                        <div class="col-1-1-all espace-block">
                            <h2 class="title-move-right">Vos histoires</h2>
                            <div class="list-histoire">
                            <c:forEach items="${stories}" var="s">
                                <div class="col-1-3">
                                    <article class = "at-histoire">
                                        <header>
                                            <a href="editStory?titre=${s.title}" class="test_a">
                                                <img class = "image-1-3-2" src="${s.image}" alt="photo6"></a>
                                            <h4>${s.title}</h4>
                                            <time datetime="2017-11-20">20/11/2021</time>
                                            <c:choose>
                                            	<c:when test="${s.publicLec}"> <h5>Publique en lecture</h5> </c:when>
                                            	<c:otherwise> <h5>Non publiée en lecture</h5> </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                            	<c:when test="${s.publicEc}"> <h5>Publique pour l'édition</h5> </c:when>
                                            	<c:otherwise> <h5>Privée en mode édition</h5> </c:otherwise>
                                            </c:choose>
                                        </header>
                                        <section>
                                        </section>
                                        <footer>
<%--                                         <c:choose>
                                        <c:when test="${!s.publicEc}">
                                            <a href="autoriserAcces?titre=${s.title}" class="button2" title="Jump to">Configurer les droits[Mode édition]</a>
                                        </c:when>
                                        <c:otherwise>
                                        <a href="#" class="button2" title="Jump to">Publique en écriture</a>
                                        </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${!s.publicLec}">
                                            <br></br>
                                            <a href="publier?titre=${s.title}" class="button2" title="Jump to">Publier[Mode lecture]</a>
                                     		</c:when>
                                     		<c:otherwise> Dépublier pour lecture  </c:otherwise>
                                     		
                                     		<c:otherwise>
                                            <button type="submit" class="button2 button_supr_p" >Publiée[Mode lecture]</button>
                                     		</c:otherwise>
                                       </c:choose> --%>
<%-- <%-- =======
 --%>                                       <a href="autoriserAcces?titre=${s.title}" class="button2" title="Jump to">Configurer les droits d'écriture</a>
                                       <form action="publier" method="get">
                                       		<input id="titre" name="titre" type="hidden" value="${s.title}">
                                       		<input id="etatPublic" name="etatPublic" type="hidden" value="${s.publicLec}">
                                       		<button type="submit" class="button2 button_supr_p">
                                       				<c:choose>
                                            		<c:when test="${!s.publicLec}"> Publier pour lecture </c:when>
                                     				<c:otherwise> Dépublier pour lecture  </c:otherwise>
                                      				 </c:choose>
                                       		</button>
                                       </form>                   
                                       
<%-- >>>>>>> 9092363dd9fc8e9faa66e027e4b938a99b510663 --%>
 --%>                                        </footer>
                                    </article>
                                </div>
                                </c:forEach>
                            </div>
                        </div>
                        
                       <%--  <p class="col-1-1-all line-separe">
                        <div class = "col-1-1-all espace-block">
                            <h2 class="title-move-right">Vos pararaphes</h2>
                            <div class="list-paragraph">
                            <c:forEach items="${paragraphs}" var="p">
                                <div class="col-1-3">
                                    <article class = "at-histoire">
                                        <header>
                                            <a href="index.heml" class="test_a"><img class = "image-1-3-2" src="images/enfant.png" alt="photo6"></a>
                                            <h4></h4>
                                            <h5>${p.story}</h5>
                                        </header>
                                        <section>
                                            <h5>
                                                ${p.text}
                                            </h5>
                                        </section>
                                        <footer>
                                            <a href="modifier?idP=${p.idParagraph}" class="button2" title="Jump to">modifier </a>
                                            <button class="button2 button_supr_p" action="supprimer?idP=${p.idParagraph}">supprimer</button>
                                        </footer>
                                    </article>
                                </div>
                           	</c:forEach>
                                
                            </div>
                        </div> --%>
                        
                        <div class="col-1-1-all espace-block">
                            <h2 class="title-move-right">Non encore validé</h2>
		 								<a href="writeParagraph?idChoice=${idChoice}"> ${text}</a>
                            </div>
		<jsp:include page="footer.jsp" />
						
			
</body>
</html>
