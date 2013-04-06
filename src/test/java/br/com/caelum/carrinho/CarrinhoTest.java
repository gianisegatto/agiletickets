package br.com.caelum.carrinho;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class CarrinhoTest {

	@Test
	public void testMaiorValorComUmProdutoNoCarrinho() throws Exception {
		
		Carrinho carrinho = new Carrinho(new Item("bola", 10d));
		
		double preco = carrinho.obterMaiorPreco();
		
		Assert.assertEquals(10d == preco, true);
		
	}

	@Test
	public void testMaiorValorComDoisProdutosNoCarrinho() throws Exception {
		
		Carrinho carrinho = new Carrinho(new Item("bola", 10d));
		carrinho.adicionarItem(new Item("quadrado", 15d));
		
		double preco = carrinho.obterMaiorPreco();
		
		Assert.assertEquals(15d == preco, true);
	}
	
}
