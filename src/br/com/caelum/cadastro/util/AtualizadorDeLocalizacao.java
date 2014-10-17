package br.com.caelum.cadastro.util;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import br.com.caelum.fragment.MapaFragment;

import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

public class AtualizadorDeLocalizacao implements GooglePlayServicesClient.ConnectionCallbacks,  LocationListener {

	private MapaFragment mapa;
	private LocationClient cliente;
	
	public AtualizadorDeLocalizacao(Context context, MapaFragment mapa) {
		this.mapa = mapa;
		this.cliente = new LocationClient(context, this, null);
		this.cliente.connect();
		
	}
	
	@Override
	public void onLocationChanged(Location location) {

		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		LatLng local= new LatLng(latitude, longitude);
		
		this.mapa.centralizarNo(local);
		
	}


	@Override
	public void onConnected(Bundle dataBundle) {
		LocationRequest request = LocationRequest.create();
		request.setInterval(2000);
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		request.setSmallestDisplacement(50);
		
		this.cliente.requestLocationUpdates(request, this);
		
	}

	@Override
	public void onDisconnected() {
		
	}
	
	public void cancela(){
		this.cliente.removeLocationUpdates(this);
		this.cliente.disconnect();
	}

}
