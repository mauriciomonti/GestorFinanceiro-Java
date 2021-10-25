import java.sql.Date;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

public class Datas {

	private int mes;
	private int ano;
	private int minuto;

	// Cria os meses
	private static String[] meses = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto",
			"Setembro", "Outubro", "Novembro", "Dezembro" };

	// Construtores

	Datas() {
		this.mes = getNumMesAtual();
		this.ano = getAnoAtual();
		this.minuto = minutoAtual();
	}

	// Métodos

	public String getMes() {
		return meses[mes - 1];
	}

	public int getNumMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMinuto() {
		return minuto;
	}

	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}

	public int getNumMesAtual() {
		Date data = new Date(System.currentTimeMillis());
		Format format = new SimpleDateFormat("MM");
		int numMes = Integer.parseInt(format.format(data.getTime()));
		return numMes;
	}

	public int getAnoAtual() {
		Date data = new Date(System.currentTimeMillis());
		Format format = new SimpleDateFormat("yyyy");
		int numAno = Integer.parseInt(format.format(data.getTime()));
		return numAno;
	}

	public java.util.Date getDataAtual() {
		Date data = new Date(System.currentTimeMillis());
		return data;
	}

	// Verifica se o formato da data esta correto
	public boolean verificaData(String data) {
		// Indice
		// True -> Passou na verificacao
		// False -> Formato errado

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		// Não permite erro no formato da data
		formatter.setLenient(false);

		boolean resultado = false;

		try {
			formatter.parse(data);
			resultado = true;
		} catch (ParseException e) {
			resultado = false;
		}

		// Permite erro no formato da data
		formatter.setLenient(true);

		return resultado;
	}

	public int retornaAno(String dataString) {
		int numAno = 0;

		// Testa se a String corresponde ao formato de data (dd/MM/yyyy)
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date data = formatter.parse(dataString);

			formatter = new SimpleDateFormat("yyyy");
			numAno = Integer.parseInt(formatter.format(data));
		} catch (ParseException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro na Conversão de Data para Int (Classe Datas)", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}

		return numAno;
	}

	public String tempoAtual() {
		Date data = new Date(System.currentTimeMillis());
		Format format = new SimpleDateFormat("HH:mm");
		String tempo = (format.format(data.getTime())).toString();

		return tempo;
	}

	// Compara para ver se passou o minuto
	public int minutoAtual() {
		Date data = new Date(System.currentTimeMillis());
		Format format = new SimpleDateFormat("mm");
		int minuto = (Integer.parseInt(format.format(data.getTime())));

		return minuto;
	}

	public String getStringData() {
		Date data = new Date(System.currentTimeMillis());
		Format format = new SimpleDateFormat("dd/MM/yyyy");

		return (format.format(data.getTime())).toString();
	}

}
