package br.com.caelum.fragment;

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

		Localizador coderUtil = new Localizador(getActivity());
		LatLng local = coderUtil
				.getCoordenada("Rua Vergueiro 3185 Vila Mariana");
		
		MarkerOptions marcador = new MarkerOptions().title("Caelum")
				.snippet("Ensino e Inovação").position(local);
		getMap().addMarker(marcador);
		
		centralizarNo(local);

		AlunoDAO dao = new AlunoDAO(getActivity());
		List<Aluno> alunos = dao.getList();
		dao.close();
		
		for (Aluno aluno : alunos) {
			Localizador localizador = new Localizador(getActivity());
			LatLng coordenadas = localizador.getCoordenada(aluno.getEndereco());
			
			if ( coordenadas != null ) {
				MarkerOptions marcadorAluno = new MarkerOptions()
				.title(aluno.getNome()).snippet(aluno.getEndereco())
				.position(coordenadas);
				
				getMap().addMarker(marcadorAluno);
				
			}
		}

	}

	public void centralizarNo(LatLng local) {

		GoogleMap mapa = getMap();
		CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(local, 10);

		mapa.moveCamera(camera);

	}

}
