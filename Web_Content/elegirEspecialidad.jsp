<%@page import="java.util.LinkedList"%>
<%@page import="entities.Especialidad"%>
<%@page import="entities.Paciente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Elegir especialidad</title>

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
	LinkedList<Especialidad> especialidades = (LinkedList<Especialidad>)request.getAttribute("especialidades");
	Boolean aviso = (Boolean)request.getAttribute("aviso");
%>
<body>
	<div>
		<div class="container">
 			<table class="table table-striped">
    			<thead>
      				<tr>
        				<th>Nombre</th>
        				<th>Seleccione una especialidad</th>
     				 </tr>
    			</thead>
    
    			<tbody>
   					 <% for (Especialidad e:especialidades) { %> 
      				<tr>
        				<td><%=e.getNombre() %></td>
        				<td>
        					<a 
        						class="btn btn-success" 
        						href="NuevoTurno?accion=ElegirEspecialidad&cod_esp=<%=e.getCodigo_esp()%>"
        					>
        						Seleccionar especialidad
        					</a>
        				</td> 
      				</tr>
    				 <% } %> 
   				</tbody> 
  			</table>
  			<a class="btn btn-primary" href="./menuPaciente.jsp">Volver atras</a>
		</div>
	</div>
	<br></br>
	<% 
   	 	if(request.getAttribute("error") != null){ %>
      		<div style="color:Tomato; text-align:center">
      			<label><%=request.getAttribute("estado") %></label>
      		</div>
   	<%} %>
</body>

</html>