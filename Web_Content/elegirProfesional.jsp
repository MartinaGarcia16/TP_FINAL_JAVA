<%@page import="java.util.LinkedList"%>
<%@page import="entities.Profesional"%>
<%@page import="entities.Paciente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	Paciente pac = (Paciente) request.getSession().getAttribute("usuario");
	if(pac == null) {
		request.getRequestDispatcher("./index.jsp").forward(request, response);
		return;
	}
	LinkedList<Profesional> profesionales = (LinkedList<Profesional>)request.getAttribute("profesionales");
%>

<body>
	<div>
		<% 
      	 	if(profesionales.size() == 0){ %>
	      		<div style="color:Tomato; text-align:center">
	      			<h2>No hay profesionales para la especialidad seleccionada.</h2>
	      			<a class="btn btn-primary" href="NuevoTurno?accion=NuevoTurno">Volver atras</a>
	      		</div>
     	<%  } else {%>
		<div class="container">
       			<table class="table table-striped">
	    			<thead>
	      				<tr>
	      					<th>Foto</th>
	        				<th>Nombre</th>
	        				<th>Apellido</th>
	        				<th>Seleccione un profesional</th>
	     				 </tr>
	    			</thead>
        			<tbody>
   					 	<% for (Profesional p:profesionales) { %> 
   				      		<tr>
   				      			<td><img class="list__image" src="data:image/jpg;base64,<%=p.getBase64Image()%>" alt="..."></td>
        						<td><%=p.getNombre() %></td>
        						<td><%=p.getApellido() %></td>
        						<td>
        							<a class="btn btn-success" href="NuevoTurno?accion=elegirProfesional&mat=<%=p.getMatricula()%>">
        								Seleccionar profesional
        							</a>
        						</td> 
      						</tr>
    				 	<% } %>
   					</tbody> 
  				</table>
  			<a class="btn btn-primary" href="NuevoTurno?accion=NuevoTurno">Volver atras</a>
		</div>
	 	<%  }  %>
	</div>
</body>

</html>