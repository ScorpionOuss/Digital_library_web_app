
<%@page import="beans.Utilisateur"%>
<%@page import="javax.security.auth.message.callback.PrivateKeyCallback.Request"%>
<%@page import="dao.ChoixDAO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


			<jsp:include page="header.jsp" />
                    <div class="col-1-1">
                        <h2>${donneeHis.title} </h2>						
                    </div>
                   	 <h2> Description </h2>
						<p> ${donneeHis.description} </p>
					<div class="col-1-1 paragraph-affiche">
					
					<c:choose>
						<c:when test="${droits == true}">
								<a href="modifier?idP=${donneeHis.firstParagraph}"> Modifier le 1 er paragraphe</a>
								</br>
		 						<a href="supprimer?idP=${donneeHis.firstParagraph}"> Supprimer le 1 er paragraphe</a>
						</c:when>
						<c:otherwise>
						<c:out value="Vous n'avez pas les droits de modification et suppresion du paragraphe"></c:out>
						</c:otherwise>
					</c:choose>
						<h4 class="paragraph-acteur">Créée par ${donneeHis.creator}</h4>
						<p class="paragraph-content"> ${donneePar.text} </p>
			
				<c:if test="${donneePar.choices != null }">
					<c:forEach items="${donneePar.choices}" var="ch">
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
					    
					    <c:set var="pseudo3" value="${utilisateur.userName}"/>
	 					<c:set var="pseudo4" value="${choiceDAO.lockedOrDoneBy(ch.idChoice)}"/> 
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