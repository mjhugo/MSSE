<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
	</head>
	<body>
		<div class="content scaffold-show" role="main">
			<p>Hello World</p>
			<g:formRemote name="myForm" on404="alert('not found!')" update="updateMe"
			              url="[controller: 'book', action:'show']">
			    Book Id: <input name="id" type="text" />
			</g:formRemote>
			<div id="updateMe">this div is updated with the result of the show call</div>
		</div>
	</body>
</html>
