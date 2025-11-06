package com.juego.howmanyburgers.servlet;

import com.juego.howmanyburgers.model.User;
import com.juego.howmanyburgers.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Nombre de usuario y contraseña son obligatorios.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        User user = UserRepository.findUserByUsername(username);

        // La verificación de la contraseña ahora se realiza a través del método checkPassword() del objeto User
        if (user != null && user.checkPassword(password)) {
            HttpSession session = request.getSession(); // Obtener o crear la sesión
            session.setAttribute("loggedInUser", user); // Guardar el objeto User en la sesión
            response.sendRedirect("index.jsp"); // Redirigir a la página principal después del login
        } else {
            request.setAttribute("error", "Nombre de usuario o contraseña incorrectos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Si un usuario ya está logueado, redirigir a index.jsp (o juego.jsp)
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            response.sendRedirect("index.jsp");
            return;
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}