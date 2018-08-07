package com.controle.vpstec.controle.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.controle.vpstec.controle.control.db.BancoController;

import java.text.DecimalFormat;

public class CadastrarActivity extends AppCompatActivity {
    EditText desc;
    EditText cod;
    EditText qt;
    EditText custo;
    EditText preco;
    Button salvar;
    BancoController insert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        insert = new BancoController(getBaseContext());
        desc = (EditText) findViewById(R.id.descri_prod);
        cod = (EditText) findViewById(R.id.cod_prod);
        qt = (EditText) findViewById(R.id.quant_prod);
        custo = (EditText) findViewById(R.id.custo_prod);
        preco = (EditText) findViewById(R.id.preco_prod);
        salvar = (Button)findViewById(R.id.bt_salvar);
        desc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    desc.setText("");
                }
                return false;
            }
        });
        cod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    cod.setText("");
                }
                return false;
            }
        });
        qt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    qt.setText("");
                }
                return false;
            }
        });
        custo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    custo.setText("");
                }
                return false;
            }
        });
        preco.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    preco.setText("");
                }
                return false;
            }
        });
        salvar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Produto p = new Produto(desc.getText().toString(),
                        Integer.parseInt(cod.getText().toString()),
                        Double.parseDouble(String.format("%.2f",Double.parseDouble(preco.getText().toString())).replace(",",".")),
                        Integer.parseInt(qt.getText().toString()),
                        Double.parseDouble(String.format("%.2f",Double.parseDouble(custo.getText().toString())).replace(",",".")));

                String result = insert.inserirProduto(p);
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(),EstoqueActivity.class);
                startActivity(intent);
            }
        });
    }

//    public void cadastrarProduto(View view){
//
//        Produto p = new Produto(desc.getText().toString(),
//                Integer.parseInt(cod.getText().toString()),
//                Double.parseDouble(String.format("%.2f",Double.parseDouble(preco.getText().toString())).replace(",",".")),
//                Integer.parseInt(qt.getText().toString()),
//                Double.parseDouble(String.format("%.2f",Double.parseDouble(custo.getText().toString())).replace(",",".")));
//
//        String result = insert.inserirProduto(p);
//        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(getBaseContext(),EstoqueActivity.class);
//        startActivity(intent);
//
//    }
}
