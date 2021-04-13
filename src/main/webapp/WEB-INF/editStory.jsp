<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lire une histoire</title>
</head>
<body>
	<h1> ${donneeHis.title}</h1>
	<h2> Crée par ${donneeHis.creator}</h2>
	<h2> Description </h2>
		<p> ${donneeHis.description} </p>
	<h2> Paragraphe </h2>
		<h3> rédigée par ${donneePar.author}</h3>
		<p> ${donneePar.text}</p>
	<h2> Suite </h2>
		<%-- see if there is a next paragraph :  --%>
		<c:choose>
			<c:when test= "${donneePar.nextParagraph != null}">
				<a href="affichParagraph?idPar=${donneePar.nextParagraph}&titleStory=${donneePar.story}"> 
					Paragraphe suivante
				</a>
			</c:when>
			<%-- otherwise we have choices to display  --%>
<%-- 			<c:otherwise>
				<c:if test="${donneePar.choices != null }">
					<c:forEach items="${donneePar.choices}" var="ch">
						Test if it's masked or not 
						<c:if test="${ch.isMasked == false}">
							<a href="LireParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> <br>
						</c:if>
						
					</c:forEach> 
				</c:if>
			</c:otherwise> --%>
			<c:otherwise>
				<c:if test="${donneePar.choices != null }">
					<c:forEach items="${donneePar.choices}" var="ch">
						<%-- Test if it's locked or not  --%>
						<c:out value="la valeur est ${ch.locked}" />
						<br/>
					 <c:choose>
						<c:when test="${ch.locked == 0}">
							<a href="writeParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> 
							<c:out value="À Éditer"/>
							<br>
						</c:when>
	 					<c:when test="${ch.locked == 1}"> 
						${ch.text} <br>

						</c:when>
					    <c:when test="${ch.locked == 2}">
							<a href="editParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> 
							<br>
						</c:when>
						
						</c:choose>
						
					</c:forEach> 
				</c:if>
			</c:otherwise>
		</c:choose>
</body>
</html>