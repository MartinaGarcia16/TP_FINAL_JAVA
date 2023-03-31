package dataBase;

import java.sql.*;
import java.util.LinkedList;
import entities.Especialidad;

public class DataEspecialidades {

	public LinkedList<Especialidad> getAll() throws SQLException {
		
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Especialidad> especialidades = new LinkedList<>();
		try {
					
			// Ejecutar la query
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select * from especialidades where estado = 1");
						
			// Mapeao de ResultSet a objeto
			if (rs!=null) {
				while(rs.next()) {
					Especialidad e = new Especialidad();
					e.setCodigo_esp(rs.getInt("codigo_esp"));
					e.setNombre(rs.getString("nombre"));
					especialidades.add(e);
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
		
		return especialidades;
	} // fin getAll

	public Especialidad getByNombre(String nombre)throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Especialidad esp = null;
		String consulta = "select * from especialidades where nombre=?";
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setString(1, nombre);
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				esp = new Especialidad(); 
				esp.setCodigo_esp(rs.getInt("codigo_esp"));
				esp.setNombre(rs.getString("nombre"));
						} // Fin del if
														
		} catch(SQLException  ex) {
			// Errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		} finally {
            try {
            	// Cerrar recursos
    			if(stmt!=null) {stmt.close();}
    			if(rs!=null) {rs.close();}
    			DbConnector.getInstancia().releaseConn(); 
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
		return esp;
	}

	public Especialidad add(Especialidad esp) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"insert into especialidades(nombre, estado) values(?, ?)",
							PreparedStatement.RETURN_GENERATED_KEYS
							);
			stmt.setString(1, esp.getNombre());
			stmt.setInt(2, 1);
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                esp.setCodigo_esp(keyResultSet.getInt(1));
            }

		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(keyResultSet!=null)keyResultSet.close();
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
		return esp;
	}

	public Especialidad getByCodigo(int id) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Especialidad esp = null;
		String consulta = "select * from especialidades where codigo_esp=?";
		
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				esp = new Especialidad(); 
				esp.setCodigo_esp(rs.getInt("codigo_esp"));
				esp.setNombre(rs.getString("nombre"));
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
		return esp;
	}

	public void update(Especialidad esp) {
		PreparedStatement stmt= null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"update especialidades set nombre=? where codigo_esp=? ");
			stmt.setString(1, esp.getNombre());
			stmt.setInt(2, esp.getCodigo_esp());
		
			stmt.executeUpdate();
			
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
		
	}

	public void delete(int cod_esp) {
		PreparedStatement stmt = null;
		
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("delete from especialidades where codigo_esp=? ");
			stmt.setInt(1, cod_esp);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public LinkedList<Especialidad> getNombres(Paciente p) throws SQLException{
		LinkedList<Especialidad> especialidades = new LinkedList<>();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select e.nombre\r\n"
				+ "from profesionales p \r\n"
				+ "inner join especialidades e \r\n"
				+ "	on p.cod_especialidad = e.codigo_esp \r\n"
				+ "inner join turnos t \r\n"
				+ "	on t.matricula_prof = p.matricula \r\n"
				+ "where t.id_paciente = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, p.getId());
		rs = stmt.executeQuery();
		
		if (rs!=null) {
			while(rs.next()) {
				Especialidad e = new Especialidad();
				e.setNombre(rs.getString("nombre"));
				especialidades.add(e);
					} // Fin del while
		} // Fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return especialidades;
	}*/
}
