<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entities.Profesional"%>
<%@page import="entities.Especialidad"%>
<%@page import="java.util.LinkedList"%>
<%@page import="entities.Administrador"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Editar profesional</title>
</head>
<% 
	Administrador adm = (Administrador) request.getSession().getAttribute("administrador");
	if(adm == null) {
		request.getRequestDispatcher("./index.jsp").forward(request, response);
		return;
	}
	Profesional p = (Profesional) request.getAttribute("profesional");
	LinkedList<Especialidad> le = (LinkedList<Especialidad>)request.getAttribute("listaEspecialidades");
%>
<body>
	<br>
	<p class="text-end"><a href= "ProfesionalesServlet?accion=listar" >Volver al Listado</a></p>
	<%
		if ((request.getAttribute("error")) != null) {
	%>
	<p class="text-center">
		<%=request.getAttribute("error")%>
	</p>
	<%
		}
	%>
	<div class="container">
		<div class="row">
			<div class="col-2"></div>
			<div class="col-7">
				<div class="card text-center">
					<div class="card-header">
						<h3 class="display-6">Modificar Profesional</h3>
					</div>
					<div class="card-body">
						<form action="ProfesionalesServlet" method="POST">
							<input type="hidden" name="id" value="<%=p.getMatricula()%>">
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">Matricula</label>
								<input type="text" class="form-control" name="matricula"
									value="<%=p.getMatricula()%>" required maxlength=10>
							</div>
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">Nombre</label>
								<input type="text" class="form-control" name="nombre"
									value="<%=p.getNombre()%>" required maxlength=45>
							</div>
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">Apellido</label>
								<input type="text" class="form-control" name="apellido"
									value="<%=p.getApellido()%>" required maxlength=45>
							</div>
							
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">Email</label>
								<input type="text" class="form-control" name="email"
									value="<%=p.getEmail()%>" required maxlength=45>
							</div>
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">Contraseña</label>
								<input type="password" class="form-control" name="password"
									value="<%=p.getPassword()%>" required maxlength=45>
							</div>
							<div>
								<label for="apellido">Ingrese la hora de inicio del profesional</label> <br>
								<input type="time" class="form-control" name="hora_inicio" min="09:00" max="18:00" step="1800" value="<%=p.getHora_inicio()%>"> <br>
							</div>
							<div>
								<label for="apellido">Ingrese la hora de fin del profesional</label> <br>
								<input type="time" class="form-control" name="hora_fin" min="09:00" max="18:00" step="1800" value="<%=p.getHora_fin()%>"> <br>
							</div>
							<div>
								<%if(p.getEstado()==1){%>
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
								<%}else{ %>
									<label for="estado">Ingrese el estado del profesional</label>
									<div class="form-check">
										<input class="form-check-input" type="radio" name="estado"
											id="exampleRadios1" value="1" > <label
											class="form-check-label" for="exampleRadios1"> Activo </label>
									</div>
									<div class="form-check">
										<input class="form-check-input" type="radio" name="estado"
											id="exampleRadios2" value="0" checked> <label
											class="form-check-label" for="exampleRadios2"> Inactivo </label>
									</div>
									<br>
								<%} %>
							</div>
							<div>
								<label for="estado">Ingrese especialidad del profesional</label>
								<select name="cod_especialidad" class="form-select" aria-label="Default select example">
								<% for (Especialidad e: le) { %>
									<% 
										String tipo;
										if(e.getCodigo_esp() == p.getEsp().getCodigo_esp()){tipo="selected";}else{tipo="";}
									%>
									<option value="<%=e.getCodigo_esp() %>" <%= tipo %>><%=e.getNombre() %></option>
						 		<% } %>
							</select>
							</div>
							<input class="btn btn-primary btn-lg" type="submit" name="accion"
								value="Actualizar">
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