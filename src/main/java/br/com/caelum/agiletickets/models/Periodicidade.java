package br.com.caelum.agiletickets.models;

public enum Periodicidade {
	
	DIARIA(1), SEMANAL(7);
	
	int quantidadeDias;
	
	private Periodicidade(int quantidadeDias) {
		this.quantidadeDias = quantidadeDias;
	}

	public int getQuantidadeDias() {
		return quantidadeDias;
	}
	
}
