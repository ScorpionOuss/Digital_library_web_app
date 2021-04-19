<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
        <link rel="stylesheet" type="text/css" href="css/simplegrid.css">
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<link href="https://fonts.googleapis.com/css?family=Amatic+SC" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Fredoka+One" rel="stylesheet">
        <!--link type="text/css" rel="stylesheet" href="css/form.css" /-->
    </head>
    <body>
	<jsp:include page="header.jsp" /> 
		<div class ="col-3-12"></div>
		<div class="col-1-2">
               <div class="loginOrRegisterBox col-1-1-all">
               		
                   <div id="form">
                       <form action="connexion" method="post" id="login">
                           <input type="text" placeholder=pseudonyme name="username">
                           <span class="erreur">${form.erreurs['username']}</span>
                           <input type="password" placeholder="mot de passe" name="motdepasse">
                            <span class="erreur">${form.erreurs['motdepasse']}</span>
                           <input type="submit" value="Connexion">
                           <span class="erreur">${form.erreurs['connexion']}</span>
                           
                       </form>
                   </div>
        	</div>
	  	</div>
	  <div class="col-3-12"></div>
	<jsp:include page="footer.jsp" />    
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
     <script>
	    window.onload = function () {
	        var login = document.getElementById("login");
	        var register=document.getElementById("register");
	        var btnlogin = document.getElementById("loginButton");
	        var btnregister = document.getElementById("registerButton");
	
	        btnlogin.onclick = function () {
	            login.style.display = "block";
	            register.style.display = "none";
	        }
	        btnregister.onclick = function () {
	        	login.style.display = "none";
	            register.style.display = "block";
	        }
	    }
	 </script>
    </body>
</html>