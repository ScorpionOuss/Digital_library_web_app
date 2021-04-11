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
		<h3> rédigée par ${par.author}</h3>
		<p> ${par.text}</p>
	<h2> Suite </h2>
		<%-- see if there is a next paragraph :  --%>
		<c:choose>
			<c:when test= "${par.nextParagraph != null}">
				<a href="affichParagraph?idPar=${par.nextParagraph}&titleStory=${par.story}"> 
					Paragraphe suivante
				</a>
			</c:when>
			<%-- otherwise we have choices to display  --%>
			<c:otherwise>
				<c:if test="${par.choices != null }">
					<c:forEach items="${par.choices}" var="ch">
						<%-- Test if it's masked or not  --%>
						<c:if test="${ch.isMasked == false}">
							<a href="LireParagraph?idChoice= ${ch.idChoice}"> ${ch.text}</a> <br>
						</c:if>
						
					</c:forEach> 
				</c:if>
			</c:otherwise>
		</c:choose>
</body>
</html>