

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


			<jsp:include page="header.jsp" />

            <div class="content">
                <div class="col-1-1" id="fond">
                    <div class="col-1-1">
                        <h2>${donneeHis.title} </h2>						
                    </div>
                   	 <h2> Description </h2>
						<p> ${donneeHis.description} </p>
					<div class="col-1-1 paragraph-affiche">
						<h4 class="paragraph-acteur">Créée par ${donneeHis.creator}</h4>
						<p class="paragraph-content"> ${donneePar.text} </p>
			
				<c:if test="${donneePar.choices != null }">
					<c:forEach items="${donneePar.choices}" var="ch">
						<%-- Test if it's locked or not  --%>
						<c:out value="la valeur est ${ch.locked}" />
						<br/>
					 <c:choose>
						<c:when test="${ch.locked == 0}">
						<c:out value="Wa samhamd ${ch.idChoice}"/>
							<a href="writeParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> 
							<c:out value="À Éditer"/>
							<br>
						</c:when>
	 					<c:when test="${ch.locked == 1}"> 
						${ch.text} <br>

						</c:when>
					    <c:when test="${ch.locked == 2}">
							<a href="editParagraph?idChoice=${ch.idChoice}"> ${ch.text}</a> 
							<br>
						</c:when>
						
						</c:choose>
						
					</c:forEach> 
				</c:if>
            </div>
            </div>
            </div>

		<jsp:include page="footer.jsp" />
</body>
</html>