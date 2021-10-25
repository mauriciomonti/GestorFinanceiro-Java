import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

public class ArquivoAcesso {

	// Atributos

	private static BufferedReader br; // para a função que le o arquivo

	// Dois \\ por causa do windows
	private static String diretorio = System.getProperty("user.home") + "//Documents//MauricioStudios/";

	// Metodos

	public int carregaSomatorioLinha(String nomeArquivo, int linhaEscolida) {

		int valorAdquirido = 0;

		try {
			FileInputStream arquivo = new FileInputStream(diretorio + nomeArquivo + ".txt");

			InputStreamReader input = new InputStreamReader(arquivo);
			br = new BufferedReader(input);

			String linha; // Para armazenar a linha inteira

			int i = 0;
			// Vai ate a linha escolida
			do {
				linha = br.readLine(); // Armazena até o /n
				if (linha != null) {
					String[] palavras = linha.split(";"); // Separa cada dado da
															// linha pelo ; na
															// string palavra

					for (int j = 0; j < palavras.length; j++) {
						palavras[j] = palavras[j].trim(); // Vai tirando os
															// espaços antes e
															// depois da string
					}

					if (i == linhaEscolida) {
						for (int k = 0; k < palavras.length; k++) {
							valorAdquirido += Integer.parseInt(palavras[k].toString());
						}
					}

				}
				i++;
			} while (linha != null);

			input.close();
			arquivo.close();
			return valorAdquirido;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valorAdquirido;
	}

	public void gravaArquivo(String nomeArquivo, String[] dadosArquivo) {
		try {

			// Verifica se a pasta existe, se não existir cria uma nova
			if (verificaArquivo(nomeArquivo) == false) {
				File data = new File(diretorio);
				data.mkdirs();
			}

			FileOutputStream arquivo = new FileOutputStream(diretorio + nomeArquivo + ".txt");
			PrintWriter pr = new PrintWriter(arquivo);

			// Grava os arquivos
			for (int i = 0; i < 40; i++) {
				pr.println(dadosArquivo[i]);
			}
			pr.close();
			arquivo.close();

			// Oculta o arquivo
			String cmd1[] = { "attrib", "+h", diretorio + nomeArquivo + ".txt" };
			Runtime.getRuntime().exec(cmd1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gravaArquivoFake(String nomeArquivo) {
		try {

			Random gerador = new Random();

			// Verifica se a pasta existe, se não existir cria uma nova
			if (verificaArquivo(nomeArquivo) == false) {
				File data = new File(diretorio);
				data.mkdirs();
			}

			FileOutputStream arquivo = new FileOutputStream(diretorio + nomeArquivo + ".txt");
			PrintWriter pr = new PrintWriter(arquivo);

			// Grava acessos randomicos
			for (int i = 0; i < 40; i++) {

				for (int j = 0; j < 60 - 1; j++) {
					pr.print(gerador.nextInt(2) + ";");
				}
				pr.println(gerador.nextInt(2) + ";");
				// Banrisul ; 135.23 ; 22/10/2016 ; true
			}
			pr.close();
			arquivo.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verificaArquivo(String nomeArquivo) {
		// Criado para verifica se o arquivo existe
		File data = new File(diretorio + nomeArquivo + ".txt");

		// Se o arquivo não existe
		if (!data.exists()) {
			return false;
		} else {
			return true;
		}
	}
}
