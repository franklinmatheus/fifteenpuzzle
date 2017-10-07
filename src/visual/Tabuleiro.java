package visual;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JPanel;

import estrutural.Posicao;

public class Tabuleiro {
	
	private static final int tamanho = 4;
	
	public JButton [][] botoes;
	private JPanel panel;
	
	/**
	 * Construtor do tabuleiro.
	 */
	public Tabuleiro() {
		botoes = new JButton[tamanho][tamanho];
		panel = new JPanel(new GridLayout(tamanho, tamanho));
		inicializarTabuleiro();
		panel = preenchePanel();
	}

	/**
	 * Inicializa o tabuleiro, ou seja, define as posições iniciais dos botões.
	 */
	private void inicializarTabuleiro() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		int numeroCelulas = tamanho * tamanho;
		
		for(Integer index = 0; index < numeroCelulas; ++index)
			array.add(index);
		
		Collections.shuffle(array);
		
		for(int indexLine = 0; indexLine < tamanho; ++indexLine) {
			for(int indexColumn = 0; indexColumn < tamanho; ++indexColumn) {
				int numeroBotao = array.remove(0);
				botoes[indexLine][indexColumn] = new JButton("" + numeroBotao);
			}
		}
	}
	
	/**
	 * Preenche o panel com os botões em posições aleatórias e o retorna.
	 */
	private JPanel preenchePanel() {
		JPanel tempPanel = new JPanel(new GridLayout(tamanho, tamanho));
		
		for(int indexLine = 0; indexLine < tamanho; ++indexLine) {
			for(int indexColumn = 0; indexColumn < tamanho; ++indexColumn) {
				JButton botao = botoes[indexLine][indexColumn];
				
				if(botao.getText().equals("0"))
					botao.setVisible(false);
				
				tempPanel.add(botao);
			}
		}
		
		return tempPanel;
	}
	
	/**
	 * Retorna uma matriz de inteiros com os números dos botões.
	 */
	public int[][] getNumeros(int tamanho) {
		
		int [][] numeros = new int[tamanho][tamanho];
		
		for(int indexLine = 0; indexLine < tamanho; ++indexLine) {
			for(int indexColumn = 0; indexColumn < tamanho; ++indexColumn) {
				numeros[indexLine][indexColumn] = Integer.parseInt(botoes[indexLine][indexColumn].getText());
			}
		}
		
		return numeros;
	}
	
	/**
	 * Retorna o painel com todos os botões.
	 */
	public JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Retorna o tamanho do tabuleiro.
	 */
	public int getTamanho() {
		return tamanho;
	}
}
