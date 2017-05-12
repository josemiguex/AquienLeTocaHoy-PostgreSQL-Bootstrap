<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>

<%@ page
	import="es.ingenia.proyectofp.Usuario, es.ingenia.proyectofp.Admin"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Anotar usuarios</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="CSS/Pagina0.css">
<script>
	$('.datepicker').pickadate({
		selectMonths : true, // Creates a dropdown to control month
		selectYears : 15
	// Creates a dropdown of 15 years to control year
	});
</script>

<style>
body {
	background-image: url(IMG/Pagina5.jpg);
	background-attachment: fixed; /*para que sea estático*/
	background-position: top right; /*arriba a la derecha*/
	background-repeat: no-repeat; /*que no se repita el fondo*/
}

table {
	background-color: #d7ccc8;
}
a {
	text-shadow: 0px 0px #000;
}
</style>
</head>
<body>
	<%
		Usuario[] datosUsuario = (Usuario[]) request.getAttribute("datosUsuario");
		Admin datosAdmin = (Admin) session.getAttribute("datosAdmin");
		session.setAttribute("datosAdmin", datosAdmin);
	%>

	<h3 style="text-align: center;">¿A quién le toca hoy?</h3>
	<%
		Date fecha = new Date();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = new GregorianCalendar();

		String date = String.valueOf(c1.get(Calendar.DAY_OF_MONTH)) + "/"
				+ String.valueOf(c1.get(Calendar.MONTH) + 1) + "/" + String.valueOf(c1.get(Calendar.YEAR));

		String hora = "";
		if (String.valueOf(c1.get(Calendar.HOUR_OF_DAY)).length() == 2) {
			hora += String.valueOf(c1.get(Calendar.HOUR_OF_DAY));
		} else {
			hora += "0" + String.valueOf(c1.get(Calendar.HOUR_OF_DAY));
		}

		hora += ":";
		if (String.valueOf(c1.get(Calendar.MINUTE)).length() == 2) {
			hora += String.valueOf(c1.get(Calendar.MINUTE));
		} else {
			hora += "0" + String.valueOf(c1.get(Calendar.MINUTE));
		}
	%>
	<div class="container">
		<div class="row">
			<div class="col l12 m12 s12">
				<form action="MainServlet?action=annotated" method="post" name="Anotador">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Chofer</th>
								<th>Pasajero</th>
								<th>DNI</th>
								<th cosplan=\"3\">Nombre y Apellidos</th>
						</thead>
						<tbody>
						<%
							for (int i = 0; i < datosUsuario.length; i++) {

								out.print("<tr><td><input type=\"radio\" name=\"" + datosUsuario[i].getDni() + "\" id=\"chofer" + i
										+ "\" value=\"Conductor\" /><label for=\"chofer" + i
										+ "\"> </td><td><input type=\"radio\" name=\"" + datosUsuario[i].getDni() + "\" id=\"pasajero"
										+ i + "\" value=\"Pasajero\" /><label for=\"pasajero" + i + "\"></td><td>"
										+ datosUsuario[i].getDni() + "</td><td>" + datosUsuario[i].getNombre() + " "
										+ datosUsuario[i].getApellidos());

							}
						%>
						</tbody>

					</table>
					<br /> <input class="col-sm-6" type="text"
						name="Fecha" value="<%=date%>" style="background: #d7ccc8">

					<input class="col-sm-6" type="text" name="Hora"
						value="<%=hora%>" required style="background: #d7ccc8">

					</table>
				</form>
			</div>
		</div>
	</div>


	<nav class="navbar navbar-default">



	<div class="container-fluid">
		<div class="navbar-header">
			<a href="#" class="navbar-brand" onclick="document.forms['Anotador'].submit(); return false;">Anotar</a>
			
		</div>
	</div>
	</nav>

</body>
</html>