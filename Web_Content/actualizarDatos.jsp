<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="entities.Paciente"%>
 <%@page import="entities.ObraSocial"%>
 <%@page import="java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Actualización de datos</title>
</head>
<meta charset="ISO-8859-1">
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
	LinkedList<ObraSocial> obras_sociales = (LinkedList<ObraSocial>)request.getAttribute("ObrasSociales");
%>
<body>
	<div class="form-createaccount">
		<h3>Ingrese sus nuevos datos</h3>
    	<form action="PacienteServlet" method="post">
    		<input name="id" type="hidden" value="<%=pac.getId()%>">
        	<input type="text" name="nombre" class="input" placeholder="Ingrese su nombre" maxlength=45 required value="<%=pac.getNombre()%>">

            <input type="text" name="apellido" class="input" placeholder="Ingrese su apellido" maxlength=45 required value="<%=pac.getApellido()%>">

            <input type="text" name="dni" class="input" placeholder="Ingrese su numero de documento" maxlength=8 required value="<%=pac.getDni()%>">
                
            <input type="email" name="email" class="input" placeholder="Input your email" maxlength=50 required value="<%=pac.getEmail()%>">

            <input type="password" name="clave" class="input" placeholder="Ingrese su clave" maxlength=45 required value="<%=pac.getPassword()%>">
                
            <input type="tel" name="celular" class="input" placeholder="Ingrese su numero de celular" maxlength=45 required value="<%=pac.getNum_tel()%>">
                
            <label for="inputCel">Obra social</label>
            <select name="obra_social" style="color: black">
            	<% for (ObraSocial os: obras_sociales) { %> 
            		<option value="<%=os.getId_obra_social() %>"
            			<%=os.getId_obra_social() == pac.getOs().getId_obra_social() ? "selected" : "" %>>
            			<%=os.getNombre() %>
            		</option>
            	<% } %>	
            </select>
     
      		<% 
            	if(request.getAttribute("msg") != null){ %>
               		<div style="color:Tomato; text-align:center">
	               		<label><%=request.getAttribute("msg") %></label>
	               	</div>
        	<%} %>
        	<div>
       			<button type="submit" class="btn btn-success" name="accion" value="ActualizarDatos">Actualizar datos</button>
       			<a href="./menuPaciente.jsp" class="btn btn-danger">Volver atrás</a>
       		</div>
       </form>
    </div>
</body>
</html>