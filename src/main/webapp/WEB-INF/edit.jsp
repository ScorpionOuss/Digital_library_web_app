<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

	<meta charset="UTF-8">
	<title>votre hero histoire</title>
	<link rel="stylesheet" type="text/css" href="css/simplegrid.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Amatic+SC" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Fredoka+One" rel="stylesheet">
<meta charset="UTF-8">
<title>Histoires à lire </title>
</head>
<body>
	<%@ page import="beans.Histoire"%>
	<%@ page import="java.util.LinkedList"%>
	<h1>Les histoires que vous pouvez lire </h1>
		${histoiresEdit.size()}

   		<jsp:include page="header.jsp" />
   
            <div class="content">
<%--             <c:if test="${v.publicLec || isConnected}">
 --%>			<%-- Display the story only if it is public for lecture or the user is logged in  --%>
                <div class="col-1-1" id="fond">
                    <div class="list-histoire">
                      <c:forEach items="${histoiresEdit}" var="v">
                        <div class="col-1-3">
                            <article class = "at-histoire">
                                <header>
                                    <a href="editStory?titre=${v.title}" class="test_a"><img class = "image-1-3-2" src="images/enfant.png" alt="photo6"></a>
                                    <h4>Enfants</h4>
                                    <time datetime="2017-11-20">20/11/2021</time>
                                </header>
                                <section>
                                    <h5>
                                        ${v.title}
                                    </h5>
                                </section>
                                <footer>
                                    <a href="index.html" class="button2" title="Jump to">create more@histoires</a>
                                </footer>
                            </article>
                        </div>
                       			</c:forEach>
                       
                    </div>
                </div>

<%--               	</c:if>
 --%>
                <jsp:include page="footer.jsp" />
                
        </main>

    </div>

</body>
</html>