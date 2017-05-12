package es.ingenia.proyectofp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Context ctx;
		boolean registrado = false;
		boolean existe = false;
		boolean json = false;
		String nextJSP = "";
		String identificador = "";
		String clave = "";
		ResultSet rs;
		ResultSet rs2;
		HttpSession misession = null;
		Admin datosAdmin = null;
		Usuario[] datosUsuario = null;

		String accion = request.getParameter("action");
		OrganizacionBD obd = new OrganizacionBD();
		switch (accion) {
		case "main":

			if (request.getParameter("errorLogin") != null && request.getParameter("errorLogin").equals("true")) {
				request.setAttribute("error", true);
			}
			nextJSP = "/Pagina0.jsp";
			break;
		case "signup":
			if (request.getParameter("duplicado") != null && request.getParameter("duplicado").equals("true")) {
				request.setAttribute("duplicado", true);
			}
			nextJSP = "/Pagina0.jsp";

			break;
		case "sendemail":
			String cadenaAleatoria = getCadenaAlfanumAleatoria(8);

			obd.EnviaEmail(request.getParameter("email"), cadenaAleatoria);

			obd.CambiaContraseña(request.getParameter("email"), cadenaAleatoria);

			request.setAttribute("noErrorEmail", true);
			nextJSP = "/Pagina0.jsp";
			break;
		case "adduser":
			misession = request.getSession(false);
			if (misession != null && misession.getAttribute("datosAdmin") != null) {
				nextJSP = "/Pagina3.jsp";
			} else {
				nextJSP = "/Pagina0.jsp";
			}
			break;
		case "logout":
			misession = request.getSession(false);
			misession.setAttribute("datosAdmin", null);
			misession.invalidate();

			nextJSP = "/Pagina0.jsp";
			break;
		case "collectdata":
			if (!request.getParameter("identificador").equals("")) {
				String codadmin = obd.RecogeCodAdmin(request.getParameter("identificador"));
				datosUsuario = obd.RecogeDatosTabla(codadmin);
				request.setAttribute("datosUsuario", datosUsuario);

				if (request.getParameter("format").equals("html")) {

					nextJSP = "/Pagina1.jsp";
				} else if (request.getParameter("format").equals("json")) {

					nextJSP = "/JSONServlet";
				}
			} else {
				nextJSP = "/Pagina0.jsp";
				request.setAttribute("esnulo", true);
			}
			break;
		case "annotate":
			misession = request.getSession(false);
			if (misession != null && misession.getAttribute("datosAdmin") != null) {
				datosAdmin = (Admin) misession.getAttribute("datosAdmin");
				datosUsuario = obd.ActualizarTabla(datosAdmin.getCodadmin());
				request.setAttribute("datosUsuario", datosUsuario);
				misession.setAttribute("datosAdmin", datosAdmin);

				nextJSP = "/Pagina4.jsp";
			} else {
				nextJSP = "/Pagina0.jsp";
			}
			break;
		case "changepassword":
			misession = request.getSession(false);
			if (misession != null && misession.getAttribute("datosAdmin") != null) {
				datosAdmin = (Admin) misession.getAttribute("datosAdmin");
				misession.setAttribute("datosAdmin", datosAdmin);
				nextJSP = "/Pagina6.jsp";
			} else {
				nextJSP = "/Pagina0.jsp";
			}
			break;
		case "modifyuser":
			if (request.getParameter("error") != null && request.getParameter("error").equals("true")) {
				request.setAttribute("duplicado", true);
			}
			String num = request.getParameter("num");
			misession = request.getSession(false);
			if (misession != null && misession.getAttribute("datosAdmin") != null) {
				datosAdmin = (Admin) misession.getAttribute("datosAdmin");
				datosUsuario = obd.ActualizarTabla(datosAdmin.getCodadmin());
				request.setAttribute("datosUsuario", datosUsuario);
				misession.setAttribute("datosAdmin", datosAdmin);
				nextJSP = "/Pagina3.jsp?num=" + num;
			} else {
				nextJSP = "/Pagina0.jsp";
			}
			break;
		case "list":
			if (request.getParameter("noError") != null && request.getParameter("noError").equals("true")) {
				request.setAttribute("noError", true);
			}
			misession = request.getSession(false);

			datosAdmin = (Admin) misession.getAttribute("datosAdmin");
			datosUsuario = obd.RecogeDatosTabla(datosAdmin.getCodadmin());
			request.setAttribute("datosUsuario", datosUsuario);
			misession.setAttribute("datosAdmin", datosAdmin);
			nextJSP = "/Pagina2.jsp";

			break;
		}

		if (!nextJSP.equals("")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean registrado = false;
		boolean existe = false;
		String nextJSP = "";
		String identificador = "";
		String clave = "";
		HttpSession misession = null;
		Admin datosAdmin = null;
		Usuario[] datosUsuario = null;
		String accion = request.getParameter("action");

		OrganizacionBD obd = new OrganizacionBD();
		Comprobaciones comprobaciones = new Comprobaciones();

		switch (accion) {
		case "signed":
			identificador = request.getParameter("identificador");

			clave = request.getParameter("clave");

			String email = request.getParameter("email");
			String codAdmin = request.getParameter("codAdmin");

			if (comprobaciones.registro(identificador, clave, email) && !comprobaciones.identificador(identificador)
					&& !comprobaciones.email(email)) {
				misession = request.getSession(true);
				datosAdmin = new Admin(identificador, clave, codAdmin, email);
				obd.añadirAdmin(datosAdmin.getIdentificador(), datosAdmin.getClave(), datosAdmin.getCodadmin(),
						datosAdmin.getEmail());
				datosAdmin.setIdadministrador(obd.RecogeIdadministrador(identificador));
				misession.setAttribute("datosAdmin", datosAdmin);
				response.sendRedirect(request.getContextPath() + "/MainServlet?action=list");
			} else {
				response.sendRedirect(request.getContextPath() + "/MainServlet?action=signup&duplicado=true");
			}

			break;
		case "login":

			identificador = request.getParameter("identificador");
			clave = request.getParameter("clave");
			datosAdmin = obd.RecogeDatosAdmin(identificador, clave);

			if (comprobaciones.login(identificador, clave, datosAdmin)) {
				misession = request.getSession(true);

				misession.setAttribute("datosAdmin", datosAdmin);
				response.sendRedirect(request.getContextPath() + "/MainServlet?action=list");

			} else {

				response.sendRedirect(request.getContextPath() + "/MainServlet?action=main&errorLogin=true");

			}

			break;
		case "deleteuser":

			misession = request.getSession(false);
			if (misession != null && misession.getAttribute("datosAdmin") != null) {
				if (request.getParameterValues("dni") != null) {
					String[] dni = request.getParameterValues("dni");

					obd.BorrarUsuario(dni);
					datosAdmin = (Admin) misession.getAttribute("datosAdmin");
					datosUsuario = obd.ActualizarTabla(datosAdmin.getCodadmin());
					request.setAttribute("datosUsuario", datosUsuario);
					misession.setAttribute("datosAdmin", datosAdmin);
					nextJSP = "/Pagina2.jsp";
				} else {
					response.sendRedirect(request.getContextPath() + "/MainServlet?action=list");
				}
			} else {
				nextJSP = "/Pagina0.jsp";
			}
			break;
		case "useradded":
			misession = request.getSession(false);
			datosAdmin = (Admin) misession.getAttribute("datosAdmin");
			datosUsuario = obd.ActualizarTabla(datosAdmin.getCodadmin());
			obd.AñadirUsuario(request.getParameter("nombre"), request.getParameter("apellidos"),
					request.getParameter("dni"), request.getParameter("email"), datosAdmin.getIdadministrador());

			datosUsuario = obd.ActualizarTabla(datosAdmin.getCodadmin());
			request.setAttribute("datosUsuario", datosUsuario);
			misession.setAttribute("datosAdmin", datosAdmin);
			nextJSP = "/Pagina2.jsp";
			break;

		case "annotated":
			misession = request.getSession(false);

			Map m = request.getParameterMap();
			Set s = m.entrySet();
			Iterator it = s.iterator();
			obd.AnotarUsuarios(m, s, it, request.getParameter("Hora"), request.getParameter("Fecha"));
			datosAdmin = (Admin) misession.getAttribute("datosAdmin");
			obd.recordatorio(datosAdmin.getIdadministrador());
			misession.setAttribute("datosAdmin", datosAdmin);
			response.sendRedirect(request.getContextPath() + "/MainServlet?action=list");

			break;
		case "newpassword":
			misession = request.getSession(false);
			datosAdmin = (Admin) misession.getAttribute("datosAdmin");
			misession.setAttribute("datosAdmin", datosAdmin);

			int valorComprobacion = comprobaciones.nuevacontrasena(request.getParameter("contrasenaActual"),
					request.getParameter("nuevaContrasena"), request.getParameter("nuevaContrasena2"), datosAdmin);
			if (valorComprobacion == 2) {
				obd.CambiarContraseña(request.getParameter("nuevaContrasena"), datosAdmin.getIdentificador());
				datosAdmin.setClave(request.getParameter("nuevaContrasena"));
				misession.setAttribute("datosAdmin", datosAdmin);
				
				response.sendRedirect(request.getContextPath() + "/MainServlet?action=list&noError=true");
			} else {
				misession = request.getSession(false);
				datosAdmin = (Admin) misession.getAttribute("datosAdmin");
				misession.setAttribute("datosAdmin", datosAdmin);
				misession.setAttribute("valorComprobacion", valorComprobacion);
				response.sendRedirect(request.getContextPath() + "/MainServlet?action=list");
			}
			break;
		case "usermodified":
			if (!comprobaciones.emailUsuario(request.getParameter("email"), request.getParameter("emailActual"))
					&& !comprobaciones.dniUsuario(request.getParameter("dni"), request.getParameter("dniActual"))) {
				obd.ModificarUsuario(request.getParameter("nombre"), request.getParameter("apellidos"),
						request.getParameter("email"), request.getParameter("dni"));
				response.sendRedirect(request.getContextPath() + "/MainServlet?action=list");
			} else {
				response.sendRedirect(request.getContextPath() + "/MainServlet?action=modifyuser&error=true&num="
						+ request.getParameter("num"));
			}

			break;

		}
		if (!nextJSP.equals("")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request, response);
		}
	}

	public String getCadenaAlfanumAleatoria(int longitud) {
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
	}
}
