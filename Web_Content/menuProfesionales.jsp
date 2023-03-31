<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entities.Profesional"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Menu principal</title>
<link rel="stylesheet" href="styles/estilos.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<% 
	Profesional prof = (Profesional) request.getSession().getAttribute("profesional");
	if(prof == null) {
		request.getRequestDispatcher("./index.jsp").forward(request, response);
		return;
	}
%>
<body>
	<div class="menu__usuario">
		<h3 class="w-100 text-center">Bienvenido <%=prof.getNombre()%></h3>
		<div>
				<img class="list__image mb-4" src="data:image/jpg;base64,<%=prof.getBase64Image()%>" alt="...">
		</div>
		<div class="text-center">	
			<a class="btn btn-primary menu__item" href="TurnosProfesional?accion=turnosProfesional">Turnos</a>
			<a href="Logout" class="btn btn-danger menu__item">Cerrar sesion</a>
    	</div> 
	</div>
</body>
</html>