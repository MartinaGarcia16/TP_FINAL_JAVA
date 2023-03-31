package entities;

import java.sql.Date;

public class Valor_especialidad {
	Especialidad esp;
	private Date fecha_desde;
	private int valor;
	
	public Especialidad getEsp() {
		return esp;
	}
	public void setEsp(Especialidad esp) {
		this.esp = esp;
	}
	public Date getFecha_desde() {
		return fecha_desde;
	}
	public void setFecha_desde(Date fecha_desde) {
		this.fecha_desde = fecha_desde;
	}
	public Integer getValor() {
		return valor;
	}
	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
