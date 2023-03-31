package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import dataBase.DataEspecialidad_ObraSocial;
import dataBase.DataEspecialidades;
import dataBase.DataProfesionales;
import dataBase.DataValor_Especialidad;
import entities.Especialidad;
import entities.Profesional;
import entities.Valor_especialidad;

public class EspecialidadesController {
	
	private DataEspecialidades de;
	
	public EspecialidadesController() {
		de = new DataEspecialidades();
	}
	
	public LinkedList<Especialidad> getAll() throws SQLException {
		return de.getAll();
	}

	public Especialidad getByNombre(String nombre) throws SQLException {
		return  de.getByNombre(nombre);
	}

	public void add(Especialidad esp,Valor_especialidad ve) {
		DataValor_Especialidad dve = new DataValor_Especialidad(); 
		esp = de.add(esp);
		ve.setEsp(esp);
		dve.insertarValor(ve);
	}

	public Especialidad getByCodigo(int id) throws SQLException {
		return de.getByCodigo(id);
	}

	public void update(Especialidad esp, Valor_especialidad ve)throws SQLException {
		DataValor_Especialidad dve = new DataValor_Especialidad(); 
		de.update(esp);
		dve.insertarValor(ve);
	}

	public int delete(int cod_esp) throws SQLException, IOException {
		DataProfesionales dp = new DataProfesionales();
		DataEspecialidad_ObraSocial deo = new DataEspecialidad_ObraSocial();
		DataValor_Especialidad dve = new DataValor_Especialidad();
		
		LinkedList<Profesional> profesionales = new LinkedList<>();
		profesionales= dp.getByEspecialidad(cod_esp);
		if (profesionales == null || profesionales.isEmpty()) {
			deo.delete(cod_esp);
			dve.delete(cod_esp);
			de.delete(cod_esp);
			return 1;
		}else {
			return 0;
		}
	}
	
	/*public LinkedList<Especialidad> getNombreEspecialidades(Paciente p) throws SQLException{
		return de.getNombres(p);
	}*/

}
