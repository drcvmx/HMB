package mx.uaemex.fi.ing_software_ii.hmb;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Servlet implementation class Bienvenida
 */
@WebServlet("/welcome")
public class Bienvenida extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter salida;
		Date fecha;
		
		fecha = new Date();
		salida = response.getWriter();
		response.setContentType("text/html");
		
		salida.println("<!DOCTYPE html>");
		salida.println("<html>");
		salida.println("<head>");
		salida.println("<title>HMB</title>");
		salida.println("</head>");
		salida.println("<body>");
		salida.println("<center>");
		salida.println("<img src=\"imagenes/cabeza.jpg\" alt=\"Encabezado\"/>");
		//salida.println("<img src=\"imagenes/calamardo.jpg\" alt=\"Calamardo\"/>");
		salida.println("<h1>How many burgers</h1>");
		salida.println("<p>"+fecha.toString()+"</p>");
		salida.println("<h2>Identificate</h2>");
		//salida.println();
		salida.println("</center>");
		salida.println("<form action=\"juego\" method=\"post\">");
		salida.println("<table>");
		salida.println("<tr>");
		salida.println("<td>Nombre ?</td>"); 
		salida.println("<td><input type=\"text\" placeholder=\"tu nombre\" name=\"usuario\"/></td>");
		salida.println("</tr>");
		salida.println("<tr>");
		salida.println("<td>Español:<input type=\"radio\" name=\"idioma\" value=\"1\"></td>");
		salida.println("<td>Inglés:<input type=\"radio\" name=\"idioma\" value=\"2\"></td>");
		salida.println("</tr>");
		salida.println("<tr>");
		salida.println("<td><input type=\"reset\" value=\"Borrar\"/></td>");
		salida.println("<td><input type=\"submit\" value=\"Enviar\"/></td>");
		salida.println("<tr>");
		salida.println("</table>");
		salida.println("</form>");
		salida.println("</body>");
		salida.println("</html>");
	}

}
