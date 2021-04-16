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
</head>
<body>
    		<jsp:include page="header.jsp" />

            <div class="content">
                <div class="col-1-1" id="fond">
                    <div class="col-1-1">
                        <h2>${paragraph.story} </h2>						
                    </div>
                    <c:choose>
						<c:when test="${droits == true}">
								<a href="modifier?idP=${paragraph.idParagraph}"> Modifier le paragraphe</a>
								</br>
		 						<a href="supprimer?idP=${paragraph.idParagraph}"> Supprimer le paragraphe</a>
						</c:when>
						<c:otherwise>
						<c:out value="Vous n'avez pas les droits de modification et suppresion du paragraphe"></c:out>
						</c:otherwise>
					</c:choose>
					<div class="col-1-1 paragraph-affiche">
						<h4 class="paragraph-acteur">${paragraph.author}</h4>
						<p class="paragraph-content"> ${paragraph.text} </p>
			
			<%-- we have choices to display  --%>
			<c:if test="${paragraph.choices != null || !paragraph.choices.isEmpty() }">
				<c:forEach items="${paragraph.choices}" var="ch">
					<%-- Test if it's locked or not  --%>
					 <c:choose>
						<c:when test="${ch.locked == 0}">
							<a href="writeParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> 
							<c:out value="À Éditer"/>
							<br>
						</c:when>
	 					<c:when test="${ch.locked == 1}"> 
					   		<c:set var="pseudo1" value="${utilisateur.userName}"/>
	 						<c:set var="pseudo2" value="${choiceDAO.lockedOrDoneBy(ch.idChoice)}"/> 
						
	 							<c:choose>
		 							<c:when test="${pseudo1 eq pseudo2}">
		 								<a href="writeParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a>
		 							<c:out value="Modifié et non validé"/>
		 							<br/>
		 							</c:when>
		 							<c:otherwise>${ch.text} <br></c:otherwise>
								</c:choose>
						</c:when>
					    <c:when test="${ch.locked == 2}">
							<a href="editParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> 
							<br>
						</c:when>
						
						</c:choose>
						
					</c:forEach> 
			</c:if>	
		</div>
		<input type ="button" class="pure-button pure-button-primary buttonadd " name="ajouDesChoix" id="ajouDesChoix" value="ajouter des choix">
		<div id="choicesForm" style="display:none">
      		<form method="post" class="pure-form pure-form-aligned creation-paragraph" action="addAChoice">
      		<input id="prevStory" name="prevStory" type="hidden" value="${donneePar.story}">
      		<input id="prevPar" name="prevPar" type="hidden" value="${donneePar.idParagraph}">
           	<div class="pure-control-group" id="LesChoix">
               <div class="pure-controls-group" >
                    <label for="LesChoix" class = "label-p" >Les Choix</label>
                    <input type ="button" class="pure-button pure-button-primary buttonadd " name="LesChoix" value="ajouter Un Choix" id="buttonaddChoix">
                	<input id="submit" type="submit" class="pure-button pure-button-primary buttonadd " name="button" value="submit">
                </div>
           </div>
     	   </form>
		</div>
  </div>
  <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
    		$('#ajouDesChoix').on('click', function(){
    			$("#choicesForm").show();
    		})
    		
    		$('#buttonaddChoix').on('click',function(){
                var div_parent = document.getElementById("LesChoix");
                var div = document.createElement('div');
                var input = document.createElement('input');
                var button = document.createElement("button");
                div.className = "pure-controls";
                input.className = "choix";
                input.name="choix"
                input.type="text";
                input.placeholder="choix...";
                button.type="button";
                button.className="btndelete";
                button.innerHTML="X";
                div.appendChild(input);
                div.appendChild(button);
                div_parent.appendChild(div);
                $('.btndelete').on('click',function(){
                $(this).parent().remove();
               });
            });
    </script>
  <jsp:include page="footer.jsp" />
			
</body>
</html>