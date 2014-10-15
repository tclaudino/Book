package br.com.caelum.fragment;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Prova;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetalhesProvaFragment extends Fragment{
	
	private Prova prova;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.provas_detalhes, container, false);
		
		if(getArguments() != null){
			this.prova = (Prova) getArguments().getSerializable("prova");
		}
		
		if(this.prova != null){
			
			TextView materia = (TextView) 
					layout.findViewById(R.id.detalhes_prova_materia);
			
			TextView data = (TextView) 
					layout.findViewById(R.id.detalhes_prova_data);
			
			ListView topicos = (ListView) 
					layout.findViewById(R.id.detalhes_prova_topicos);
			
			materia.setText(prova.getMateria());
			data.setText(prova.getData());
			
			ArrayAdapter<String> adapter = 
					new ArrayAdapter<String>(
							getActivity(), 
							android.R.layout.simple_list_item_1,
							prova.getTopicos());
			
			
			topicos.setAdapter(adapter);
			
		}
		
		return layout;
		
		
	}

}
