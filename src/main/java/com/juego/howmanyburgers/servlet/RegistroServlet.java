package com.juego.howmanyburgers.servlet;

import com.juego.howmanyburgers.model.User;
import com.juego.howmanyburgers.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password"); // Contraseña en texto plano (para demostración)
        String email = request.getParameter("email"); // Nuevo: para el correo electrónico

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Nombre de usuario y contraseña son obligatorios.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // En un entorno de producción, la contraseña se hashearía (e.g., BCrypt.hashpw(password, BCrypt.gensalt()))
        // Por simplicidad, aquí la "contraseña hasheada" es la misma contraseña en texto plano
        // Usamos el constructor completo para User
        User newUser = new User(0L, username, password, email); // ID 0L porque la BD lo generará
        
        if (UserRepository.addUser(newUser)) {
            request.setAttribute("mensaje", "¡Registro exitoso! Por favor, inicia sesión.");
            response.sendRedirect("login.jsp"); // Redirigir a la página de login
        } else {
            request.setAttribute("error", "El nombre de usuario ya existe. Por favor, elige otro.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Simplemente reenvía a la página de registro si se accede por GET
        request.getRequestDispatcher("registro.jsp").forward(request, response);
    }
}
