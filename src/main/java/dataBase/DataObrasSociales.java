package dataBase;

import java.sql.*;
import java.util.LinkedList;
import entities.ObraSocial;

public class DataObrasSociales {

	public LinkedList<ObraSocial> getAll() {
		// TODO Auto-generated method stub
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<ObraSocial> obrasSociales= new LinkedList<>();
		try {
			
			// Ejecutar la query
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select * from obras_sociales");
						
			// Mapeao de ResultSet a objeto
			if (rs!=null) {
				while(rs.next()) {
					ObraSocial os = new ObraSocial();
					os.setId_obra_social(rs.getInt("id_obra_social"));
					os.setNombre(rs.getString("nombre"));
					obrasSociales.add(os);
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
		return obrasSociales;
	}

	public ObraSocial getByNombre(String nombre) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ObraSocial os = null;
		String consulta = "select * from obras_sociales where nombre=?";
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setString(1, nombre);
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				os = new ObraSocial(); 
				os.setId_obra_social(rs.getInt("id_obra_social"));
				os.setNombre(rs.getString("nombre"));
			} // Fin del if
			
			// Cerrar recursos
			if(stmt!=null) {stmt.close();}
			if(rs!=null) {rs.close();}
			DbConnector.getInstancia().releaseConn(); 
											
		} catch(SQLException  ex) {
			// Errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		}
		return os;
	}

	public boolean add(ObraSocial ob) {
		// TODO Auto-generated method stub
		int res = 0;
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"insert into obras_sociales(nombre) values(?)",
							PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, ob.getNombre());
			res = stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                ob.setId_obra_social(keyResultSet.getInt(1));
            }
			
		} catch (SQLException e) {
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

	public ObraSocial getByCodigo(int id) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ObraSocial ob = null;
		String consulta = "select * from obras_sociales where id_obra_social=?";
		
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				ob = new ObraSocial(); 
				ob.setId_obra_social(rs.getInt("id_obra_social"));
				ob.setNombre(rs.getString("nombre"));
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
		return ob;
	}

	public boolean update(ObraSocial ob) {
		// TODO Auto-generated method stub
		int res = 0;
		PreparedStatement stmt= null;
		ResultSet rs=null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"update obras_sociales set nombre=? where id_obra_social=? ");
			stmt.setString(1, ob.getNombre());
			stmt.setInt(2, ob.getId_obra_social());
		
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

	public void delete(int cod_os) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("delete from obras_sociales where id_obra_social=? ");
			stmt.setInt(1, cod_os);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (stmt != null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
