<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>votre hero histoire</title>
    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/simplegrid.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
    <link href="https://fonts.googleapis.com/css?family=Amatic+SC" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Fredoka+One" rel="stylesheet">
</head>
<body>
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
						<li class="col-1-3"><a href="creation.html`" class="button2" title="partie-3">Creation</a></li>
					</div>
					<div class = "col-3-12">
						<li class="col-4-12"><a class = "separe">•</a></li>
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
                            <form method="post" class="pure-form pure-form-aligned creation-paragraph" action="creation">
                                <fieldset>
                                    <div class="pure-control-group" id="nomp">
                                        <label for="nom" class = "label-p">titre du histoire</label>
                                        <input name='nom' id="nom" type="text" placeholder="titre">
                                        <span class="msgE">erreur message</span>
                                    </div>
                                    <div class="pure-control-group" id="sitep">
                                        <label for="site">image URL</label>
                                        <input id="site" name="site" type="text" placeholder="URL">
                                        <span>erreur message</span>
                                    </div>
                                    <div class="pure-control-group" id="droit" >
                                      <label class = "label-p"> Autorisation du Edit</label>
                                      <label for="typecand" class="pure-radio">
                                        <input type="radio" class="droitbtn" id="public" name="typecand" value="public" checked>
                                        public
                                      </label>
                                      <label for="typecand" class="pure-radio" >
                                        <input type="radio"  class="droitbtn" id = "invite" name="typecand" value="invite" >
                                        invite
                                      </label>
                                      <span class="add"><input class="pure-button pure-button-primary buttoadd buttonadd " name="add" value="add" id="buttonadd"></span>
                                      <span class="msgE">erreur message</span>
                                    </div>
                                    <div class="pure-control-group" id="anneep">
                                        <label for="annee" class = "label-p">annee creation</label>
                                        <input id="annee" name="annee" type="text" placeholder="01/01/2000">
                                        <span class="msgE">erreur message</span>
                                    </div>
                                    <div class="pure-control-group" id="descrp">
                                        <label for="presentation" class = "label-p">presentation</label>
                                        <textarea id="descr" name="presentation" rows="3" cols="80" placeholder="c'est une histoire lie ..."></textarea>
                                        <span class="msgE">erreur message</span>
                                    </div>
                                    <div class="pure-control-group" id="paragraphp">
                                        <label for="paragraph" class = "label-p" >histoire</label>
                                        <textarea id="paragraph" name="paragraph" rows="10" cols="80" placeholder="il y a longtemps ..."></textarea>
                                        <span class="msgE">erreur message</span>
                                    </div>
                                    <div class="pure-controls" id="casep">
                                            <label for="conditions" class="pure-checkbox" >
                                            <input id="case" name='case' type="checkbox">  I've read the terms and conditions
                                            <span class="msgE">erreur message</span>
                                            </label>
                                        <input type="submit" id="btn" name="button" value="submit" class="pure-button pure-button-primary button">
                                    </div>
                        
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
        </main>
    </div>

    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <script>
      $(function(){
          $('form span').hide();
          var flag=0;
          console.log("jquery ok");
          $('#dept,#nom,#stylemusic,#annee,#descr,#site,#email,#case:checked').each(
              function(index,element){
                $(element).focus(function(){
                  $(this).css("background-color","#FFFFCC");
                });
                $(element).blur(function(){
                  $(this).css("background-color","");
                  choix(index);
                });
              }
          );
          function choix(index){
            var res
            switch (index) {
              case 0:vDept();break;
              case 1:vNom();vAjax();break;
              case 2:vStyle();break;
              case 3:vAnnee();break;
              case 4:vDescr();break;
              case 7:vCheck();break;
              default:break;
            }
          }
          function vAjax(){
            var nom = $('#nom').val();
            if(nom!=''){
              $.ajax({
                  type:'post',
                  url:'verifierAjax.php',
                  dataType: "json",
                  data:{nom:nom},
                  async:false,
                  success:function(resultat){
                        console.log('entrer dans ajax');
                        if(!resultat){
                            console.log('n\'existe pas dans data');
                            $('#nom + span').html("n'existe pas dans data");
                            $('#nom + span').show();
                        }else{
                          console.log('non resulat');
                        }
                  }
              });
            }
          }
          function vNom(){
            var str='#nom + span';
            var res;
            if($('#nom').val()==''){
              $(str).html("obligatoire remplir");
              $(str).show();
              return false;
            }else {
              $(str).hide();
              return true;
            }
            
          }

          function vDescr(){
            var str='#descr + span'
            if($('#descr').val()==''){
              $(str).html("obligatoire remplir");
              $(str).show();
              return false;
            }else{
              $(str).hide();
              return true;
            }
          }

          function vparagraph(){
            var str='#paragraph + span'
            if($('#paragraph').val()==''){
              $(str).html("obligatoire remplir");
              $(str).show();
              return false;
            }else{
              $(str).hide();
              return true;
            }
          }


          function vRadio(){
            if($('input:radio:checked').length!=1){
              $('#droit > span').html("obligatoire remplir");
              $('#droit > span').show();
              return false;
            }else{
              $('#droit > span').hide();
              return true;
            }
          }
          function vCheck(){
            if($('#case:checked').length!=1){
              $('#case + span').html("&emsp; obligatoire remplir");
              $('#case + span').show();
              return false;
            }else{
              $('#case + span').hide();
              return true;
            }
          }
          function vAnnee(){
            var str='#annee + span'
            if ($('#annee').val()=='') {
               $(str).html("obligatoire remplir");
                $(str).show();
               return false;
            }else if(!($('#annee').val().match(/^(?:(?:(?:0?[1-9]|1[0-9]|2[0-8])(\/?)(?:0?[1-9]|1[0-2])|(?:29|30)(\/?)(?:0?[13-9]|1[0-2])|31(\/?)(?:0?[13578]|1[02]))(\/?)(?!0000)[0-9]{4}|29([-/.]?)0?2(\/?)(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00))$$/))){   //???? pourquoi???
               $(str).html("date incorrect");
               $(str).show();
               return false;
            }else{
               $(str).hide();
               return true;
            }
          }
          function verif(){
            let res=true;
            if(!vNom())
              res=false;
            if(!vDescr())
              res=false;
            if(!vparagraph())
              res=false;
            if(!vRadio())
              res=false;
            if(!vCheck())
              res=false;
            if(!vAnnee())
              res=false;
            return res;
          }

          function addElementDiv(obj) {
            var parent = document.getElementById(obj);
            var div = document.createElement("div");
            div.setAttribute("id", "newDiv");
            div.innerHTML = "js 动态添加div";
            parent.appendChild(div);
        }
          /*
          $("#stylemusic").autocomplete({
            source: "cherche.php",
          });
          $('#nom').autocomplete({
            source:"cherche2.php"
          }); */
          $('#btn').on('click',function(){
            verif();
            vAjax();
          });
          $('#invite').on('click',function(){
              $('.add').show();
          });
          $('#public').on('click',function(){
              $('.add').hide();
          });
          
      }
      )


    </script>
