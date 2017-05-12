package es.ingenia.proyectofp;

public class Admin {
	
	String identificador;
	String clave;
	String codadmin;
	String idadministrador;
	String email;
	
	public Admin () {
		this.identificador = null;
		this.clave = null;
		this.codadmin = null;
		this.idadministrador = null;
		this.email = null;
	}
	
	public Admin(String identificador, String clave, String codadmin, String email) {
		this.identificador = identificador;
		this.clave = clave;
		this.codadmin = codadmin;
		this.email = email;
	}

	
	public Admin(String identificador, String clave, String codadmin, String idadministrador, String email) {
		this.identificador = identificador;
		this.clave = clave;
		this.codadmin = codadmin;
		this.idadministrador = idadministrador;
		this.email = email;
	}

	public String getCodadmin() {
		return codadmin;
	}

	public String getIdadministrador() {
		return idadministrador;
	}

	public String getEmail() {
		return email;
	}

	public String getIdentificador() {
		return identificador;
	}

	public String getClave() {
		return clave;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public void setCodadmin(String codadmin) {
		this.codadmin = codadmin;
	}

	public void setIdadministrador(String idadministrador) {
		this.idadministrador = idadministrador;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
