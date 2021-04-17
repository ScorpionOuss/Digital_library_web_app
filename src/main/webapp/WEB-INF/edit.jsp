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
<title>Histoires à éditer </title>
</head>
<body>
	<%@ page import="beans.Histoire"%>
	<%@ page import="java.util.LinkedList"%>
	<h1>Les histoires que vous pouvez éditer </h1>

   		<jsp:include page="header.jsp" />
   
            <div class="content">
                <div class="col-1-1" id="fond">
                    <div class="list-histoire">
                      <c:forEach items="${histoiresEdit}" var="v">
                        <div class="col-1-3">
                            <article class = "at-histoire">
                                <header>
                                    <a href="editStory?titre=${v.title}" class="test_a"><img class = "image-1-3-2" src="${v.image}" alt="photo6"></a>
                                    <h4>${v.title}</h4>
                                </header>
                                <section>
                                    <h5>
                                        ${v.description}
                                    </h5>
                                </section>
                                <footer>
                                    <a href="creation" class="button2" title="Jump to">create more@histoires</a>
                                </footer>
                            </article>
                        </div>
                       			</c:forEach>
                       
                    </div>
                </div>
                <jsp:include page="footer.jsp" />

    </div>

</body>
</html>