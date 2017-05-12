package es.ingenia.proyectofp;

public class Usuario {
	
	String dni;
	String nombre;
	String apellidos;
	String conductor;
	String pasajero;
	String fecha;
	String hora;
	String email;
	
	public Usuario(String d, String n, String a, String c, String p, String f, String h, String e) {
		this.dni = d;
		this.nombre = n;
		this.apellidos = a;
		this.conductor = c;
		this.pasajero = p;
		this.fecha = f;
		this.hora = h;
		this.email = e;	
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getConductor() {
		return conductor;
	}
	
	
	public void setConductor(String conductor) {
		this.conductor = conductor;
	}
	
	public String getPasajero() {
		return pasajero;
	}
	
	
	public void setPasajero(String pasajero) {
		this.pasajero = pasajero;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
	
	

}
