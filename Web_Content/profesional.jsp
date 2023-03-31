<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="java.util.LinkedList"%>
<%@page import="entities.Profesional"%>
<%@page import="entities.Especialidad"%>
<%@page import="entities.Administrador"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="styles/estilos.css">
	<!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<title>Listado de profesionales</title>
	<%
		Administrador adm = (Administrador) request.getSession().getAttribute("administrador");
		if(adm == null) {
			request.getRequestDispatcher("./index.jsp").forward(request, response);
			return;
		}
		LinkedList<Especialidad> le = (LinkedList<Especialidad>)request.getAttribute("listaEspecialidades");        	
		LinkedList<Profesional> lp = (LinkedList<Profesional>)request.getAttribute("listaProfesionales");
    %>
</head>
<body>
	<br>
	<p class="text-end"><a href="panel.jsp">Volver al panel administrativo</a></p>
	<div>
		<br>
		<%if ((request.getAttribute("retro"))!=null) { %>
			<p class="text-center"> <%=request.getAttribute("retro")%> </p>		
		<% } %>
		<p class="text-center"> Advertencia: Recuerde que al eliminar un profesional de la clínica todos sus turnos serán eliminados</p>
		<br>
      	<div class="text-center">
	    	<a href="ProfesionalesServlet?accion=Agregar" class="btn btn-primary">Agregar</a>
	    </div>
  	</div>
    <div class="table-responsive">
	    <table class="table">
      		<thead>
	        	<tr class="table-info">
	        		<th scope="col">Foto</th>
	        		<th scope="col">Matricula</th>
	          		<th scope="col">Nombre</th>
	          		<th scope="col">Apellido</th>
	          		<th scope="col">Especialidad</th>
	          		<th scope="col">Email</th>
	          		<th scope="col">Estado</th>
	          	 	<th scope="col">Acciones</th>
	        	</tr>
	      	</thead>
	      	<tbody>
	      		<% for (Profesional p : lp) { %>
	        		<tr>
	        			<td><img class="list__image" src="data:image/jpg;base64,<%=p.getBase64Image()%>" alt="..."></td>
	        			<td><%=p.getMatricula() %></td>
	          			<td><%=p.getNombre()%></td>
	         		 	<td><%=p.getApellido()%></td>
	          			<% for (Especialidad e : le) { 
	          				if(e.getCodigo_esp() == p.getEsp().getCodigo_esp()){%>
	          				<td> <%=e.getNombre() %></td>
	          			<% } }%>
	          			<td><%=p.getEmail()%></td>
	            		<% String est;
	            			if (p.getEstado() == 1)
	            			{est="Activo";} else {est="Inactivo";}%> 
	            		<td> <%= est %></td>      
	          			<td>	          
	          				<a class="btn btn-warning" href="ProfesionalesServlet?accion=Editar&matricula=<%=p.getMatricula()%>">Modificar</a>
							<a class="btn btn-danger" href="ProfesionalesServlet?accion=Eliminar&matricula=<%=p.getMatricula()%>">Eliminar</a>
	         		 	</td>
	        		</tr>
        		<% } %>
	      	</tbody>
    	</table>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>