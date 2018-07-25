package com.controle.vpstec.controle.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.controle.vpstec.controle.control.db.BancoController;

public class CadastrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
    }

    public void cadastrarProduto(View view){
        BancoController insert = new BancoController(getBaseContext());
        EditText desc = (EditText) findViewById(R.id.descri_prod);
        EditText cod = (EditText) findViewById(R.id.cod_prod);
        EditText qt = (EditText) findViewById(R.id.quant_prod);
        EditText custo = (EditText) findViewById(R.id.custo_prod);
        EditText preco = (EditText) findViewById(R.id.preco_prod);
        Produto p = new Produto(desc.getText().toString(),
                Integer.parseInt(cod.getText().toString()),
                Double.parseDouble(preco.getText().toString()),
                Integer.parseInt(qt.getText().toString()),
                Double.parseDouble(custo.getText().toString()));

        String result = insert.inserirProduto(p);
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

    }
}
