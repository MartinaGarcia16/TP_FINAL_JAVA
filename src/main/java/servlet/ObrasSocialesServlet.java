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
import entities.Especialidad_ObralSocial;
import entities.ObraSocial;
import logic.Especialidad_ObraSocialController;
import logic.EspecialidadesController;
import logic.ObrasSocialesController;

/**
 * Servlet implementation class ObrasSocialesServlet
 */
@WebServlet("/ObrasSocialesServlet")
public class ObrasSocialesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObrasSocialesServlet() {
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
		
		Administrador admin = (Administrador) request.getSession().getAttribute("administrador");
		if(admin == null) {
			response.sendRedirect("./index.jsp");
			return;
		}
		
		String accion = request.getParameter("accion");
		
		if (accion.equalsIgnoreCase("listar")) {
			ObrasSocialesController obc= new ObrasSocialesController();
			LinkedList<ObraSocial> obrasSociales=null;
			
			try {
				obrasSociales = obc.getAll();
				request.setAttribute("listaObrasSociales", obrasSociales);
				request.setAttribute("retro", request.getAttribute("estado"));
				request.getRequestDispatcher("./obraSocial.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		else if (accion.equalsIgnoreCase("agregar")) {
			ObraSocial ob = new ObraSocial();
			ObrasSocialesController obc = new ObrasSocialesController();
			String nombre = request.getParameter("nombre");
			
			try {
				if (obc.getByNombre(nombre) == null) {
					ob.setNombre(nombre);
					boolean create = obc.add(ob);
					if(create) {
						request.setAttribute("estado", "Obra Social agregada correctamente.");
						request.getRequestDispatcher("ObrasSocialesServlet?accion=listar").forward(request, response);
					} else {
						request.setAttribute("estado", "Hubo un error al agregar la obra social. Por favor intente más tarde.");
						request.getRequestDispatcher("ObrasSocialesServlet?accion=listar").forward(request, response);
					}				
				} else {
					request.setAttribute("estado", "La obra social ingresada ya existe.");
					request.getRequestDispatcher("./addObraSocial.jsp").forward(request, response);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if (accion.equalsIgnoreCase("Editar")) {
			ObraSocial os =new ObraSocial();
			ObrasSocialesController obc =new ObrasSocialesController();
			int id = Integer.parseInt(request.getParameter("id"));
			
			try {
				os = obc.getByCodigo(id);
				request.setAttribute("obraSocial", os);
				request.getRequestDispatcher("./editObraSocial.jsp").forward(request, response);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}	
		}
		
		else if (accion.equalsIgnoreCase("Actualizar")) {
			ObraSocial os = null;
			ObrasSocialesController obc = new ObrasSocialesController();
			int id = Integer.parseInt(request.getParameter("id"));
			String nombre = request.getParameter("nombre");
			
			try {
				os = obc.getByNombre(nombre);
				if (os != null && os.getId_obra_social() != id) {
					request.setAttribute("estado", "No se ha podido actualizar la obra social. Ya existe una con ese nombre");
					request.getRequestDispatcher("ObrasSocialesServlet?accion=Listar").forward(request, response);
				} else {
					os = new ObraSocial();
					os.setId_obra_social(id);
					os.setNombre(nombre);
					boolean update = obc.update(os);
					if(update) {
						request.setAttribute("estado", "Obra social actualizada correctamente.");
						request.getRequestDispatcher("ObrasSocialesServlet?accion=listar").forward(request, response);
					} else {
						request.setAttribute("estado", "No se ha podido actualizar la obra social. Por favor intente de nuevo más tarde.");
						request.getRequestDispatcher("ObrasSocialesServlet?accion=listar").forward(request, response);
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(accion.equalsIgnoreCase("Eliminar")) {
			ObrasSocialesController obc = new ObrasSocialesController();
			int codigo = Integer.parseInt(request.getParameter("id"));
			
			try {
				obc.delete(codigo);
				request.setAttribute("estado", "Obra social eliminada exitosamente.");
				request.getRequestDispatcher("ObrasSocialesServlet?accion=Listar").forward(request, response);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		
		else if(accion.equalsIgnoreCase("ListarEspecialidades")) {
			ObraSocial os = new ObraSocial();
			Especialidad_ObraSocialController eoc = new Especialidad_ObraSocialController();
			EspecialidadesController ec = new EspecialidadesController();
			LinkedList<Especialidad_ObralSocial> especialidadesIncluidas = null;
			LinkedList<Especialidad> especialidadesNoIncluidas = null;
			LinkedList<Especialidad> especialidades = null;
			int codigo = Integer.parseInt(request.getParameter("id"));
			
			try {
				os.setId_obra_social(codigo);
				especialidades = ec.getAll();
				especialidadesIncluidas = eoc.getEspecialidadesIncluidas(codigo);
				especialidadesNoIncluidas = eoc.getEspecialidadesNoIncluidas(codigo);
				request.setAttribute("obraSocial", os);
				request.setAttribute("listaespecialidadesIncluidas", especialidadesIncluidas);
				request.setAttribute("listaespecialidadesNoIncluidas", especialidadesNoIncluidas);
				request.setAttribute("listaespecialidades", especialidades);
				request.setAttribute("retro", request.getAttribute("estado"));
				request.getRequestDispatcher("./especialidad_obrasocial.jsp").forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		else if(accion.equalsIgnoreCase("EliminarEspecialidad")) {
			Especialidad_ObraSocialController eosc = new Especialidad_ObraSocialController();
			int idEsp = (Integer.parseInt(request.getParameter("idEsp")));
			int idObra = (Integer.parseInt(request.getParameter("idObra")));
						
			try {
				eosc.deleteFromObraSocialEspecialidad(idEsp, idObra);
				request.setAttribute("estado", "Especialidad eliminada correctamente de la obra social.");
				request.getRequestDispatcher("ObrasSocialesServlet?accion=ListarEspecialidades&id="+idObra).forward(request, response);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(accion.equalsIgnoreCase("AgregarEspecialidad")) {
			Especialidad_ObralSocial eos =new Especialidad_ObralSocial();
			Especialidad esp = new Especialidad();
			ObraSocial os = new ObraSocial();
			Especialidad_ObraSocialController eosc = new Especialidad_ObraSocialController();
			int idObra = (Integer.parseInt(request.getParameter("idObra")));
			
			esp.setCodigo_esp(Integer.parseInt(request.getParameter("idEsp")));
			eos.setEsp(esp);
			os.setId_obra_social(idObra);
			eos.setOs(os);
			eos.setPorcentaje_cobertura(Float.parseFloat(request.getParameter("porcentaje")));
			
			try {
				eosc.addEspecialidadObraSocial(eos);
				request.setAttribute("estado", "Especialidad agregada correctamente de la obra social.");
				request.getRequestDispatcher("ObrasSocialesServlet?accion=ListarEspecialidades&id="+idObra).forward(request, response);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
