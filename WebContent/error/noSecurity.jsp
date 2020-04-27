<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>Error Page</title>
<style>
body {
	background-color: #0367a2;
}

a {
	color: #fff;
}

a:hover {
	color: #eee;
	text-shadow: 0 0 3px #fff;
}

.container {
	margin: 15% auto;
	padding: 10px;
	/* width: 700px;
	color: #fff; 
	text-align: center;*/
}

.sad-face {
	font-size: 2.5em;
	padding-right: 10px;
}

.error-code {
	font-size: 3em;
	font-style: italic;
	padding-right: 10px;
}
</style>
</head>
<body>
	<div class="container">
		<p>${msg}</p>
		<p style="margin-top: 10px; font-weight: bold;">
			<a href="javascript:history.back(-1);">返&nbsp;回&nbsp;</a>
		</p>
	</div>
</body>
</html>
