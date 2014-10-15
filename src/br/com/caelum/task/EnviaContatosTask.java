package br.com.caelum.task;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.caelum.cadastro.AlunoConverter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.support.WebClient;

public class EnviaContatosTask extends AsyncTask<Object, Object, String>{
	
	private Context context;
	private final String endereco = "http://www.caelum.com.br/mobile";
	private ProgressDialog progress;
	
	public EnviaContatosTask(Context contex) {
		this.context = contex;
	}
	
	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(context,"Aguarde...","Envio de dados para a web", true, true);
	
	}
	
	@Override
	protected String doInBackground(Object... params) {
		AlunoDAO dao = new AlunoDAO(context);
		List<Aluno> alunos = dao.getList();
		dao.close();
		
		String json = new AlunoConverter().toJSON(alunos);
		String media = new WebClient(endereco).post(json);
		
		return media;
	}

	@Override
	protected void onPostExecute(String result) {
		progress.dismiss();
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
	}
}
