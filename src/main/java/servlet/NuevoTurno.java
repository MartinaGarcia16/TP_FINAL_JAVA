package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Especialidad;
import entities.Especialidad_ObralSocial;
import entities.ObraSocial;
import entities.Paciente;
import entities.Profesional;
import entities.Turnos;
import entities.Valor_especialidad;
import logic.Especialidad_ObraSocialController;
import logic.EspecialidadesController;
import logic.ProfesionalController;
import logic.TurnosController;
import logic.Valor_EspecialidadController;

/**
 * Servlet implementation class NuevoTurno
 */
@WebServlet("/NuevoTurno")
public class NuevoTurno extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NuevoTurno() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Paciente usuario = (Paciente) request.getSession().getAttribute("usuario");
		if(usuario == null) {
			response.sendRedirect("./index.jsp");
			return;
		}
		
		String accion = request.getParameter("accion");
		
		if (accion.equalsIgnoreCase("NuevoTurno")) {
			EspecialidadesController espCtrl = new EspecialidadesController();
			LinkedList<Especialidad> especialidades = new LinkedList<Especialidad>();
			try {
				especialidades = espCtrl.getAll();
				request.setAttribute("especialidades", especialidades);
				request.setAttribute("error", request.getAttribute("estado"));
				request.getRequestDispatcher("./elegirEspecialidad.jsp").forward(request, response); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		else if (accion.equalsIgnoreCase("ElegirEspecialidad")) {
			TurnosController turCtrl = new TurnosController(); 
			int cod_esp = Integer.parseInt(request.getParameter("cod_esp"));
			Paciente pac = (Paciente) request.getSession().getAttribute("usuario");
			Turnos t = null;
			
			try {
				t = turCtrl.getTurnosPacienteEspecialidad(pac.getId(), cod_esp);
				if (t == null) {
					ProfesionalController profCtrl = new ProfesionalController(); 
					LinkedList<Profesional> profesionales = profCtrl.getByEspecialidad(cod_esp);
					request.setAttribute("profesionales", profesionales);
					request.getRequestDispatcher("./elegirProfesional.jsp").forward(request, response); 
				} else {
					request.setAttribute("estado", "Ya tenés un turno reservado para esta especialidad");
					request.getRequestDispatcher("NuevoTurno?accion=NuevoTurno").forward(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		else if (accion.equalsIgnoreCase("elegirProfesional")) {
			ProfesionalController profCtrl = new ProfesionalController(); 
			TurnosController turCtrl = new TurnosController();
			String mat = request.getParameter("mat");
			
			try {
				Profesional prof = profCtrl.getByMatricula(mat);
				LocalTime hora_inicio = prof.getHora_inicio();
				LocalTime hora_fin = prof.getHora_fin();
				
				LinkedList<LocalDateTime> fechasYHorariosPosibles = new LinkedList<>();
			    LocalDateTime inicio = LocalDateTime.now().plusDays(1).withHour(hora_inicio.getHour()).withMinute(hora_inicio.getMinute()).withSecond(0);
			    LocalDateTime fin = inicio.withHour(hora_fin.getHour()).withMinute(hora_fin.getMinute()).withSecond(0);

			    for (int i = 0; i < 5; i++) {
			        while (inicio.isBefore(fin)) {
			            if (inicio.getDayOfWeek() != DayOfWeek.SATURDAY && inicio.getDayOfWeek() != DayOfWeek.SUNDAY) {
			            	boolean availability = turCtrl.checkAvailability(inicio, mat);
			            	if(availability)
			            		fechasYHorariosPosibles.add(inicio);
			            }
			            inicio = inicio.plusMinutes(30);
			        }
			        inicio = inicio.plusDays(1).withHour(hora_inicio.getHour()).withMinute(hora_inicio.getMinute());
			        fin = fin.plusDays(1).withHour(hora_fin.getHour()).withMinute(hora_fin.getMinute());
			    }
				
				request.setAttribute("profesional", prof);
				request.setAttribute("fechasYHorariosPosibles", fechasYHorariosPosibles);
				request.getRequestDispatcher("./turnosDisponibles.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		else if (accion.equalsIgnoreCase("elegirTurno")) {
			ProfesionalController profCtrl = new ProfesionalController();
			Valor_EspecialidadController veCtrl = new Valor_EspecialidadController();
			TurnosController turCtrl = new TurnosController();
			Especialidad_ObraSocialController eosCtrl = new Especialidad_ObraSocialController();
			Paciente pac = (Paciente) request.getSession().getAttribute("usuario");
			String mat = request.getParameter("mat");
			String fechaHora = request.getParameter("fechaHora");
			LocalDateTime fechaHoraElegido = LocalDateTime.parse(fechaHora);
			
			try {
				Turnos t = new Turnos();
				t.setFecha_turno(fechaHoraElegido.toLocalDate());
				t.setHora_turno(fechaHoraElegido.toLocalTime());
				t.setPaciente(pac);
				Profesional prof = profCtrl.getByMatricula(mat);
				t.setProf(prof);
				boolean create = turCtrl.asignarTurno(t);
				if(create) {
					ObraSocial os = pac.getOs();
					Valor_especialidad ve = veCtrl.getValorPorCodigo(prof.getEsp().getCodigo_esp());
					Especialidad_ObralSocial porc = eosCtrl.getPorcentajeCobertura(prof.getEsp().getCodigo_esp(), os.getId_obra_social());
					
					request.setAttribute("porc", porc.getPorcentaje_cobertura());
					request.setAttribute("ve", ve);
					request.setAttribute("os", os);
					request.setAttribute("turno", t);
					request.getRequestDispatcher("./confirmacionTurno.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("./turnoError.jsp").forward(request, response);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
