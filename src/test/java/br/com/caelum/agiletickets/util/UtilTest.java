package br.com.caelum.agiletickets.util;

import org.junit.Assert;
import org.junit.Test;

public class UtilTest {

	@Test
	public void transformaPalavraEmTitulo() {
		Util util = new Util();
		util.setLimiteCaracteresTitulo(12);
		String titulo = util.transformaEmTitulo("um titulo qualquer");

		Assert.assertEquals("Um Titulo Qu", titulo);
	}

}
