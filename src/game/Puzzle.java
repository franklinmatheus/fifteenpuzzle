package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import estrutural.Posicao;
import visual.Tabuleiro;

public class Puzzle extends JFrame implements ActionListener {

	private Tabuleiro tabuleiro;
	private int [][] numeros;
	private int tamanho;
	
	/**
	 * Construtor do puzzle.
	 */
	public Puzzle() {
		tabuleiro = new Tabuleiro();
		
		this.tamanho = tabuleiro.getTamanho();
		numeros = new int[tamanho][tamanho];
		
		preencheNumeros(tamanho);
	}

	/**
	 * Preenche a matriz com os números respectivos dos botões.
	 * @param tamanho Tamanho da matriz.
	 */
	private void preencheNumeros(int tamanho) {
		numeros = tabuleiro.getNumeros(tamanho);
	}
	
	/**
	 * Define todo o design da janela.
	 */
	public void fazerJanela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setTitle("Fifteen Puzzle");
		
		for(int indexLine = 0; indexLine < tamanho; ++indexLine) {
			for(int indexColumn = 0; indexColumn < tamanho; ++indexColumn) {
				tabuleiro.botoes[indexLine][indexColumn].addActionListener(this);
			}
		}
		
		JButton fechar = new JButton("Fechar");
		fechar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		add(fechar, BorderLayout.SOUTH);
		add(tabuleiro.getPanel());
	}
	
	/**
	 * Retorna a posição do botão baseado no número dele.
	 */
	private Posicao getPosicaoBotao(int numeroBotao) {
		Posicao posicao = new Posicao();
		
		for(int indexLine = 0; indexLine < tamanho; ++indexLine) {
			for(int indexColumn = 0; indexColumn < tamanho; ++indexColumn) {
				if(numeros[indexLine][indexColumn] == numeroBotao) {
					posicao.posicaoX = indexColumn;
					posicao.posicaoY = indexLine;
					break;
				}
			}
		}
		
		return posicao;
	}
	
	/**
	 * Encontra por uma posição vazia adjacente ao botão clicado.
	 * @param posicaoBotaoClicado	Posição do botão de foi clicado.
	 * @return	Retorna a posição do espaço vazio (caso não esteja ao redor do botão clicado, seus valores serão os default).
	 */
	public Posicao encontrarEspacoVazioAdjacente(Posicao posicaoBotaoClicado) {
		Posicao posicaoEspacoVazio = new Posicao();
		Posicao [] ladosAdjacentes = {
				new Posicao(posicaoBotaoClicado.posicaoX, posicaoBotaoClicado.posicaoY - 1), // UP
				new Posicao(posicaoBotaoClicado.posicaoX - 1, posicaoBotaoClicado.posicaoY), // LEFT
				new Posicao(posicaoBotaoClicado.posicaoX, posicaoBotaoClicado.posicaoY + 1), // BOTTOM
				new Posicao(posicaoBotaoClicado.posicaoX + 1, posicaoBotaoClicado.posicaoY), // RIGHT
		};
		
		for(int index = 0; index < 4; ++index) {
			try {
				if(numeros[ladosAdjacentes[index].posicaoY][ladosAdjacentes[index].posicaoX] == 0) {
					posicaoEspacoVazio = ladosAdjacentes[index];
					break;
				}
			} catch (Exception e) {
				System.out.println("[exception 1]");
			}
		}
		
		return posicaoEspacoVazio;
	}
	
	/**
	 * Move o botão para o espaço vazio adjacente.
	 * @param botaoClicado	Posição do botão clicado.
	 * @param espacoVazio	Posição do espaço vazio.
	 */
	public void moverBotao(Posicao botaoClicado, Posicao espacoVazio) {
		
		tabuleiro.botoes[espacoVazio.posicaoY][espacoVazio.posicaoX]
				.setText(tabuleiro.botoes[botaoClicado.posicaoY][botaoClicado.posicaoX].getText());
		
		tabuleiro.botoes[espacoVazio.posicaoY][espacoVazio.posicaoX].setVisible(true);
		tabuleiro.botoes[botaoClicado.posicaoY][botaoClicado.posicaoX].setText("0");
		tabuleiro.botoes[botaoClicado.posicaoY][botaoClicado.posicaoX].setVisible(false);
		
	}
	
	/**
	 * Testa se o usuário ganhou.
	 */
	private boolean ganhou() {
		int numeroAtual = 1;
		
		for(int indexLine = 0; indexLine < tamanho; ++indexLine) {
			for(int indexColumn = 0; indexColumn < tamanho; ++indexColumn) {
				if(numeros[indexLine][indexColumn] != numeroAtual)
					return false;
				
				if(numeroAtual == (tamanho*tamanho - 1))
					numeroAtual = 0;
				else
					++numeroAtual;
			}
		}
		
		System.out.println("[game over] WIN");
		return true;
	}

	public void actionPerformed(ActionEvent e) {
		
		int numeroBotao = Integer.parseInt(e.getActionCommand());
		System.out.println(numeroBotao);
			
		Posicao posicaoBotaoClicado = getPosicaoBotao(numeroBotao);	
		Posicao posicaoEspacoVazio = encontrarEspacoVazioAdjacente(posicaoBotaoClicado);
			
		if(posicaoEspacoVazio.posicaoX >= 0 && posicaoEspacoVazio.posicaoX >= 0) {
			numeros[posicaoEspacoVazio.posicaoY][posicaoEspacoVazio.posicaoX] = 
					numeros[posicaoBotaoClicado.posicaoY][posicaoBotaoClicado.posicaoX];
					
			numeros[posicaoBotaoClicado.posicaoY][posicaoBotaoClicado.posicaoX] = 0;
				
			moverBotao(posicaoBotaoClicado, posicaoEspacoVazio);
		}
		
		if(ganhou()) {
			JOptionPane informarVitoria = new JOptionPane();
			informarVitoria.showMessageDialog(this, "Parabéns, você ganhou!", "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle();
		puzzle.fazerJanela();
		puzzle.setVisible(true);
	}
}
