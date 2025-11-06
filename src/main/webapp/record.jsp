<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.juego.howmanyburgers.model.User" %>
<%@ page import="com.juego.howmanyburgers.model.Record" %>
<%@ page import="com.juego.howmanyburgers.repository.UserRepository" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mi Récord</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"> <!-- Nuevo enlace CSS -->
</head>
<body>
    <div class="container">
        <h1>Mi Récord de Adivinanzas</h1>
        
        <% 
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            // Obtener el récord del usuario logueado
            Record userRecord = UserRepository.getUserRecord(loggedInUser.getUsername());
        %>

        <p>Usuario: <%= loggedInUser.getUsername() %></p>

        <% if (userRecord != null) { %>
            <p>Mejor Récord: <%= userRecord.getPuntaje() %> intentos</p>
            <p>Fecha del Récord: <%= userRecord.getFecha() %></p>
        <% } else { %>
            <p class="message">Aún no tienes un récord. ¡Juega para establecer uno!</p> <!-- Usando clase CSS -->
        <% } %>
        
        <p><a href="juego">Volver al Juego</a></p>
        <p><a href="index.jsp">Volver al Inicio</a></p>
        <p><a href="logout">Cerrar Sesión</a></p>
    </div>
</body>
</html>