<%@page import="java.util.LinkedList"%>
<%@page import="entities.Profesional"%>
<%@page import="entities.Especialidad"%>
<%@page import="entities.Turnos"%>
<%@page import="entities.Paciente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Historia clinica</title>

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
	LinkedList<Turnos> turnos = (LinkedList<Turnos>)request.getAttribute("hcPaciente");
%>

<body>
	<div>
   		<h1>Historia clinica de: <%=pac.getNombre()%> <%=pac.getApellido() %></h1>
   		<% 
      	 	if(turnos.size() == 0){ %>
	      		<div style="color:Tomato; text-align:center">
	      			<h2>Usted no tienen turnos anteriores.</h2>
	      			<a class="btn btn-primary" href="./menuPaciente.jsp">Volver atras</a>
	      		</div>
     	<%  } else {%>
	   		<div class="container">
			  	<table class="table table-striped">
			    	<thead>
			      		<tr>
			        		<th>Fecha turno</th>
	        				<th>Hora turno</th>
	        				<th>Profesional</th>
	        				<th>Especialidad</th>
			      		</tr>
				    </thead>
				    <tbody>
			    		<% for (Turnos t : turnos) { %> 
	      				<tr>
	        				<td><%=t.getFecha_turno() %></td>
	        				<td><%=t.getHora_turno() %></td>
	        				<td><%=t.getProf().getNombre()%> <%=t.getProf().getApellido() %></td>
	        				<td><%=t.getProf().getEsp().getNombre()%></td>
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