package mx.uaemex.fi.ing_software_ii.hmb;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Servlet implementation class HMB
 */
@WebServlet(urlPatterns = { "/hmb" }, 
	initParams = { 
		@WebInitParam(name = "max", value = "100", description = "Maxima numero de hamburguesas")
	})
public class HMB extends HttpServlet {
	private int maximo;
	private Random rand;
	
       
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.rand = new Random(19);
		this.maximo = Integer.parseInt(config.getInitParameter("max"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter salida;
		int hamburguesas;
		Integer hamComidas = -1;
		HttpSession sesion;
		String n;
		
		salida = response.getWriter();
		hamburguesas = Integer.parseInt(request.getParameter("hamburguesas"));
		
		response.setContentType("text/html");
		salida.println("<!DOCTYPE html>");
		salida.println("<html>");
		salida.println("<head>");
		salida.println("<meta charset=\"UTF-8\">");
		salida.println("<title>HMB - Login</title>");
		salida.println("</head>");
		salida.println("<body>");
		//Leo la sesion
		sesion = request.getSession();
		hamComidas = (Integer)sesion.getAttribute("loteria"); //Lee el valor
		if(hamComidas == null) {
			hamComidas = this.come();
			sesion.setAttribute("loteria", hamComidas);
		}
		if(hamComidas < hamburguesas) {
			salida.println("<h1>Nooooo, tampoco como tanto.</h1>");
			salida.println("<p><img src=\"imagenes/nocomotanto.png\"></p>");
		} else{
			if(hamComidas > hamburguesas) {
				salida.println("<h1>Me subestimas.</h1>");
				salida.println("<p><img src=\"imagenes/mesubestimas.jpeg\"></p>");
			} else {
				hamComidas = this.rand.nextInt(this.maximo);
				salida.println("<h1>Bravo, ganastes!!</h1>");
				hamComidas = this.come();
				sesion.setAttribute("loteria", hamComidas);
			}	
		}
		salida.println("</body>");
		salida.println("</html>");
	}
	
	private int come() {
		return this.rand.nextInt(this.maximo)+1;
	}
}
