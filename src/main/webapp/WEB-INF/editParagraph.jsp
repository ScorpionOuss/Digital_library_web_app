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
				<a href="editParagraph?idPar=${paragraph.nextParagraph}&titleStory=${paragraph.story}"> 
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
							[à Éditer] ${ch.idChoice}
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
			</c:otherwise>
		</c:choose>
</body>
</html>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>votre hero histoire</title>
	<link rel="stylesheet" type="text/css" href="../css/simplegrid.css">
	<link rel="stylesheet" type="text/css" href="../css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Amatic+SC" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Fredoka+One" rel="stylesheet">
</head>
<body>
    <div class="grid">
		<header >
			<div class="col-1-1" id="top">
					<div class="col-1-1">
						<h1 >histoire titre</h1>
					</div>
					<nav>
						<div id="deux-button" class="bbb">
							<div class="col-1-2-left">
								<div class="selected">
		                    		<a class="button" id="HOME" href="index.html"> HOME </a>
		                    	</div>
							</div>
							<div class="col-1-2-right">
							<a class="button" id="support" href="index.html">SUPPORT</a>
							</div>
						</div>
					</nav>
			</div>
		</header>
		<main>
            <div class="col-1-1-all">
				<nav >
				<ul>
					<div class="col-9-12">
						<li class= "col-1-3" ><a href="histoire-list.html" class="button2" title="partie-1">Histoires</a></li>
						<li class="col-1-3"><a href="edit-list.html" class="button2" title="partie-2">Edit</a></li>
						<li class="col-1-3"><a href="creation.html" class="button2" title="partie-3">Creation</a></li>
					</div>
					<div class = "col-3-12">
						<li class="col-4-12"><a class = "separe">|</a></li>
						<div class="dropdown">
							<li class="col-8-12"><a href="connexion.html" class="button2 dropbtn" title="partie-4">Login</a>
									<div class="dropdown-content">
										  <a href="#lien">preference</a>
										  <a href="#lien">log out</a>
										  <a href="#lien">espace personnel</a>
									</div>
							</li>
						</div>
					</div>
				</ul>
				</nav>
			</div>
            <div class="content">
                <div class="col-1-1" id="fond">
                    <div class="col-1-1">
                        <h2>Trésor du dragon titre de histoire charger </h2>						
                    </div>
					<div class="col-1-1 paragraph-affiche">
						<h4 class="paragraph-acteur">dragon_zero</h4>
						<p class="paragraph-content">La nuit tombe et la récréation est presque terminée. Mais le courageux roi Jack et ses fidèles chevaliers Zak et Caspar protègent toujours leur château fort contre les dragons féroces et les bêtes terribles. Quand ils ont résisté désespérément, sont soudainement apparus</p>
						<oi class="paragraph-choix "><a class = "button3"href="">Un trou est apparu dans le mur de la ville.</a></oi>
						<oi class="paragraph-choix "><a class="button3" href="">Il y a une figure mystérieuse dans le ciel.</a></oi>
						<oi class="paragraph-choix "><a class = "button3" href="">Zak a senti une grande puissance dans son corps.</a></oi>
					</div>
            </div>

</body>
</html>