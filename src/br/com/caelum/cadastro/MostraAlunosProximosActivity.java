package br.com.caelum.cadastro;

import br.com.caelum.fragment.MapaFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MostraAlunosProximosActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.map_layout);
		
		MapaFragment mapaFragment = new MapaFragment();
		
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.mapa, mapaFragment);
		tx.commit();
		
	}

}
