<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="es.ingenia.proyectofp.Usuario"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mostrar tabla</title>
<link rel="stylesheet" type="text/css" href="CSS/Pagina2.css">
<style>
body {
	background-image: url(IMG/Pagina2.jpg);
	background-attachment: fixed; /*para que sea estático*/
	background-position: top right; /*arriba a la derecha*/
	background-repeat: no-repeat; /*que no se repita el fondo*/
}

table {
	background-color: #d9d9d9;
}

</style>

</head>
<body>

	<%
		Usuario[] datosUsuario = (Usuario[]) request.getAttribute("datosUsuario");
	%>
	<p id="demo"></p>

	<%
		int z = 0;
		int i = 0;
		int y = 0;
	%>
	<script>
		var i, myObj, x = "";
		myObj = [
	<%for (y = 0; y < datosUsuario.length; y++) {

				out.print("{\"nombre\":");
				out.print("\"" + datosUsuario[y].getNombre() + "\",");

				out.print("\"apellidos\":");
				out.print("\"" + datosUsuario[y].getApellidos() + "\",");

				out.print("\"dni\":");
				out.print("\"" + datosUsuario[y].getDni() + "\",");

				out.print("\"pasajero\":");
				out.print("\"" + datosUsuario[y].getPasajero() + "\",");

				out.print("\"conductor\":");
				out.print("\"" + datosUsuario[y].getConductor() + "\",");

				out.print("\"fecha\":");
				out.print("\"" + datosUsuario[y].getFecha() + "\",");

				out.print("\"hora\":");
				out.print("\"" + datosUsuario[y].getHora() + "\"},");
			}%>
		]

		for (i in myObj.datos) {
			x += myObj.datos[i].dni + " " + myObj.datos[i].nombre + " "
					+ myObj.datos[i].apellidos + " " + myObj.datos[i].pasajero
					+ " " + myObj.datos[i].conductor + " "
					+ myObj.datos[i].fecha + " " + myObj.datos[i].hora
					+ " </br>";
		}

		document.getElementById("demo").innerHTML = x;
	</script>

	<h4 style="text-align: center;">¿A quién le toca hoy?</h4>

	<div class="container">
		<div class="row">
			<div class="col l12 m12 s12">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Orden</th>
							<th></th>
							<th>DNI</th>
							<th cosplan=\"3\">Nombre y Apellidos</th>
							<th>Pasajero</th>
							<th>Conductor</th>
							<th>Última fecha y hora</th>
						<tr>
					</thead>
					<%
						for (i = 0; i < datosUsuario.length; i++) {

							if (datosUsuario[i].getFecha() == null) {
								datosUsuario[i].setFecha("-----------------------------");
								datosUsuario[i].setHora("");
							}
							out.print("<tr><td>" + (i + 1) + "<td><td>" + datosUsuario[i].getDni() + "</td><td>"
									+ datosUsuario[i].getNombre() + " " + datosUsuario[i].getApellidos() + "</td><td>"
									+ datosUsuario[i].getPasajero() + "</td><td>" + datosUsuario[i].getConductor() + "</td><td>"
									+ datosUsuario[i].getFecha() + " " + datosUsuario[i].getHora() + "</td></tr>");

						}
					%>
				</table>
			</div>
		</div>
	</div>

	<button class="button" data-toggle="modal" data-target="#login">Iniciar
			Sesión</button>

		<div class="modal fade" id="login" role="dialog">
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">×</button>
						<h4>
							<span class="glyphicon glyphicon-lock"></span> Iniciar Sesión
						</h4>
					</div>

					<div class="modal-body">
						<form action="MainServlet?action=login" method="post" role="form">

							<div class="form-group row">
								<div class="col-xs-6">
									<label for="Identificador">Nombre de administrador</label> <input
										class="form-control" type="text" name="identificador"
										placeholder="Identificador" required>
								</div>

								<div class="col-xs-6">
									<label for="clave" min-length="6">Clave de
										administrador</label> <input class="form-control" type="password"
										name="clave" placeholder="Clave" required>


								</div>
							</div>
							<input class="button" type="submit" value="Iniciar sesión">
							<a href="Pagina7.jsp">¿Has olvidado tu contraseña?</a> <br /> <br />
						</form>
					</div>
				</div>
			</div>
		</div>
		<br><br>

		<button class="button" data-toggle="modal" data-target="#showtable">Mostrar
			tabla</button>

		<div class="modal fade" id="showtable" role="dialog">
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">×</button>
						<h4>
							<span class="glyphicon glyphicon-lock"></span> Mostrar tabla
						</h4>
					</div>

					<div class="modal-body">
						<form action="MainServlet?action=collectdata" method="get"
							role="form">
							<input type="hidden" name="action" value="collectdata"> <input
								type="hidden" name="format" value="html">
							<div class="form-group row">
								<div class="col-xs-12">
									<label for="Identificador">Nombre de administrador</label> <input
										class="form-control" type="text" name="identificador"
										placeholder="Identificador" required>
								</div>

							</div>
							<input class="button" type="submit" value="Mostrar tabla">

						</form>
					</div>
				</div>
			</div>
		</div>
	


</body>
</html>