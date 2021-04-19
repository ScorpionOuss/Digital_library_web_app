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
                    <ul class="container-list js-accord">
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
                               What can you do in your personal space？
                            </p>
                            <p class="answer js-target target--off">
                                You can view the stories you created in your personal space (not including the stories you participated in), you can add the participants of the story or choose to tell the story to open it to everyone. Similarly, you can also choose to publish your story so that other audiences can watch it.
                            </p>
                            <span class="answer-button js-trigger trigger--off">
                                "&nbsp;"
                            </span>
                        </li>
                        <li class="item js-item target--off">
                            <p class="question js-shown">
                                Why are some stories you can read but not edit?
                            </p>
                            <p class="answer js-target target--off">
                                The editing authority of each story is edited by the founder of the story. If you really want to participate, please contact the relevant creator.
                            </p>
                            <span class="answer-button js-trigger trigger--off">
                                "&nbsp;"
                            </span>
                        </li>
                        <li class="item js-item target--off">
                            <p class="question js-shown">
                                about team  du Votre hero histoire?
                            </p>
                            <p class="answer js-target target--off">
                               Our team is composed of four young, energetic and passionate student engineers. This project originated from a school-related topic.
                            </p>
                            <span class="answer-button js-trigger trigger--off">
                                "&nbsp;"
                            </span>
                        </li>
                    </ul>
                    <ul class="container-list">
                        <li class="item js-item target--off">
                            <p class="question js-shown">
                                How can I return to the previous option when reading mode?
                            </p>
                            <p class="answer js-target target--off">
                                Under the reading mode, there are all the corresponding choices you have made in the past, you can choose to click to return to the original paragraph, and select another one
                            </p>
                            <span class="answer-button js-trigger trigger--off">
                                "&nbsp;"
                            </span>
                        </li>
                        <li class="item js-item target--off">
                            <p class="question js-shown">
                                How can I participate in editing a story?
                            </p>
                            <p class="answer js-target target--off">
                                First of all, you need to be logged in to edit and create. At this time, you click on edition, and all the stories that you have the right to participate in editing will be listed. You can click on a story to read it. When reading each paragraph, you can see whether the choice is edited or not. You can select the paragraph that has not been edited to continue your imagination. You also have the right to add a choice yourself.
                            </p>
                            <span class="answer-button js-trigger trigger--off">
                                "&nbsp;"
                            </span>
                        </li>
                        <li class="item js-item target--off">
                            <p class="question js-shown">
                                How can I delete a paragraph that I edit?
                            </p>
                            <p class="answer js-target target--off">
                               To delete a paragraph first, you need to be the author of this paragraph, and the deletion of this paragraph does not affect other paragraphs before you have the right to delete it.
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
