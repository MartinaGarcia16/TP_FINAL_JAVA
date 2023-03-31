package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import dataBase.DataProfesionales;
import entities.Profesional;

public class ProfesionalController {
	
	private DataProfesionales dp;
	
	public ProfesionalController() {
		dp = new DataProfesionales();
	}
	
	public LinkedList<Profesional> getByEspecialidad(int cod_esp) throws SQLException, IOException {
		return dp.getByEspecialidad(cod_esp);
	}

	public LinkedList<Profesional> getAll() throws SQLException, IOException {
		return dp.getAll();
	}

	public Profesional getByEmail(String email) throws SQLException {
		return dp.getByEmail(email);
	}

	public boolean add(Profesional p2) {
		return dp.add(p2);
	}

	public Profesional getByMatricula(String mat) throws SQLException {
		return dp.getByMatricula(mat);
	}

	public boolean update(Profesional p2, String matricula) throws SQLException {
		return dp.update(p2,matricula);
	}

	public boolean delete(String mat) throws SQLException {
		return dp.delete(mat);
	}
	
	public Profesional getByUser(Profesional p) throws SQLException, IOException {
		return dp.getProfesionalByUser(p);
	}

}
