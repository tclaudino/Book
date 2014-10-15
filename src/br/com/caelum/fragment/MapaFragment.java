package br.com.caelum.fragment;

import java.io.IOException;
import java.util.List;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.util.Localizador;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends SupportMapFragment {
	
	@Override
	public void onResume() {
		super.onResume();
		try {
			Localizador localizador = new Localizador(getActivity());
			LatLng local = localizador.getCoordenada("Rua Vergueiro 3185 Vila Mariana");
			MarkerOptions marcador = new MarkerOptions().title("Caelum")
					.snippet("Ensino e Inovação")
					.position(local);
			getMap().addMarker(marcador);
			
			AlunoDAO dao = new AlunoDAO(getActivity());
			List<Aluno> alunos = dao.getList();
			
			for (Aluno aluno : alunos) {
				
				LatLng localAluno = localizador.getCoordenada(aluno.getEndereco());
				
				MarkerOptions marcadorAluno = new MarkerOptions().title(aluno.getNome())
						.snippet(aluno.getEndereco())
						.position(localAluno);
				getMap().addMarker(marcadorAluno);
				
			}
			
			this.centralizarNo(local);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void centralizarNo(LatLng local) {
		
		GoogleMap mapa = getMap();
		CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(local, 10);
		
		mapa.moveCamera(camera);
		
	}
	
	

}
