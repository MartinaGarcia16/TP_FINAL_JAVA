<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@page import="java.util.LinkedList"%>
<%@page import="entities.Profesional"%>
<%@page import="entities.Paciente"%>
<%@page import="entities.Turnos"%>
 <%@page import="java.sql.Date" %>
 <%@page import="java.sql.Time" %>
 <%@page import="entities.Administrador"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Listado de todos los turnos</title>
<%
	Administrador adm = (Administrador) request.getSession().getAttribute("administrador");
	if(adm == null) {
		request.getRequestDispatcher("./index.jsp").forward(request, response);
		return;
	}
	LinkedList<Turnos> turnos = (LinkedList<Turnos>)request.getAttribute("listaTurnos");
%>
</head>
<body>
	<br>
	<p class="text-end"><a href="panel.jsp">Volver al panel administrativo</a>.</p>
	<br>
	<div class="table-responsive">
    	<table class="table">
     		<thead>
        		<tr class="table-info">
        			<th scope="col">Numero</th>
          			<th scope="col">Fecha</th>
          			<th scope="col">Hora</th>
          			<th scope="col">Matricula</th>
          			<th scope="col">Profesional</th>
          			<th scope="col">Paciente</th>
        		</tr>
      		</thead>
      		<tbody>
      			<% for (Turnos t : turnos) { %>		
      				<% Date fecha = Date.valueOf(t.getFecha_turno()); %>
      				<% Time hora = Time.valueOf(t.getHora_turno()); %>
        			<tr>
        				<td><%=t.getNumero()%></td>
          				<td><%= fecha %></td>
          				<td><%= hora %></td>
          				<td><%=t.getProf().getMatricula()%></td>
          				<td><%=t.getProf().getNombre() + " " + t.getProf().getApellido()%></td>
          				<td><%=t.getPaciente().getNombre() + " " + t.getPaciente().getApellido()%></td>
        			</tr>
        		<% } %>
      		</tbody>
    	</table>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>

</body>
</html>