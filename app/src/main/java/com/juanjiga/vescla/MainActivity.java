package com.juanjiga.vescla;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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

           /* ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher); */

        listado = (ListView) findViewById(R.id.lista_listView);
        //listado.setDivider(null);
        database = new DataBaseControl(this);
        //insercion();
        //listado.setAdapter(database.listadoClaves(this));
        listadoClaves();

        /*Cursor cursor = database.cargarCursorClaves();
        String[] from = new String[]{database._id, database.C_NOMBRE, database.C_USUARIO, database.C_PASSWORD};
        int[] to = new int[]{R.id.Id_textView, R.id.Nombre_textView, R.id.Usuario_textView,R.id.Password_textView};
        cursorAdapter = new CursorAdapter(this, R.layout.fila, cursor, from, to, 0);
        listado.setAdapter(cursorAdapter);*/

        /*Cursor cursor = database.cargarCursorClaves();
        String[] from = new String[]{database._id, database.C_NOMBRE, database.C_USUARIO, database.C_PASSWORD};
        int[] to = new int[]{R.id.Id_textView, R.id.Nombre_textView, R.id.Usuario_textView,R.id.Password_textView};
        adapter = new SimpleCursorAdapter(this, R.layout.fila, cursor, from, to, 0);
        listado.setAdapter(adapter);*/

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.fila, R.id.Nombre_textView,
               // new String[]{"Juan", "Mónica", "Lucía"});
        //listado.setAdapter(arrayAdapter);

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, position + " --> " +
                        database.getIdFromPosition(adapter, position), Toast.LENGTH_SHORT).show();
                /*database.getIdFromPosition(database.listadoClaves(MainActivity.this), position),
                        Toast.LENGTH_SHORT).show();*/
                //int nId = getIdFromPosition(position);
                Clave clave = database.buscarClaveById(database.getIdFromPosition(adapter, position));
                fireCustomDialog(clave);
            }
        });
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            listado.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listado.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                }
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.cam_menu, menu);
                    return true;
                }
                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_clave:
                            for (int nC = adapter.getCount() - 1; nC >= 0; nC--) {
                                if (listado.isItemChecked(nC)) {
                                    database.deleteClave(database.getIdFromPosition(adapter, nC));
                                }
                            }
                            mode.finish();
                            adapter.changeCursor(database.cargarCursorClaves());
                            return true;
                    }
                    return false;
                }
                @Override
                public void onDestroyActionMode(ActionMode mode) {
                }
            });
        //}
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireCustomDialog(null);
                Snackbar.make(view, "Añade una nueva Clave", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private int getIdFromPosition(int nC) {
        return (int) adapter.getItemId(nC);
        }
    private void listadoClaves() {
        Cursor cursor = database.cargarCursorClaves();
        String[] from = new String[]{database._id, database.C_NOMBRE};//database.C_USUARIO, database.C_PASSWORD};
        int[] to = new int[]{R.id.Id_textView, R.id.Nombre_textView};//R.id.Usuario_textView, R.id.Password_textView};
        adapter = new SimpleCursorAdapter(this, R.layout.fila, cursor, from, to, 0);
        listado.setAdapter(adapter);
    }
    private void insercion() {
        Clave primodato = new Clave("Juan", "juanjiga", "luci1314 1");
        Clave dos = new Clave("Monica", "moessa", "chusss1971 2");
        Clave tres = new Clave("Lucía", "lujies", "chiquichuss 3");
        database.insertarClave(primodato);
        database.insertarClave(dos);
        database.insertarClave(tres);
        database.insertar("Juan...", "juanjiga", "luci1314 5");
        database.insertar("Mónica...", "moessa", "chiquichuss 6");
        database.insertar("Lucía...", "lujies", "bubi 7");
    }
    private void fireCustomDialog(final Clave clave){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.cuadro_dialogo);
        TextView titulo= (TextView) dialog.findViewById(R.id.cd_titulo_textView);
        final EditText editNombre = (EditText) dialog.findViewById(R.id.cd_nombre_editText);
        final EditText editUsuario = (EditText) dialog.findViewById(R.id.cd_usuario_editText);
        final EditText editPassword = (EditText) dialog.findViewById(R.id.cd_password_editText);
        Button botonCancelar = (Button) dialog.findViewById(R.id.cd_cancelar_button);
        Button botonGuardar = (Button) dialog.findViewById(R.id.cd_guardar_button);
        LinearLayout cuadroDialogo = (LinearLayout) dialog.findViewById(R.id.cd_cuadro_linearLayout);
        final boolean isEdit = (clave != null);
        if (isEdit){
            titulo.setText("Modificar Clave");
            editNombre.setText(clave.getNombre());
            editUsuario.setText(clave.getUsuario());
            editPassword.setText(clave.getPassword());
            cuadroDialogo.setBackgroundColor(getResources().getColor(R.color.grisclaro));
            } else {
                titulo.setText("Añadir Clave");
            }
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nombre = editNombre.getText().toString();
                String Usuario = editUsuario.getText().toString();
                String Password = editPassword.getText().toString();
                if (isEdit) {
                    Clave claveModificada = new Clave(clave.getId(), Nombre, Usuario, Password);
                    database.updateClave(claveModificada);
                } else {
                    database.insertar(Nombre, Usuario, Password);
                }
                adapter.changeCursor(database.cargarCursorClaves());
                dialog.dismiss();
            }
        });
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
                fireCustomDialog(null);
                return true;
            case R.id.borrar_actionBar:
                Toast.makeText(MainActivity.this, "Borrado", Toast.LENGTH_SHORT).show();
                database.borraTodo();
                //database.listadoClaves(this);
                listadoClaves();
                return true;
            case R.id.salir_actionBar:
                finish();
                return true;
            default:
                return false;
        }
    }
}
