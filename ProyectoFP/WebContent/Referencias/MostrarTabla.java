 package es.ingenia.proyectofp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class MainServlet2
 */
public class MostrarTabla extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarTabla() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
        Context ctx;
        Connection connection = null;
        Statement stmt = null;
        boolean existe = false;
        
		try {
			ctx = new InitialContext();
	        DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ProyectoFP");
	        connection = ds.getConnection();
	        //response.getWriter().append("<h1 style=\"text-align: center;\">�A QUI�N LE TOCA HOY?</h1>");
		    String query = "SELECT USUARIO.DNI, USUARIO.NOMBRE, USUARIO.APELLIDO1, USUARIO.APELLIDO2, date_format(FECHA, '%d/%m/%Y'), time_format(HORA, '%H:%i'), USUARIO.CONDUCTOR, USUARIO.PASAJERO FROM ADMINISTRADOR, USUARIO WHERE ADMINISTRADOR.IDADMINISTRADOR = USUARIO.IDADMINISTRADOR AND CODADMIN = '" + request.getParameter("CodAdmin") + "' ORDER BY (USUARIO.VA/USUARIO.CONDUCTOR) desc, USUARIO.FECHA asc, USUARIO.HORA asc";     
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        //response.getWriter().append("<table style=\"margin: 0 auto;\">");
	        response.getWriter().append("<thead><tr><th>Orden</th><th></th><th>DNI</th><th cosplan=\"3\">Nombre y Apellidos</th><th>Pasajero</th><th>Conductor</th><th>Última fecha y hora</th></thead>");
	        int i = 1;
	        while (rs.next()) {
	        	String dni = rs.getString("DNI");
	            String nombre = rs.getString("NOMBRE");
	            String apellido1 = rs.getString("APELLIDO1");
	            String apellido2 = rs.getString("APELLIDO2");
	            String pasajero = rs.getString("PASAJERO"); 
	            String fecha = rs.getString("date_format(FECHA, '%d/%m/%Y')");
	            String hora = rs.getString("time_format(HORA, '%H:%i')");
	            
	            String conductor = rs.getString("CONDUCTOR");
	            
	            int pasajeroNum = Integer.parseInt(pasajero) - 1;
	            int conductorNum = Integer.parseInt(conductor) - 1;
	            
	             pasajero = Integer.toString(pasajeroNum);  
	            
	             conductor = Integer.toString(conductorNum);  
	            response.getWriter().append("<tr><td>" + i + "</td><td></td><td>"+dni+"</td><td cosplan=\"3\">"+nombre+" "+apellido1+" "+apellido2+"</label></td><td>" + pasajero + "</td><td>" + conductor + "</td><td colspan=\"2\"> " +  fecha + " " + hora + "</td></tr>");
	            i++;	            
	        }
	       
	        
		} catch (NamingException e) {
			response.getWriter().append(e.getMessage());
			e.printStackTrace();
	    } catch (SQLException e ) {
			e.printStackTrace();
			response.getWriter().append(e.getMessage());	        
	    } finally {
	        if (stmt != null) {	        	
	        	try {
	        		
	        		connection.close();
	        	stmt.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
	        } 
	    }		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

                  