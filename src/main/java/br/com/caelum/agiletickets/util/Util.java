package br.com.caelum.agiletickets.util;


public class Util {

	private int limiteCaracteresTitulo=7;

	public void setLimiteCaracteresTitulo(int limiteCaracteresTitulo) {
		this.limiteCaracteresTitulo = limiteCaracteresTitulo;
	}

	public String transformaEmTitulo(String titulo) {
		String[] palavras = titulo.split("\\s");
		StringBuilder temp = new StringBuilder();

		for (String p : palavras) {
			String primeiraLetra = p.substring(0, 1);
			String restoDaPalavra = p.substring(1);
			temp.append(primeiraLetra.toUpperCase() + restoDaPalavra.toLowerCase() + " ");
		}

		String capitalizado = temp.toString();
		return capitalizado.substring(0, limiteCaracteresTitulo);
	}

}
