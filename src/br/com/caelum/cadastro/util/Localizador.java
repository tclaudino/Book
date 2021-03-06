package br.com.caelum.cadastro.util;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

public class Localizador {

	private Geocoder geo;

	public Localizador(Context context) {
		geo = new Geocoder(context);

	}

	public LatLng getCoordenada(String endereco) {

		try {
			List<Address> address = geo.getFromLocationName(endereco, 1);

			if (!address.isEmpty()) {
				Address e = address.get(0);

				return new LatLng(e.getLatitude(), e.getLongitude());
			} else {
				return null;
			}

		} catch (IOException e) {
			return null;
		}

	}

}
