package es.ingenia.proyectofp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class OrganizacionBD {

	Connection connection = null;

	public OrganizacionBD() {

		Context ctx;

		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/postgres");
			connection = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	public void añadirAdmin(String identificador, String clave, String codAdmin, String email) {
		Statement stmt = null;
		String query = "insert into ADMINISTRADOR (IDENTIFICADOR, CLAVE, CODADMIN, EMAIL) values ('" + identificador
				+ "','" + clave + "','" + codAdmin + "','" + email + "')";
		try {
			stmt = connection.createStatement();
			int rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String RecogeIdadministrador(String identificador) {
		Statement stmt = null;
		String idadministrador = "";
		String query = "SELECT IDADMINISTRADOR FROM ADMINISTRADOR WHERE IDENTIFICADOR='" + identificador + "'";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {

				idadministrador = rs.getString("IDADMINISTRADOR");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idadministrador;

	}

	public String LogIn(String identificador, String clave) {
		String query;

		return query = "SELECT * FROM ADMINISTRADOR WHERE IDENTIFICADOR='" + identificador + "' AND CLAVE='" + clave
				+ "'";
	}

	public String RecogeCodAdmin(String identificador) {
		Statement stmt = null;
		String query = "SELECT CODADMIN FROM ADMINISTRADOR WHERE IDENTIFICADOR='" + identificador + "'";
		String codAdmin = "";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				System.out.print(rs.getString("CODADMIN"));
				codAdmin = rs.getString("CODADMIN");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return codAdmin;
	}

	public Usuario[] RecogeDatosTabla(String CodAdmin) {
		Statement stmt = null;
		Usuario[] datosUsuarioAUX = new Usuario[100];

		String query = "SELECT to_char(FECHA, 'dd-mm-YYYY'), to_char(HORA, 'HH12:MI'), USUARIO.EMAIL, USUARIO.DNI, USUARIO.NOMBRE, USUARIO.APELLIDOS, USUARIO.HORA, USUARIO.CONDUCTOR, USUARIO.PASAJERO FROM ADMINISTRADOR, USUARIO WHERE ADMINISTRADOR.IDADMINISTRADOR = USUARIO.IDADMINISTRADOR AND CODADMIN = '"
				+ CodAdmin + "' ORDER BY (USUARIO.VA/USUARIO.CONDUCTOR) desc, USUARIO.FECHA asc, USUARIO.HORA asc";
		int i = 0;

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {

				String dni = rs.getString("DNI");
				String nombre = rs.getString("NOMBRE");
				String apellidos = rs.getString("APELLIDOS");

				String email = rs.getString("EMAIL");
				String pasajero = rs.getString("PASAJERO");

				String fecha = rs.getString(1);

				String hora = rs.getString(2);

				String conductor = rs.getString("CONDUCTOR");

				int pasajeroNum = Integer.parseInt(pasajero) - 1;
				int conductorNum = Integer.parseInt(conductor) - 1;

				pasajero = Integer.toString(pasajeroNum);

				conductor = Integer.toString(conductorNum);

				datosUsuarioAUX[i] = new Usuario(dni, nombre, apellidos, conductor, pasajero, fecha, hora, email);
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}

		Usuario[] datosUsuario = new Usuario[i];

		for (int j = 0; j < i; j++) {
			datosUsuario[j] = new Usuario(datosUsuarioAUX[j].getDni(), datosUsuarioAUX[j].getNombre(),
					datosUsuarioAUX[j].getApellidos(), datosUsuarioAUX[j].getConductor(),
					datosUsuarioAUX[j].getPasajero(), datosUsuarioAUX[j].getFecha(), datosUsuarioAUX[j].getHora(),
					datosUsuarioAUX[j].getEmail());
		}

		return datosUsuario;
	}

	public Admin RecogeDatosAdmin(String identificador, String clave) {

		Statement stmt = null;
		Admin datosAdmin = new Admin();
		boolean hayResultado = false;

		String query = "SELECT IDENTIFICADOR, CLAVE, IDADMINISTRADOR, CODADMIN, EMAIL FROM ADMINISTRADOR WHERE IDENTIFICADOR='"
				+ identificador + "' AND  CLAVE='" + clave + "'";

		try {

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				hayResultado = true;
				datosAdmin.setIdadministrador(rs.getString("IDADMINISTRADOR"));
				datosAdmin.setCodadmin(rs.getString("CODADMIN"));
				datosAdmin.setEmail(rs.getString("EMAIL"));
				datosAdmin.setIdentificador(rs.getString("IDENTIFICADOR"));
				datosAdmin.setClave(rs.getString("CLAVE"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (hayResultado) {
			return datosAdmin;
		} else {
			return null;
		}
	}

	public void BorrarUsuario(String[] dni) {

		Statement stmt = null;
		try {
			for (int i = 0; i < dni.length; i++) {

				String query = "DELETE FROM USUARIO WHERE DNI LIKE '" + dni[i] + "'";
				stmt = connection.createStatement();
				int rs = stmt.executeUpdate(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Usuario[] ActualizarTabla(String CodAdmin) {
		Statement stmt = null;
		Usuario[] datosUsuarioAUX = new Usuario[100];

		String query = "SELECT to_char(FECHA, 'dd-mm-YYYY'), to_char(HORA, 'HH12:MI'), USUARIO.EMAIL, USUARIO.DNI, USUARIO.NOMBRE, USUARIO.APELLIDOS, USUARIO.CONDUCTOR, USUARIO.PASAJERO FROM ADMINISTRADOR, USUARIO WHERE ADMINISTRADOR.IDADMINISTRADOR = USUARIO.IDADMINISTRADOR AND CODADMIN = '"
				+ CodAdmin + "' ORDER BY (USUARIO.VA/USUARIO.CONDUCTOR) desc, USUARIO.FECHA asc, USUARIO.HORA asc";
		int i = 0;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String dni = rs.getString("DNI");
				String nombre = rs.getString("NOMBRE");
				String apellidos = rs.getString("APELLIDOS");
				String email = rs.getString("EMAIL");
				String pasajero = rs.getString("PASAJERO");
				String fecha = rs.getString(1);
				String hora = rs.getString(2);

				String conductor = rs.getString("CONDUCTOR");

				int pasajeroNum = Integer.parseInt(pasajero) - 1;
				int conductorNum = Integer.parseInt(conductor) - 1;

				pasajero = Integer.toString(pasajeroNum);

				conductor = Integer.toString(conductorNum);

				datosUsuarioAUX[i] = new Usuario(dni, nombre, apellidos, conductor, pasajero, fecha, hora, email);
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		Usuario[] datosUsuario = new Usuario[i];

		for (int j = 0; j < i; j++) {
			datosUsuario[j] = new Usuario(datosUsuarioAUX[j].getDni(), datosUsuarioAUX[j].getNombre(),
					datosUsuarioAUX[j].getApellidos(), datosUsuarioAUX[j].getConductor(),
					datosUsuarioAUX[j].getPasajero(), datosUsuarioAUX[j].getFecha(), datosUsuarioAUX[j].getHora(),
					datosUsuarioAUX[j].getEmail());
		}

		return datosUsuario;
	}

	public void EnviaEmail(String email, String cadenaAleatoria) {

		String to = email;

		String from = "info@aquienletocahoy.es";

		String host = "correo.ingenia.es";

		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("Restablecimiento de contraseña");

			message.setText("Esta es tu nueva contraseña de acceso: " + cadenaAleatoria
					+ "\n Puedes cambiarla dándole a cambiar contraseña una vez iniciada la sesión, no respondas a este correo");

			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public void CambiaContraseña(String email, String cadenaAleatoria) {
		Statement stmt = null;
		String query = "UPDATE ADMINISTRADOR SET CLAVE='" + cadenaAleatoria + "' WHERE EMAIL='" + email + "'";
		try {
			stmt = connection.createStatement();
			int rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AñadirUsuario(String nombre, String apellidos, String dni, String email, String IDADMINISTRADOR) {
		Statement stmt = null;
		String query = "INSERT INTO USUARIO (DNI, NOMBRE, APELLIDOS, EMAIL, IDADMINISTRADOR, PASAJERO, CONDUCTOR,VA) VALUES ('"
				+ dni + "','" + nombre + "','" + apellidos + "','" + email + "','" + IDADMINISTRADOR + "',1,1,1)";
		try {
			stmt = connection.createStatement();
			int rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AnotarUsuarios(Map m, Set s, Iterator it, String Hora, String Fecha) {
		Statement stmt = null;

		while (it.hasNext()) {

			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();

			String key = entry.getKey();
			String[] value = entry.getValue();

			if (!key.equals("IDADMINISTRADOR")) {
				if (!key.equals("Identificador")) {
					if (!key.equals("Fecha")) {
						if (!key.equals("Hora")) {
							String query = "UPDATE USUARIO SET " + value[0].toString() + "=" + value[0].toString()
									+ "+1,VA=VA + 1, FECHA='" + Fecha + "', HORA='" + Hora + "' WHERE DNI='" + key
									+ "'";
							try {
								stmt = connection.createStatement();
								int rs = stmt.executeUpdate(query);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	public void CambiarContraseña(String nuevaContrasena, String identificador) {
		Statement stmt = null;
		String query = "UPDATE ADMINISTRADOR SET CLAVE='" + nuevaContrasena + "' WHERE IDENTIFICADOR='" + identificador
				+ "'";
		try {
			stmt = connection.createStatement();
			int rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void ModificarUsuario(String nombre, String apellidos, String email, String dni) {
		Statement stmt = null;
		Statement stmt2 = null;
		String id = "";

		String query = "SELECT ID FROM USUARIO WHERE DNI LIKE '" + dni + "'";

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				id = rs.getString("ID");

			}
			String query2 = "UPDATE USUARIO SET NOMBRE='" + nombre + "', APELLIDOS='" + apellidos + "', DNI='" + dni
					+ "', EMAIL='" + email + "' WHERE ID LIKE '" + id + "'";

			stmt2 = connection.createStatement();
			int rs2 = stmt.executeUpdate(query2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void recordatorio(String IDADMINISTRADOR) {
		Statement stmt = null;
		try {
			String query = "SELECT EMAIL FROM USUARIO WHERE IDADMINISTRADOR='" + IDADMINISTRADOR
					+ "' ORDER BY (VA/CONDUCTOR) desc, FECHA asc, HORA asc LIMIT 2;";
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// Recipient's email ID needs to be mentioned.
				String to = rs.getString("EMAIL");

				// Sender's email ID needs to be mentioned
				String from = "info@aquienletocahoy.es";

				// Assuming you are sending email from localhost
				String host = "correo.ingenia.es";

				// Get system properties
				Properties properties = System.getProperties();

				// Setup mail server
				properties.setProperty("mail.smtp.host", host);

				// Get the default Session object.
				Session session = Session.getDefaultInstance(properties);

				// Create a default MimeMessage object.
				MimeMessage message = new MimeMessage(session);
				// Set From: header field of the header.
				message.setFrom(new InternetAddress(from));
				// Set To: header field of the header.
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				// Set Subject: header field
				message.setSubject("¿A QUIEN LE TOCA HOY?");
				// Now set the actual message
				message.setText(
						"Le recordamos que esta en los primeros puestos en la lista para llevar el coche.\nSaludos.\nPor favor no responda este mensaje.");
				// Send message
				Transport.send(message);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
