package entities;

public class Paciente{
	private int id;
	private String nombre;
	private String apellido;
	private String dni;
	private String email;
	private String password;
	private String num_tel;
	private ObraSocial os;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNum_tel() {
		return num_tel;
	}
	public void setNum_tel(String tel) {
		this.num_tel = tel;
	}
	public ObraSocial getOs() {
		return os;
	}
	public void setOs(ObraSocial os) {
		this.os = os;
	}


}
