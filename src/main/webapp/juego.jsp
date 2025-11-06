<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.juego.howmanyburgers.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>How Many Burgers - El Juego</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"> <!-- Nuevo enlace CSS -->
</head>
<body>
    <div class="container">
        <img src="img/logo.jpeg" alt="How Many Burgers Logo" class="logo">
        <h1>How Many Burgers</h1>
        
        <% 
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            String feedback = (String) session.getAttribute("feedback");
            Integer attempts = (Integer) session.getAttribute("attempts");
            Integer numeroAdivinar = (Integer) session.getAttribute("numeroAdivinar"); // Para verificar si el juego está activo

            if (loggedInUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>

        <p>¡Hola, <%= loggedInUser.getUsername() %>! </p>
        
        <%-- Mostrar imágenes según el feedback (adaptado para el nuevo flujo) --%>
        <% 
            String imagenJuego = "imagenhome.jpg"; // Por defecto
            if (feedback != null) {
                if (feedback.contains("MENOR")) {
                    imagenJuego = "numeromenor.jpeg";
                } else if (feedback.contains("MAYOR")) {
                    imagenJuego = "numeromayor.jpg";
                } else if (feedback.contains("¡Ganaste!")) {
                    imagenJuego = "ganaste.jpg";
                }
            }
        %>
        <img src="img/<%= imagenJuego %>" alt="Resultado del juego" class="game-image"> <!-- Cambiado a game-image -->

        <p><%= (feedback != null) ? feedback : "He pensado un número entre 1 y 100. ¡Adivínalo!" %></p>
        
        <form action="juego" method="post">
            <label for="intento">Introduce tu adivinanza (1-100):</label>
            <input type="number" id="intento" name="intento" min="1" max="100" required>
            <button type="submit">Adivinar</button>
        </form>

        <%-- Si el juego ha terminado (ganó), mostrar opción de jugar de nuevo --%>
        <% if (numeroAdivinar == null && feedback != null && feedback.contains("¡Ganaste!")) { %>
            <p><a href="juego?reiniciar=true">Jugar de nuevo</a></p>
        <% } %>
        
        <p><a href="record.jsp">Ver mi Récord</a></p>
        <p><a href="logout">Cerrar Sesión</a></p>
        <p><a href="index.jsp">Volver al Inicio</a></p>
    </div>
</body>
</html>