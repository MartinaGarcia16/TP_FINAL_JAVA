package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Administrador;
import entities.Especialidad;
import entities.Valor_especialidad;
import logic.EspecialidadesController;
import logic.Valor_EspecialidadController;

/**
 * Servlet implementation class EspecialidadesServlet
 */
@WebServlet("/EspecialidadesServlet")
public class EspecialidadesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EspecialidadesServlet() {
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
			EspecialidadesController ec = new EspecialidadesController();
			Valor_EspecialidadController vec = new Valor_EspecialidadController();
			LinkedList<Especialidad> especialidades = null;
			LinkedList<Valor_especialidad> valores = null;
			
			try {
				especialidades = ec.getAll();
				valores = vec.getValores();
				request.setAttribute("listaEspecialidades", especialidades);
				request.setAttribute("listaValores", valores);
				request.setAttribute("retro", request.getAttribute("estado"));
				request.getRequestDispatcher("./especialidad.jsp").forward(request, response);
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		} 
		
		else if (accion.equalsIgnoreCase("Agregar")) {
			EspecialidadesController ec = new EspecialidadesController();
			String nombre = request.getParameter("nombre");
			
			try {
				if (ec.getByNombre(nombre) == null) {
					Valor_especialidad ve = new Valor_especialidad();
					Especialidad esp = new Especialidad();
					ve.setValor(Integer.parseInt(request.getParameter("valor")));
					esp.setNombre(nombre);
					ec.add(esp, ve);
					request.setAttribute("estado", "Especialidad agregada correctamente.");
					request.getRequestDispatcher("EspecialidadesServlet?accion=listar").forward(request, response);
				} else {
					request.setAttribute("estado", "Especialidad ingresada ya existe.");
					request.getRequestDispatcher("./addEspecialidad.jsp").forward(request, response);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if (accion.equalsIgnoreCase("Editar")) {
			Especialidad esp = new Especialidad();
			Valor_especialidad ve = new Valor_especialidad();
			EspecialidadesController ec = new EspecialidadesController();
			Valor_EspecialidadController vec = new Valor_EspecialidadController();
			int id = Integer.parseInt(request.getParameter("id"));
			
			try {
				esp = ec.getByCodigo(id);
				ve = vec.getValorPorCodigo(id);
				request.setAttribute("especialidad", esp);
				request.setAttribute("valor", ve);
				request.getRequestDispatcher("./editEspecialidad.jsp").forward(request, response);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}			
		}
		
		else if (accion.equalsIgnoreCase("Actualizar")) {
			Especialidad esp = null;
			EspecialidadesController ec = new EspecialidadesController();
			int id = Integer.parseInt(request.getParameter("id"));
			String nombre = request.getParameter("nombre");
			
			try {
				esp = ec.getByNombre(nombre);
				if(esp != null && esp.getCodigo_esp() != id) {
					request.setAttribute("estado", "No se ha podido actualizar la especialidad. Ya existe una con ese nombre");
					request.getRequestDispatcher("EspecialidadesServlet?accion=Listar").forward(request, response);
				} else {
					Valor_especialidad va= new Valor_especialidad();
					esp = new Especialidad();
					esp.setCodigo_esp(id);
					esp.setNombre(nombre);
					va.setEsp(esp);
					va.setValor(Integer.parseInt(request.getParameter("valor")));
					ec.update(esp, va);
					request.setAttribute("estado", "Especialidad actualizada correctamente.");
					request.getRequestDispatcher("EspecialidadesServlet?accion=listar").forward(request, response);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(accion.equalsIgnoreCase("Eliminar")) {
			Especialidad esp = new Especialidad();
			EspecialidadesController ec = new EspecialidadesController();
			int codigo = Integer.parseInt(request.getParameter("id"));
			esp.setCodigo_esp(codigo);
			
			try {
				int numero;
				numero = ec.delete(codigo);
				if (numero == 1) {
				request.setAttribute("estado", "Especialidad eliminada exitosamente.");
				request.getRequestDispatcher("EspecialidadesServlet?accion=Listar").forward(request, response);
				} else {
					request.setAttribute("estado", "No se pudo eliminar porque hay profesionales con esa especialidad.");
					request.getRequestDispatcher("EspecialidadesServlet?accion=Listar").forward(request, response);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}
