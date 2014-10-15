package br.com.caelum.cadastro;

import br.com.caelum.cadastro.modelo.Aluno;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

public class FormularioHelper {
	private Aluno aluno;
	
	private EditText nome;
	private EditText telefone;
	private EditText site;
	private RatingBar nota;
	private ImageView foto;
	private EditText endereco;
	
	public FormularioHelper(FormularioActivity activity) {
		this.aluno = new Aluno();
		this.nome = (EditText) activity.findViewById(R.id.nome);
		this.telefone = (EditText) activity.findViewById(R.id.telefone);
		this.site = (EditText) activity.findViewById(R.id.site);
		this.nota= (RatingBar) activity.findViewById(R.id.nota);
		this.endereco = (EditText) activity.findViewById(R.id.endereco);
		this.foto = (ImageView) activity.findViewById(R.id.foto);
	}
	
	public Aluno pegaAlunoDoFormulario(){
		aluno.setNome(nome.getText().toString());
		aluno.setEndereco(endereco.getText().toString());
		aluno.setSite(site.getText().toString());
		aluno.setTelefone(telefone.getText().toString());
		aluno.setNota(Double.valueOf(nota.getProgress()));
		
		return aluno;
	}
	
	public void colocarNoFormulario(Aluno aluno){
		nome.setText(aluno.getNome());
		telefone.setText(aluno.getTelefone());
		site.setText(aluno.getSite());
		nota.setProgress(aluno.getNota().intValue());
		endereco.setText(aluno.getEndereco());
		
		this.aluno = aluno;
		
		if(aluno.getCaminhoFoto() != null){
			carregarImage(aluno.getCaminhoFoto());
		}
		
	}
	
	public ImageView getFoto() {
		return foto;
	}

	public void carregarImage(String localArquivoFoto) {

		Bitmap imageFoto = BitmapFactory.decodeFile(localArquivoFoto);
		Bitmap imageFotoReduzida = Bitmap.createScaledBitmap(imageFoto, 100, 100, true);
		foto.setImageBitmap(imageFotoReduzida);
		
		aluno.setCaminhoFoto(localArquivoFoto);
	}
}
