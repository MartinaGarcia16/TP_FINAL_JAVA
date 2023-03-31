package dataBase;

import java.sql.*;
import java.util.LinkedList;
import entities.ObraSocial;
import entities.Paciente;
import logic.ObrasSocialesController;

public class DataPacientes {
	ObrasSocialesController osCtrl = new ObrasSocialesController();

	public void setNullOS(int cod_os) {
		// TODO Auto-generated method stub
		PreparedStatement stmt= null;
		ResultSet rs=null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"update pacientes set id_obra_social=null where id_obra_social=? ");
			stmt.setInt(1, cod_os);
			stmt.executeUpdate();
			
		}  catch (SQLException e) {
            e.printStackTrace();
            
		} finally {
            try {
                if(rs!=null)rs.close();
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}

	public LinkedList<Paciente> getAll() {
		// TODO Auto-generated method stub
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Paciente> pacientes = new LinkedList<>();
		
		try {	
			// Ejecutar la query
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select id, dni, nombre, apellido, num_tel, email, id_obra_social from pacientes");
						
			// Mapeao de ResultSet a objeto
			if (rs!=null) {
				while(rs.next()) {
					Paciente p = new Paciente();
					p.setId(rs.getInt("id"));
					p.setNombre(rs.getString("nombre"));
					p.setApellido(rs.getString("apellido"));
					p.setDni(rs.getString("dni"));
					p.setEmail(rs.getString("email"));
					p.setNum_tel(rs.getString("num_tel"));
					ObraSocial os = new ObraSocial();
					os = osCtrl.getByCodigo(rs.getInt("id_obra_social"));
					p.setOs(os);
					pacientes.add(p);
				} // Fin del while
			} // Fin del if
		
		} catch(SQLException  ex) {
			ex.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return pacientes;	
	}
	
	public Paciente getByUser(Paciente p) throws SQLException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Paciente pac = null;
		String consulta = "select id, dni, nombre, apellido, num_tel, password, email, id_obra_social \r\n"
				+ " from pacientes p where p.email=? and p.password=?";
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setString(1, p.getEmail());
			stmt.setString(2, p.getPassword());
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				pac = new Paciente();
				pac.setId(rs.getInt("id"));
				pac.setNombre(rs.getString("nombre"));
				pac.setApellido(rs.getString("apellido"));
				pac.setDni(rs.getString("dni"));
				pac.setEmail(rs.getString("email"));
				pac.setPassword(rs.getString("password"));
				pac.setNum_tel(rs.getString("num_tel"));
				ObraSocial os = new ObraSocial();
				os = osCtrl.getByCodigo(rs.getInt("id_obra_social"));
				pac.setOs(os);
			} // Fin del if
											
		} catch(SQLException  ex) {
			// Errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return pac;
	}
	
	public boolean altaPaciente(Paciente pac) {
		int res = 0;
		PreparedStatement stmt=null;
		
		try {		
		// Ejecutar la query
		stmt = DbConnector.getInstancia().getConn().prepareStatement(
				"insert into pacientes (nombre, apellido, dni, email, password, num_tel, id_obra_social) values(?,?,?,?,?,?,?)"
				,PreparedStatement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, pac.getNombre());
		stmt.setString(2, pac.getApellido());
		stmt.setString(3, pac.getDni());
		stmt.setString(4, pac.getEmail());
		stmt.setString(5, pac.getPassword());
		stmt.setString(6, pac.getNum_tel());
		stmt.setInt(7, pac.getOs().getId_obra_social());
		
		res = stmt.executeUpdate();
		ResultSet Keyrs = stmt.getGeneratedKeys(); 
	
		if (Keyrs != null && Keyrs.next()) {
			int id = Keyrs.getInt(1);
			pac.setId(id);		
		}		
		
		} catch(SQLException  ex) {
			ex.printStackTrace();
			
		} finally {
			try {
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return res > 0;
	}
	
	public boolean update(Paciente pac) {
		// TODO Auto-generated method stub
		int res = 0;
		PreparedStatement stmt= null;
		String consulta = "update pacientes \n"
				+ "set email=?, password=?, nombre=?, apellido=?, num_tel=?, dni=?,  id_obra_social=? \n"
				+ "where id=?";
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(consulta);
			stmt.setString(1, pac.getEmail());
			stmt.setString(2, pac.getPassword());
			stmt.setString(3, pac.getNombre());
			stmt.setString(4, pac.getApellido());
			stmt.setString(5, pac.getNum_tel());
			stmt.setString(6, pac.getDni());
			stmt.setInt(7, pac.getOs().getId_obra_social());
			stmt.setInt(8, pac.getId());
	
			res = stmt.executeUpdate();
			
		}  catch (SQLException e) {
            e.printStackTrace();
            
		} finally {
            try {
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
		
		return res > 0;
	}
	
	public Paciente getByEmail(String email) {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Paciente pac = null;
		String consulta = "select id, dni, nombre, apellido, num_tel, email, id_obra_social \r\n"
				+ " from pacientes p where p.email=?";
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				pac = new Paciente();
				pac.setId(rs.getInt("id"));
				pac.setNombre(rs.getString("nombre"));
				pac.setApellido(rs.getString("apellido"));
				pac.setDni(rs.getString("dni"));
				pac.setEmail(rs.getString("email"));
				pac.setNum_tel(rs.getString("num_tel"));
				ObraSocial os = new ObraSocial();
				os = osCtrl.getByCodigo(rs.getInt("id_obra_social"));
				pac.setOs(os);
			} // Fin del if
											
		} catch(SQLException  ex) {
			// Errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return pac;
	}

	public Paciente getById(int id) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Paciente pac = null;
		String consulta = "select * from pacientes where id=?";
		try {
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);

			// Ejecutar la query
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			// Mapeo de ResultSet a objeto
			if (rs != null && rs.next()) {
				pac = new Paciente();
				pac.setId(rs.getInt("id"));
				pac.setNombre(rs.getString("nombre"));
				pac.setApellido(rs.getString("apellido"));
				pac.setDni(rs.getString("dni"));
				pac.setEmail(rs.getString("email"));
				pac.setNum_tel(rs.getString("num_tel"));
				ObraSocial os = new ObraSocial();
				os = osCtrl.getByCodigo(rs.getInt("id_obra_social"));
				pac.setOs(os);
			} // Fin del if
		} catch (SQLException ex) {
			// Errores
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			try {
				if(stmt!=null) {stmt.close();}
				if(rs!=null) {rs.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return pac;
	}
}
