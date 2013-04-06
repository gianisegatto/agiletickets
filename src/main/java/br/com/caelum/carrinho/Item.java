package br.com.caelum.carrinho;

public class Item implements Comparable<Item> {

	private String nome;

	private double preco;
	
	public Item(String nome, double preco) {
		this.nome = nome;
		this.preco = preco;
	}
	
	public String getNome() {
		return nome;
	}

	public double getPreco() {
		return preco;
	}

	@Override
	public int compareTo(Item o) {
		return Double.valueOf(this.preco).compareTo(Double.valueOf(o.preco));
	}
}