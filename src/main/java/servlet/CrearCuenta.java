package servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entities.ObraSocial;
import entities.Paciente;
import logic.PacientesController;
import logic.ObrasSocialesController;


@WebServlet({ "/CrearCuenta", "/crearcuenta", "/Crearcuenta", "/crearCuenta" })
public class CrearCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CrearCuenta() {
        super(); 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Paciente pac = new Paciente();
		PacientesController pacCtrl = new PacientesController();
		ObraSocial os = new ObraSocial();
		ObrasSocialesController osCtrl = new ObrasSocialesController();
		
		pac.setNombre(request.getParameter("nombre"));
		pac.setApellido(request.getParameter("apellido"));
		pac.setDni(request.getParameter("dni"));
		pac.setNum_tel(request.getParameter("celular"));
		pac.setEmail(request.getParameter("email"));
		pac.setPassword(request.getParameter("clave"));
		
		try {
			// Validar que el usuario no exista
			os = osCtrl.getByCodigo(Integer.parseInt(request.getParameter("obra_social")));
			pac.setOs(os);
			if(pacCtrl.getByEmail(pac.getEmail()) != null) {
				//ya existe
				request.setAttribute("msg", "Ya existe un paciente con este email.");
				request.getRequestDispatcher("NuevaCuenta").forward(request, response);
			} else {
				//valido
				boolean create = pacCtrl.altaPaciente(pac);
				if (create) {
					request.getRequestDispatcher("bienvenido.html").forward(request, response);
				} else {
					request.setAttribute("msg", "Hubo un error al crear su cuenta, por favor intente de nuevo.");
					request.getRequestDispatcher("NuevaCuenta").forward(request, response);
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}

