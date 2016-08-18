package com.juanjiga.vescla;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listado;
    private CursorAdapter cursorAdapter;
    private SimpleCursorAdapter adapter;
    private DataBaseControl database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listado = (ListView) findViewById(R.id.lista_listView);
        listado.setDivider(null);

        database = new DataBaseControl(this);

        /*if (savedInstanceState == null) {
            database.deleteAllReminders();
             database.insertar("Juan...", "juanjiga", "luci1314 5");
             database.insertar("Mónica...", "moessa", "chiquichuss 6");

        }*/

        final Clave primodato = new Clave("Juan", "juanjiga", "luci1314 1");
        Clave dos = new Clave("Monica", "moessa", "chusss1971 2");
        Clave tres = new Clave("Lucía", "lujies", "chiquichuss 3");
        database.insertarClave(primodato);
        database.insertarClave(dos);
        database.insertarClave(tres);
        database.insertar("Juan...", "juanjiga", "luci1314 5");
        database.insertar("Mónica...", "moessa", "chiquichuss 6");
        database.insertar("Lucía...", "lujies", "bubi 7");


        /*Cursor cursor = database.cargarCursorClaves();
        String[] from = new String[]{database._id, database.C_NOMBRE, database.C_USUARIO, database.C_PASSWORD};
        int[] to = new int[]{R.id.Id_textView, R.id.Nombre_textView, R.id.Usuario_textView,R.id.Password_textView};
        cursorAdapter = new CursorAdapter(this, R.layout.fila, cursor, from, to, 0);
        listado.setAdapter(cursorAdapter);*/

        /*private int getIdFromPosition(int nC) {
                     adapter.getItemId(nC);
        return (int) cursorAdapter.getItemId(nC);
        }
        database.deleteRemindersById(getIdFromPosition(nC));*/

        Cursor cursor = database.cargarCursorClaves();
        String[] from = new String[]{database._id, database.C_NOMBRE, database.C_USUARIO, database.C_PASSWORD};
        int[] to = new int[]{R.id.Id_textView, R.id.Nombre_textView, R.id.Usuario_textView,R.id.Password_textView};
        adapter = new SimpleCursorAdapter(this, R.layout.fila, cursor, from, to, 0);
        listado.setAdapter(adapter);

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.fila, R.id.Nombre_textView,
               // new String[]{"Juan", "Mónica", "Lucía"});
        //listado.setAdapter(arrayAdapter);

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, position + " " + primodato.getUsuario() + " "
                        + primodato.getPassword(), Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Remplazar por tu acción", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insertar_actionBar:
                Toast.makeText(MainActivity.this, "Añadir", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.borrar_actionBar:
                database.deleteClave("Juan");
                return true;
            case R.id.salir_actionBar:
                finish();
                return true;
            default:
                return false;
        }

    }
}
