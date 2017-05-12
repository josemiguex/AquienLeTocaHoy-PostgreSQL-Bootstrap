<%@ page language="java" import="java.util.Random" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recuperación de contraseña</title>

<meta name="viewport" content="width=device-width, initial-scale=1">      
	  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css">
	  <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>           
      <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js"></script> 
      <link rel="stylesheet" type="text/css" href="CSS/Pagina6.css">
</head>
<body background="IMG/Pagina6.jpg">

	
	
<div class="contenedor">
<legend><h5>Recuperación de la contraseña</h5></legend>
<p>Si has olvidado tu contraseña, podemos enviarte un email con una nueva contraseña</p>
<form class="col s12" action="MainServlet" method="get">
<div class="row">

<div class="input-field col l4 m12 s12">
	<input type="email" name="email" placeholder="Pon aquí tu email" required>
	<label for="email">Email</label>
	
	</div>
    <input type="hidden" name="action" value="sendemail" required>
	
	<input class="button" type="submit" value="Solicitar nueva contraseña">
	</div>
	</form>
</div>


</body>
</html>