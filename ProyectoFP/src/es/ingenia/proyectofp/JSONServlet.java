package es.ingenia.proyectofp;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;

/**
 * Servlet implementation class JSON
 */
@WebServlet("/JSON")
public class JSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JSONServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String jsonString = "[";
		Usuario[] datosUsuario = (Usuario[]) request.getAttribute("datosUsuario");

		for (int y = 0; y < datosUsuario.length; y++) {

			jsonString += "{\"nombre\":" + "\"" + datosUsuario[y].getNombre() + "\"," + "\"apellidos\":" + "\""
					+ datosUsuario[y].getApellidos() + "\"," + "\"dni\":" + "\"" + datosUsuario[y].getDni() + "\","
					+ "\"pasajero\":" + "\"" + datosUsuario[y].getPasajero() + "\"," + "\"conductor\":" + "\""
					+ datosUsuario[y].getConductor() + "\"," + "\"fecha\":" + "\"" + datosUsuario[y].getFecha() + "\","
					+ "\"hora\":" + "\"" + datosUsuario[y].getHora() + "\"}";

			if (y < datosUsuario.length - 1) {
				jsonString += ",";
			}
		}
		jsonString += "]";
		response.getWriter().append(jsonString);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
