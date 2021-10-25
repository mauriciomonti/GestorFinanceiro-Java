import javax.swing.JOptionPane;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

	private String botUsername;
	private String botToken;

	// Vai armazenar todos os pagamentos Vencidos
	private Lista listaVencidos = new Lista();

	// Vai armazenar todos os pagamentos Pendentes
	private Lista listaPendentes = new Lista();

	private SendMessage message = new SendMessage();

	// Variavel que controla a ordem do programa
	private static int indice;
	
	// Variavel que armazena o dia atual
	private String data;

	public TelegramBot(String botUsername, String botToken, Lista listaVencidos, Lista listaPendentes, String data) {
		this.botUsername = botUsername;
		this.botToken = botToken;
		this.listaVencidos = listaVencidos;
		this.listaPendentes = listaPendentes;

		indice = 0;
		this.data = data; 
	}

	public void onUpdateReceived(Update update) {

		// We check if the update has a message and the message has text
		if (update.hasMessage() && update.getMessage().hasText()) {

			if (indice == 0) {
				JOptionPane.showMessageDialog(null, botUsername + " Sincronizado com Sucesso !!!", "Informação",
						JOptionPane.INFORMATION_MESSAGE);

				// Configura o ID e o texto da mensagem
				message.setChatId(update.getMessage().getChatId());

				// Metodo para mandar mensagem
				sendMensagem(
						"Para consultar a LISTA de pagamentos VENCIDOS e PENDENTES envie para mim a seguinte Palavra 'Mostre'");

				indice++;
			} else {

				String receiveMessage = update.getMessage().getText();

				// Compara se a mensagem recebida esta certa
				if (receiveMessage.toLowerCase().compareTo("mostre".toString()) == 0 && indice == 1) {

					// Metodo para mandar mensagem
					sendMensagem("PAGAMENTOS VENCIDOS DO DIA  " + data);

					// Mostra todos os pagamentos vencidos
					mostraPagamentosVencidos();

					// Metodo para mandar mensagem
					sendMensagem("PAGAMENTOS PENDENTES DO DIA  " + data);

					// Mostra todos os pagamentos pendentes
					mostraPagamentosPendentes();

					indice++;
				} else if (receiveMessage.toLowerCase().compareTo("mostre".toString()) == 0 && indice > 1) {
					// Metodo para mandar mensagem
					sendMensagem("A Lista dos Pagamentos do Dia " + data + " Já Foi Enviada");
				} else {
					// Metodo para mandar mensagem
					sendMensagem("Comando Inválido");

				}
			}
		}
	}

	public String getBotUsername() {
		return botUsername;
	}

	public String getBotToken() {
		return botToken;
	}

	private void mostraPagamentosPendentes() {

		String mensagem;

		if (listaPendentes.getTamanhoLista() != 0) {

			for (int i = 0; i < listaPendentes.getTamanhoLista(); i++) {

				mensagem = "Pagamento '" + listaPendentes.getListaPagamento().get(i).getNome()
						+ "' vencimento na data de " + listaPendentes.getListaPagamento().get(i).getStringData()
						+ " com o valor de " + listaPendentes.getListaPagamento().get(i).getValor() + " Reais";

				// Metodo para mandar mensagem
				sendMensagem(mensagem);
			}
		} else {
			mensagem = "Nenhum Pagamento Pendente";

			// Metodo para mandar mensagem
			sendMensagem(mensagem);
		}
	}

	private void mostraPagamentosVencidos() {

		String mensagem;

		if (listaVencidos.getTamanhoLista() != 0) {

			for (int i = 0; i < listaVencidos.getTamanhoLista(); i++) {

				mensagem = "Pagamento '" + listaVencidos.getListaPagamento().get(i).getNome() + "' vencido na data de "
						+ listaVencidos.getListaPagamento().get(i).getStringData() + " com o valor de "
						+ listaVencidos.getListaPagamento().get(i).getValor() + " Reais";

				// Metodo para mandar mensagem
				sendMensagem(mensagem);
			}
		} else {
			mensagem = "Nenhum Pagamento Vencido";

			// Metodo para mandar mensagem
			sendMensagem(mensagem);

		}
	}

	public void sendMensagem(String mensagem) {
		message.setText(mensagem);
		// Tenta mandar a mensagem para o smartphone
		try {
			sendMessage(message); // Call method to send the message
		} catch (TelegramApiException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao Enviar a Mensagem", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
}
