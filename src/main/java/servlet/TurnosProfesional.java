package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Profesional;
import entities.Turnos;
import logic.TurnosController;

/**
 * Servlet implementation class TurnosProfesional
 */
@WebServlet("/TurnosProfesional")
public class TurnosProfesional extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TurnosProfesional() {
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
		Profesional profesional = (Profesional) request.getSession().getAttribute("profesional");
		if(profesional == null) {
			response.sendRedirect("./index.jsp");
			return;
		}
		
		String accion = request.getParameter("accion");
		
		if (accion.equalsIgnoreCase("turnosProfesional")) {
			TurnosController tc=new TurnosController();
			LinkedList<Turnos> turnos = null;
			
			try {
				turnos = tc.getTurnosProfesional(profesional.getMatricula());
				request.setAttribute("turnosProfesional", turnos);
				request.getRequestDispatcher("./turnosProfesionales.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
	}

}
