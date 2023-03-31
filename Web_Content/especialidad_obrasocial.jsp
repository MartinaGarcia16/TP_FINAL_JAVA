<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.LinkedList"%>
<%@page import="entities.Especialidad"%>
<%@page import="entities.Especialidad_ObralSocial"%>
<%@page import="entities.ObraSocial"%>
<%@page import="entities.Administrador"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Vincular obra social con especialidades</title>
<%
	Administrador adm = (Administrador) request.getSession().getAttribute("administrador");
	if(adm == null) {
		request.getRequestDispatcher("./index.jsp").forward(request, response);
		return;
	}
	ObraSocial os = (ObraSocial)request.getAttribute("obraSocial");
	LinkedList<Especialidad> esp = (LinkedList<Especialidad>)request.getAttribute("listaespecialidades");
	LinkedList<Especialidad_ObralSocial> espIncluidas=(LinkedList<Especialidad_ObralSocial>)request.getAttribute("listaespecialidadesIncluidas");
	LinkedList<Especialidad> espNoincluidas=(LinkedList<Especialidad>)request.getAttribute("listaespecialidadesNoIncluidas");
%>
</head>
<body>
<br>
<p class="text-end"><a href= "ObrasSocialesServlet?accion=listar" >Volver al Listado</a></p>
 <% 
    if(request.getAttribute("estado") != null){ %>
     <div style="color:black; text-align:center">
     	<label><%=request.getAttribute("estado") %></label>
     </div>
<%} %>
<h2>Especialidades Incluidas</h2>
<div class="table-responsive">
    <table class="table">
      <thead>
        <tr class="table-info">
        	<th scope="col">Codigo especialidad</th>
          	<th scope="col">Nombre</th>
          	<th scope="col">Porcentaje Cobertura</th>
           	<th scope="col">Acciones</th>
        </tr>
      </thead>
      <tbody>
      <% for (Especialidad_ObralSocial eos : espIncluidas) { %>
        <tr>
        	  <td> <%=eos.getEsp().getCodigo_esp() %></td>
	          <% for (Especialidad e : esp) { 
		          if(e.getCodigo_esp() == eos.getEsp().getCodigo_esp()){
		          %>
		          	<td><%=e.getNombre()%></td>
		          <% } %>
	          <% } %>
	          <td> <%=eos.getPorcentaje_cobertura() %></td>
	          <td>
	          	<a class="btn btn-warning" href="ObrasSocialesServlet?accion=EliminarEspecialidad&idEsp=<%=eos.getEsp().getCodigo_esp()%>&idObra=<%=eos.getOs().getId_obra_social()%>">Eliminar</a>
	          </td>
        </tr>
      <% } %>
      </tbody>
    </table>
</div>
    <br>
    <h2>Especialidades No Incluidas</h2>
<div class="table-responsive">
    <table class="table">
      <thead>
        <tr class="table-info">
	        <th scope="col">Codigo especialidad</th>
	        <th scope="col">Nombre</th>
	        <th scope="col">Porcentaje Cobertura</th>
	        <th scope="col">Acciones</th>
	    </tr>
      </thead>
    </table>
</div>
      <% for (Especialidad eno : espNoincluidas) { %>
      <form action="ObrasSocialesServlet" method="POST">
	      <div class="row align-items-center">
			  <div class="col">
			  	<input type=number class="form-control-plaintext" name="idEsp" value="<%=eno.getCodigo_esp()%>" readonly>
			  </div>
			  <div class="col">
			  	<input type=text class="form-control-plaintext" name="nombre" value="<%=eno.getNombre()%>" readonly>
			  </div>
			  <div class="col">
			  	<input type="number" step="0.01" max=1 min=0 class="form-control" name="porcentaje" required>
			  </div>
			  <div class="col">
			    <input type="hidden" name="idObra" value="<%=os.getId_obra_social()%>">
			    <button class="btn btn-primary" type="submit" name="accion" value="AgregarEspecialidad">Agregar</button>
			  </div>
		  </div>
	  </form>
      <hr>
      <% } %>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
    
</body>
</html>