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
</head>
<body>
    		<jsp:include page="header.jsp" />

            <div class="content">
                <div class="col-1-1" id="fond">
                    <div class="col-1-1">
                        <h2>${paragraph.story} </h2>						
                    </div>
					<div class="col-1-1 paragraph-affiche">
						<h4 class="paragraph-acteur">${paragraph.author}</h4>
						<p class="paragraph-content"> ${paragraph.text} </p>
			
			<%-- we have choices to display  --%>
			<c:if test="${donneePar.choices != null || !donneePar.choices.isEmpty() }">
				<c:forEach items="${donneePar.choices}" var="ch">
					<%-- Test if it's locked or not  --%>
					 <c:choose>
						<c:when test="${ch.locked == 0}">
							<a href="writeParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> 
							<c:out value="À Éditer"/>
							<br>
						</c:when>
	 					<c:when test="${ch.locked == 1}"> 
						${ch.text} <br>

						</c:when>
					    <c:otherwise>
							<a href="editParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> 
							<br>
						</c:otherwise>
						
						</c:choose>
						
					</c:forEach> 
			</c:if>
			
			</div>
           </div>
			<jsp:include page="footer.jsp" />
			
</body>
</html>