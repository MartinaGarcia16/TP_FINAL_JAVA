<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="entities.Especialidad" %>
<%@page import="java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<title>Agregar profesional</title>
	<%
    //Socio s = (Socio)session.getAttribute("usuario");
		LinkedList<Especialidad> le = (LinkedList<Especialidad>)request.getAttribute("listaEspecialidades");
	%>
</head>
<body>
	<br>
	<p class="text-end"><a href= "ProfesionalesServlet?accion=listar" >Volver al Listado</a></p>
	<div class="container">
		<div class="row">
			<div class="col-2"></div>
			<div class="col-7">
				<div class="card text-center">
					<div class="card-header">
						<h3 class="display-6">Agregar Profesional</h3>
					</div>
					<div class="card-body">
						<form action="ProfesionalesServlet" method="Post" enctype="multipart/form-data">
							<div>
								<label for="matricula">Ingrese matricula del profesional</label> <br>
								<input type="text" class="form-control" name="matricula" pattern="[0-9]{8,10}" title="Solo se aceptan números" required maxlength=10> <br>
							</div>
							<div>
								<label for="nombre">Ingrese el nombre del profesional</label> <br>
								<input type="text" class="form-control" name="nombre" required maxlength=45> <br>
							</div>
							<div>
								<label for="apellido">Ingrese el apellido del profesional</label> <br>
								<input type="text" class="form-control" name="apellido" required maxlength=45> <br>
							</div>
							<div>
								<label for="apellido">Ingrese la hora de inicio del profesional</label> <br>
								<input type="time" class="form-control" name="hora_inicio" min="09:00" max="18:00" step="1800" value="09:00"> <br>
							</div>
							<div>
								<label for="apellido">Ingrese la hora de fin del profesional</label> <br>
								<input type="time" class="form-control" name="hora_fin" min="09:00" max="18:00" step="1800" value="18:00"> <br>
							</div>
							<div>
								<label for="estado">Ingrese el estado del profesional</label>
								<div class="form-check">
									<input class="form-check-input" type="radio" name="estado"
										id="exampleRadios1" value="1" checked> <label
										class="form-check-label" for="exampleRadios1"> Activo </label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="radio" name="estado"
										id="exampleRadios2" value="0"> <label
										class="form-check-label" for="exampleRadios2"> Inactivo </label>
								</div>
								<br>
							</div>
							<div>
								<label for="email">Ingrese el email del profesional</label> <br>
								<input type="text" class="form-control" name="email" required maxlength=45> <br>
							</div>
							<div>
								<label for="password">Ingrese el contraseña del profesional</label> 
								<br> 
								<input type="password" class="form-control" name="password" required maxlength=45> 
								<br>
							</div>
							<div>
								<label for="estado">Ingrese especialidad del profesional</label>
								<br>							
								<select name="cod_especialidad" class="form-select" aria-label="Default select example">
									<% for (Especialidad e: le) { %>
										<option value="<%=e.getCodigo_esp() %>"><%=e.getNombre() %></option>
							 		<% } %>
								</select>
								<br>
							</div>
							<div>
								<label for="image">Foto del profesional</label> 
								<br>
								<input type="file" name="image" id="image" size="50" required>
								<br>
							</div>
							<% 
					      	 	if(request.getAttribute("estado") != null){ %>
						      		<div style="color:Tomato; text-align:center">
						      			<label><%=request.getAttribute("estado") %></label>
						      		</div>
					    	<%} %>
							<button class="btn btn-primary btn-lg" type="submit" name="accion" value="Add">Agregar</button>
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