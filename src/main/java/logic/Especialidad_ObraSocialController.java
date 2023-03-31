package logic;

import java.sql.SQLException;
import java.util.LinkedList;

import dataBase.DataEspecialidad_ObraSocial;
import entities.Especialidad;
import entities.Especialidad_ObralSocial;

public class Especialidad_ObraSocialController {
	
	private DataEspecialidad_ObraSocial deo;
	
	public Especialidad_ObraSocialController() {
		deo = new DataEspecialidad_ObraSocial();
	}
	
	public LinkedList<Especialidad_ObralSocial> getEspecialidadesIncluidas(int cod_os) throws SQLException{
		return deo.getEspecialidadesIncluidas(cod_os);
	}

	public LinkedList<Especialidad> getEspecialidadesNoIncluidas(int cod_os) throws SQLException {
		return deo.getEspecialidadesNoIncluidad(cod_os);
	}
	
	public Especialidad_ObralSocial getPorcentajeCobertura(Integer e, Integer os) throws SQLException {
		return deo.getPorcentajeCobertura(e, os);
	}
	
	public void deleteFromObraSocialEspecialidad(int idEsp, int idObra) throws SQLException {
		deo.deleteFromObraSocialEspecialidad(idEsp, idObra);
	}
	
	public void addEspecialidadObraSocial(Especialidad_ObralSocial eos) throws SQLException {
		deo.add(eos);
	}
}
