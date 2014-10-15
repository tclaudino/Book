package br.com.caelum.cadastro.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Aluno;

public class ListaAlunoAdapter extends BaseAdapter {
	private final List<Aluno> alunos;
	private final Activity activity;

	public ListaAlunoAdapter(Activity activity, List<Aluno> alunos) {
		this.activity = activity;
		this.alunos = alunos;

	}

	@Override
	public int getCount() {
		return alunos.size();
	}

	@Override
	public Object getItem(int posicao) {
		return alunos.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		// TODO Auto-generated method stub
		return alunos.get(posicao).getId();
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup parent) {
		View view = activity.getLayoutInflater().inflate(R.layout.item, null);
		
		Aluno aluno = alunos.get(posicao);
		
		TextView nome = (TextView) view.findViewById(R.id.nome);
		nome.setText(aluno.toString());
		
		Bitmap bm;
		
		if(aluno.getCaminhoFoto() != null){
			bm = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
		}else{
			bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
		}
		
		bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
		
		ImageView foto = (ImageView) view.findViewById(R.id.foto);
		foto.setImageBitmap(bm);
		
		if(posicao % 2 == 0){
			view.setBackgroundColor(activity.getResources().getColor(R.color.linha_par));
			
		}

		return view;
	}

}
