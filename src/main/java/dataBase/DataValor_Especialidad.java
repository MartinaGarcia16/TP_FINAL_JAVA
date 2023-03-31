package dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Especialidad;
import entities.Valor_especialidad;
import logic.EspecialidadesController;

public class DataValor_Especialidad {

	EspecialidadesController espCtrl = new EspecialidadesController();
	
	public void delete(int cod_esp) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("delete from valor_especialidad where cod_especialidad=? ");
			stmt.setInt(1, cod_esp);
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

	public LinkedList<Valor_especialidad> getValoresActuales() {
		// TODO Auto-generated method stub
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Valor_especialidad> valores = new LinkedList<>();
		try {
					
		// Ejecutar la query
		stmt = DbConnector.getInstancia().getConn().createStatement();
		rs = stmt.executeQuery("select valor.fecha,valor.cod_especialidad,valor_especialidad.valor from valor_especialidad\r\n"
				+ "inner join (\r\n"
				+ "select max(fecha_desde) fecha, cod_especialidad from valor_especialidad\r\n"
				+ "group by cod_especialidad) valor\r\n"
				+ "on valor.fecha=valor_especialidad.fecha_desde and valor.cod_especialidad=valor_especialidad.cod_especialidad");
					
		// Mapeao de ResultSet a objeto
		if (rs!=null) {
			while(rs.next()) {
				Valor_especialidad val = new Valor_especialidad();
				Especialidad esp = espCtrl.getByCodigo(rs.getInt("cod_especialidad"));
				val.setEsp(esp);
				val.setValor(rs.getInt("valor"));
				valores.add(val);
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
		
		return valores;
	}

	public void insertarValor(Valor_especialidad ve) {
		// TODO Auto-generated method stub
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"insert into valor_especialidad(fecha_desde,cod_especialidad,valor) values(now(),?,?)"
							);
			stmt.setInt(1, ve.getEsp().getCodigo_esp());
			stmt.setInt(2, ve.getValor());
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

	public Valor_especialidad getValorPorCodigo(int id) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Valor_especialidad val = null;
		String consulta = "select valor.fecha,valor.cod_especialidad,valor_especialidad.valor from valor_especialidad\r\n"
				+ "inner join (\r\n"
				+ "select max(fecha_desde) fecha, cod_especialidad from valor_especialidad\r\n"
				+ "group by cod_especialidad) valor\r\n"
				+ "on valor.fecha=valor_especialidad.fecha_desde and valor.cod_especialidad=valor_especialidad.cod_especialidad\r\n"
				+ "where valor.cod_especialidad=?";
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				val = new Valor_especialidad(); 
				Especialidad esp = espCtrl.getByCodigo(rs.getInt("cod_especialidad"));
				val.setEsp(esp);
				val.setValor(rs.getInt("valor"));
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
		return val;
	}

}
