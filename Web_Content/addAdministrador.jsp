<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entities.Administrador"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Agregar administrador</title>
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
	<div class="container">
		<div class="row">
			<div class="col-2"></div>
			<div class="col-7">
				<div class="card text-center">
	  				<div class="card-body">
	    				<div class="card-header">
							<h3 class="display-6">Agregar Administrador</h3>
						</div>
					    <form action="AdministradoresServlet" method="Post">
				        	<div>
			          			<label for="username">Ingrese username del administrador</label> 
					          	<br>
					          	<input type="text" class="form-control" name="username" required maxlength=45>
					         	<br>
					        </div>
					        <div>
				          		<label for="password">Ingrese contraseña del administrador</label> 
					          	<br>
					          	<input type="password" class="form-control" name="password" required maxlength=45>
					         	<br>
					        </div>
					        <% 
					      	 	if(request.getAttribute("estado") != null){ %>
						      		<div style="color:Tomato; text-align:center">
						      			<label><%=request.getAttribute("estado") %></label>
						      		</div>
					     	<%} %>
					        <button class="btn btn-primary btn-lg" type="submit" name="accion" value="Agregar">Agregar</button>
				     	</form>
	  				</div>
				</div>
			</div>
			<div class="col-2"></div>
		</div>
	</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>