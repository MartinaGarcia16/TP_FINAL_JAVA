package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Administrador;
import entities.Paciente;
import entities.Profesional;
import logic.AdministradorController;
import logic.PacientesController;
import logic.ProfesionalController;

@WebServlet({ "/Signin", "/signin", "/SignIn", "/SIGNIN", "/signIn" })
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Signin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("usuario");
            session.removeAttribute("administrador");
            session.removeAttribute("profesional");
        }
		Paciente pac = new Paciente();
		Profesional prof = new Profesional();
		Administrador adm = new Administrador();
		AdministradorController admCtrl= new AdministradorController();
		ProfesionalController profCtrl= new ProfesionalController();
		PacientesController pacCtrl= new PacientesController();
		
		String username = request.getParameter("username");   // Recuperar informaci�n de un formulario html
		String password = request.getParameter("password");
		
		try {
			adm.setUsername(username);
			adm.setPassword(password);
			adm = admCtrl.validate(adm);
			if(adm != null) {
				request.getSession().setAttribute("administrador", adm);
				request.getRequestDispatcher("./panel.jsp").forward(request, response);	
			} else {
				pac.setEmail(username);
				pac.setPassword(password);
				pac = pacCtrl.validateLogin(pac);
				if (pac != null){
					request.getSession().setAttribute("usuario", pac);
					request.getRequestDispatcher("./menuPaciente.jsp").forward(request, response); 
				} else {
					prof.setEmail(username);
					prof.setPassword(password);
					prof = profCtrl.getByUser(prof);
					if (prof != null){
						request.getSession().setAttribute("profesional", prof);
						request.getRequestDispatcher("./menuProfesionales.jsp").forward(request, response);; 
					} else {
						request.setAttribute("msg", "Usuario y/o contraseña inexistente");
						request.getRequestDispatcher("./index.jsp").forward(request, response);
					} 
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}	
}


