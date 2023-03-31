package logic;

import java.sql.SQLException;
import java.util.LinkedList;

import dataBase.DataEspecialidad_ObraSocial;
import dataBase.DataObrasSociales;
import dataBase.DataPacientes;
import entities.ObraSocial;

public class ObrasSocialesController {
	
	private DataObrasSociales dos;
	
	public ObrasSocialesController() {
		dos = new DataObrasSociales();
	}
	
	public LinkedList<ObraSocial> getAll() throws SQLException {
		return dos.getAll();
	}

	public ObraSocial getByNombre(String nombre) throws SQLException {
		return dos.getByNombre(nombre);
	}

	public boolean add(ObraSocial ob) {
		return dos.add(ob);
	}

	public ObraSocial getByCodigo(int id) throws SQLException {
		return dos.getByCodigo(id);
	}

	public boolean update(ObraSocial ob) {
		return dos.update(ob);
	}

	public void delete(int cod_os) throws SQLException{
		DataEspecialidad_ObraSocial deo = new DataEspecialidad_ObraSocial();
		DataPacientes dp = new DataPacientes();
		dp.setNullOS(cod_os);
		deo.deleteFromObraSocial(cod_os);
		dos.delete(cod_os);
	}
}
