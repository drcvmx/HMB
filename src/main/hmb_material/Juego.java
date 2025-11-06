package mx.uaemex.fi.ing_software_ii.hmb;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class Juego
 */
@WebServlet("/juego")
public class Juego extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter salida;
		int idioma;
		String usuario;
		
		salida = response.getWriter();
		idioma = Integer.parseInt(request.getParameter("idioma"));
		usuario = request.getParameter("usuario");
		
		response.setContentType("text/html");
		salida.println("<!DOCTYPE html>");
		salida.println("<html>");
		salida.println("<head>");
		salida.println("<meta charset=\"UTF-8\">");
		salida.println("<title>HMB - Login</title>");
		salida.println("</head>");
		salida.println("<body>");
		salida.println("<h1>How many burgers</h1>");
		
		if(idioma==2) {
			salida.println("<h2>The game</h2>");
			salida.println("<p>Hello, "+usuario+"</p>");
			salida.println("<p>There were only 100 krustycrab burgers when I got here. I dare you to find out how many burgers I ate.</p>");
		} else {
			salida.println("<h2>El juego</h2>");
			salida.println("<p>Hola, "+usuario+"</p>");
			salida.println("<p>Encontr&eacute; abierto el almac&eacute;n de cangreburgers cuando s&oacute;lo quedaban 100 de ellas,\n"
					+ "si Bob entro 10 minutos despu&eacute;s, te reto a adivinar cuantas hamburguesas alcance a comer.</p>");
		}
		salida.println("<p align=\"center\"><img src=\"imagenes/calamardo.jpg\" alt=\"Calamardo\"/></p>");
		salida.println("<form action=\"hmb\" method=\"post\">");
		salida.println("<table>");
		salida.println("<tr>");
		salida.println("<td>");
		salida.println("<label>Nombre:</label>");
		salida.println("</td>");
		salida.println("<td>");
		salida.println("<input type=\"text\" placeholder=\""+usuario+"\" name=\"userName\" >");
		salida.println("</td>");
		salida.println("</tr>");
		salida.println("<tr>");
		salida.println("<td>");
		salida.println("<label>Tu n&uacute;mero:</label>");
		salida.println("</td>");
		salida.println("<td>");
		salida.println("<input type=\"number\" name=\"hamburguesas\" min=\"1\" max=\"100\">");
		salida.println("</td>");
		salida.println("</tr>");
		salida.println("<tr>");
		salida.println("<td>");
		salida.println("<input type=\"submit\" value=\"Adivinar\">");
		salida.println("</td>");
		salida.println("<td>");
		salida.println("<input type=\"reset\" value=\"Borrar\">");
		salida.println("</td>");
		salida.println("</tr>");
		salida.println("<table>");
		salida.println("</form>");
		salida.println("</body>");
		salida.println("</html>");
	}

}
