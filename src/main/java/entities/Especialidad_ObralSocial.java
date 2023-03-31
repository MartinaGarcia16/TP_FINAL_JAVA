package entities;

public class Especialidad_ObralSocial {
	private Especialidad esp;
	private ObraSocial os;
	private Float porcentaje_cobertura;
	
	public ObraSocial getOs() {
		return os;
	}
	public void setOs(ObraSocial os) {
		this.os = os;
	}
	public Especialidad getEsp() {
		return esp;
	}
	public void setEsp(Especialidad esp) {
		this.esp = esp;
	}
	public Float getPorcentaje_cobertura() {
		return porcentaje_cobertura;
	}
	public void setPorcentaje_cobertura(Float porcentaje_cobertura) {
		this.porcentaje_cobertura = porcentaje_cobertura;
	}
}
