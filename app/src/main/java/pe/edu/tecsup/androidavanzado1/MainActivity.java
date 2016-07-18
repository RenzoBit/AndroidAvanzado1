package pe.edu.tecsup.androidavanzado1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCamara, btnQR, btnLocalizacion, btnBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Comentando
        btnCamara = (Button) findViewById(R.id.btnCamara);
        btnQR = (Button) findViewById(R.id.btnQR);
        btnLocalizacion = (Button) findViewById(R.id.btnLocalizacion);
        btnBluetooth = (Button) findViewById(R.id.btnBluetooth);

        btnCamara.setOnClickListener(this);
        btnQR.setOnClickListener(this);
        btnLocalizacion.setOnClickListener(this);
        btnBluetooth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btnCamara:
                intent = new Intent(MainActivity.this, CamaraIntentActivity.class);
                break;
            case R.id.btnQR:
                intent = new Intent(MainActivity.this, LecturaQRActivity.class);
                break;
            case R.id.btnLocalizacion:
                intent = new Intent(MainActivity.this, LocalizacionActivity.class);
                break;
            case R.id.btnBluetooth:
                intent = new Intent(MainActivity.this, BTLucesActivity.class);
                break;
        }
        startActivity(intent);
    }
}
