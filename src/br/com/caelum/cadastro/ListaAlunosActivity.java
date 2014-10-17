package br.com.caelum.cadastro;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import br.com.caelum.cadastro.adapter.ListaAlunoAdapter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.task.EnviaContatosTask;

public class ListaAlunosActivity extends Activity {

	private ListView listaAlunos;
	private List<Aluno> alunos;
	private Aluno alunoSelecionado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_aluno);
		
		this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);
		
		listaAlunos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int posicao,
					long arg3) {
				
				Intent edicao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
				Aluno alunoEdicao = (Aluno) adapter.getItemAtPosition(posicao);
			
				edicao.putExtra(Extras.ALUNO_SELECIONADO, 
						alunoEdicao);
				
				startActivity(edicao);
				
			}
		});
		
		listaAlunos.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter , View arg1,
					int posicao, long arg3) {

				alunoSelecionado = (Aluno) adapter.getItemAtPosition(posicao);
				
				return false;
			}
		});
		
		registerForContextMenu(listaAlunos);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.carregarLista();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.menu_novo:
			Intent intent = new Intent(this, FormularioActivity.class);
			startActivity(intent);
			return false;
			
		case R.id.menu_enviar_alunos:
			new EnviaContatosTask(this).execute();
			return false;
			
		case R.id.menu_receber_provas:
			Intent provas = new Intent(this, ProvasActivity.class);
			startActivity(provas);
			return false;
			
		case R.id.menu_mapa:
			Intent mapa = new Intent(this, MostraAlunosProximosActivity.class);
			startActivity(mapa);
			return false;

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		MenuItem ligar = menu.add("Ligar");
		Intent intentLigar = new Intent(Intent.ACTION_CALL);
		intentLigar.setData(Uri.parse("tel:"+alunoSelecionado.getTelefone()));
		ligar.setIntent(intentLigar);
		
		
		MenuItem sms = menu.add("Enviar SMS");
		Intent intentSms = new Intent(Intent.ACTION_VIEW);
		intentSms.setData(Uri.parse("sms:"+alunoSelecionado.getTelefone()));
		intentSms.putExtra("sms_body", "bla bla bla");
		sms.setIntent(intentSms);
		
		MenuItem mapa = menu.add("Achar no Mapa");
		Intent intentMapa = new Intent(Intent.ACTION_VIEW);
		String endereco = alunoSelecionado.getEndereco();
		intentMapa.setData(
				Uri.parse("geo:0,0?z=14&q="+endereco));
		mapa.setIntent(intentMapa);
		
		MenuItem site = menu.add("Navegar no site");
		Intent intentSite = new Intent(Intent.ACTION_VIEW);
		String siteAluno = alunoSelecionado.getSite().startsWith("http:") ?
				alunoSelecionado.getSite() : "http:"+alunoSelecionado.getSite();
		intentSite.setData(Uri.parse(siteAluno));
		site.setIntent(intentSite);
		
		MenuItem deletar = menu.add("Deletar");
		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				new AlertDialog.Builder(ListaAlunosActivity.this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Deletar")
				.setMessage("Deseja mesmo deletar?")
				.setPositiveButton("Quero", 
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
								dao.deletar(alunoSelecionado);
								dao.close();
								carregarLista();
								
							}
						}).setNegativeButton("Não", null).show();
				
				return false;
			}
		});
		
		menu.add("Enviar E-mail");
	}
	
	private void carregarLista(){
		AlunoDAO dao = new AlunoDAO(this);
		alunos = dao.getList();
		
		ListaAlunoAdapter adapter = new ListaAlunoAdapter(this, alunos);
		
		this.listaAlunos.setAdapter(adapter);
		
	}
}
