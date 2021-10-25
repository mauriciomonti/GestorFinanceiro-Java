import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class ArquivoTelegram {
	
	// Atributos

	private static BufferedReader br; // para a função que le o arquivo
	

	// Carrega os atributos do TelegramBot de acordo com os gravado no arquivo
	// .txt
	public String carregaTelegramDados() {

		try {
			String diretorio = "Banco de Dados/Telegram";

			FileInputStream arquivo = new FileInputStream(diretorio + ".txt");

			InputStreamReader input = new InputStreamReader(arquivo);
			br = new BufferedReader(input);

			String linha; // Para armazenar a linha inteira

			linha = br.readLine(); // Armazena até o /n

			input.close();
			arquivo.close();

			return linha;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Dados do Bot Telegram Inexistente, Por Favor Crie um Novo Bot", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	public void gravaTelegramDados(String username, String token) {
		try {
			String diretorio = "Banco de Dados/Telegram";

			FileOutputStream arquivo = new FileOutputStream(diretorio + ".txt");
			PrintWriter pr = new PrintWriter(arquivo);

			pr.print(username);
			pr.print(" ; " + token);
			// username ; token

			pr.close();
			arquivo.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao Gravar o Arquivo do Telegram", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
