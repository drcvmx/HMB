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
import java.util.ArrayList;

@WebServlet("/records")
public class RecordsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedInUser = null;
        if (session != null) {
            loggedInUser = (User) session.getAttribute("loggedInUser");
        }

        // Obtener el récord personal del usuario logueado
        if (loggedInUser != null) {
            Record userRecord = UserRepository.getUserRecord(loggedInUser.getUsername());
            request.setAttribute("userRecord", userRecord);
        }

        // Obtener el Top 10 de récords
        ArrayList<Record> top10Records = UserRepository.getTop10Records();
        request.setAttribute("top10Records", top10Records);

        // Forward a la JSP para mostrar los récords
        request.getRequestDispatcher("top10.jsp").forward(request, response);
    }
}
