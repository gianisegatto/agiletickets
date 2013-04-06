package br.com.caelum.agiletickets.servlet;

import javax.servlet.http.HttpServletRequest;

public class Extratora {
	
	private HttpServletRequest req;
	
	public Extratora(HttpServletRequest req) {
		this.req = req;
	}
	
	public Double calcular() {
		
		String precoParam = req.getParameter("preco");
		String quantidadeParam = req.getParameter("quantidade");
		
		Double preco = Double.parseDouble(precoParam);
		Integer quantidade = Integer.parseInt(quantidadeParam); 
		
		Double precoTotal = preco * quantidade;
		
		return precoTotal;
	}
	
}
