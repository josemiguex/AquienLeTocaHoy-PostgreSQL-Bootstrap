<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.Random" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="CSS/Pagina0.css">

<title>¿A quién le toca hoy?</title>


<style>
body {
	background-image: url(IMG/Pagina0.jpg);
	background-attachment: fixed; /*para que sea estático*/
	background-position: bottom center; /*arriba a la derecha*/
	background-repeat: no-repeat; /*que no se repita el fondo*/
}
</style>
</head>
<body>

	<script>
if (${error == 'true'}) {
	document.write("<div class=\"alert alert-danger alert-dismissable\">");
	  document.write("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
	  document.write("<strong>¡Error!</strong> Usuario o contraseña incorrectos");
	document.write("</div>");
}

if (${noErrorEmail == 'true'}) {
   	  document.write("<div class=\"alert alert-success alert-dismissable\">");
	  document.write("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
	  document.write("<strong>Información:</strong> Se ha enviado un email con la nueva contraseña");
	  document.write("</div>");
	
}

if (${esnulo == 'true'}) {
		document.write("<div class=\"alert alert-danger alert-dismissable\">");
		  document.write("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
		  document.write("<strong>¡Error!</strong> No has introducido identificador");
		  document.write("</div>");
	} 
	
if (${duplicado == 'true'}) {
	  document.write("<div class=\"alert alert-danger alert-dismissable\">");
	  document.write("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
	  document.write("<strong>¡Error!</strong> El email o identificador introducido ya existe en nuestra base de datos");
	  document.write("</div>");
	
}
	</script>

	<h3 style="text-align: center;">¿A quién le toca hoy?</h3>
	<h4 style="background-color: #8d6e63;" align="center">La función
		de este programa es mediante la anotación de cuántas veces ha ido como
		conductor o pasajero un determinado grupo de personas, mostrar la
		prioridad de quién debería ir como conductor o pasajero, por ejemplo
		si alguien ha ido muchas veces como pasajero y nunca como conductor se
		pondrá primero en la tabla</h4>
	<div class="contenedor">


		<button class="button" data-toggle="modal" data-target="#signup">Registrarse</button>
		</button>
		</a> <br /> <br />
		<div class="modal fade" id="signup" role="dialog">
			<%!String getCadenaAlfanumAleatoria(int longitud) {
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while (i < longitud) {
			char c = (char) r.nextInt(255);
			if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
				cadenaAleatoria += c;
				i++;
			}
		}
		return cadenaAleatoria;
	}%>

			<%
				String codAdmin = getCadenaAlfanumAleatoria(10);
			%>
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">×</button>
						<h4>
							<span class="glyphicon glyphicon-lock"></span>Registrarse
						</h4>
					</div>

					<div class="modal-body">
						<form action="MainServlet?action=signed" method="post" role="form">

							<div class="form-group row">

								<div class="col-xs-6">
									<label for="identificador">Administrador</label><br> <input
										class="form-control" type="text" name="identificador"
										placeholder="Identificador" required>
								</div>

								<div class="col-xs-6">
									<label for="clave">Contraseña</label><br> <input
										class="form-control" type="password" name="clave"
										placeholder="Contraseña" required>

								</div>

								<div class="col-xs-12">
									<label for="email">Email</label> <input class="form-control"
										type="email" name="email" placeholder="Email" required>
								</div>

							</div>
							<input type="hidden" name="codAdmin" value="<%=codAdmin%>">
							<input class="button" type="submit" value="Registrarse"><br />

						</form>
					</div>
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

		<br /> <br />
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
						<form action="MainServlet?action=collectdata" data-toggle="validator" role="form" method="get"
							role="form">
							<input type="hidden" name="action" value="collectdata"> <input
								type="hidden" name="format" value="html">
							<div class="form-group row">
								<div class="col-xs-12">
									<label for="Identificador" class="control-label">Nombre de administrador</label> <input
										class="form-control" type="text" name="identificador"
										placeholder="Identificador" required>
										<div class="help-block with-errors"></div>
								</div>

							</div>
							<input class="button" type="submit" value="Mostrar tabla">

						</form>
					</div>
				</div>
			</div>
		</div>




	</div>
</body>
</html>