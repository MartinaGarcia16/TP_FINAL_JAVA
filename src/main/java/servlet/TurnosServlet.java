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
import entities.Turnos;
import logic.TurnosController;

/**
 * Servlet implementation class TurnosServlet
 */
@WebServlet("/TurnosServlet")
public class TurnosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TurnosServlet() {
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
			TurnosController tc=new TurnosController();
			LinkedList<Turnos> turnos = null;
			
			try {
				turnos = tc.getAll();
				request.setAttribute("listaTurnos", turnos);
				request.getRequestDispatcher("./TurnosTodos.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
