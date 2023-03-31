package dataBase;

import java.sql.*;
import java.util.LinkedList;
import entities.Especialidad;
import entities.Especialidad_ObralSocial;
import entities.ObraSocial;
import logic.ObrasSocialesController;


public class DataEspecialidad_ObraSocial {

	ObrasSocialesController osCtrl = new ObrasSocialesController();
	
	public void delete(int cod_esp) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("delete from especialidad_obrasocial where cod_especialidad=? ");
			stmt.setInt(1, cod_esp);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (rs != null){rs.close();}
				if (stmt != null){stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteFromObraSocial(int cod_os) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("delete from especialidad_obrasocial where id_os=? ");
			stmt.setInt(1, cod_os);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (rs != null){rs.close();}
				if (stmt != null){stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		
	}

	public LinkedList<Especialidad_ObralSocial> getEspecialidadesIncluidas(int cod_os) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Especialidad_ObralSocial> especialidades = new LinkedList<>();
		String consulta = "select * \n"
				+ "from especialidad_obrasocial \n"
				+ "where id_os = ? \n"
				+ "and cod_especialidad not in(select codigo_esp from especialidades e where e.estado = 0)";
		
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setInt(1, cod_os);
			rs = stmt.executeQuery();
			
			// Mapeao de ResultSet a objeto
			if (rs!=null) {
				while(rs.next()) {
					Especialidad_ObralSocial esp = new Especialidad_ObralSocial();
					ObraSocial os = osCtrl.getByCodigo(rs.getInt("id_os"));
					esp.setOs(os);
					esp.setEsp(new Especialidad());
					esp.getEsp().setCodigo_esp(rs.getInt("cod_especialidad"));
					esp.setPorcentaje_cobertura(rs.getFloat("porcentaje_cobertura"));
					especialidades.add(esp);
				} // Fin del while
			} // Fin del if
											
		} catch(SQLException  ex) {
			// Errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (stmt != null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return especialidades;
	}

	public LinkedList<Especialidad_ObralSocial> getOsCobertura(Especialidad e) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Especialidad_ObralSocial> os = new LinkedList<>();
		String consulta = "select * from especialidad_obrasocial where cod_especialidad=?";
		
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setInt(1, e.getCodigo_esp());
			rs = stmt.executeQuery();
			
			// Mapeao de ResultSet a objeto
			if (rs!=null) {
				while(rs.next()) {
					Especialidad_ObralSocial esp = new Especialidad_ObralSocial();
					esp.setEsp(e);
					esp.setOs(new ObraSocial());
					esp.setPorcentaje_cobertura(rs.getFloat("porcentaje_cobertura"));
					os.add(esp);
				} // Fin del while
			} // Fin del if
											
		} catch(SQLException ex) {
			// Errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
			
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (stmt != null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return os;
	}

	public LinkedList<Especialidad> getEspecialidadesNoIncluidad(int cod_os) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Especialidad> especialidades = new LinkedList<>();
		String consulta = "select * from especialidades where codigo_esp not in(select cod_especialidad from especialidad_obrasocial where id_os = ? )";
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setInt(1, cod_os);
			rs = stmt.executeQuery();
			
			// Mapeao de ResultSet a objeto
			if (rs!=null) {
				while(rs.next()) {
					Especialidad esp = new Especialidad();
					esp.setCodigo_esp(rs.getInt("codigo_esp"));
					esp.setNombre(rs.getString("nombre"));
					especialidades.add(esp);
				} // Fin del while
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
		return especialidades;
	}

	public void deleteFromObraSocialEspecialidad(int idEsp, int idObra) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("delete from especialidad_obrasocial where id_os=? and cod_especialidad=?");
			stmt.setInt(1, idObra);
			stmt.setInt(2, idEsp);
			stmt.executeUpdate();

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
	}

	public void add(Especialidad_ObralSocial eos) {
		// TODO Auto-generated method stub
		PreparedStatement stmt= null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"insert into especialidad_obrasocial(porcentaje_cobertura,id_os,cod_especialidad) values(?,?,?)"
							);
			stmt.setFloat(1, eos.getPorcentaje_cobertura());
			stmt.setInt(2, eos.getOs().getId_obra_social());
			stmt.setInt(3, eos.getEsp().getCodigo_esp());
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

	public Especialidad_ObralSocial getPorcentajeCobertura(int esp, int os) throws SQLException {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Especialidad_ObralSocial esp_os = new Especialidad_ObralSocial();
		String consulta = "select e_os.porcentaje_cobertura from especialidad_obrasocial e_os \r\n"
				+ "where e_os.cod_especialidad = ? and e_os.id_os = ?";
		
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			stmt.setInt(1, esp);
			stmt.setInt(2, os);
			rs = stmt.executeQuery();
			
			if(rs!= null && rs.next()) {
				esp_os.setPorcentaje_cobertura(rs.getFloat("porcentaje_cobertura"));
				esp_os.setEsp(new Especialidad());
				esp_os.getEsp().setCodigo_esp(esp);
				esp_os.setOs(new ObraSocial());
				esp_os.getOs().setId_obra_social(os);
			} else {
				esp_os.setPorcentaje_cobertura(0.0f);
				esp_os.setEsp(new Especialidad());
				esp_os.getEsp().setCodigo_esp(esp);
				esp_os.setOs(new ObraSocial());
				esp_os.getOs().setId_obra_social(os);
			}

		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
            	if(rs!=null) {rs.close();}
        		if(stmt!=null) {stmt.close();}
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
		
		return esp_os;
	}
}
