package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entities.Administrador;
import entities.Especialidad;
import entities.Profesional;
import logic.EspecialidadesController;
import logic.ProfesionalController;
import logic.TurnosController;

/**
 * Servlet implementation class ProfesionalesServlet
 */
@WebServlet("/ProfesionalesServlet")
@MultipartConfig(maxFileSize = 16177215)
public class ProfesionalesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfesionalesServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Administrador admin = (Administrador) request.getSession().getAttribute("administrador");
		if(admin == null) {
			response.sendRedirect("./index.jsp");
			return;
		}
		
		String accion = request.getParameter("accion");
		
		if (accion.equalsIgnoreCase("listar")) {
			ProfesionalController pc = new ProfesionalController();
			EspecialidadesController ec = new EspecialidadesController();
			LinkedList<Especialidad> especialidades = null;
			LinkedList<Profesional> profesionales = null;

			try {
				profesionales = pc.getAll();
				especialidades = ec.getAll();
				request.setAttribute("listaProfesionales", profesionales);
				request.setAttribute("listaEspecialidades", especialidades);
				request.setAttribute("retro", request.getAttribute("estado"));
				request.getRequestDispatcher("./profesional.jsp").forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		else if (accion.equalsIgnoreCase("agregar")) {
			EspecialidadesController ec = new EspecialidadesController();
			LinkedList<Especialidad> especialidades = null;
			
			try {
				especialidades = ec.getAll();
				request.setAttribute("estado", request.getAttribute("estado"));
				request.setAttribute("listaEspecialidades", especialidades);
				request.getRequestDispatcher("./addProfesional.jsp").forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		else if (accion.equalsIgnoreCase("Add")) {
			ProfesionalController pc = new ProfesionalController();
			String email = request.getParameter("email");
			String matricula = request.getParameter("matricula");
			LocalTime hora_inicio = LocalTime.parse(request.getParameter("hora_inicio"));
			LocalTime hora_fin = LocalTime.parse(request.getParameter("hora_fin"));
			InputStream inputStream = null;
	        Part filePart = request.getPart("image");
	        
	        if (filePart != null) {
	            inputStream = filePart.getInputStream();
	        }
			
			try {
				if (pc.getByMatricula(matricula) == null) {
					if(pc.getByEmail(email) == null) {
						if(hora_inicio.isBefore(hora_fin)) {
							Profesional p = new Profesional();
							p.setMatricula(matricula);
							p.setNombre(request.getParameter("nombre"));
							p.setApellido(request.getParameter("apellido"));
							p.setEmail(email);
							p.setPassword(request.getParameter("password"));
							p.setEstado(Integer.parseInt(request.getParameter("estado")));
							p.setHora_inicio(hora_inicio);
							p.setHora_fin(hora_fin);
							Especialidad esp = new Especialidad();
							esp.setCodigo_esp(Integer.parseInt(request.getParameter("cod_especialidad")));
							p.setEsp(esp);
							p.setFoto(inputStream);
							boolean create = pc.add(p);
							
							if(create) {
								request.setAttribute("estado", "Profesional agregado correctamente.");
								request.getRequestDispatcher("ProfesionalesServlet?accion=listar").forward(request, response);
							} else {
								request.setAttribute("estado", "Hubo un error al agregar al profesional.");
								request.getRequestDispatcher("ProfesionalesServlet?accion=listar").forward(request, response);
							}
							
						} else {
							request.setAttribute("estado", "La hora de fin no puede ser menor o igual a la hora de inicio.");
							request.getRequestDispatcher("ProfesionalesServlet?accion=agregar").forward(request, response);
						}
					} else {
						request.setAttribute("estado", "Ya existe un profesional registrado con ese email.");
						request.getRequestDispatcher("ProfesionalesServlet?accion=agregar").forward(request, response);
					}
				} else {
					request.setAttribute("estado", "Ya existe un profesional registrado con esa matricula.");
					request.getRequestDispatcher("ProfesionalesServlet?accion=agregar").forward(request, response);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		
		else if (accion.equalsIgnoreCase("Editar")) {
			Profesional p = new Profesional();
			ProfesionalController pc = new ProfesionalController();
			EspecialidadesController ec = new EspecialidadesController();
			LinkedList<Especialidad> especialidades = null;
			String mat = request.getParameter("matricula");
			
			try {
				p = pc.getByMatricula(mat);
				especialidades = ec.getAll();
				request.setAttribute("profesional", p);
				request.setAttribute("listaEspecialidades", especialidades);
				request.getRequestDispatcher("./editProfesional.jsp").forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		else if (accion.equalsIgnoreCase("Actualizar")) {
			Profesional p = null;
			ProfesionalController pc = new ProfesionalController();
			String id = request.getParameter("id");
			String email = request.getParameter("email");
			String matricula = request.getParameter("matricula");
			LocalTime hora_inicio = LocalTime.parse(request.getParameter("hora_inicio"));
			LocalTime hora_fin = LocalTime.parse(request.getParameter("hora_fin"));
			
			try {
				p = pc.getByEmail(email);
				if(p != null && !(p.getMatricula().equals(id))) {
					request.setAttribute("estado", "No se ha podido actualizar el profesional. Ya existe uno con ese email.");
					request.getRequestDispatcher("ProfesionalesServlet?accion=Listar").forward(request, response);
				} else {
					p = pc.getByMatricula(matricula);
					if(p != null && !(p.getMatricula().equals(id))) {
						request.setAttribute("estado", "No se ha podido actualizar el profesional. Ya existe uno con esa matricula.");
						request.getRequestDispatcher("ProfesionalesServlet?accion=Listar").forward(request, response);
					} else {
						if(hora_inicio.isBefore(hora_fin)) {
							p = new Profesional();
							p.setMatricula(matricula);
							p.setNombre(request.getParameter("nombre"));
							p.setApellido(request.getParameter("apellido"));
							p.setEmail(email);
							p.setPassword(request.getParameter("password"));
							p.setEstado(Integer.parseInt(request.getParameter("estado")));
							p.setHora_inicio(hora_inicio);
							p.setHora_fin(hora_fin);
							Especialidad esp = new Especialidad();
							esp.setCodigo_esp(Integer.parseInt(request.getParameter("cod_especialidad")));
							p.setEsp(esp);
							boolean update = pc.update(p, id);
							if(update) {
								request.setAttribute("estado", "Profesional editado correctamente.");
								request.getRequestDispatcher("ProfesionalesServlet?accion=listar").forward(request, response);
							} else {
								request.setAttribute("estado", "Hubo un error al actualizar el profesional.");
								request.getRequestDispatcher("ProfesionalesServlet?accion=listar").forward(request, response);
							}							
						} else {
							request.setAttribute("estado", "No se ha podido actualizar el profesional. La hora de fin no puede ser menor o igual a la hora de inicio.");
							request.getRequestDispatcher("ProfesionalesServlet?accion=listar").forward(request, response);
						}
					}	
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
		
		else if(accion.equalsIgnoreCase("Eliminar")) {
			ProfesionalController pc = new ProfesionalController();
			TurnosController tc = new TurnosController();
			String mat = request.getParameter("matricula");
			
			try {
				tc.deletePorMatricula(mat);
				boolean delete = pc.delete(mat);
				if(delete) {
					request.setAttribute("estado", "Profesional eliminado exitosamente.");
					request.getRequestDispatcher("ProfesionalesServlet?accion=Listar").forward(request, response);
				} else {
					request.setAttribute("estado", "Hubo un error al eliminar al profesional.");
					request.getRequestDispatcher("ProfesionalesServlet?accion=Listar").forward(request, response);
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
