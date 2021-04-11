<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Histoires à lire </title>
</head>
<body>
	<%@ page import="beans.Histoire"%>
	<%@ page import="java.util.LinkedList"%>
	<h1>Les histoires que vous pouvez lire </h1>
		${histoires.size()}
		<c:forEach items="${histoires}" var="v">
       		<a href="LireUneHistoire?titre=${v.title}"> ${v.title} </a>
   		</c:forEach>

</body>
</html>