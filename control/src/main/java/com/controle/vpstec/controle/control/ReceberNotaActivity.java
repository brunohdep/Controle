package com.controle.vpstec.controle.control;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.controle.vpstec.controle.control.db.BancoController;

public class ReceberNotaActivity extends AppCompatActivity {
    String codigo;
    BancoController crud;
    TextView nome;
    TextView valor;
    Button receber;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_nota);
        codigo = this.getIntent().getStringExtra("codigo");
        crud = new BancoController(getBaseContext());
        cursor = crud.listarVendaPorNumerodaVenda(Integer.parseInt(codigo));
        nome = (TextView)findViewById(R.id.rec_nome);
        valor = (TextView)findViewById(R.id.rec_valor);
        nome.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        valor.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))));
        receber = (Button)findViewById(R.id.bt_receber);
        receber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                crud.receberVenda(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("numerovenda"))));
                Intent intent = new Intent(ReceberNotaActivity.this,NotasActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
