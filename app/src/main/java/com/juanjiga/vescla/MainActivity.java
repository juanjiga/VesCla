package com.juanjiga.vescla;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
//import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private DataBaseControl database;
    private Cursor cursor;
    private ListView lista;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DataBaseControl(this);
        lista = (ListView) findViewById(R.id.lista_listView);

        Clave primodato = new Clave("Juan", "juanjiga", "luci1314");
        Clave dos = new Clave("Monica", "moessa", "chusss1971");
        Clave tres = new Clave("Lucía", "lujies", "chiquichuss");
        Clave cuatro = new Clave("Lucía Jiménez", "nueva", "holachiquichuss");
        database.insertarClave(primodato);
        database.insertarClave(dos);
        database.insertarClave(tres);
        database.insertarClave(cuatro);

        database.insertar("Juan...", "juanjiga", "luci1314");
        database.insertar("Mónica...", "moessa", "chiquichuss");
        database.insertar("Lucía...", "lujies", "bubi");
        database.insertar("nombre...", "usuario", "ahora");

        String[] from = new String[]{database.C_NOMBRE, database.C_USUARIO, database.C_PASSWORD};
        int[] to = new int[]{R.id.Nombre_textView, R.id.Usuario_textView,R.id.Password_textView};

        cursor = database.cargarCursorClaves();
        adapter = new SimpleCursorAdapter(this, R.layout.fila, cursor, from, to, 0);
        lista.setAdapter(adapter);





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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
