<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1> Paragraphe </h1>
		<h3> rédigée par ${paragraph.author}</h3>
		<p> ${paragraph.text}</p>
	<h2> Suite </h2>
		<%-- see if there is a next paragraph :  --%>
		<c:choose>
			<c:when test= "${paragraph.nextParagraph != null}">
				<a href="LireParagraph?idPar=${paragraph.nextParagraph}&titleStory=${paragraph.story}"> 
					Paragraphe suivante
				</a>
			</c:when>
			<%-- otherwise we have choices to display  --%>
		<c:otherwise>
				<c:if test="${donneePar.choices != null }">
					<c:forEach items="${donneePar.choices}" var="ch">
						<%-- Test if it's locked or not  --%>
					 <c:choose>
						<c:when test="${ch.locked == 0}">
							<a href="writeParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> 
							[à Éditer]
							<br>
						</c:when>
	 					<c:when test="${ch.locked == 1}"> 
						${ch.text} <br>

						</c:when>
					    <c:otherwise>
							<a href="EditParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> 
							<br>
						</c:otherwise>
						
						</c:choose>
						
					</c:forEach> 
				</c:if>
			</c:otherwise>
		</c:choose>
</body>
</html>