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
		    	<jsp:include page="header.jsp" />
                        <div class="col-1-1">
                             <form method="post" class="pure-form pure-form-aligned creation-paragraph" action="modifier">
								<input id="idChoice" name="idChoice" type="hidden" value="${idChoice}">
                                <fieldset>
                                   
                                    <div class="pure-control-group" id="paragraphp">
                                        <label for="paragraph" class = "label-p" >histoire</label>
                                        <textarea id="paragraph" name="paragraph" rows="10" cols="80" >${paragraph.text}</textarea>
                                        <span class="msgE">erreur message</span>
                                    
                                    <div class="pure-controls" id="casep">

                                        <input id="submit" type="submit" name="button" value="modify"  class="pure-button pure-button-primary button">
                                        <input id="annuler" type="submit" name="annuler" value="annuler"  class="pure-button pure-button-primary button">
                                   	</div>
                        
                                </fieldset>
                            </form>
                        </div>
    <jsp:include page="footer.jsp" />

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

          $('#buttonadd').on('click',function(){
              var div_parent = document.getElementById("invite-users");
              var div = document.createElement('div');
              var label = document.createElement('label');
              var input = document.createElement('input');
              var button = document.createElement("button");
              var span = document.createElement("span");
              div.className = "pure-control-group add";
              div.id = "invite-user-p";
              label.className="label-p";
              label.for = "userid";
              label.innerHTML = "invite username";
              input.className = "invite-user";
              input.name="userid"
              input.type="text";
              input.placeholder="username";
              button.type="button";
              button.className="btndelete";
              button.innerHTML="X";

              span.className="msgE";
              span.innerHTML="erreur message";
              span.style.display = "none";      
              div.appendChild(label);
              div.appendChild(input);
              div.appendChild(button);
              div.appendChild(span);
              div_parent.appendChild(div);
              $('.btndelete').on('click',function(){
              $(this).parent().remove();
             });
          });

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

          
          
      }
      )


    </script>
    
</body>
</html>