package estrutural;

public class Posicao {
	
	public int posicaoX;
	public int posicaoY;
	
	/**
	 * Construtor da classe posição.
	 * @param posicaoX	Valor X.
	 * @param posicaoY	Valor Y.
	 */
	public Posicao(int posicaoX, int posicaoY) {
		this.posicaoX = posicaoX;
		this.posicaoY = posicaoY;
	}
	
	/**
	 * Construtor default.
	 */
	public Posicao() {
		this.posicaoX = -1;
		this.posicaoY = -1;
	}
}
