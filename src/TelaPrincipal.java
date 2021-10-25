import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.Vector;
import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class TelaPrincipal extends JFrame {

	private JPanel mainPanel;
	private JPanel namePanel;
	private JPanel buttonPanel;
	private JTable table;
	private JScrollPane scrollPane;

	// Cria a lista
	private static Lista minhaLista = new Lista();

	// Cria classe data (que pega o mes e o ano atual)
	private static Datas data = new Datas();

	/**
	 * Main
	 **/
	public static void main(String[] args) {

		/*
		 * JOptionPane.showMessageDialog(null,
		 * "Depois de Editar algum Dado na Tabela Pressione 'ENTER' para Confirmar"
		 * , "Aviso para Edição da Tabela", JOptionPane.WARNING_MESSAGE);
		 */

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Tela Principal
	 */
	public TelaPrincipal() {

		// Verifica se o usuario tem o direito de usar o programa pela senha
		verificaAcesso();

		// Salva tudo quando fecha a tabela
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				gravaLista();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(TelaPrincipal.class.getResource("/windowBuilder/images/Lion-48.png")));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Programa para Controle de Despesas");
		setBounds(100, 100, 960, 590);
		setLocationRelativeTo(null); // Tela no meio
		mainPanel = new JPanel();

		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));

		namePanel = new JPanel();
		mainPanel.add(namePanel, BorderLayout.SOUTH);

		JLabel lblMaurcioOliveiraMonti = new JLabel("Maur\u00EDcio Oliveira Monti");
		lblMaurcioOliveiraMonti.setFont(new Font("Harlow Solid Italic", Font.ITALIC, 12));

		JLabel lblMauricioomontigmailcom = new JLabel("mauricioomonti@gmail.com");
		lblMauricioomontigmailcom.setFont(new Font("Harlow Solid Italic", Font.ITALIC, 12));
		GroupLayout gl_namePanel = new GroupLayout(namePanel);
		gl_namePanel.setHorizontalGroup(gl_namePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_namePanel.createSequentialGroup().addComponent(lblMaurcioOliveiraMonti)
						.addPreferredGap(ComponentPlacement.RELATED, 683, Short.MAX_VALUE)
						.addComponent(lblMauricioomontigmailcom)));
		gl_namePanel.setVerticalGroup(gl_namePanel.createParallelGroup(Alignment.TRAILING).addGroup(
				gl_namePanel.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_namePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMaurcioOliveiraMonti).addComponent(lblMauricioomontigmailcom))));
		namePanel.setLayout(gl_namePanel);

		buttonPanel();

		tabelaPagamentos();
	}

	/**
	 * Painel dos botões
	 */
	private void buttonPanel() {
		buttonPanel = new JPanel();

		mainPanel.add(buttonPanel, BorderLayout.WEST);

		JButton btnAdicionarNovoPagamento = new JButton("Adicionar Novo Pagamento");
		btnAdicionarNovoPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				adicionaLinha();

			}
		});
		btnAdicionarNovoPagamento.setIcon(
				new ImageIcon(TelaPrincipal.class.getResource("/windowBuilder/images/Stork With Bundle-48.png")));

		JButton btnRemoverPagamentosSelecionados = new JButton("Remover Pagamento Selecionado");
		btnRemoverPagamentosSelecionados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				deletaLinhaSelecionada();
			}
		});
		btnRemoverPagamentosSelecionados.setIcon(new ImageIcon(
				TelaPrincipal.class.getResource("/windowBuilder/images/Flying Stork With Bundle-48.png")));

		JLabel lblTotalFaltante = new JLabel("Total Faltante:");
		lblTotalFaltante.setForeground(new Color(0, 51, 0));
		lblTotalFaltante.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel lblTotalPago = new JLabel("Total Pago:");
		lblTotalPago.setForeground(new Color(0, 51, 0));
		lblTotalPago.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel lblValorTotalPago = new JLabel("VALOR");
		lblValorTotalPago.setForeground(new Color(51, 204, 51));
		lblValorTotalPago.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel lblValorTotalFaltante = new JLabel("VALOR");
		lblValorTotalFaltante.setForeground(new Color(51, 204, 51));
		lblValorTotalFaltante.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel lblMesAtual = new JLabel("M\u00EAs Atual:");
		lblMesAtual.setForeground(new Color(0, 0, 102));
		lblMesAtual.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel lblValorMesAtual = new JLabel("VALOR");
		lblValorMesAtual.setForeground(new Color(0, 0, 255));
		lblValorMesAtual.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel lblAnoAtual = new JLabel("Ano Atual:");
		lblAnoAtual.setForeground(new Color(0, 0, 102));
		lblAnoAtual.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel lblValorAnoAtual = new JLabel("VALOR");
		lblValorAnoAtual.setForeground(Color.BLUE);
		lblValorAnoAtual.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel lblValorHorarioAtual = new JLabel("VALOR");
		lblValorHorarioAtual.setForeground(new Color(186, 85, 211));
		lblValorHorarioAtual.setFont(new Font("Arial", Font.BOLD, 18));

		JButton btnVisualizarPagamentosAtrasados = new JButton("Visualizar Pagamentos Atrasados");
		btnVisualizarPagamentosAtrasados
				.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/windowBuilder/images/Turtle-48.png")));
		btnVisualizarPagamentosAtrasados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Mostra os pagamentos atrasados
				mostraDatasVencidas();
			}
		});

		// Atualiza inicialmente o buttonPanel
		buttonPanel.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				// Atualiza o Valor do Mês Atual
				lblValorMesAtual.setText(data.getMes());

				// Atualiza o Valor do Ano Atual
				lblValorAnoAtual.setText(Integer.toString(data.getAno()));

				// Atualiza o Valor Total Pago Atual
				lblValorTotalPago.setText(refreshLebel(1));

				// Atualiza o Valor Total Pago Faltante
				lblValorTotalFaltante.setText(refreshLebel(-1));

				// Atualiza com a hora atual
				lblValorHorarioAtual.setText(data.tempoAtual());

				// Verifica se ainda tem algum pagamento atrasado
				// Deixa oculto o botão de pagamentos se não tiver mais nenhuma
				// data vencida
				btnVisualizarPagamentosAtrasados.setVisible(verificaDatasVencidas());
			}
		});

		JButton btnAtualizar = new JButton("ATUALIZAR");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gravaLista();

				// Atualiza o Valor do Mês Atual
				lblValorMesAtual.setText(data.getMes());

				// Atualiza o Valor do Ano Atual
				lblValorAnoAtual.setText(Integer.toString(data.getAno()));

				// Atualiza o Valor Total Pago Atual
				lblValorTotalPago.setText(refreshLebel(1));

				// Atualiza o Valor Total Pago Faltante
				lblValorTotalFaltante.setText(refreshLebel(-1));

				// Verifica se ainda tem algum pagamento atrasado
				// Deixa oculto o botão de pagamentos se não tiver mais nenhuma
				// data vencida
				btnVisualizarPagamentosAtrasados.setVisible(verificaDatasVencidas());
			}
		});

		btnAtualizar.setFont(new Font("Jokerman", Font.PLAIN, 31));

		JButton btnMesPosterior = new JButton("M\u00EAs Posterior");
		btnMesPosterior.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/windowBuilder/images/Right-24.png")));
		btnMesPosterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gravaLista();

				carregaTabela(carregaMes(1));

				// Atualiza o Valor do Mês Atual
				lblValorMesAtual.setText(data.getMes());

				// Atualiza o Valor do Ano Atual
				lblValorAnoAtual.setText(Integer.toString(data.getAno()));

				// Atualiza o Valor Total Pago Atual
				lblValorTotalPago.setText(refreshLebel(1));

				// Atualiza o Valor Total Pago Faltante
				lblValorTotalFaltante.setText(refreshLebel(-1));

				// Verifica se ainda tem algum pagamento atrasado
				// Deixa oculto o botão de pagamentos se não tiver mais nenhuma
				// data vencida
				btnVisualizarPagamentosAtrasados.setVisible(verificaDatasVencidas());
			}
		});

		JButton btnMesAnterior = new JButton("M\u00EAs Anterior");
		btnMesAnterior.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/windowBuilder/images/Left-24.png")));
		btnMesAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gravaLista();

				carregaTabela(carregaMes(-1));

				// Atualiza o Valor do Mês Atual
				lblValorMesAtual.setText(data.getMes());

				// Atualiza o Valor do Ano Atual
				lblValorAnoAtual.setText(Integer.toString(data.getAno()));

				// Atualiza o Valor Total Pago Atual
				lblValorTotalPago.setText(refreshLebel(1));

				// Atualiza o Valor Total Pago Faltante
				lblValorTotalFaltante.setText(refreshLebel(-1));

				// Verifica se ainda tem algum pagamento atrasado
				// Deixa oculto o botão de pagamentos se não tiver mais nenhuma
				// data vencida
				btnVisualizarPagamentosAtrasados.setVisible(verificaDatasVencidas());
			}
		});

		JLabel lblHorarioAtual = new JLabel("Hor\u00E1rio Real:");
		lblHorarioAtual.setForeground(new Color(186, 85, 211));
		lblHorarioAtual.setFont(new Font("Arial", Font.BOLD, 18));

		// Configura a comunicação do Telegram
		JButton btnTelegram = new JButton("");
		btnTelegram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Cria uma dependencia com a classe arquivo
				// para carregar os dados do bot
				ArquivoTelegram arqTelegram = new ArquivoTelegram();

				String username = "";
				String token = "";

				JOptionPane.showMessageDialog(null,
						"Você Iniciou o Envio dos Pagamentos Vencidos e Pendentes pelo Telegram para o Smartphone",
						"Informação", JOptionPane.INFORMATION_MESSAGE);

				int answer;
				answer = JOptionPane.showConfirmDialog(null, "Deseja Criar um Novo Bot ?", "Aviso",
						JOptionPane.YES_NO_OPTION);
				// Indice resposta
				// -1 = Fechado
				// 0 = Sim
				// 1 = Não

				if (answer == 0) {
					username = JOptionPane.showInputDialog("Digite o Username do Bot");
					token = JOptionPane.showInputDialog("Digite o Token do Bot");

					if (username == null || token == null) {
						answer = 1;
					} else {
						// Grava os novos dados no arquivo Telegram
						arqTelegram.gravaTelegramDados(username, token);
					}

				}
				if (answer == 1) {

					// Puxa os dados do arquivo Telegram
					// Separa cada dado da linha pelo ; na string palavra
					String[] palavras = arqTelegram.carregaTelegramDados().split(";");

					for (int i = 0; i < palavras.length; i++) {
						palavras[i] = palavras[i].trim(); // Vai tirando os
						// espaços antes e
						// depois da string
					}

					username = palavras[0];
					token = palavras[1];
				}

				// Se fechar a aba não faz nada
				if (answer != -1) {
					JOptionPane.showMessageDialog(null,
							"Pressione OK e aguarde 10 segundos. Depois ENVIE uma MENSAGEM para o bot e AGUARDE a CONFIRMAÇÃO do recebimento",
							"Informação", JOptionPane.INFORMATION_MESSAGE);

					// Cria o bot com os dados obtidos ou gravados
					inicializaBot(username, token);
				}
			}
		});

		btnTelegram.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/windowBuilder/images/Telegram-icon.png")));

		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_buttonPanel
				.createSequentialGroup()
				.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnAdicionarNovoPagamento, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
						.addGroup(gl_buttonPanel.createSequentialGroup()
								.addComponent(btnAtualizar, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnTelegram, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_buttonPanel.createSequentialGroup().addGap(21).addGroup(gl_buttonPanel
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_buttonPanel.createSequentialGroup()
										.addComponent(lblTotalPago, GroupLayout.PREFERRED_SIZE, 125,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblValorTotalPago,
												GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_buttonPanel.createSequentialGroup().addComponent(lblTotalFaltante)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(lblValorTotalFaltante, GroupLayout.PREFERRED_SIZE, 125,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_buttonPanel.createSequentialGroup()
										.addComponent(lblAnoAtual, GroupLayout.PREFERRED_SIZE, 125,
												GroupLayout.PREFERRED_SIZE)
										.addGap(12).addComponent(lblValorAnoAtual, GroupLayout.PREFERRED_SIZE, 125,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_buttonPanel.createSequentialGroup()
										.addComponent(lblMesAtual, GroupLayout.PREFERRED_SIZE, 125,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblValorMesAtual,
												GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_buttonPanel.createSequentialGroup()
										.addComponent(lblHorarioAtual, GroupLayout.PREFERRED_SIZE, 125,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(lblValorHorarioAtual, GroupLayout.PREFERRED_SIZE, 125,
												GroupLayout.PREFERRED_SIZE))))
						.addGroup(Alignment.LEADING,
								gl_buttonPanel.createSequentialGroup()
										.addComponent(btnMesAnterior, GroupLayout.PREFERRED_SIZE, 145,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnMesPosterior, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
						.addComponent(btnRemoverPagamentosSelecionados, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
						.addComponent(btnVisualizarPagamentosAtrasados, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
				.addContainerGap()));
		gl_buttonPanel.setVerticalGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_buttonPanel
				.createSequentialGroup().addComponent(btnAdicionarNovoPagamento)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnRemoverPagamentosSelecionados)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE).addComponent(btnMesAnterior)
						.addComponent(btnMesPosterior))
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnVisualizarPagamentosAtrasados)
				.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
				.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(lblHorarioAtual, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblValorHorarioAtual, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAnoAtual, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblValorAnoAtual, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMesAtual, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblValorMesAtual, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalPago, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblValorTotalPago, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE, false).addComponent(lblTotalFaltante)
						.addComponent(lblValorTotalFaltante, GroupLayout.PREFERRED_SIZE, 22,
								GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnTelegram, 0, 0, Short.MAX_VALUE).addComponent(btnAtualizar,
								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));
		buttonPanel.setLayout(gl_buttonPanel);

		// Executa a função sempre de acordo com o delay no timer
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent clockRefresh) {
				// Compara se o minuto real é diferente do armazenado
				if (data.minutoAtual() != data.getMinuto()) {
					data.setMinuto(data.minutoAtual());

					// Atualiza com a hora atual
					lblValorHorarioAtual.setText(data.tempoAtual());
				}
			}
		};
		new Timer(1000, taskPerformer).start(); // 1000 = 1 seg
	}

	/**
	 * Tabela:
	 */
	private void tabelaPagamentos() {
		scrollPane = new JScrollPane();

		// Le o mouse
		scrollPane.addMouseListener(new MouseAdapter() {
			// Le o click do mouse
			public void mouseClicked(MouseEvent e) {
				// Se alguma celula esta sendo editada deseleciona ao clicar
				// fora
				if (table.isEditing()) {
					table.getCellEditor().stopCellEditing();
				}
			}

			// Le a saida do mouse
			public void mouseExited(MouseEvent e) {
				// Se alguma celula esta sendo editada deseleciona ao clicar
				// fora
				if (table.isEditing()) {
					table.getCellEditor().stopCellEditing();
				}
			}
		});

		mainPanel.add(scrollPane, BorderLayout.CENTER);
		table = new JTable();
		table.setFont(new Font("Arial", Font.BOLD, 16));

		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		table.setForeground(new Color(0, 128, 0));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Nome", "Valor", "Data", "Situa\u00E7\u00E3o" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, Boolean.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(15);

		// Centraliza todas as linhas da tabela menos a ultima (3 colunas)
		for (int i = 0; i < table.getColumnCount() - 1; i++) {
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		// Atualiza todas as linhas de acordo com o conteudo da "minhaLista"
		carregaTabela(carregaMes(0));
	}

	// Salva e Atualiza as modificacoes atuais
	private void gravaLista() {
		// Remove todos os elementos da lista
		minhaLista.limpaLista();

		// Recebe o valor de cada celula e grava no objeto pagamento para
		// adicionar no objeto lista posteriormente
		for (int linha = 0; linha < table.getRowCount(); linha++) {
			// Cria uma classe pagamento para armazenar os dados e adicionar na
			// classe lista
			Pagamentos pagamento = new Pagamentos();
			for (int coluna = 0; coluna < table.getColumnCount(); coluna++) {

				switch (coluna) {
				case 0: // Nome
					// Se o valor não existe substitui por um valido para
					// gravação
					if (table.getModel().getValueAt(linha, coluna) == null) {
						pagamento.setNome("");
					} else {
						pagamento.setNome(table.getModel().getValueAt(linha, coluna).toString().trim());
					}

					break;
				case 1: // Valor
					// Se o valor não existe substitui por um valido para
					// gravação
					if (table.getModel().getValueAt(linha, coluna) == null
							|| table.getModel().getValueAt(linha, coluna).toString().trim() == "") {
						pagamento.setValor(0.0);
					} else {
						String valor;
						valor = table.getModel().getValueAt(linha, coluna).toString().trim();

						// Tira o R$
						if (valor.contains("R")) {

							valor = valor.substring(3);
						}

						// Tira os , e transforma em .
						if (valor.contains(",")) {

							valor = valor.replaceAll(",", ".");
						}

						// Verifica se contem mais de um .
						int contador = 0;
						for (int i = valor.length() - 1; i > 0; i--) {
							// Se achou um .
							if (valor.charAt(i) == '.') {
								if (contador >= 1) {
									// Deleta os seguintes .
									// E reorganiza a string
									valor = valor.substring(0, i) + valor.substring(i + 1, valor.length());
								}
								contador++;
							}
						}

						pagamento.setValor(Double.parseDouble(valor));
					}
					break;
				case 2: // Data
					// Se o valor não existe substitui por um valido para
					// gravação
					if (table.getModel().getValueAt(linha, coluna) == null || data
							.verificaData(table.getModel().getValueAt(linha, coluna).toString().trim()) == false) {
						pagamento.setStringData("00/00/0000");
					} else {
						pagamento.setStringData(table.getModel().getValueAt(linha, coluna).toString().trim());
					}
					break;
				case 3: // Estado
					// Se o valor não existe substitui por um valido para
					// gravação
					if (table.getModel().getValueAt(linha, coluna) == null) {
						pagamento.setEstado(false);
					} else {
						pagamento.setEstado(
								Boolean.parseBoolean(table.getModel().getValueAt(linha, coluna).toString().trim()));
					}
					break;
				}
			}
			minhaLista.newPagamento(pagamento);
		}

		minhaLista.salvaLista(data.getMes(), data.getAno());
		carregaTabela(minhaLista);
		// JOptionPane.showMessageDialog(null, "Salvo e Atualizado !!!",
		// "Informação", JOptionPane.INFORMATION_MESSAGE);
	}

	private void deletaLinhaSelecionada() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "Por Favor Selecione Uma Linha", "Aviso", JOptionPane.WARNING_MESSAGE);
		} else {
			// Remove linha selecionada no momento
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.removeRow(table.getSelectedRow());
		}
	}

	private void adicionaLinha() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addRow(new Object[] { null, null });
	}

	// Função que controla o fluxo para a troca de mês
	private Lista carregaMes(int indice) {
		// Indices
		// 0 mes inicial do programa
		// 1 vai para o mes posterior
		// -1 vai para o mes anterior

		// Armazena o mes antes do processo
		int oldMes = data.getNumMes();
		int oldAno = data.getAno();

		if (indice == 1) {
			// Verifica se esta perto do final do ano
			if (data.getNumMes() < 12) {
				// Vai para o mes posterior
				data.setMes(data.getNumMes() + 1);
			} else {
				// Vai para o ano posterior
				data.setAno(data.getAno() + 1);
				data.setMes(1);
			}
		} else if (indice == -1) {
			// Verifica se esta perto do inicio do ano
			// Os meses começa a partir de 1 (Janeiro)
			if (data.getNumMes() > 1) {
				// Vai para o mes anterior
				data.setMes(data.getNumMes() - 1);
			} else {
				// Vai para o ano anterior
				data.setAno(data.getAno() - 1);
				data.setMes(12);
			}
		} else if (indice != 0) {
			JOptionPane.showMessageDialog(null, "Erro no Indice da Função carregarMes", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}

		// Verifica se o arquivo da data solicitada existe
		if (minhaLista.verificaArquivoData(data.getMes(), data.getAno()) == false) {
			int answer = JOptionPane.showConfirmDialog(null, "Mês não encontrado, Deseja criar um novo?", "Aviso",
					JOptionPane.YES_NO_OPTION);
			if (answer == 0) {
				JOptionPane.showMessageDialog(null, "Criado o mês de " + data.getMes() + " de " + data.getAno(),
						"Informação", JOptionPane.INFORMATION_MESSAGE);

				// Limpa a lista
				minhaLista.limpaLista();
				// Cria um novo arquivo
				minhaLista.salvaLista(data.getMes(), data.getAno());
			} else {
				// Verifica se esta no inicio do programa
				if (indice == 0) {
					// Verifica se esta perto do inicio do ano
					// Os meses começa a partir de 1 (Janeiro)
					if (data.getNumMes() > 1) {
						// Vai para o mes anterior
						data.setMes(data.getNumMes() - 1);
					} else {
						// Vai para o ano anterior
						data.setAno(data.getAno() - 1);
						data.setMes(12);
					}
				} else {
					// Volta para o mes em que estava e não faz nada
					data.setMes(oldMes);
					data.setAno(oldAno);
				}
				JOptionPane.showMessageDialog(null, "Voltou para o mês de " + data.getMes() + " de " + data.getAno(),
						"Informação", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		// Carrega a lista do mes
		minhaLista.carregaLista(data.getMes(), data.getAno());
		return minhaLista;
	}

	// Usado por varios metodos para limpar e carregar a lista com novos dados
	private void carregaTabela(Lista lista) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}

		// Aumenta a altura de cada celula
		table.setRowHeight(40); // altura 40 pixels

		// Armazena o valor da Data antes de ir para tabela
		String dataString;

		for (int i = 0; i < lista.getTamanhoLista(); i++) {
			@SuppressWarnings("rawtypes")
			Vector<Comparable> linha = new Vector<Comparable>();
			linha.add(lista.getListaPagamento().get(i).getNome());

			if (lista.getListaPagamento().get(i).getValor() == 0) {
				// Para adicionar o cifrão nos valores
				linha.add("");
			} else {
				// Para adicionar o cifrão nos valores
				linha.add("R$ " + lista.getListaPagamento().get(i).getValor());
			}

			// Se for 00/00/0000 = "30/11/0002" = Ano(0002) -> Manda espaço em
			// branco para tabela
			if (data.retornaAno(lista.getListaPagamento().get(i).getStringData()) == 2) {
				dataString = "";
			} else {
				dataString = lista.getListaPagamento().get(i).getStringData();
			}
			linha.add(dataString);

			linha.add(lista.getListaPagamento().get(i).getEstado());

			model.addRow(linha);

		}
	}

	// Atualiza o label 1: Valor total pago; label -1: Valor total faltante
	private String refreshLebel(int indice) {
		// Indices
		// 1 Valor total pago
		// -1 Valor total faltante

		double total = 0;
		String valor = "";

		for (int linha = 0; linha < table.getRowCount(); linha++) {
			// Verifica se é nulo ( "" )
			if (table.getModel().getValueAt(linha, 1) == null
					|| table.getModel().getValueAt(linha, 1).toString().trim() == "") {
				valor = "0";
			} else {
				// Tira o R$
				valor = table.getModel().getValueAt(linha, 1).toString().trim();
				valor = valor.substring(3);
			}

			if (Boolean.parseBoolean(table.getModel().getValueAt(linha, 3).toString().trim()) == true && indice == 1) {
				total += Double.parseDouble(valor);
			} else if (Boolean.parseBoolean(table.getModel().getValueAt(linha, 3).toString().trim()) == false
					&& indice == -1) {
				total += Double.parseDouble(valor);
			} else if (indice != 1 && indice != -1) {
				JOptionPane.showMessageDialog(null, "Erro ao Calcular os Valores Total Pago e Total Faltante", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		// Diminuir as casas decimais
		DecimalFormat df = new DecimalFormat("###,##0.00");
		return "R$ " + df.format(total);
	}

	// Função que mostra os pagamentos que venceram
	private void mostraDatasVencidas() {
		// Controla até onde pode verificar os arquivos
		int verificar = 1;

		int oldMes = data.getNumMes();
		int oldAno = data.getAno();

		// VAI PARA A DATA DEPOIS DO ULTIMO ARQUIVO
		while (minhaLista.verificaArquivoData(data.getMes(), data.getAno()) == true) {
			if (data.getNumMes() < 12) {
				// Vai para o mes posterior
				data.setMes(data.getNumMes() + 1);
			} else {
				// Vai para o ano posterior
				data.setAno(data.getAno() + 1);
				data.setMes(1);
			}
		}

		// VOLTA PARA DATA ANTERIOR
		if (data.getNumMes() > 1) {
			// Vai para o mes anterior
			data.setMes(data.getNumMes() - 1);
		} else {
			// Vai para o ano anterior
			data.setAno(data.getAno() - 1);
			data.setMes(12);
		}

		while (verificar > 0) {
			// Inicio da validação (Se o dia da Lista for menor ou igual ao dia
			// atual + "intervaloProximoDia")
			minhaLista.carregaLista(data.getMes(), data.getAno());

			for (int i = 0; i < minhaLista.getTamanhoLista(); i++) {
				// Verifica se pagou a conta
				if (minhaLista.getListaPagamento().get(i).getEstado() == false) {
					// Se for 00/00/0000 = "30/11/0002" = Ano(0002) -> Não
					// considera porque é uma data em branca
					if (data.retornaAno(minhaLista.getListaPagamento().get(i).getStringData()) != 2) {
						// Verifica se a data da conta já venceu
						if (minhaLista.getListaPagamento().get(i).getData().before(data.getDataAtual()) == true) {
							JOptionPane.showMessageDialog(null,
									"Nome: " + minhaLista.getListaPagamento().get(i).getNome() + ", Valor: R$ "
											+ minhaLista.getListaPagamento().get(i).getValor() + ", Data: "
											+ minhaLista.getListaPagamento().get(i).getStringData(),
									"Aviso Pagamento Vencido", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}

			// Se for o inicio do ano volta para o ano anterior
			if (data.getNumMes() == 1) {
				data.setAno(data.getAno() - 1);
				data.setMes(12);
			} else {
				data.setMes(data.getNumMes() - 1);
			}

			// Se o arquivo não existir termina a varredura
			if (minhaLista.verificaArquivoData(data.getMes(), data.getAno()) == false) {
				verificar = 0;
			}
		}

		data.setMes(oldMes);
		data.setAno(oldAno);
	}

	// Verifica se possui alguma data anterior vencida
	private boolean verificaDatasVencidas() {
		// Indice
		// false = Não possui data anterior vencida
		// true = Possui data anterior vencida

		int oldMes = data.getNumMes();
		int oldAno = data.getAno();

		// VAI PARA A DATA DEPOIS DO ULTIMO ARQUIVO
		while (minhaLista.verificaArquivoData(data.getMes(), data.getAno()) == true) {
			if (data.getNumMes() < 12) {
				// Vai para o mes posterior
				data.setMes(data.getNumMes() + 1);
			} else {
				// Vai para o ano posterior
				data.setAno(data.getAno() + 1);
				data.setMes(1);
			}
		}

		// VOLTA PARA DATA ANTERIOR
		if (data.getNumMes() > 1) {
			// Vai para o mes anterior
			data.setMes(data.getNumMes() - 1);
		} else {
			// Vai para o ano anterior
			data.setAno(data.getAno() - 1);
			data.setMes(12);
		}

		while (minhaLista.verificaArquivoData(data.getMes(), data.getAno()) == true) {
			// Inicio da validação (Se o dia da Lista for menor ou igual ao dia
			// atual + "intervaloProximoDia")
			minhaLista.carregaLista(data.getMes(), data.getAno());

			for (int i = 0; i < minhaLista.getTamanhoLista(); i++) {
				// Verifica se pagou a conta
				if (minhaLista.getListaPagamento().get(i).getEstado() == false) {
					// Se for 00/00/0000 = "30/11/0002" = Ano(0002) -> Não
					// considera porque é uma data em branca
					if (data.retornaAno(minhaLista.getListaPagamento().get(i).getStringData()) != 2) {
						// Verifica se a data da conta já venceu
						if (minhaLista.getListaPagamento().get(i).getData().before(data.getDataAtual()) == true) {
							data.setMes(oldMes);
							data.setAno(oldAno);
							return true;
						}
					}
				}
			}

			// Se for o inicio do ano volta para o ano anterior
			if (data.getNumMes() == 1) {
				data.setAno(data.getAno() - 1);
				data.setMes(12);
			} else {
				data.setMes(data.getNumMes() - 1);
			}
		}

		data.setMes(oldMes);
		data.setAno(oldAno);
		return false;
	}

	public void inicializaBot(String username, String token) {

		// Initialize Api Context
		ApiContextInitializer.init();

		// Instantiate Telegram Bots API
		TelegramBotsApi botsApi = new TelegramBotsApi();

		// Register our bot
		try {
			botsApi.registerBot(new TelegramBot(username, token, pagamentosVencidosPendentes(-1),
					pagamentosVencidosPendentes(1), data.getStringData()));

		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	// Função usada pelo TelegramBot para retornar uma Lista apenas com os
	// pagamento vencidos
	private Lista pagamentosVencidosPendentes(int indice) {
		// Indice
		// -1 -> Pagamento Vencidos
		// 1 -> Pagamentos Pendentes

		// Cria uma lista de pagamentos
		Lista lista = new Lista();

		// Controla até onde pode verificar os arquivos
		int verificar = 1;

		int oldMes = data.getNumMes();
		int oldAno = data.getAno();

		// VAI PARA A DATA DEPOIS DO ULTIMO ARQUIVO
		while (minhaLista.verificaArquivoData(data.getMes(), data.getAno()) == true) {

			if (data.getNumMes() < 12) {
				// Vai para o mes posterior
				data.setMes(data.getNumMes() + 1);
			} else {
				// Vai para o ano posterior
				data.setAno(data.getAno() + 1);
				data.setMes(1);
			}
		}

		// VOLTA PARA DATA ANTERIOR
		if (data.getNumMes() > 1) {
			// Vai para o mes anterior
			data.setMes(data.getNumMes() - 1);
		} else {
			// Vai para o ano anterior
			data.setAno(data.getAno() - 1);
			data.setMes(12);
		}

		while (verificar > 0) {
			// Inicio da validação (Se o dia da Lista for menor ou igual ao dia
			// atual + "intervaloProximoDia")
			minhaLista.carregaLista(data.getMes(), data.getAno());

			for (int i = 0; i < minhaLista.getTamanhoLista(); i++) {
				// Verifica se pagou a conta
				if (minhaLista.getListaPagamento().get(i).getEstado() == false) {
					// Se for 00/00/0000 = "30/11/0002" = Ano(0002)
					// Não considera porque é uma data em branca
					if (data.retornaAno(minhaLista.getListaPagamento().get(i).getStringData()) != 2) {
						// Verifica se a data da conta já venceu
						if (minhaLista.getListaPagamento().get(i).getData().before(data.getDataAtual()) == true
								&& indice == -1) {

							Pagamentos pagamento = new Pagamentos();

							pagamento.setNome(minhaLista.getListaPagamento().get(i).getNome());
							pagamento.setValor(minhaLista.getListaPagamento().get(i).getValor());
							pagamento.setEstado((minhaLista.getListaPagamento().get(i).getEstado()));
							pagamento.setData(minhaLista.getListaPagamento().get(i).getData());

							// Adiciona os pagamentos vencidos na lista
							lista.newPagamento(pagamento);
						}
						// Verifica se a data da conta não venceu
						else if (minhaLista.getListaPagamento().get(i).getData().after(data.getDataAtual()) == true
								&& indice == 1) {

							Pagamentos pagamento = new Pagamentos();

							pagamento.setNome(minhaLista.getListaPagamento().get(i).getNome());
							pagamento.setValor(minhaLista.getListaPagamento().get(i).getValor());
							pagamento.setEstado((minhaLista.getListaPagamento().get(i).getEstado()));
							pagamento.setData(minhaLista.getListaPagamento().get(i).getData());

							// Adiciona os pagamentos vencidos na lista
							lista.newPagamento(pagamento);
						}
					}
				}
			}

			// Se for o inicio do ano volta para o ano anterior
			if (data.getNumMes() == 1) {
				data.setAno(data.getAno() - 1);
				data.setMes(12);
			} else {
				data.setMes(data.getNumMes() - 1);
			}

			// Se o arquivo não existir termina a varredura
			if (minhaLista.verificaArquivoData(data.getMes(), data.getAno()) == false) {
				verificar = 0;
			}
		}

		data.setMes(oldMes);
		data.setAno(oldAno);

		return lista;
	}

	// Verifica o código primeiro
	public void verificaAcesso() {
		Acesso acesso = new Acesso("admin");

		// Verifica se o arquivo existe
		// Se o arquivo não existe pergunta a senha para o usuario
		// Se o arquivo existe apenas verifica a sequencia de ativação do mesmo
		acesso.verificaRegistro();
	}
}