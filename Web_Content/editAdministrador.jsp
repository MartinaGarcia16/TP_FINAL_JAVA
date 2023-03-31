<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entities.Administrador"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<title>Editar administrador</title>
</head>
<% 
	Administrador adm = (Administrador) request.getSession().getAttribute("administrador");
	if(adm == null) {
		request.getRequestDispatcher("./index.jsp").forward(request, response);
		return;
	}
%>
<body>
	<br>
	<p class="text-end"><a href= "AdministradoresServlet?accion=listar" >Volver al Listado</a></p>
	<%
		if ((request.getAttribute("error")) != null) {
	%>
	<p class="text-center">
		<%=request.getAttribute("error")%>
	</p>
	<%
		}
	%>
	<%
		Administrador admin = (Administrador) request.getAttribute("administrador");
	%>
	<div class="container">
		<div class="row">
			<div class="col-2"></div>
			<div class="col-7">
				<div class="card text-center">
					<div class="card-header">
						<h3 class="display-6">Modificar Administrador</h3>
					</div>
					<div class="card-body">
						<form action="AdministradoresServlet" method="POST">
							<input type="hidden" name="id" value="<%=admin.getId()%>">														
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">Username</label>
								<input type="text" class="form-control" name="username"
									value="<%=admin.getUsername()%>" required maxlength=45>
							</div>
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">Contraseña</label>
								<input type="password" class="form-control" name="password"
									value="<%=admin.getPassword()%>" required maxlength=45>
							</div>
							<button class="btn btn-primary btn-lg" type="submit" name="accion"
								value="Actualizar">Actualizar</button>
						</form>
					</div>
				</div>
			</div>
			<div class="col-3"></div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>


</body>
</html>