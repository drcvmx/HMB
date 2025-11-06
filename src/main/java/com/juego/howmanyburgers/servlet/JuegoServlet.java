package com.juego.howmanyburgers.servlet;

import com.juego.howmanyburgers.model.Record;
import com.juego.howmanyburgers.model.User;
import com.juego.howmanyburgers.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

@WebServlet("/juego")
public class JuegoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = getLoggedInUser(request);

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Integer numeroAdivinar = (Integer) session.getAttribute("numeroAdivinar");
        String feedback = "";
        Integer attempts = (Integer) session.getAttribute("attempts");

        if (attempts == null) {
            attempts = 0;
        }

        if (request.getParameter("intento") != null) {
            try {
                int intento = Integer.parseInt(request.getParameter("intento"));
                attempts++;
                session.setAttribute("attempts", attempts);

                if (intento < numeroAdivinar) {
                    feedback = "El número que adivinaste es MENOR.";
                } else if (intento > numeroAdivinar) {
                    feedback = "El número que adivinaste es MAYOR.";
                } else {
                    feedback = "¡Ganaste, " + loggedInUser.getUsername() + "! El número secreto era " + numeroAdivinar + ". Lo adivinaste en " + attempts + " intentos.";
                    session.removeAttribute("numeroAdivinar"); // El juego terminó
                    
                    // Guardar/Actualizar récord
                    Record currentRecord = UserRepository.getUserRecord(loggedInUser.getUsername());
                    // El método en Record para obtener los intentos ahora es getPuntaje()
                    if (currentRecord == null || attempts < currentRecord.getPuntaje()) {
                        Record newRecord = new Record(); // Usar constructor vacío
                        newRecord.setUsuario(loggedInUser); // Establecer el usuario
                        newRecord.setPuntaje(attempts); // Usar setPuntaje
                        newRecord.setFecha(new Date()); // Establecer la fecha
                        newRecord.setJuego(UserRepository.getHowManyBurgersGame()); // Establecer el juego
                        // La posición se puede establecer en 0 o dejar por defecto si no es relevante ahora
                        newRecord.setPosicion(0); 

                        UserRepository.saveUserRecord(loggedInUser.getUsername(), newRecord);
                        feedback += " ¡Nuevo récord personal!";
                    }
                }
            } catch (NumberFormatException e) {
                feedback = "Por favor, introduce un número válido.";
            }
        }

        session.setAttribute("feedback", feedback);
        response.sendRedirect("juego.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedInUser = getLoggedInUser(request);
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp"); // Redirigir al login si no hay usuario
            return;
        }

        HttpSession session = request.getSession();
        Integer numeroAdivinar = (Integer) session.getAttribute("numeroAdivinar");
        
        // Inicializar un nuevo juego si no hay uno activo o si se solicita reiniciar
        if (request.getParameter("reiniciar") != null || numeroAdivinar == null) {
            numeroAdivinar = new Random().nextInt(100) + 1;
            session.setAttribute("numeroAdivinar", numeroAdivinar);
            session.setAttribute("attempts", 0); // Reiniciar intentos
            session.setAttribute("feedback", "He pensado un número entre 1 y 100. ¡Adivínalo, " + loggedInUser.getUsername() + "!");
        }
        
        response.sendRedirect("juego.jsp");
    }

    /**
     * Método auxiliar para obtener el objeto User del usuario logueado de la sesión.
     * @param request La HttpServletRequest actual.
     * @return El objeto User del usuario logueado, o null si no hay ninguno.
     */
    private User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute("loggedInUser");
        }
        return null;
    }
}