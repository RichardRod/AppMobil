package com.sandra.webservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String URL_SERVIDOR = "https://ahuehuete-shop.mx/WebService/coches.php";

    EditText marca;
    EditText modelo;
    EditText color;
    EditText anio;
    EditText pasajeros;
    EditText cilindros;
    String transmision;
    String combustible;

    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.transmisionSpinner);
        marca = (EditText) findViewById(R.id.txtMarca);
        modelo = (EditText) findViewById(R.id.txtModelo);
        color = (EditText) findViewById(R.id.txtColor);
        anio = (EditText) findViewById(R.id.txtAnio);
        pasajeros = (EditText) findViewById(R.id.txtPasajeros);
        cilindros = (EditText) findViewById(R.id.txtCilindros);

        btnGuardar = (Button) findViewById(R.id.btnGuardar);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transmisiones_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertarDatos(
                        marca.getText().toString(),
                        modelo.getText().toString(),
                        color.getText().toString(),
                        anio.getText().toString(),
                        pasajeros.getText().toString(),
                        cilindros.getText().toString()
                );
            }
        });


    }

    public void InsertarDatos(
            final String marca,
            final String modelo,
            final String color,
            final String anio,
            final String pasajeros,
            final String cilindros) {

        //Toast.makeText(MainActivity.this, marca + " " + modelo + " " + color + " " + anio + " " + pasajeros + " " + cilindros + " :: " + combustible + " => " + transmision, Toast.LENGTH_LONG).show();

        class EnviarPeticion extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {

                List<NameValuePair> valores = new ArrayList<>();
                valores.add(new BasicNameValuePair("marca", marca));
                valores.add(new BasicNameValuePair("modelo", modelo));
                valores.add(new BasicNameValuePair("color", color));
                valores.add(new BasicNameValuePair("anio", anio));
                valores.add(new BasicNameValuePair("pasajeros", pasajeros));
                valores.add(new BasicNameValuePair("cilindros", cilindros));
                valores.add(new BasicNameValuePair("transmision", transmision));
                valores.add(new BasicNameValuePair("combustible", combustible));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URL_SERVIDOR);
                    httpPost.setEntity(new UrlEncodedFormEntity(valores));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();





                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }

                return "Datos Insertados";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(MainActivity.this, "Datos Insertados", Toast.LENGTH_LONG).show();
            }
        }

        EnviarPeticion enviarPeticion = new EnviarPeticion();
        enviarPeticion.execute(marca, modelo, color, anio, pasajeros, cilindros);

    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.opcionGasolina:
                if (checked)
                    combustible = "Gasolina";
                    break;
            case R.id.opcionDiesel:
                if (checked)
                    combustible = "Diesel";
                break;

            case R.id.opcionElectrico:
                if (checked)
                    combustible = "Electrico";
                    break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG);
        transmision = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}