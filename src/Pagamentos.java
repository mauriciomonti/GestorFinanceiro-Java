import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

public class Pagamentos
{

	// Atributos

	private String nome;
	private Double valor;
	private java.util.Date data;
	private Boolean estado;

	// Constante que controla o formato do horario que vai ser recebido e
	// enviado pelo programa
	// static-variavel global da classe ; final-não muda o valor
	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	// Construtor

	public Pagamentos(String nome, Double valor, String data, Boolean estado)
	{
		this.nome = nome;
		this.valor = valor;

		if (data == null)
		{
			data = "00/00/0000"; // Se for Null substitui por data zerada
		}
		// Testa se a String corresponde ao formato de data (dd/MM/yyyy)
		try
		{
			this.data = formatter.parse(data);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro no Construtor Pagamento no Atributo Data", "Erro", JOptionPane.ERROR_MESSAGE);
		}

		this.estado = estado;
	}

	// Mesmo se não atribui-se todos a null o java automaticamente iria fazer isso
	public Pagamentos()
	{
		this.nome = null;
		this.valor = null;
		try
		{
			this.data = formatter.parse("00/00/0000");
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro no Construtor Pagamento no Atributo Data", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		this.estado = null;
	}

	// Métodos

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Double getValor()
	{
		return valor;
	}

	public void setValor(Double valor)
	{
		this.valor = valor;
	}

	public java.util.Date getData()
	{ // Passa data em formato java.util.Date
		return data;
	}

	public String getStringData()
	{ // Passa data em string "dd/MM/yyyy"
		return formatter.format(data);
	}

	public void setData(java.util.Date data)
	{
		this.data = data;
	}

	public void setStringData(String data)
	{
		if (data == null)
		{
			data = "00/00/0000"; // Se for Null substitui por data zerada
		}
		// Testa se a String corresponde ao formato de data (dd/MM/yyyy)
		try
		{
			this.data = formatter.parse(data);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro na Atribuição da Data", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Boolean getEstado()
	{
		return estado;
	}

	public void setEstado(Boolean estado)
	{
		this.estado = estado;
	}

}
