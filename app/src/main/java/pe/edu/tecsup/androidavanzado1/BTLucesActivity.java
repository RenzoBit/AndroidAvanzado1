package pe.edu.tecsup.androidavanzado1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BTLucesActivity extends AppCompatActivity {

    private BluetoothAdapter BA;
    private BluetoothDevice arduinoBT;
    private BluetoothSocket arduinoSocket;
    private OutputStream mmOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btluces);

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    encenderLuz1();
                } else {
                    apagarLuz1();
                }
            }
        });

        Switch switch2 = (Switch) findViewById(R.id.switch2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    encenderLuz2();
                } else {
                    apagarLuz2();
                }
            }
        });

      /*
       * Inicio de Configuración de BlueTooth
       */
        BA = BluetoothAdapter.getDefaultAdapter();

        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Encendido", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Ya está listo el Bluetooth", Toast.LENGTH_LONG).show();
        }

        Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("MOTOTAXI")) {
                    arduinoBT = device;
                    Toast.makeText(getApplicationContext(), "Conectado al Arduino", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }

        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Standard
            // SerialPortService
            // ID
            arduinoSocket = arduinoBT.createRfcommSocketToServiceRecord(uuid);
            arduinoSocket.connect();
            mmOutputStream = arduinoSocket.getOutputStream();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Un Problema con el BlueTooth: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

      /*
       * Fin de Configuración de BlueTooth
       */
    }

    public void encenderLuz1() {
        try {
            Log.i("===>", "Encendiendo Luz1");
            mmOutputStream.write("a".getBytes());
            Log.i("===>", "Luz1 encendida");
        } catch (IOException e) {
            Log.i("===>", "Excepción: " + e.getMessage());
        }
    }

    public void apagarLuz1() {
        try {
            Log.i("===>", "Apagando Luz1");
            mmOutputStream.write("b".getBytes());
            Log.i("===>", "Luz1 apagada");
        } catch (IOException e) {
            Log.i("===>", "Excepción: " + e.getMessage());
        }
    }

    public void encenderLuz2() {
        try {
            mmOutputStream.write("c".getBytes());
        } catch (IOException e) {
            Log.i("===>", "Excepción: " + e.getMessage());
        }
    }

    public void apagarLuz2() {
        try {
            mmOutputStream.write("d".getBytes());
        } catch (IOException e) {
            Log.i("===>", "Excepción: " + e.getMessage());
        }
    }

}