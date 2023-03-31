<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="entities.Turnos"%>
 <%@page import="entities.Paciente"%>
  <%@page import="entities.Profesional"%>
 <%@page import="java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Turnos</title>
</head>

<meta charset="ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/estilos.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<body>
	<% 
		Profesional prof = (Profesional) request.getSession().getAttribute("profesional");
		if(prof == null) {
			request.getRequestDispatcher("./index.jsp").forward(request, response);
			return;
		}
		LinkedList<Turnos> turnos = (LinkedList<Turnos>)request.getAttribute("turnosProfesional");
	%>

	<div>
		<h2>Turnos de <%=prof.getNombre()%> <%=prof.getApellido() %></h2>
		<% 
      	 	if(turnos.size() == 0){ %>
	      		<div style="color:Black; text-align:center">
	      			<h2>No hay turnos asignados a este profesional.</h2>
	      			<a class="btn btn-primary" href="./menuProfesionales.jsp">Volver atras</a>
	      		</div>
     	<%  } else {%>
			<div class="container">
	 			<table class="table table-striped">
	    			<thead>
	      				<tr>
	      					<th>Nombre paciente</th>
	      					<th>Apellido paciente</th>
	        				<th>Fecha turno</th>
	        				<th>Hora turno</th>
	     				 </tr>
	    			</thead>
	    			<tbody>
	    
	   					 <% for (Turnos t:turnos) { %> 
	      				<tr>
	      					<td><%=t.getPaciente().getNombre()%></td>
	        				<td><%=t.getPaciente().getApellido() %></td>
	        				<td><%=t.getFecha_turno() %></td>
	        				<td><%=t.getHora_turno() %></td>
	      				</tr>
	    				 <% } %>
	   				</tbody> 
	  			</table>
	  			<a class="btn btn-primary" href="./menuProfesionales.jsp">Volver atras</a>
			</div>
		<%  }  %>
	</div>
</body>
</html>