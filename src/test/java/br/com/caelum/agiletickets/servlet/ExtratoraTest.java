package br.com.caelum.agiletickets.servlet;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.junit.Test;
import static org.mockito.Mockito.*;


public class ExtratoraTest {

	@Test
	public void deveCalcularTotal5TicketsDe40Reais() throws Exception {
		
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getParameter("preco")).thenReturn("40");
		when(req.getParameter("quantidade")).thenReturn("5");
		
		Extratora extratora = new Extratora(req);
		Double total = extratora.calcular();
		
		Assert.assertEquals(200d, total);
	}
	
}
