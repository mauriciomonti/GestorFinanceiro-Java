import java.util.ArrayList;

public class Lista
{

	// Atributos

	// Declarando e criando uma ArrayList dos pagamentos
	private ArrayList<Pagamentos> listaPagamento = new ArrayList<Pagamentos>();

	// Construtores

	public Lista(ArrayList<Pagamentos> listaPagamento)
	{
		this.listaPagamento = listaPagamento;
	}

	public Lista(Pagamentos pagamento)
	{
		listaPagamento.add(pagamento);
	}

	public Lista()
	{
	}
	
	// Metodos

	public void setListaPagamento(ArrayList<Pagamentos> listaPagamento)
	{
		this.listaPagamento = listaPagamento;
	}

	public ArrayList<Pagamentos> getListaPagamento()
	{
		return listaPagamento;
	}

	public int getTamanhoLista()
	{
		return listaPagamento.size();
	}

	public void newPagamento(Pagamentos pagamento)
	{
		listaPagamento.add(pagamento);
	}

	public void salvaLista(String mes, int ano)
	{
		ArquivoPagamentos meuArquivo = new ArquivoPagamentos();
		meuArquivo.gravaArquivo(this.listaPagamento, mes, ano);
		
		// Organiza os pagamentos
		organizaPagamentos();
	}

	public void carregaLista(String mes, int ano)
	{
		// Limpa a lista atual
		listaPagamento.clear();
		ArquivoPagamentos meuArquivo = new ArquivoPagamentos();
		this.listaPagamento = meuArquivo.carregaArquivo(mes, ano);

		// Organiza os pagamentos
		organizaPagamentos();
	}

	public void limpaLista()
	{
		listaPagamento.removeAll(listaPagamento);
	}

	public boolean verificaArquivoData(String mes, int ano)
	{
		ArquivoPagamentos meuArquivo = new ArquivoPagamentos();

		return meuArquivo.verificaArquivo(mes, ano);
	}

	public void organizaPagamentos()
	{
		Pagamentos auxPagamento = new Pagamentos();

		int i = 0;

		// Reorganiza todo o vetor de acordo com a data em ordem crescente
		while (i + 1 < listaPagamento.size() && listaPagamento.size() > 0)
		{
			// Verifica se a proxima data vem depois da atual
			if (listaPagamento.get(i).getData().after(listaPagamento.get(i + 1).getData()) == true)
			{
				auxPagamento = listaPagamento.get(i);

				listaPagamento.set(i, listaPagamento.get(i + 1));

				listaPagamento.set(i + 1, auxPagamento);

				i = 0;
			}
			else
			{
				i++;
			}
		}
	}
}
