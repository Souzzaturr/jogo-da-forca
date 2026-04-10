import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class JogoDaForca {
	private ArrayList<String> historico = new ArrayList<>();
	private ArrayList<String> listaDePalavras = new ArrayList<>();
	private ArrayList<Integer> listaDeOcorrencias = new ArrayList<>();
	private ArrayList<String> letrasTentadas = new ArrayList<>();
	private String palavra = "";
	private String dica = "";
	private int acertos = 0;
	private int penalidade = 0;
	private boolean gravouHistorico = false;
	
	
	public JogoDaForca() {
		InputStream stream = this.getClass().getResourceAsStream("/dados/palavras.txt");
		if (stream == null) {
			JOptionPane.showMessageDialog(null, "Arquivo de palavras inexistente!");
			System.exit(0);
		}
		Scanner arquivo = new Scanner(stream);
		String linha = "";
		while (arquivo.hasNext()) {
			linha = arquivo.nextLine();
			
			if (!linha.trim().isEmpty()) {
        		this.listaDePalavras.add(linha);
    		}
		}
		
		arquivo.close();	
	}
	
	public void iniciar() {
		int tamanho = this.listaDePalavras.size();
		Random sorteio = new Random();
		int sorteioIndex = sorteio.nextInt(tamanho);
		this.palavra = this.listaDePalavras.get(sorteioIndex).split(";")[0].trim().toUpperCase();
		this.dica = this.listaDePalavras.get(sorteioIndex).split(";")[1].trim();
		this.acertos = 0;
		this.penalidade = 0;
		this.listaDeOcorrencias.clear();
		this.letrasTentadas.clear();
		this.gravouHistorico = false;
	}
	
	public String getDica() {
		return dica;
	}
	
	public String getPalavra() {
		ArrayList<String> letras = new ArrayList<>();
		for (int i=0; i < this.palavra.length(); ++i) letras.add("*");

		for (int i=0; i < this.listaDeOcorrencias.size(); ++i) {
			int posicao = listaDeOcorrencias.get(i);
			letras.set(posicao, String.valueOf(palavra.charAt(posicao)));
		}

		String palavra = String.join("", letras);
	
		return palavra;
	}
	
	public ArrayList<String> getResultados() {
		return this.historico;
	}
	
	public ArrayList<Integer> getOcorrencias(String letra) throws Exception {
		letra = letra.toUpperCase();
		
		if (letra.length() != 1 || !letra.matches("[A-Z]")) {
			throw new Exception("Valor inválido! Digite apenas uma letra de A a Z.");
		}

		if (letrasTentadas.contains(letra)) {
			throw new Exception("Letra " + letra.toUpperCase() + " já foi tentada");
		}

		ArrayList<Integer> ocorrencias = new ArrayList<>();
		String[] letras = this.palavra.split("");
		boolean errou = true;

		for (int i=0; i < letras.length; ++i) {
			if (letras[i].equalsIgnoreCase(letra)) {
				ocorrencias.add(i+1);
				this.listaDeOcorrencias.add(i);
				acertos++;
				errou = false;
			}
		}

		this.letrasTentadas.add(letra);
		Collections.sort(this.listaDeOcorrencias);
		
		if (errou) {
			this.penalidade++;
			this.historico.add(letra + " - Errou");

		} else {
			this.historico.add(letra + " - Acertou");
		}
		
		return ocorrencias;
	}
	
	public boolean terminou() {
		if (this.acertos == this.palavra.length() || this.penalidade == 6) 
			return true;
		return false;
	}

	public int getAcertos() {
		return this.acertos;
	}
	
	public int getCodigoPenalidade() {
		return this.penalidade;
	}
	
	public String getNomePenalidade() {
		switch (this.penalidade) {
		case 1: return "Perdeu a primeira perna"; 
		case 2: return "Perdeu a segunda perna"; 
		case 3: return "Perdeu o primeiro braço";
		case 4: return "Perdeu o segundo braço"; 
		case 5: return "Perdeu o tronco"; 
		case 6: return "Perdeu a cabeça";  
		default: return "Sem penalidades";
		}
	}
	
	public String getResultado() {
		if (this.acertos == this.palavra.length()) {
			if (!this.gravouHistorico) {
				this.historico.add(this.palavra + " - venceu");
				this.gravouHistorico = true;
			}
			return "venceu";
			
		} else if (this.penalidade == 6) {
			if (!this.gravouHistorico) {
				this.historico.add(this.palavra + " - perdeu");
				this.gravouHistorico = true;
			}
			return "perdeu";
		}
		return "em andamento";
	}
	
}
