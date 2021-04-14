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
		<%--we have choices to display  --%>
		<c:if test="${paragraph.choices != null || !paragraph.choices.isEmpty() }">
			<h2> Suite </h2>
			<c:forEach items="${paragraph.choices}" var="ch">
				<%-- Test if it's masked or not  --%>
				<c:if test="${ch.isMasked == false}">
					<a href="LireParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> <br>
				</c:if>		
			</c:forEach> 
		</c:if>
		
	<h2> Historique </h2>
		<%-- When reading the user must have the ability to get back to the first paragraph --%>
		<a href="AlterHisAndRead?idChoice=-1"> Get back to the first paragraph</a> <br>
		<c:forEach items="${history.hisChoices}" var="hCh">				
			<a href="AlterHisAndRead?idChoice=${hCh.idChoice}"> ${hCh.text}</a> <br>
		</c:forEach> 
</body>
</html>