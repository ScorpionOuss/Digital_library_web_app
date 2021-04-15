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
<meta charset="UTF-8">
<title>Histoires à lire </title>
</head>
<body>
	<%@ page import="beans.Histoire"%>
	<%@ page import="java.util.LinkedList"%>
    <div class="grid">
		<header >
			<div class="col-1-1" id="top">
					<div class="col-1-1">
						<h1 >Les histoires que vous pouvez lire</h1>
					</div>
					<nav>
						<div id="deux-button" class="bbb">
							<div class="col-1-2-left">
								<div class="selected">
		                    		<a class="button" id="HOME" href="index.jsp"> HOME </a>
		                    	</div>
							</div>
							<div class="col-1-2-right">
							<a class="button" id="support" href="index.jsp">SUPPORT</a>
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
						<li class= "col-1-3" ><a href="afficherHistoires" class="button2" title="partie-1">Histoires</a></li>
						<li class="col-1-3"><a href="edit" class="button2" title="partie-2">Edit</a></li>
						<li class="col-1-3"><a href="creation" class="button2" title="partie-3">Creation</a></li>
					</div>
					<div class = "col-3-12">
						<li class="col-4-12"><a class = "separe">|</a></li>
						<div class="dropdown">
                            <li class="col-8-12"><a href="connexion" class="button2 dropbtn" title="partie-4">Login</a>
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
                    
                    <div class="list-histoire">
                         <c:forEach items="${histoires}" var="v">
           					 
                        <div class="col-1-3">
                            <article class = "at-histoire">
                                <header>
                                    <a href="LireUneHistoire?titre=${v.title}" class="test_a"><img class = "image-1-3-2" src="images/enfant.png" alt="photo6"></a>
                                    <h4>${v.title}</h4>
                                </header>
                                <section>
                                    <h5>
                                        ${v.description}
                                    </h5>
                                </section>
                                <footer>
                                    <a href="edit" class="button2" title="Jump to">create more stories</a>
                                </footer>
                            </article>
                        </div>
			</c:forEach>
                    </div>
                </div>

                
        </main>

    </div>

</body>
</html>