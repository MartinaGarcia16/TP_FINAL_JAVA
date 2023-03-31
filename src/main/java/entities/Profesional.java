package entities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.Base64;

public class Profesional{
	private String matricula;
	private String nombre;
	private String apellido;
	private String email;
	private String password;
	private Integer estado;
	private LocalTime hora_inicio;
	private LocalTime hora_fin;
	private Especialidad esp;
	private InputStream foto;
	private String base64Image;
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
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
	public Especialidad getEsp() {
		return esp;
	}
	public void setEsp(Especialidad e) {
		this.esp = e;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public LocalTime getHora_inicio() {
		return hora_inicio;
	}
	
	public void setHora_inicio(LocalTime hora_inicio) {
		this.hora_inicio = hora_inicio;
	}
	
	public LocalTime getHora_fin() {
		return hora_fin;
	}
	
	public void setHora_fin(LocalTime hora_fin) {
		this.hora_fin = hora_fin;
	}
	
	public InputStream getFoto() {
		return this.foto;
	}
	
	public void setFoto(InputStream image) {
		this.foto = image;
	}
	
    public String getBase64Image() {
		return this.base64Image;
    }
    
    public void setBase64Image(InputStream image) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		
		while ((bytesRead = image.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}
		
		byte[] imageBytes = outputStream.toByteArray();
		
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		
		outputStream.close();
		this.base64Image = base64Image;
    }
}
