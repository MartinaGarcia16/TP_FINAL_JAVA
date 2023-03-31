package logic;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;

import dataBase.DataTurnos;
import entities.Turnos;

public class TurnosController {
	
	private DataTurnos dt;
	
	public TurnosController() {
		dt = new DataTurnos();
	}
	
	public void deletePorMatricula(String mat) {
		dt.deletePorMatricula(mat);
	}

	public LinkedList<Turnos> getAll() throws SQLException{
		return dt.getAll();
	}
	
	public LinkedList<Turnos> getHCPaciente(int id) throws SQLException{
		return  dt.getHCPaciente(id);
	}

	public boolean asignarTurno(Turnos t) throws SQLException {
		return dt.asignarTurno(t);
	}
	
	public LinkedList<Turnos> getTurnosPaciente(int id) throws SQLException{
		return dt.getTurnosPaciente(id);
	}

	public void cancelarTurno(int nro_turno) throws SQLException {
		dt.cancelarTurno(nro_turno);
	}
	
	public LinkedList<Turnos> getTurnosProfesional(String mat) throws SQLException{
		return dt.getTurnosProfesional(mat);
	}

	/*public LinkedList<Turnos> getTurnos(Profesional p) throws SQLException {
		return dt.getTurnos(p);
	}*/
	
	/*public LinkedList<Paciente> getTurnosPacientesProfActual(Profesional p) throws SQLException{
		return dt.getTurnosPacientesProfActual(p);
	}*/
	
	public Turnos getTurno(int nro_turno) throws SQLException {
		return dt.getTurno(nro_turno);
	}
	
	public Turnos getTurnosPacienteEspecialidad(int id_pac, int id_esp) throws SQLException{
		return dt.getTurnoPacienteEspecialidad(id_pac, id_esp);
	}
	
	public Boolean checkAvailability(LocalDateTime fechaHora, String mat) throws SQLException {
		return dt.checkAvailability(fechaHora, mat);
	}
}
