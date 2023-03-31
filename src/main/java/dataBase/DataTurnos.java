package dataBase;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import entities.Paciente;
import entities.Profesional;
import entities.Turnos;
import logic.PacientesController;
import logic.ProfesionalController;

public class DataTurnos {
	
	ProfesionalController profCtrl = new ProfesionalController();
	PacientesController pacCtrl = new PacientesController();
	
	public void deletePorMatricula(String mat) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("delete from turnos where matricula_prof=? ");
			stmt.setString(1, mat);
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

	public LinkedList<Turnos> getAll() {
		// TODO Auto-generated method stub
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Turnos> turnos = new LinkedList<>();
		try {	
			// Ejecutar la query
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select * from turnos t order by t.fecha_turno");
						
			// Mapeao de ResultSet a objeto
			if (rs!=null) {
				while(rs.next()) {
					Turnos t = new Turnos();
					t.setNumero(rs.getInt("numero"));
					t.setFecha_turno(rs.getObject("fecha_turno", LocalDate.class));
					t.setHora_turno(rs.getObject("hora_turno", LocalTime.class));
					Profesional prof = profCtrl.getByMatricula(rs.getString("matricula_prof"));
					t.setProf(prof);
					Paciente pac = pacCtrl.getById(rs.getInt("id_paciente"));
					t.setPaciente(pac);
					turnos.add(t);
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
		
		return turnos;	
	}
	
	public LinkedList<Turnos> getHCPaciente(int id) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Turnos> turnos = new LinkedList<>();
		String consulta = "select * \n"
				+ "from turnos t \n"
				+ "where t.id_paciente = ? \n"
				+ "	and (t.fecha_turno < current_date()\n"
				+ "	or (t.hora_turno < current_time() and t.fecha_turno = current_date()))\n"
				+ "order by t.fecha_turno";
		try {
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);

			// Ejecutar la query
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			// Mapeo de ResultSet a objeto
			if (rs!=null) {
				while(rs.next()) {
					Turnos t = new Turnos();
					t.setNumero(rs.getInt("numero"));
					t.setFecha_turno(rs.getObject("fecha_turno", LocalDate.class));
					t.setHora_turno(rs.getObject("hora_turno", LocalTime.class));
					Profesional prof = profCtrl.getByMatricula(rs.getString("matricula_prof"));
					t.setProf(prof);
					Paciente pac = pacCtrl.getById(rs.getInt("id_paciente"));
					t.setPaciente(pac);
					turnos.add(t);
				} // Fin del while
			} // Fin del if

		} catch (SQLException ex) {
			// Errores
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return turnos;
	}

	public boolean asignarTurno(Turnos t) throws SQLException {
		int res = 0;
		PreparedStatement stmt=null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
				"insert into turnos (fecha_turno, hora_turno, matricula_prof, id_paciente) values(?,?,?,?)"
				,PreparedStatement.RETURN_GENERATED_KEYS);
	
			stmt.setDate(1, Date.valueOf(t.getFecha_turno()));
			stmt.setTime(2, Time.valueOf(t.getHora_turno()));
			stmt.setString(3, t.getProf().getMatricula());
			stmt.setInt(4, t.getPaciente().getId());
	
			res = stmt.executeUpdate();
			ResultSet Keyrs = stmt.getGeneratedKeys(); 
			
			if (Keyrs != null && Keyrs.next()) {
				int numero = Keyrs.getInt(1);
				t.setNumero(numero);		
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
	} //fin asignarTurno

	public LinkedList<Turnos> getTurnosPaciente(int id) throws SQLException{
		
		LinkedList<Turnos> turnosPaciente = new LinkedList<>();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select * \n"
				+ "from turnos t \n"
				+ "where t.id_paciente = ? \n"
				+ "	and t.fecha_turno > current_date()\n"
				+ "	or (t.hora_turno > current_time() and t.fecha_turno = current_date())\n"
				+ "order by t.fecha_turno";
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs!=null) {
				while(rs.next()) {
					Turnos t = new Turnos();
					t.setNumero(rs.getInt("numero"));
					t.setFecha_turno(rs.getObject("fecha_turno", LocalDate.class));
					t.setHora_turno(rs.getObject("hora_turno", LocalTime.class));
					Profesional prof = profCtrl.getByMatricula(rs.getString("matricula_prof"));
					t.setProf(prof);
					Paciente pac = pacCtrl.getById(rs.getInt("id_paciente"));
					t.setPaciente(pac);
					turnosPaciente.add(t);
				} // Fin del while
			} // Fin del if
		} catch (SQLException  ex) {
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
		return turnosPaciente;
	}

	public void cancelarTurno(int nro_turno) throws SQLException {
		PreparedStatement stmt=null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("delete from turnos where numero=? ");
			stmt.setInt(1, nro_turno);
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
	
	public LinkedList<Turnos> getTurnosProfesional(String mat) throws SQLException{
		LinkedList<Turnos> turnosProfesional = new LinkedList<>();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select * \n"
				+ "from turnos t \n"
				+ "where t.matricula_prof = ?\n"
				+ "and t.fecha_turno > current_date()\n"
				+ "or (t.hora_turno > current_time() and t.fecha_turno = current_date())\n"
				+ "order by t.fecha_turno";
		
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			stmt.setString(1, mat);
			rs = stmt.executeQuery();
			
			if (rs!=null) {
				while(rs.next()) {
					Turnos t = new Turnos();
					t.setNumero(rs.getInt("numero"));
					t.setFecha_turno(rs.getObject("fecha_turno", LocalDate.class));
					t.setHora_turno(rs.getObject("hora_turno", LocalTime.class));
					Profesional prof = profCtrl.getByMatricula(rs.getString("matricula_prof"));
					t.setProf(prof);
					Paciente pac = pacCtrl.getById(rs.getInt("id_paciente"));
					t.setPaciente(pac);
					turnosProfesional.add(t);
				} // Fin del while
			} // Fin del if
		} catch (SQLException  ex) {
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
		return turnosProfesional;
	}
	
	/*public LinkedList<Turnos> getTurnos(Profesional p) throws SQLException {
		LinkedList<Turnos> turnos = new LinkedList<>();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select t.fecha_turno, t.hora_turno, t.numero \r\n"
				+ "from turnos t \r\n"
				+ "inner join profesionales p \r\n"
				+ "	on p.matricula = t.matricula_prof \r\n"
				+ "where p.matricula = ? and t.id_paciente is null \r\n"
				+ "order by t.fecha_turno, t.hora_turno";
			
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setString(1, p.getMatricula());
		rs = stmt.executeQuery();
		
		if (rs!=null) {
			while(rs.next()) {
				Turnos t = new Turnos();
				t.setFecha_turno(rs.getObject("fecha_turno", LocalDate.class));
				t.setHora_turno(rs.getObject("hora_turno", LocalTime.class));
				t.setNumero(rs.getInt("numero"));
				turnos.add(t);
					} // Fin del while
		} // Fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return turnos;
	} // Fin getTurnos*/

	/*public LinkedList<Paciente> getTurnosPacientesProfActual(Profesional p) throws SQLException{
		LinkedList<Paciente> pacientes = new LinkedList<>();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select p.nombre, p.apellido \r\n"
				+ "from pacientes p \r\n"
				+ "inner join turnos t \r\n"
				+ "	on p.id = t.id_paciente \r\n"
				+ "where t.matricula_prof = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setString(1, p.getMatricula());
		rs = stmt.executeQuery();
		
		if (rs!=null) {
			while(rs.next()) {
				Paciente pac = new Paciente();
				pac.setNombre(rs.getString("nombre"));
				pac.setApellido(rs.getString("apellido"));
				pacientes.add(pac);
					} // Fin del while
		} // Fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return pacientes;
	}*/
	
	public Turnos getTurno(int nro_turno) throws SQLException {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Turnos t = new Turnos();
		String consulta = "select t.numero, t.fecha_turno, t.hora_turno, t.matricula_prof from turnos t \r\n"
				+ "where t.numero = ?";
		
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			stmt.setInt(1, nro_turno);
			rs = stmt.executeQuery();
			
			if(rs!= null && rs.next()) {
				t.setNumero(rs.getInt("numero"));
				t.setFecha_turno(rs.getObject("fecha_turno", LocalDate.class));
				t.setHora_turno(rs.getObject("hora_turno", LocalTime.class));
				Profesional prof = profCtrl.getByMatricula(rs.getString("matricula_prof"));
				t.setProf(prof);
				Paciente pac = pacCtrl.getById(rs.getInt("id_paciente"));
				t.setPaciente(pac);
				
			} //fin del if
		} catch (SQLException  ex) {
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
		return t;
	}
	
	public Turnos getTurnoPacienteEspecialidad(int id_paciente, int id_esp) throws SQLException{
		Turnos t = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select *\n"
				+ "from turnos t\n"
				+ "inner join profesionales p\n"
				+ "	on p.matricula = t.matricula_prof\n"
				+ "where t.id_paciente = ?\n"
				+ "	and p.cod_especialidad = ?\n"
				+ "	and t.fecha_turno > current_date()\n"
				+ "	or (t.hora_turno > current_time() and t.fecha_turno = current_date())\n"
				+ "order by t.fecha_turno";
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			stmt.setInt(1, id_paciente);
			stmt.setInt(2,  id_esp);
			rs = stmt.executeQuery();
			
			if (rs!=null) {
				while(rs.next()) {
					t = new Turnos();
					t.setNumero(rs.getInt("numero"));
					t.setFecha_turno(rs.getObject("fecha_turno", LocalDate.class));
					t.setHora_turno(rs.getObject("hora_turno", LocalTime.class));
					Profesional prof = profCtrl.getByMatricula(rs.getString("matricula_prof"));
					t.setProf(prof);
					Paciente pac = pacCtrl.getById(rs.getInt("id_paciente"));
					t.setPaciente(pac);
				} // Fin del while
			} // Fin del if
		} catch (SQLException  ex) {
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
		return t;
	}

	public Boolean checkAvailability(LocalDateTime fechaHora, String mat)throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Boolean availability = false;
		Paciente p = null;
		
		String consulta = "select id_paciente from turnos where matricula_prof=? and fecha_turno=? and hora_turno=?";
		
		try{
			// Crear la conexión
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setString(1, mat);
			stmt.setDate(2, Date.valueOf(fechaHora.toLocalDate()));
			stmt.setTime(3, Time.valueOf(fechaHora.toLocalTime()));
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				p = new Paciente();
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
		if (p == null)
			availability = true;
		
		return availability;
	}
}
