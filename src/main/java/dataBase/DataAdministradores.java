package dataBase;

import java.sql.*;
import java.util.LinkedList;
import entities.Administrador;

public class DataAdministradores {

	public Administrador getByUser(Administrador a) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Administrador adm = null;
		String consulta = "select * from administradores where username=? and password=?";
		
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setString(1, a.getUsername());
			stmt.setString(2, a.getPassword());
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs != null && rs.next()) {
				adm = new Administrador(); 
				adm.setId(rs.getInt("id"));
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
		return adm;
	}

	public LinkedList<Administrador> getAll() {
		// TODO Auto-generated method stub
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Administrador> administradores = new LinkedList<>();
		
		try {	
			// Ejecutar la query
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select * from administradores");
						
			// Mapeao de ResultSet a objeto
			if (rs!=null) {
				while(rs.next()) {
					Administrador adm = new Administrador();
					adm.setId(rs.getInt("id"));
					adm.setUsername(rs.getString("username"));
					adm.setPassword(rs.getString("password"));
					administradores.add(adm);
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
		
		return administradores;
	}

	public Administrador getByUsername(String username) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Administrador adm = null;
		String consulta = "select * from administradores where username=?";
		
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				adm = new Administrador(); 
				adm.setId(rs.getInt("id"));
				adm.setUsername(rs.getString("username"));
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
		
		return adm;
	}

	public boolean add(Administrador a) {
		// TODO Auto-generated method stub
		int res = 0;
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"insert into administradores(username, password) values(?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, a.getUsername());
			stmt.setString(2, a.getPassword());
			res = stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                a.setId(keyResultSet.getInt(1));
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
		
		return res > 0;
	}

	public Administrador getById(int id) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Administrador adm = null;
		String consulta = "select id, username, password \r\n"
				+ " from administradores where id=?";
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				adm = new Administrador(); 
				adm.setId(rs.getInt("id"));
				adm.setUsername(rs.getString("username"));
				adm.setPassword(rs.getString("password"));
			} // Fin del if
											
		} catch(SQLException  ex) {
			// Errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		} finally {
			// Cerrar recursos
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return adm;
	}

	public boolean update(Administrador a) {
		// TODO Auto-generated method stub
		int res = 0;
		PreparedStatement stmt= null;
		ResultSet rs=null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"update administradores set username=?, password=? where id=? ");
			stmt.setString(1, a.getUsername());
			stmt.setString(2, a.getPassword());
			stmt.setInt(3, a.getId());
	
			res = stmt.executeUpdate();
			
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
		
		return res > 0;
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		int res = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("delete from administradores where id=? ");
			stmt.setInt(1,id);
			res = stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res > 0;
	}

}
