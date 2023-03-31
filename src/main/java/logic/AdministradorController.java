package logic;

import java.sql.SQLException;
import java.util.LinkedList;
import dataBase.DataAdministradores;
import entities.Administrador;

public class AdministradorController {
	
	private DataAdministradores da;
	
	public AdministradorController() {
		da = new DataAdministradores();
	}

	public Administrador validate(Administrador a) throws SQLException {
		return da.getByUser(a);
	}

	public LinkedList<Administrador> getAll() throws SQLException {
		return da.getAll();
	}

	public boolean add(Administrador a) throws SQLException {
		return da.add(a);
	}

	public Administrador getById(int id) throws SQLException {
		return da.getById(id);
	}

	public boolean update(Administrador a) throws SQLException {
		return da.update(a);
	}

	public boolean delete(int id) throws SQLException {
		return da.delete(id);
	}
	
	public Administrador getByUsername(String username) throws SQLException {
		return da.getByUsername(username);
	}
}
