<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>votre hero histoire</title>
	<link rel="stylesheet" type="text/css" href="css/simplegrid.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Amatic+SC" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Fredoka+One" rel="stylesheet">
	<link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css" />
</head>
<body >
	<jsp:include page="header.jsp" />
			<section>
				<div class="container-read col-1-1">
					<div class="col-1-2">
						<h2 id="partie-1">Time to read some stories</h2>
						<p>Hello friends from all over the world. Here is a warehouse where all kinds of strange imaginations are gathered. Every story is composed by a different person, and there are countless interesting things here! 
						</p>

						<p>
							A story with multiple trends, here will produce a different plot based on your unique choice, I believe this will definitely bring you a new experience.Come and enjoy those wonderful stories.
						</p>
						<a class="button4" href="afficherHistoires">Explore</a>
					</div>
					<div class="col-1-2">
						<p><img src="images/people.jpg" alt="help article" class="test_a"></p>
					</div>
				</div>
			</section>
			<section >
				<div class="col-1-1">
					<h2 id="partie-2">Use your imagination</h2>
				</div>
				<div class="col-1-1">
					<article class="at">
						<header>
							<a href="creation" class="test_a"><img class = "image-1-1 cut-height" src="images/partie-4.jpg"></a>
						</header>
						<section>
							<h5>Here you can create your own story, or you can invite your friends to create together. A group of people work together to complete a multi-branch story. You can freely edit the options of any paragraph of a story and create it with everyone.</h5>
						</section>
					</article>
				</div>
			</section>

			<section >
				<div class="col-1-1">
					<h2 id="partie-4">Autres</h2>
				</div>
				<div class="col-1-2" id="lastar">
					<article>
						<header>
							<h4>
								Didacticiel
							</h4>
						</header>
						<section>
							<h5>Vous avez des questions ?</h5>
							<p>Vous ne savez pas par où commencer votre histoire? Vous avez des questions pendant le processus de création? Nos didacticiels vous guident pas à pas.
							</p>
						</section>
						<footer>
							<a href="support" class="button2" title="jump to">read more@news</a>
						</footer>
					</article>
				</div>
				<div class="col-1-2">
					<p><img src="images/help.png" alt="help article" class="test_a"></p>
				</div>
			</section>

	<jsp:include page="footer.jsp" />

	<script src="https://unpkg.com/aos@next/dist/aos.js"></script>
	<script>
	  AOS.init();
	</script>
</body>
</html>
