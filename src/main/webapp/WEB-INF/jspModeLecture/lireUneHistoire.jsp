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
		<%-- we have choices to display  --%>
		<c:if test="${donneePar.choices != null || !donneePar.choices.isEmpty()}">
			<h2> Suite </h2>
				<c:forEach items="${donneePar.choices}" var="ch">
					<%-- Test if it's masked or not  --%>
					<c:if test="${ch.isMasked == false}">
						<a href="LireParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> <br>
					</c:if>	
				</c:forEach> 
		</c:if>
</body>
</html>