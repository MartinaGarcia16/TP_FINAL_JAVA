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
import logic.AdministradorController;


/**
 * Servlet implementation class AdministradoresServlet
 */
@WebServlet("/AdministradoresServlet")
public class AdministradoresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdministradoresServlet() {
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
			AdministradorController ac = new AdministradorController();
			try {
				LinkedList<Administrador> administradores = ac.getAll();
				request.setAttribute("listaAdministradores", administradores);
				request.setAttribute("retro", request.getAttribute("estado"));
				request.getRequestDispatcher("./administrador.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		else if (accion.equalsIgnoreCase("Agregar")) {
			AdministradorController ac = new AdministradorController();
			String username = request.getParameter("username");

			try {
				if (ac.getByUsername(username) == null) {
					Administrador adm = new Administrador();
					adm.setUsername(username);
					adm.setPassword(request.getParameter("password"));
					boolean create = ac.add(adm);
					if(create) {
						request.setAttribute("estado", "Administrador agregado correctamente.");
						request.getRequestDispatcher("AdministradoresServlet?accion=listar").forward(request, response);
					} else {
						request.setAttribute("estado", "Hubo un error al crear el administrador. Intente de nuevo más tarde");
						request.getRequestDispatcher("AdministradoresServlet?accion=listar").forward(request, response);
					}
					
				} else {
					request.setAttribute("estado", "Administrador ingresado ya existe con ese Email");
					request.getRequestDispatcher("./addAdministrador.jsp").forward(request, response);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if (accion.equalsIgnoreCase("Editar")) {
			Administrador adm =new Administrador();
			AdministradorController ac = new AdministradorController();
			int codigo = Integer.parseInt(request.getParameter("id"));
			
			try {
				adm = ac.getById(codigo);
				request.setAttribute("administrador", adm);
				request.getRequestDispatcher("./editAdministrador.jsp").forward(request, response);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}			
		}
		
		else if (accion.equalsIgnoreCase("Actualizar")) {
			Administrador adm = null;
			AdministradorController ac = new AdministradorController();
			int id = Integer.parseInt(request.getParameter("id"));
			String username = request.getParameter("username");
			
			try {
				adm = ac.getByUsername(username);
				if(adm != null && adm.getId() != id) {
					request.setAttribute("estado", "No se ha podido actualizar el administrador. Ya existe una con ese username");
					request.getRequestDispatcher("AdministradoresServlet?accion=Listar").forward(request, response);
				} else {
					adm = new Administrador();
					adm.setId(id);
					adm.setUsername(username);
					adm.setPassword(request.getParameter("password"));
					boolean update = ac.update(adm);
					if(update) {
						request.setAttribute("estado", "Administrador editado correctamente.");
						request.getRequestDispatcher("AdministradoresServlet?accion=listar").forward(request, response);
					} else {
						request.setAttribute("estado", "No se ha podido actualizar el administrador. Intente de nuevo más tarde");
						request.getRequestDispatcher("AdministradoresServlet?accion=listar").forward(request, response);
					}
									}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
		
		else if(accion.equalsIgnoreCase("Eliminar")) {
			AdministradorController ac=new AdministradorController();
			int codigo = Integer.parseInt(request.getParameter("id"));
			
			try {
				boolean delete = ac.delete(codigo);
				if(delete) {
					request.setAttribute("estado", "Administrador eliminado exitosamente.");
					request.getRequestDispatcher("AdministradoresServlet?accion=Listar").forward(request, response);
				} else {
					request.setAttribute("estado", "Hubo un error al eliminar al administrador. Intente de nuevo más tarde");
					request.getRequestDispatcher("AdministradoresServlet?accion=Listar").forward(request, response);
				}
							} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}				
		}
	}

}
