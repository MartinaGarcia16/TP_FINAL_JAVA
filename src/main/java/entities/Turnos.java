package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turnos {
	private int numero;
	private LocalDate fecha_turno;
	private LocalTime hora_turno;
	private Profesional prof;
	private Paciente pac;
	private int estado;
	
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public LocalDate getFecha_turno() {
		return fecha_turno;
	}
	public void setFecha_turno(LocalDate fecha_turno) {
		this.fecha_turno = fecha_turno;
	}
	public LocalTime getHora_turno() {
		return hora_turno;
	}
	public void setHora_turno(LocalTime hora_turno) {
		this.hora_turno = hora_turno;
	}
	public Profesional getProf() {
		return prof;
	}
	public void setProf(Profesional p) {
		this.prof = p;
	}
	public Paciente getPaciente() {
		return pac;
	}
	public void setPaciente(Paciente p) {
		this.pac = p;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}
