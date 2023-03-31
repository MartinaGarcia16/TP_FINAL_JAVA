 <%@page import="entities.ObraSocial"%>
 <%@page import="java.util.LinkedList"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Nueva Cuenta</title>
		<meta charset="ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="./styles/estilos.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
		
	</head>
<body>
		<% 
			LinkedList<ObraSocial> obras_sociales = (LinkedList<ObraSocial>)request.getAttribute("obras_sociales");
		%>	
		 <div class="form-createaccount">
	        <form action="CrearCuenta" method="post">
                <h3>CREAR CUENTA</h3>
  
                <input type="text" name="nombre" class="input" placeholder="Ingrese su nombre" maxlength=45 required>

                <input type="text" name="apellido" class="input" placeholder="Ingrese su apellido" maxlength=45 required>

                <input type="text" name="dni" class="input" placeholder="Ingrese su numero de documento" pattern="[0-9]{6,8}" title="Solo se aceptan números" maxlength=8 required>
                
                <input type="email" name="email" class="input" placeholder="Input your email" maxlength=50 required>

                <input type="password" name="clave" class="input" placeholder="Ingrese su clave" maxlength=45 required>
                
                <input type="tel" name="celular" class="input" placeholder="Ingrese su numero de celular" pattern="[0-9]{8,45}" title="Solo se aceptan números" maxlength=45 required>
                
                <label for="inputCel">Obra social</label>
                <select name="obra_social" style="color: black">
                	<% for (ObraSocial os:obras_sociales) { %> 
                		<option value="<%=os.getId_obra_social() %>"><%=os.getNombre() %></option>
                	<% } %>	
                </select>
                <% 
	               if(request.getAttribute("msg") != null){ %>
	               <div style="color:Tomato; text-align:center">
	               	<label><%=request.getAttribute("msg") %></label>
	               </div>
           		<%} %>
                <div class="text-center button-div">
                	<button type="submit" class="btn btn-success">Crear cuenta</button>
                	<a href="./index.jsp" class="btn btn-danger">Cancelar</a>
                </div>
	        </form>
	    </div>
	</body>
</html>