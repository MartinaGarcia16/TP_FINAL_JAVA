<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entities.Paciente"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Menú paciente</title>
<link rel="stylesheet" href="styles/estilos.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<% 
	Paciente pac = (Paciente) request.getSession().getAttribute("usuario");
	if(pac == null) {
		request.getRequestDispatcher("./index.jsp").forward(request, response);
		return;
	};
%>
<body>
	<div>
		<div class="menu__usuario">
			<h3 class="">MENU PRINCIPAL</h3>
			<a href="NuevoTurno?accion=NuevoTurno" class="btn btn-primary menu__item">Reservar turno</a>
	        <a href="PacienteServlet?accion=MisTurnos" class="btn btn-primary menu__item">Mis turnos</a>
	        <a href="PacienteServlet?accion=EditarDatos" class="btn btn-primary menu__item">Actualizar mis datos</a>
	        <a href="PacienteServlet?accion=HistoriaClinica" class="btn btn-primary menu__item">Historia clinica</a>
	        <a href="Logout" class="btn btn-danger menu__item">Cerrar sesion</a>
	    </div> 
	</div>
</body>
</html>