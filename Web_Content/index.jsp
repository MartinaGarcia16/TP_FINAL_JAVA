<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>Iniciar sesión</title>
	    <link rel="stylesheet" href="styles/estilos.css">
	</head>
	<body>
		<h1 class="login__title">Clínica UTN</h1>
		<section class="form-login">
	       <form action="signin" method="post">
	        <h3>Iniciar sesion</h3>
	       	<label for="inputEmail">Usuario</label>
	        <input type="text" name="username" id="inputEmail" class="input" placeholder="Ingrese su usuario">
	        <label for="inputPass">Contraseña</label>
	        <input type="password" name="password" id="inputPass" class="input" placeholder="Ingrese su clave">
	        <% 
               if(request.getAttribute("msg") != null){ %>
               <div style="color:Tomato; text-align:center">
               	<label><%=request.getAttribute("msg") %></label>
               </div>
            <%} %>
	        <button type="submit" class="input-button">Ingresar</button>
	        <p><a href="NuevaCuenta">Crear una cuenta</a></p>
	       </form>
	    </section>
	</body>
</html>