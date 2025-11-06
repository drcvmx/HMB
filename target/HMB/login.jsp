<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"> <!-- Nuevo enlace CSS -->
</head>
<body>
    <div class="container">
        <h1>Iniciar Sesión</h1>
        
        <%-- Mostrar mensajes de error o éxito --%>
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p> <!-- Usando clase CSS -->
        <% } %>
        <% if (request.getAttribute("mensaje") != null) { %>
            <p class="message"><%= request.getAttribute("mensaje") %></p> <!-- Usando clase CSS -->
        <% } %>

        <form action="login" method="post">
            <div> <!-- Envolviendo el input en un div -->
                <label for="username">Nombre de Usuario:</label>
                <input type="text" id="username" name="username" required>
            </div>
            
            <div> <!-- Envolviendo el input en un div -->
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" required>
            </div>
            
            <button type="submit">Iniciar Sesión</button>
        </form>
        
        <p>¿No tienes una cuenta? <a href="registro.jsp">Regístrate aquí</a></p>
        <p><a href="index.jsp">Volver al Inicio</a></p>
    </div>
</body>
</html>
