<%@page import="java.util.LinkedList"%>
<%@page import="entities.Turnos"%>
<%@page import="entities.Profesional"%>
<%@page import="entities.Paciente"%>
<%@page import="java.sql.Date" %>
<%@page import="java.sql.Time" %>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Listado profesionales</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="styles/estilos.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	
	<%
		Paciente pac = (Paciente)session.getAttribute("usuario");
		if(pac == null) {
			request.getRequestDispatcher("./index.jsp").forward(request, response);
			return;
		}
		LinkedList<LocalDateTime> turnos = (LinkedList<LocalDateTime>)request.getAttribute("fechasYHorariosPosibles");
		Profesional prof = (Profesional)request.getAttribute("profesional");
	%>
</head>
<body>
	<div>
		<h2>Profesional: <%=prof.getNombre() %> <%=prof.getApellido() %></h2>
		<h2>Usuario: <%=pac.getNombre() %> <%=pac.getApellido() %></h2>
		<input type="hidden" name="matricula_profesional" value="<%=prof.getMatricula() %>">
		<div class="container">
 			<table class="table table-striped">
    			<thead>
      				<tr>
        				<th>Fecha turno</th>
        				<th>Hora turno</th>
        				<th>Seleccione su turno</th>
     				 </tr>
    			</thead>
    			<tbody>
   					 <% for (LocalDateTime t: turnos) { %> 
   					 	<% 
   					 		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	   						LocalDate fecha = t.toLocalDate();
	   						LocalTime hora = t.toLocalTime();
						%>
	      				<tr>
	        				<td><%=fecha %></td>
	        				<td><%=hora.format(formatter) %></td>
	        				<td>
	        					<a 
	        						class="btn btn-success"
	        						href="NuevoTurno?accion=elegirTurno&mat=<%=prof.getMatricula()%>&fechaHora=<%=t%>">
	        						Reservar
	        					</a>
	        				</td> 
	      				</tr>
    				 <% } %>
   				</tbody> 
  			</table>
  			<a class="btn btn-primary" href="NuevoTurno?accion=ElegirEspecialidad&cod_esp=<%=prof.getEsp().getCodigo_esp()%>">Volver atras</a>
		</div>
	</div>
</body>

</html>