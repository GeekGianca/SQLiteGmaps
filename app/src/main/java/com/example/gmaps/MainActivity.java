package com.example.gmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText nombre;
    EditText direccion;
    EditText latitud;
    EditText longitud;
    Button agregarbtn;
    Button cancelarbtn;
    AlertDialog dialog;
    Person persona;
    DbSqlite dbSqlite;
    RecyclerView listperson;
    Adapter adaptador;
    List<Person> personaList;
    String idpersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbSqlite = new DbSqlite(this);
        listperson = findViewById(R.id.listado);
        listperson.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        listperson.setLayoutManager(manager);
        cargar();
    }

    private void cargar() {
        personaList = dbSqlite.getAllPerson();
        adaptador = new Adapter(personaList, this);
        adaptador.notifyDataSetChanged();
        listperson.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Eliminar")) {
            dbSqlite.deletePerson(personaList.get(item.getOrder()));
            Toast.makeText(this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
            cargar();
        } else if (item.getTitle().equals("Actualizar")) {
            crearNuevaPersona(true);
            idpersona = personaList.get(item.getOrder()).getId() + "";
            nombre.setText(personaList.get(item.getOrder()).getNombre());
            direccion.setText(personaList.get(item.getOrder()).getDireccion());
            latitud.setText(personaList.get(item.getOrder()).getLat() + "");
            longitud.setText(personaList.get(item.getOrder()).getLng() + "");
        }
        return super.onContextItemSelected(item);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("consultajson.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Log.v("MainActivity", "Load json ok");
        } catch (IOException ex) {
            Log.v("MainActivity", "Error: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addperson:
                crearNuevaPersona(false);
                break;
            case R.id.loadjson:
                loadJSONFromAsset();
                Toast.makeText(this, "Cargar", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void crearNuevaPersona(final boolean isUpdate) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Agregar Nueva Persona");
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_addlayout, null);
        nombre = view.findViewById(R.id.nombretext);
        direccion = view.findViewById(R.id.direcciontext);
        latitud = view.findViewById(R.id.latitudtext);
        longitud = view.findViewById(R.id.longitudtext);
        agregarbtn = view.findViewById(R.id.agregarbtn);
        if (isUpdate) {
            agregarbtn.setText("Actualizar");
        } else {
            agregarbtn.setText("Agregar");
        }
        cancelarbtn = view.findViewById(R.id.cancelarbtn);
        agregarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persona = new Person();
                persona.setNombre(nombre.getText().toString());
                persona.setDireccion(direccion.getText().toString());
                persona.setLat(Float.parseFloat(latitud.getText().toString()));
                persona.setLng(Float.parseFloat(longitud.getText().toString()));
                if (isUpdate) {
                    persona.setId(Integer.parseInt(idpersona));
                    idpersona = null;
                    dbSqlite.updatePerson(persona);
                    Toast.makeText(MainActivity.this, "Actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    dbSqlite.addPerson(persona);
                    Toast.makeText(MainActivity.this, "Agregando", Toast.LENGTH_SHORT).show();
                }
                cargar();
                dialog.dismiss();
            }
        });
        cancelarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        alertDialog.setView(view);
        dialog = alertDialog.create();
        dialog.show();
    }
}
