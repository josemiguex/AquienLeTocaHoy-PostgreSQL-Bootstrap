<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page
	import="es.ingenia.proyectofp.Usuario, es.ingenia.proyectofp.Admin"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="CSS/Pagina4.css">

<title>Añadir usuario</title>

<style>
body {
	background-image: url(IMG/Pagina4.jpg);
	background-attachment: fixed; /*para que sea estático*/
	background-position: bottom center; /*arriba a la derecha*/
	background-repeat: no-repeat; /*que no se repita el fondo*/
}
</style>

</head>
<body>

	<script>
	if (${duplicado == 'true'}) {
		  alert("El DNI o email que has introducido ya está en uso");
	}
	</script>


	<h3 style="text-align: center;">¿A quién le toca hoy?</h3>
	<%
		Admin datosAdmin = (Admin) session.getAttribute("datosAdmin");
		Usuario[] datosUsuario = (Usuario[]) request.getAttribute("datosUsuario");
		session.setAttribute("datosAdmin", datosAdmin);
		session.setAttribute("datosUsuario", datosUsuario);
	%>

	<%
		String servlet = "MainServlet?action=useradded";
		String nombre = "";
		String apellidos = "";
		String dni = "";
		String email = "";
		int num = 0;
		if (request.getParameter("num") != null) {
			num = Integer.parseInt(request.getParameter("num"));
			nombre = datosUsuario[num].getNombre();
			apellidos = datosUsuario[num].getApellidos();
			dni = datosUsuario[num].getDni();
			email = datosUsuario[num].getEmail();

			servlet = "MainServlet?action=usermodified";
		}
	%>

	<form action="<%=servlet%>" method="post">
		<div class="form-group row">
			
			<div class="col-xs-6">
        		<label for="Nombre">Nombre:</label>
       	 		<input class="form-control" name="nombre" type="text" placeholder="Nombre" value="<%=nombre%>" required>
     		</div>
			
			<div class="col-xs-6">
        		<label for="Apellidos">Apellidos:</label>
       	 		<input class="form-control" name="apellidos" type="text" placeholder="Apellidos" value="<%=apellidos%>" required>
     		</div>
     		</div>
     		<div class="form-group row">
			<div class="col-xs-6">
        		<label for="DNI">DNI:</label>
       	 		<input class="form-control" name="dni" type="text" placeholder="DNI" value="<%=dni%>" maxlength="9" required>
     		</div>

			<div class="col-xs-6">
        		<label for="email">Correo electrónico:</label>
       	 		<input class="form-control" name="email" type="text" placeholder="Correo electrónico" value="<%=email%>" data-error="wrong" data-success="right" required>
     		</div>
     		</div>
			<br> <br>
			<center>

				<%
					if (request.getParameter("num") == null) {
						out.println("<input class=\"button\" type=\"submit\" value=\"Añadir Usuario\">");
					} else {
						
						out.println("<input type=\"hidden\" name=\"datos\" value=\"datos\">");
						out.println("<input type=\"hidden\" name=\"num\" value=\"" + num + "\">");
						out.println("<input type=\"hidden\" name=\"dniActual\" value=\"" + datosUsuario[num].getDni() + "\">");
						out.println("<input type=\"hidden\" name=\"emailActual\" value=\"" + datosUsuario[num].getEmail() + "\">");
						out.println("<input class=\"button\" type=\"submit\" value=\"Modificar Usuario\">");
					}
				%>
			</center>
		</div>
	</form>


</body>
</html>