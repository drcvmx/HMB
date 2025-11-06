<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.juego.howmanyburgers.model.User" %>
<%@ page import="com.juego.howmanyburgers.model.Record" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Top 10 Récords y Mi Récord</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"> <!-- Nuevo enlace CSS -->
</head>
<body>
    <div class="container">
        <h1>Tabla de Récords</h1>

        <%
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            Record userRecord = (Record) request.getAttribute("userRecord");
            ArrayList<Record> top10Records = (ArrayList<Record>) request.getAttribute("top10Records");
        %>

        <% if (loggedInUser != null) { %>
            <h2>Mi Mejor Récord</h2>
            <p>Usuario: <strong><%= loggedInUser.getUsername() %></strong></p>
            <% if (userRecord != null) { %>
                <p>Mejor Puntaje: <%= userRecord.getPuntaje() %> intentos</p>
                <p>Fecha del Récord: <%= userRecord.getFecha() %></p>
            <% } else { %>
                <p class="message">Aún no tienes un récord personal. ¡Juega para establecer uno!</p>
            <% } %>
            <hr>
        <% } %>

        <h2>Top 10 Mejores Puntuaciones</h2>
        <% if (top10Records != null && !top10Records.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>Posición</th>
                        <th>Usuario</th>
                        <th>Puntaje (Intentos)</th>
                        <th>Fecha</th>
                    </tr>
                </thead>
                <tbody>
                    <% int position = 1; %>
                    <% for (Record record : top10Records) { %>
                        <tr>
                            <td><%= position++ %></td>
                            <td><%= record.getUsuario().getUsername() %></td>
                            <td><%= record.getPuntaje() %></td>
                            <td><%= record.getFecha() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p class="message">No hay récords disponibles para el Top 10.</p>
        <% } %>

        <div class="links">
            <% if (loggedInUser != null) { %>
                <a href="juego">Volver al Juego</a>
                <a href="index.jsp">Volver al Inicio</a>
                <a href="logout">Cerrar Sesión</a>
            <% } else { %>
                <a href="login.jsp">Iniciar Sesión</a>
                <a href="registro.jsp">Registrarse</a>
                <a href="index.jsp">Volver al Inicio</a>
            <% } %>
        </div>
    </div>
</body>
</html>
