<%@page import="java.util.LinkedList"%>
<%@page import="entities.Turnos"%>
<%@page import="entities.ObraSocial"%>
<%@page import="entities.Valor_especialidad"%>
<%@page import="entities.Paciente"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Confirmación de turno</title>

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
	ObraSocial os = (ObraSocial)request.getAttribute("os");
	Float porc = (Float)request.getAttribute("porc");
	Valor_especialidad ve = (Valor_especialidad)request.getAttribute("ve");
	Turnos t = (Turnos)request.getAttribute("turno");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
%>

<body>
	<div>
		<div class="container">
			<p>Su turno fue confirmado</p>
   			<table class="table table-striped">
   				<thead>
     				<tr>
       					<th>Profesional</th>
       					<th>Especialidad</th>
       					<th>Fecha</th>
       					<th>Hora</th>
       					<th>Obra Social</th>
       					<th>Descuento</th>
       					<th>Total a pagar</th>
    				</tr>
   				</thead>
      			<tbody>
		      		<tr>
   						<td><%=t.getProf().getNombre()%><%=t.getProf().getApellido()%></td>
   						<td><%=t.getProf().getEsp().getNombre()%></td>
   						<td><%=t.getFecha_turno()%></td>
   						<td><%=t.getHora_turno().format(formatter)%></td>
   						<td><%=os.getNombre()%></td>
   						<td>% <%=porc * 100%></td>
   						<td>$ <%=Math.round(ve.getValor() * (1 - porc))%></td>
					</tr>
				</tbody> 
			</table>
  			<a class="btn btn-success" href="./menuPaciente.jsp">Volver</a>
		</div>
	</div>
</body>

</html>