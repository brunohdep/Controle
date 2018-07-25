package com.controle.vpstec.controle.control;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.controle.vpstec.controle.control.db.BancoController;
import com.controle.vpstec.controle.control.db.ControleContract;

public class AlterarActivity extends AppCompatActivity {
    TextView id;
    TextView cod;
    TextView valor;
    TextView descricao;
    TextView quantidade;
    TextView custo;
    Button alterar;
    Button deletar;
    String codigo;
    BancoController crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Cursor cursor;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);
        codigo = this.getIntent().getStringExtra("codigo");
        crud = new BancoController(getBaseContext());
        id = (TextView) findViewById(R.id.alt_id);
        cod = (TextView) findViewById(R.id.alt_cod);
        valor = (TextView) findViewById(R.id.alt_preco);
        descricao = (TextView) findViewById(R.id.alt_desc);
        quantidade = (TextView) findViewById(R.id.alt_quant);
        custo = (TextView) findViewById(R.id.alt_custo);

        alterar = (Button) findViewById(R.id.bt_alt_prod);
        deletar = (Button)findViewById(R.id.bt_deletar);

        cursor = crud.carregarDadoById(Integer.parseInt(codigo));
        id.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry._ID))));
        cod.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.CODIGO))));
        valor.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.VALOR))));
        descricao.setText(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.DESCRICAO)));
        quantidade.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.QUANTIDADE))));
        custo.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.CUSTO))));
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                crud.alterarRegistro(Integer.parseInt(codigo),
                        descricao.getText().toString(),
                        Integer.parseInt(cod.getText().toString()),
                        Double.parseDouble(valor.getText().toString()),
                        Integer.parseInt(quantidade.getText().toString()),
                        Double.parseDouble(custo.getText().toString()));
                Intent intent = new Intent(AlterarActivity.this,EstoqueActivity.class);
                startActivity(intent);
                finish();
            }
        });


        deletar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                crud.deletarRegistro(Integer.parseInt(codigo));
                Intent intent = new Intent(AlterarActivity.this,EstoqueActivity.class);
                startActivity(intent);
                finish();
            }

        });
           
    }

}
