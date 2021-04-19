<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>SUPPORT</title>
    <link rel="stylesheet" type="text/css" href="css/support.css">
	<link href="https://fonts.googleapis.com/css?family=Amatic+SC" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Fredoka+One" rel="stylesheet">
	
</head>
<body>
    <jsp:include page="header.jsp" />
        <div class="container-question-structure">
            <section class="tabbed col-xs-offset-1 col-lg-offset-2 col-xs-22 col-lg-20">
                <h2>FOIRE AUX QUESTION</h2>
                <div class="container-lists">
                    <ul class="container-list js-accord" component:js-accord>
                        <li class="item js-item target--off">
                            <p class="question js-shown">
                                comment creer une histoire par soi meme?
                            </p>
                            <p class="answer js-target target--off">
                                il faut connexion avec une compte et clique la button creation.
                            </p>
                            <span class="answer-button js-trigger trigger--off">
                                "&nbsp;"
                            </span>
                        </li>
                        <li class="item js-item target--off">
                            <p class="question js-shown">
                                comment creer une histoire par soi meme?
                            </p>
                            <p class="answer js-target target--off">
                                il faut connexion avec une compte et clique la button creation.
                            </p>
                            <span class="answer-button js-trigger trigger--off">
                                "&nbsp;"
                            </span>
                        </li>
                        <li class="item js-item target--off">
                            <p class="question js-shown">
                                comment creer une histoire par soi meme?
                            </p>
                            <p class="answer js-target target--off">
                                il faut connexion avec une compte et clique la button creation.
                            </p>
                            <span class="answer-button js-trigger trigger--off">
                                "&nbsp;"
                            </span>
                        </li>
                    </ul>
                    <ul class="container-list">
                        <li class="item js-item target--off">
                            <p class="question js-shown">
                                comment creer une histoire par soi meme?
                            </p>
                            <p class="answer js-target target--off">
                                il faut connexion avec une compte et clique la button creation.
                            </p>
                            <span class="answer-button js-trigger trigger--off">
                                "&nbsp;"
                            </span>
                        </li>
                        <li class="item js-item target--off">
                            <p class="question js-shown">
                                comment creer une histoire par soi meme?
                            </p>
                            <p class="answer js-target target--off">
                                il faut connexion avec une compte et clique la button creation.
                            </p>
                            <span class="answer-button js-trigger trigger--off">
                                "&nbsp;"
                            </span>
                        </li>
                        <li class="item js-item target--off">
                            <p class="question js-shown">
                                comment creer une histoire par soi meme?
                            </p>
                            <p class="answer js-target target--off">
                                il faut connexion avec une compte et clique la button creation.
                            </p>
                            <span class="answer-button js-trigger trigger--off">
                                "&nbsp;"
                            </span>
                        </li>
                    </ul>
                </div>
            </section>
        </div>
    <jsp:include page="footer.jsp" />
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
		$(function () {
            $(".answer-button").click(function () {
            	var answer = $(this).prev();
                var question=answer.prev();
                var item = $(this).parent();
    
                if(item.attr("class")==="item js-item target--off"){
	                answer.attr("class","answer js-target target--on");
	                $(this).attr("class","answer-button js-trigger trigger--on");
	                item.attr("class","item js-item target--on");
	                question.css("color","#FFF");

	            }else{
	            	answer.attr("class","answer js-target target--off");
	                $(this).attr("class","answer-button js-trigger trigger--off");
	                item.attr("class","item js-item target--off");
	                question.css("color","#333");
	            }
            })

        })
    </script>
</body>
</html>
