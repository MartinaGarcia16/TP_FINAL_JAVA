<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="entities.Turnos"%>
 <%@page import="entities.Paciente"%>
 <%@page import="java.util.LinkedList"%>
 <%@page import="java.time.LocalDate"%>
 <%@page import="java.time.LocalTime"%>
 <%@page import="java.sql.Date" %>
 <%@page import="java.sql.Time" %>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mis turnos</title>
</head>

<meta charset="ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/estilos.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<% 
		Paciente pac = (Paciente) request.getSession().getAttribute("usuario");
		if(pac == null) {
			request.getRequestDispatcher("./index.jsp").forward(request, response);
			return;
		};
		LinkedList<Turnos> turnos = (LinkedList<Turnos>)request.getAttribute("turnosPaciente");
%>

<body>
	<div>
		<h2>Turnos de <%=pac.getNombre()%> <%=pac.getApellido() %></h2>
		<% 
      	 	if(turnos.size() == 0){ %>
	      		<div style="color:Tomato; text-align:center">
	      			<h2>Usted no tiene turnos reservados.</h2>
	      			<a class="btn btn-primary" href="./menuPaciente.jsp">Volver atras</a>
	      		</div>
     	<%  } else {%>
		<div class="container">
			<%if ((request.getAttribute("estado"))!=null) { %>
				<p class="text-center"> <%=request.getAttribute("estado")%> </p>		
			<% } %>
 			<table class="table table-striped">
    			<thead>
      				<tr>
        				<th>Fecha turno</th>
        				<th>Hora turno</th>
        				<th>Profesional</th>
        				<th>Especialidad</th>
        				<th>Acciones</th>
     				 </tr>
    			</thead>
    
    			<tbody>
    
   					 <% for (Turnos t : turnos) { %> 
      				<tr>
        				<td><%=t.getFecha_turno() %></td>
        				<td><%=t.getHora_turno() %></td>
        				<td><%=t.getProf().getNombre()%> <%=t.getProf().getApellido() %></td>
        				<td><%=t.getProf().getEsp().getNombre()%></td>
        				<td><a class="btn btn-danger" href="PacienteServlet?accion=CancelarTurno&nro=<%=t.getNumero()%>">Cancelar</a></td>
      				</tr>
    				 <% } %>
   				</tbody> 
  			</table>
  			<a class="btn btn-primary" href="./menuPaciente.jsp">Volver atras</a>
		</div>
		<%  }  %>
	</div>

</body>
</html>