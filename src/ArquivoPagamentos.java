import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ArquivoPagamentos {

	// Atributos

	private static BufferedReader br; // para a função que le o arquivo

	// Metodos

	public ArrayList<Pagamentos> carregaArquivo(String mes, int ano) {

		// Criando um ArrayList dos pagamentos
		ArrayList<Pagamentos> listaPagamento = new ArrayList<Pagamentos>();

		try {
			String diretorio = "Banco de Dados/" + ano + "/";

			FileInputStream arquivo = new FileInputStream(diretorio + mes + ".txt");

			InputStreamReader input = new InputStreamReader(arquivo);
			br = new BufferedReader(input);

			String linha; // Para armazenar a linha inteira

			do {
				linha = br.readLine(); // Armazena até o /n
				if (linha != null) {
					String[] palavras = linha.split(";"); // Separa cada dado da
															// linha pelo ; na
															// string palavra

					for (int i = 0; i < palavras.length; i++) {
						palavras[i] = palavras[i].trim(); // Vai tirando os
															// espaços antes e
															// depois da string
					}

					// Adiciona os dados adquiridos na classe pagamento
					Pagamentos pagamento = new Pagamentos(palavras[0], Double.parseDouble(palavras[1]), palavras[2],
							Boolean.parseBoolean(palavras[3]));

					// Adiciona o pagamento na lista
					listaPagamento.add(pagamento);

				}
			} while (linha != null);

			input.close();
			arquivo.close();
			return listaPagamento;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao Carregar o Arquivo do Mes/Ano", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		JOptionPane.showMessageDialog(null, "Erro ao Carregar o Arquivo do Mes/Ano", "Erro", JOptionPane.ERROR_MESSAGE);
		return listaPagamento;
	}

	public void gravaArquivo(ArrayList<Pagamentos> listaPagamento, String mes, int ano) {
		try {
			String diretorio = "Banco de Dados/" + ano + "/";

			// Verifica se a pasta ano existe, se não existir cria uma nova
			if (verificaArquivo(mes, ano) == false) {
				File data = new File(diretorio);
				data.mkdirs();
			}

			FileOutputStream arquivo = new FileOutputStream(diretorio + mes + ".txt");
			PrintWriter pr = new PrintWriter(arquivo);

			for (int i = 0; i < listaPagamento.size(); i++) {
				pr.print(listaPagamento.get(i).getNome());
				pr.print(" ; " + listaPagamento.get(i).getValor());
				pr.print(" ; " + listaPagamento.get(i).getStringData());
				pr.println(" ; " + listaPagamento.get(i).getEstado());
				// Banrisul ; 135.23 ; 22/10/2016 ; true
			}
			pr.close();
			arquivo.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao Gravar o Arquivo do Mes/Ano", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean verificaArquivo(String mes, int ano) {
		String diretorio = "Banco de Dados/" + ano + "/";

		// Criado para verifica se o arquivo existe
		File data = new File(diretorio + mes + ".txt");

		// Se o arquivo não existe
		if (!data.exists()) {
			return false;
		} else {
			return true;
		}
	}
}
