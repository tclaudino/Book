package br.com.caelum.cadastro;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioActivity extends Activity {

	private FormularioHelper helper;
	private String localArquivoFoto;
	private static final int TIRAR_FOTO = 123;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		this.helper = new FormularioHelper(this);
		
		Intent intent = getIntent();
		Aluno alunoParaSerEditado = (Aluno) intent.getSerializableExtra(Extras.ALUNO_SELECIONADO);
		
		Button botao = (Button) findViewById(R.id.botao);
		
		if(alunoParaSerEditado != null){
			botao.setText("Alterar");
			
			helper.colocarNoFormulario(alunoParaSerEditado);
		}
		
		botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Aluno aluno = helper.pegaAlunoDoFormulario();
				
				AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
				
				if(aluno.getId() == null){
					dao.insere(aluno);
					
				} else{
					dao.alterar(aluno);
				}
				
				dao.close();
				
				finish();
			}
		});
		
		
		
		ImageView foto = helper.getFoto();
		foto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
				
				Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Uri localFoto = Uri.fromFile(new File(localArquivoFoto));
				irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
				
				startActivityForResult(irParaCamera, TIRAR_FOTO);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == TIRAR_FOTO){
			
			if(resultCode == Activity.RESULT_OK){
				helper.carregarImage(this.localArquivoFoto);
			
			}else {
				this.localArquivoFoto = null;
				
			}
				
			
		}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.formulario, menu);
		return true;
	}

}
