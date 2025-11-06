<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.juego.howmanyburgers.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>How Many Burgers - Inicio</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"> <!-- Nuevo enlace CSS -->
</head>
<body>
    <div class="container"> <!-- Asegurando la clase container -->
        <img src="img/logo.jpeg" alt="How Many Burgers Logo" class="logo"> <!-- Asegurando la clase logo -->
        <h1>Bienvenido a How Many Burgers!</h1>
        
        <% 
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser != null) {
        %>
                <p>¡Hola, <%= loggedInUser.getUsername() %>! ¿Qué quieres hacer?</p>
                <p><a href="juego">Jugar</a></p>
                <p><a href="record.jsp">Ver mi Récord</a></p>
                <p><a href="logout">Cerrar Sesión</a></p>
        <% 
            } else {
        %>
                <p>Por favor, regístrate o inicia sesión para jugar.</p>
                <p><a href="registro.jsp">Registrarse</a></p>
                <p><a href="login.jsp">Iniciar Sesión</a></p>
        <%
            }
        %>
        <p><a href="records">Ver Top 10 Récords</a></p>  <!-- Nuevo enlace -->
    </div>
</body>
</html>