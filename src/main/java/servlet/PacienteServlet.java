package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.ObraSocial;
import entities.Paciente;
import entities.Turnos;
import logic.ObrasSocialesController;
import logic.PacientesController;
import logic.TurnosController;



@WebServlet({ "/PacienteServlet"})
public class PacienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PacienteServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Paciente usuario = (Paciente) request.getSession().getAttribute("usuario");
		if(usuario == null) {
			response.sendRedirect("./index.jsp");
			return;
		}
		
		String accion = request.getParameter("accion");
		
		if (accion.equalsIgnoreCase("MisTurnos")) {
			TurnosController tc = new TurnosController();
			Paciente pac = (Paciente) request.getSession().getAttribute("usuario");
			LinkedList<Turnos> turnos = null;
			
			try {
				turnos = tc.getTurnosPaciente(pac.getId());
				request.setAttribute("turnosPaciente", turnos);
				request.getRequestDispatcher("./misTurnos.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		
		else if(accion.equalsIgnoreCase("CancelarTurno")) {
			TurnosController tc = new TurnosController();
			int nro = Integer.parseInt(request.getParameter("nro"));
			
			try {
				tc.cancelarTurno(nro);
				request.setAttribute("estado", "Turno cancelado exitosamente.");
				request.getRequestDispatcher("PacienteServlet?accion=MisTurnos").forward(request, response);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if (accion.equalsIgnoreCase("HistoriaClinica")) {
			TurnosController tc = new TurnosController();
			Paciente pac = (Paciente) request.getSession().getAttribute("usuario");
			LinkedList<Turnos> turnos = null;
			
			try {
				turnos = tc.getHCPaciente(pac.getId());
				request.setAttribute("hcPaciente", turnos);
				request.getRequestDispatcher("./historiaClinica.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		else if (accion.equalsIgnoreCase("EditarDatos")) {
			ObrasSocialesController osc = new ObrasSocialesController();
			
			try {
				LinkedList<ObraSocial> obras_sociales = osc.getAll();
				request.setAttribute("ObrasSociales", obras_sociales);
				request.setAttribute("msg", request.getAttribute("msg"));
				request.getRequestDispatcher("./actualizarDatos.jsp").forward(request, response);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		else if(accion.equalsIgnoreCase("ActualizarDatos")) {
			Paciente pac = null;
			PacientesController pacCtrl = new PacientesController();
			ObraSocial os = new ObraSocial();
			ObrasSocialesController osCtrl = new ObrasSocialesController();
			int id = Integer.parseInt(request.getParameter("id"));
			String email = request.getParameter("email");
			
			try {
				// Validar que el usuario no exista
				pac = pacCtrl.getByEmail(email);
				if(pac != null && pac.getId() != id) {
					//ya existe
					request.setAttribute("msg", "Ya existe un paciente con este email.");
					request.getRequestDispatcher("PacienteServlet?accion=EditarDatos").forward(request, response);
				} else {
					//valido
					pac = new Paciente();
					pac.setId(id);
					pac.setNombre(request.getParameter("nombre"));
					pac.setApellido(request.getParameter("apellido"));
					pac.setDni(request.getParameter("dni"));
					pac.setNum_tel(request.getParameter("celular"));
					pac.setEmail(email);
					pac.setPassword(request.getParameter("clave"));
					os = osCtrl.getByCodigo(Integer.parseInt(request.getParameter("obra_social")));
					pac.setOs(os);
					boolean update = pacCtrl.actualizarDatosPaciente(pac);
					
					if(update) {
						request.getSession().setAttribute("usuario", pac);
						request.setAttribute("msg", "Datos actualizados correctamente.");
						request.getRequestDispatcher("menuPaciente.jsp").forward(request, response);
					} else {
						request.setAttribute("msg", "Hubo un error al actualizar los datos. Por favor intente más tarde.");
						request.getRequestDispatcher("PacienteServlet?accion=EditarDatos").forward(request, response);
					}
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
