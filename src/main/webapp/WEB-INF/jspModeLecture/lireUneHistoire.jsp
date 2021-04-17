<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
   <link rel="stylesheet" type="text/css" href="css/simplegrid.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
    <link href="https://fonts.googleapis.com/css?family=Amatic+SC" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Fredoka+One" rel="stylesheet">
<title>Lire une histoire</title>
</head>
<body>
	<jsp:include page="../header.jsp" />

             <div class="col-1-1">
                <h2>${donneeHis.title} </h2>						
             </div>
			<div class="col-1-1 paragraph-affiche">
				<h4 class="paragraph-acteur">Crée par ${donneeHis.creator}</h4>
				<h5>${donneeHis.description} </h5>
			<p class="paragraph-content"> ${donneePar.text} </p>
			
			<c:if test="${donneePar.choices != null || !donneePar.choices.isEmpty() }">
					<h2> Suite </h2>
					<c:forEach items="${donneePar.choices}" var="ch">
						<%-- Test if it's masked or not  --%>
						<c:if test="${ch.isMasked == false}">
							<a href="LireParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> <br>
						</c:if>		
					</c:forEach>
			</c:if> 

		<jsp:include page="../footer.jsp" />
</body>
</html>