</body>
<footer id="end" >
    <a href="#top" class="button2">return top</a><br><br>
    &copy; 2021, Votre hero histoire team<br>
  All trademarks and registered trademarks appearing on
  this site are the property of their respective owners.<br>
  <a href="index.html" class="button2" title="about">@about us</a>
</footer>
</html> 

<%-- <%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Inscription</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="creation">
            <fieldset>
                <legend>Inscription</legend>
                <p>Vous pouvez vous inscrire via ce formulaire.</p>

                <label for="name">Titre <span class="requis">*</span></label>
                <input type="text" id="name" name="name" value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" />
                <span class="erreur">${form.erreurs['name']}</span>
                <br />

                <label for="site">url<span class="requis">*</span></label>
                <input type="text" id="site" name="site" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['site']}</span>
                <br />

                <label for="annee">date création <span class="requis">*</span></label>
                <input type="password" id="annee" name="annee" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['annee']}</span>
                <br />

                <label for="presentation">Description</label>
                <input type="text" id="presentation" name="presentation" value="<c:out value="${utilisateur.nom}"/>" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['presentation']}</span>
                <br />

				<label for="paragraph">First Paragraph</label>
                <input type="text" id="paragraph" name="paragraph" value="<c:out value="${utilisateur.nom}"/>" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['paragraph']}</span>
                <br />
                
                <input type="submit" value="Créer" class="sansLabel" />
                <br />
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
            </fieldset>
        </form>
    </body>
</html> --%>
