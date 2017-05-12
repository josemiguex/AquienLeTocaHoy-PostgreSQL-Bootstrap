package es.ingenia.proyectofp;

import es.ingenia.proyectofp.Admin;

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

public class Comprobaciones {

	Connection connection = null;

	public Comprobaciones() {

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

	public boolean registro(String identificador, String clave, String email) {

		boolean noesnulo = false;

		if ((!identificador.equals("") && !clave.equals("") && !email.equals(""))) {
			noesnulo = true;
		}
		return noesnulo;

	}

	public boolean login(String identificador, String clave, Admin datosAdmin) {

		boolean loginCorrecto = false;

		if ((!identificador.equals("")) && (!clave.equals("")) && (datosAdmin != null)) {
			loginCorrecto = true;
		}
		return loginCorrecto;

	}

	public boolean identificador(String identificador) {

		boolean identificadorduplicado = false;
		Statement stmt = null;
		String query = "SELECT IDENTIFICADOR FROM ADMINISTRADOR";

		try {

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String identificadorBD = rs.getString("IDENTIFICADOR");

				if (identificadorBD.equals(identificador)) {
					identificadorduplicado = true;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return identificadorduplicado;

	}

	public boolean email(String email) {

		boolean emailduplicado = false;
		Statement stmt = null;
		String query = "SELECT EMAIL FROM ADMINISTRADOR";

		try {

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String emailBD = rs.getString("EMAIL");

				if (emailBD.equals(email)) {
					emailduplicado = true;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emailduplicado;

	}

	public int nuevacontrasena(String contrasenaActual, String nuevaContrasena, String nuevaContrasena2,
			Admin datosAdmin) {
		int contrasenacambiada = 0;

		if (contrasenaActual.equals(datosAdmin.getClave())) {
			contrasenacambiada++;
			if (nuevaContrasena.equals(nuevaContrasena2)) {
				contrasenacambiada++;
			}
		}
		return contrasenacambiada;
	}

	public boolean emailUsuario(String email, String emailActual) {
		Statement stmt = null;
		boolean duplicado = false;
		try {
			if (!email.equals(emailActual)) {
				String query = "SELECT EMAIL FROM USUARIO";
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);

				while (rs.next()) {
					if (email.equals(rs.getString("EMAIL"))) {

						duplicado = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return duplicado;
	}

	public boolean dniUsuario(String dni, String dniActual) {
		Statement stmt = null;
		boolean duplicado = false;
		try {
			if (!dni.equals(dniActual)) {
				String query = "SELECT DNI FROM USUARIO";
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);

				while (rs.next()) {
					if (dni.equals(rs.getString("DNI"))) {
						duplicado = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return duplicado;
	}
}
