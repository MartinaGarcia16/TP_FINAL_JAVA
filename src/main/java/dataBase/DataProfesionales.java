package dataBase;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Especialidad;
import entities.Profesional;
import logic.EspecialidadesController;

public class DataProfesionales {
	
	EspecialidadesController espCtrl = new EspecialidadesController();

	public LinkedList<Profesional> getByEspecialidad(int cod_esp) throws IOException{
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Profesional> profesionales = new LinkedList<>();
		String consulta = "select * from profesionales where cod_especialidad=? and estado=1";
		try {
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);

			// Ejecutar la query
			stmt.setInt(1, cod_esp);
			rs = stmt.executeQuery();

			// Mapeo de ResultSet a objeto
			if (rs != null) {
				while (rs.next()) {
					Profesional p = new Profesional();
					p.setMatricula(rs.getString("matricula"));
					p.setNombre(rs.getString("nombre"));
					p.setApellido(rs.getString("apellido"));
					Especialidad esp = espCtrl.getByCodigo(rs.getInt("cod_especialidad"));
					p.setEsp(esp);
					p.setEstado(rs.getInt("estado"));
					p.setHora_inicio(rs.getTime("hora_inicio").toLocalTime());
					p.setHora_fin(rs.getTime("hora_fin").toLocalTime());
					p.setFoto(rs.getBinaryStream("foto"));
					p.setBase64Image(rs.getBinaryStream("foto"));
					profesionales.add(p);
				} // Fin del while
			} // Fin del if

		} catch (SQLException ex) {
			// Errores
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (stmt != null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return profesionales;
	}

	public LinkedList<Profesional> getAll() throws IOException{
		// TODO Auto-generated method stub
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Profesional> profesionales = new LinkedList<>();
		
		try {

			// Ejecutar la query
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select * from profesionales");

			// Mapeao de ResultSet a objeto
			if (rs != null) {
				while (rs.next()) {
					Profesional p = new Profesional();
					p.setMatricula(rs.getString("matricula"));
					p.setNombre(rs.getString("nombre"));
					p.setApellido(rs.getString("apellido"));
					p.setEmail(rs.getString("email"));
					Especialidad esp = espCtrl.getByCodigo(rs.getInt("cod_especialidad"));
					p.setEsp(esp);
					p.setEstado(rs.getInt("estado"));
					p.setHora_inicio(rs.getTime("hora_inicio").toLocalTime());
					p.setHora_fin(rs.getTime("hora_fin").toLocalTime());
					p.setFoto(rs.getBinaryStream("foto"));
					p.setBase64Image(rs.getBinaryStream("foto"));
					profesionales.add(p);
				} // Fin del while
			} // Fin del if

		} catch (SQLException ex) {
			ex.printStackTrace();

		} finally {
			try {
				if (rs != null) {rs.close();}
				if (stmt != null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return profesionales;
	}

	public Profesional getByEmail(String email) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Profesional pro = null;
		String consulta = "select * from profesionales where email=?";
		try {
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);

			// Ejecutar la query
			stmt.setString(1, email);
			rs = stmt.executeQuery();

			// Mapeo de ResultSet a objeto
			if (rs != null && rs.next()) {
				pro = new Profesional();
				pro.setMatricula(rs.getString("matricula"));
				pro.setNombre(rs.getString("nombre"));
				pro.setApellido(rs.getString("apellido"));
				pro.setEmail(rs.getString("email"));
				Especialidad esp = espCtrl.getByCodigo(rs.getInt("cod_especialidad"));
				pro.setHora_inicio(rs.getTime("hora_inicio").toLocalTime());
				pro.setHora_fin(rs.getTime("hora_fin").toLocalTime());
				pro.setEsp(esp);
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
		return pro;
	}

	public boolean add(Profesional prof) {
		// TODO Auto-generated method stub
		int res = 0;
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"insert into profesionales(matricula,nombre,apellido,email,password,cod_especialidad,estado,hora_inicio,hora_fin,foto) values(?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, prof.getMatricula());
			stmt.setString(2, prof.getNombre());
			stmt.setString(3, prof.getApellido());
			stmt.setString(4, prof.getEmail());
			stmt.setString(5, prof.getPassword());
			stmt.setInt(6, prof.getEsp().getCodigo_esp());
			stmt.setInt(7, prof.getEstado());
			stmt.setTime(8, Time.valueOf(prof.getHora_inicio()));
			stmt.setTime(9, Time.valueOf(prof.getHora_fin()));
			stmt.setBlob(10, prof.getFoto());
			
			res = stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (stmt != null){stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res > 0;
	}

	public Profesional getByMatricula(String mat) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Profesional p = null;
		String consulta = "select * from profesionales where matricula=?";
		
		try {
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);

			// Ejecutar la query
			stmt.setString(1, mat);
			rs = stmt.executeQuery();

			// Mapeo de ResultSet a objeto
			if (rs != null && rs.next()) {
				p = new Profesional();
				p.setMatricula(rs.getString("matricula"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				p.setEmail(rs.getString("email"));
				Especialidad esp = espCtrl.getByCodigo(rs.getInt("cod_especialidad"));
				p.setEsp(esp);
				p.setEstado(rs.getInt("estado"));
				p.setHora_inicio(rs.getTime("hora_inicio").toLocalTime());
				p.setHora_fin(rs.getTime("hora_fin").toLocalTime());
			}

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
		return p;
	}

	public boolean update(Profesional p2, String matricula) {
		// TODO Auto-generated method stub
		int res = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"update profesionales set matricula=?,nombre=?,apellido=?,email=?,password=?,cod_especialidad=?,estado=?,hora_inicio=?,hora_fin=? where matricula=? ");
			stmt.setString(1, p2.getMatricula());
			stmt.setString(2, p2.getNombre());
			stmt.setString(3, p2.getApellido());
			stmt.setString(4, p2.getEmail());
			stmt.setString(5, p2.getPassword());
			stmt.setInt(6, p2.getEsp().getCodigo_esp());
			stmt.setInt(7, p2.getEstado());
			stmt.setTime(8, Time.valueOf(p2.getHora_inicio()));
			stmt.setTime(9, Time.valueOf(p2.getHora_fin()));
			stmt.setString(10, matricula);

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

	public boolean delete(String mat) {
		// TODO Auto-generated method stub
		int res = 0;
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn()
					.prepareStatement("delete from profesionales where matricula=? ");
			stmt.setString(1, mat);
			res = stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null){stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res > 0;
	}
	
	public Profesional getProfesionalByUser(Profesional p) throws SQLException, IOException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Profesional prof = null;
		String consulta = "select * from profesionales where email=? and password=?";
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setString(1, p.getEmail());
			stmt.setString(2, p.getPassword());
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) { 
				prof = new Profesional();
				prof.setMatricula(rs.getString("matricula"));
				prof.setNombre(rs.getString("nombre"));
				prof.setApellido(rs.getString("apellido"));
				prof.setEmail(rs.getString("email"));
				Especialidad esp = espCtrl.getByCodigo(rs.getInt("cod_especialidad"));
				prof.setEsp(esp);
				prof.setEstado(rs.getInt("estado"));
				prof.setHora_inicio(rs.getTime("hora_inicio").toLocalTime());
				prof.setHora_fin(rs.getTime("hora_fin").toLocalTime());
				prof.setFoto(rs.getBinaryStream("foto"));
				prof.setBase64Image(rs.getBinaryStream("foto"));
			} // Fin del if
											
		} catch(SQLException  ex) {
			// Errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		} finally {
			try {
				if(stmt!=null) {stmt.close();}
				if(rs!=null) {rs.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return prof;
	}
	
}
