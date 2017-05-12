<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page
	import="es.ingenia.proyectofp.Usuario, es.ingenia.proyectofp.Admin"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administración de usuarios</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
body {
	background-image: url(IMG/Pagina3.jpg);
	background-attachment: fixed; /*para que sea estático*/
	background-position: top left; /*arriba a la derecha*/
	background-repeat: no-repeat; /*que no se repita el fondo*/
}

a {
	color: black;
}

table {
	margin: 0 auto;
	background-color: #dbdbdb;
}
</style>
</head>
<body background="IMG/Pagina3.jpg">
	<script>
	if (${noError == 'true'}) {
	  	  document.write("<div class=\"alert alert-success alert-dismissable\">");
		  document.write("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
		  document.write("<strong>Información:</strong> Contraseña cambiada correctamente");
		  document.write("</div>");
	} else if (${valorComprobacion == '0'}) {
		document.write("<div class=\"alert alert-danger alert-dismissable\">");
		  document.write("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
		  document.write("<strong>Error:</strong> No se ha podido cambiar la contraseña debido a que la contraseña actual que has introducido es incorrecta");
		  document.write("</div>");
		  
	} else if (${valorComprobacion == '1'}) {
		document.write("<div class=\"alert alert-danger alert-dismissable\">");
		  document.write("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
		  document.write("<strong>Error:</strong> No se ha podido cambiar la contraseña debido a que los campos no coinciden");
		  document.write("</div>");
	}
	${valorComprobacion = '3'}
	</script>

	<h3
		style="text-align: center; color: white; text-shadow: 0.1em 0.1em #333;">¿A
		quién le toca hoy?</h3>



	<%
		Usuario[] datosUsuario = (Usuario[]) request.getAttribute("datosUsuario");
		Admin datosAdmin = (Admin) session.getAttribute("datosAdmin");
	%>

	<div class="container">

		<table class="table table-striped">
			<form action="MainServlet?action=deleteuser" method="post"
				name="form1">
				<thead>
					<tr>
						<th></th>
						<th>DNI</th>
						<th>Nombre y Apellidos</th>
						<th>Conductor</th>
						<th>Pasajero</th>
						<th>Última Fecha y Hora</th>
				</thead>
				<%
					for (int i = 0; i < datosUsuario.length; i++) {

						if (datosUsuario[i].getFecha() == null) {
							datosUsuario[i].setFecha("-----------------------------");
							datosUsuario[i].setHora("");
						}
						out.print("<tr><td><input type=\"checkbox\" name=\"dni\" id=\"" + datosUsuario[i].getDni()
								+ "\" value=\"" + datosUsuario[i].getDni() + "\"><label for=\"" + datosUsuario[i].getDni()
								+ "\"> </td><td><a href=\"MainServlet?action=modifyuser&num=" + i + "\">"
								+ datosUsuario[i].getDni() + "</a></td><td><a href=\"MainServlet?action=modifyuser&num=" + i
								+ "\">" + datosUsuario[i].getNombre() + " " + datosUsuario[i].getApellidos() + "</a></td><td>"
								+ datosUsuario[i].getConductor() + "</td><td>" + datosUsuario[i].getPasajero() + "</td><td>"
								+ datosUsuario[i].getFecha() + " " + datosUsuario[i].getHora() + "</td></tr>");
								
					}
				%>


			</form>
		</table>
	</div>
	</div>
	</div>

	<form action="MainServlet" method="get" name="form2">
		<input type="hidden" name="action" value="adduser">
	</form>
	<nav class="navbar navbar-default">


	<div class="container-fluid">
		<div class="navbar-header">
			<a href="#" class="navbar-brand" onclick="document.forms['form2'].submit(); return false;">Añadir</a>

			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

		</div>
		<div class="collapse navbar-collapse" id="myNavbar">


			<form action="MainServlet" name="form3" method="get">
				<input type="hidden" name="action" value="annotate">
				<%
					session.setAttribute("datosAdmin", datosAdmin);
					session.setAttribute("datosUsuario", datosUsuario);
				%>

			</form>

			<input type="hidden" name="IdAdministrador"
				value="<%=datosAdmin.getIdadministrador()%>">
			<ul class="nav navbar-nav navbar-left">
				<li><a href="#"
					onclick="document.forms['form1'].submit(); return false;">Eliminar</a></li>
				<li><a href="#"
					onclick="document.forms['form3'].submit(); return false;">Anotar</a></li>
			</ul>

			<form action="MainServlet" name="form4" method="get"></form>

			</ul>
			<ul class="nav navbar-nav navbar-right" id="nav-mobile"
				class="right hide-on-med-and-down">
				<li><a href="#" data-toggle="modal"
					data-target="#changepassword">Cambiar contraseña</a>
				<li><a href="MainServlet?action=logout">Cerrar sesión</a></li>
			</ul>

		</div>
	</nav>

	<div class="modal fade" id="changepassword" role="dialog">
		<div class="modal-dialog">

			<div class="modal-content">
				<form action="MainServlet?action=newpassword" method="post"
					role="form">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">×</button>
						<h4>
							<span class="glyphicon glyphicon-lock"></span> Cambiar contraseña
						</h4>
					</div>

					<div class="modal-body">

						<input type="hidden" name="action" value="newpassword">
						<div class="form-group">
							<label for="contraseaActual">Contraseña actual</label> <input
								type="password" id="contrasenaActual" name="contrasenaActual"
								class="form-control" placeholder="Pon aquí la contraseña actual"
								required>
						</div>

						<div class="form-group">
							<label for="nuevaContrasena">Nueva contraseña</label> <input
								type="password" name="nuevaContrasena" class="form-control"
								id="nuevaContrasena" placeholder="Pon aquí la nueva contraseña"
								required>
						</div>

						<div class="form-group">
							<label for="nuevaContrasena2"></label> <input type="password"
								class="form-control" name="nuevaContrasena2"
								id="nuevaContrasena2" placeholder="Repite la nueva contraseña"
								required>
						</div>
						<input class="btn btn-primary" type="submit"
							value="Cambiar contraseña">
					</div>

				</form>
			</div>
		</div>
	</div>
	</div>

</body>
</html>