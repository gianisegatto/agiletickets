package br.com.caelum.agiletickets.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalculadoraTicketServlet extends HttpServlet {

	private static final long serialVersionUID = -6458924578783516753L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Extratora extratora = new Extratora(req);
		Double precoTotal = extratora.calcular();
		
		req.setAttribute("precoTotal", precoTotal);
		
		resp.sendRedirect("xpto");
	}
	
}
