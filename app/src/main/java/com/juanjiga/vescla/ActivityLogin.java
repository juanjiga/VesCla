package com.juanjiga.vescla;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityLogin extends AppCompatActivity implements OnClickListener {

    private String clave = "";
    private TextView pass;
    private Button[] boton = new Button[10];
    private Button borrar, entrar;
    private int pulsaciones = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        pass = (TextView) findViewById(R.id.pass_textView);
        boton[0] = (Button) findViewById(R.id.button0);
        boton[1] = (Button) findViewById(R.id.button1);
        boton[2] = (Button) findViewById(R.id.button2);
        boton[3] = (Button) findViewById(R.id.button3);
        boton[4] = (Button) findViewById(R.id.button4);
        boton[5] = (Button) findViewById(R.id.button5);
        boton[6] = (Button) findViewById(R.id.button6);
        boton[7] = (Button) findViewById(R.id.button7);
        boton[8] = (Button) findViewById(R.id.button8);
        boton[9] = (Button) findViewById(R.id.button9);
        borrar = (Button) findViewById(R.id.buttonBorrar);
        entrar = (Button) findViewById(R.id.buttonEntrar);

        for (int i=0; i<10; i++){boton[i].setOnClickListener(this);}
        borrar.setOnClickListener(this);
        entrar.setOnClickListener(this);
        entrar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {
        pulsaciones++;
        if (pulsaciones == 4)
            entrar.setVisibility(View.VISIBLE);
        if (pulsaciones < 5) {
            for (int i = 0; i < 10; i++) {
                if (v == boton[i]) {
                    clave = clave + i;
                    pass.setText(clave);
                }
            }
        }
        if (v== borrar){
            resetear();
        }
        if (v == entrar){
            if (clave.equals("1314")) {
                Toast.makeText(getBaseContext(), "Correcto", Toast.LENGTH_SHORT).show();
                Intent nueva = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(nueva);
            }
            else {
                Toast.makeText(getBaseContext(), "Pin Incorrecto", Toast.LENGTH_SHORT).show();
                resetear();
            }
        }
    }

    public void resetear(){
        pulsaciones = 0;
        clave = "";
        pass.setText(clave);
        entrar.setVisibility(View.INVISIBLE);
    }

}
