package pe.edu.tecsup.androidavanzado1;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocalizacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);
    }

    public void localizar(View v) {
        Localizador obj = new Localizador(this);

        if(obj.canGetLocation()){

            double latitude = obj.getLatitude();
            double longitude = obj.getLongitude();

            TextView latitud = (TextView)findViewById(R.id.tvLatitud);
            TextView longitud = (TextView)findViewById(R.id.tvLongitud);
            TextView direccion = (TextView)findViewById(R.id.tvDireccion);

            latitud.setText(String.valueOf(latitude));
            longitud.setText(String.valueOf(longitude));

            String dir = cargarDireccion(latitude, longitude);

            direccion.setText(dir);


        }else{
            obj.showSettingsAlert();
        }
    }

    public String cargarDireccion(double latitud, double longitud) {

        Geocoder gcd = new Geocoder(this.getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        StringBuffer sb = new StringBuffer();
        try {
            addresses = gcd.getFromLocation(latitud, longitud, 1);
            if (addresses.size() > 0) {
                sb.append(addresses.get(0).getCountryName() + " ");
                sb.append(addresses.get(0).getCountryCode() + " ");
                sb.append(addresses.get(0).getLocality() + " ");
                sb.append(addresses.get(0).getAddressLine(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}