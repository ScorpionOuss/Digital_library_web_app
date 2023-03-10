<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>votre hero histoire</title>
	<link rel="stylesheet" type="text/css" href="css/simplegrid.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/connexion.css">
	<link href="https://fonts.googleapis.com/css?family=Amatic+SC" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Fredoka+One" rel="stylesheet">
</head>
<body >
	<div class="grid">
		<header >
			<div class="col-1-1" id="top">
					<div class="col-1-1">
						<h1 >Votre Hero histoire</h1>
					</div>
					<nav>
						<div id="deux-button" class="bbb">
							<div class="col-1-2-left">
								<div class="selected">
		                    		<a class="button" id="HOME" href="accueil"> ACCUEIL </a>
		                    	</div>
							</div>
							<div class="col-1-2-right">
							<a class="button" id="support" href="support">SUPPORT</a>
							</div>
						</div>
					</nav>
			</div>
		</header>
		<main>
			<div class="col-1-1-all">
				<nav id="sommaire">
				<ul>
					<div class="col-9-12">
						<li class= "col-1-3" ><a href="afficherHistoires" class="button2" title="partie-1">Histoires</a></li>
						<li class="col-1-3"><a href="edit" class="button2" title="partie-2">Édition</a></li>
						<li class="col-1-3"><a href="creation" class="button2" title="partie-3">Création</a></li>
					</div>
					<div class = "col-3-12">
						<li class="col-4-12"><a class = "separe">•</a></li>
						<div class="dropdown">
                    <c:choose>
                    	<c:when test="${empty utilisateur}">
						<li class="col-8-12"><a href="connexion" class="button2 dropbtn" title="partie-4">Connexion</a>
                  		<div class="dropdown-content">
                  		<a href="inscription">Inscription</a>
                    	</c:when>
                    	<c:otherwise>
							<li class="col-8-12"><a href="logout" class="button2 dropbtn" title="partie-4">Déconnexion</a>
	                    	<div class="dropdown-content">
							<!--   		<a href="#lien">preference</a> -->
							  		<a href="espacePersonnel">Espace personnel</a>
								</div>
                    	</c:otherwise>
                    </c:choose>
						
						</div>
						
					</div>
				</ul>
				</nav>
				<div class="content">
					<div class="col-1-1" id="fond">

</body>
</html>