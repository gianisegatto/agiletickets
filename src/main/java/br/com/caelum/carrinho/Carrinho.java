package br.com.caelum.carrinho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carrinho {

	private List<Item> itens;
	
	public Carrinho(Item item) {
		this.itens = new ArrayList<Item>();
		this.itens.add(item);
	}

	public double obterMaiorPreco() {
		
		//Collections.sort(itens);
		Collections.reverse(itens);
		
		return this.itens.get(0).getPreco();
	}

	public void adicionarItem(Item item) {
		this.itens.add(item);
	}
}
