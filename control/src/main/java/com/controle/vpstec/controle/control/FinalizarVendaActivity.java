package com.controle.vpstec.controle.control;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.controle.vpstec.controle.control.db.BancoController;

import java.sql.SQLOutput;
import java.util.Calendar;
import java.util.Date;

public class FinalizarVendaActivity extends AppCompatActivity {
    int pago = 0;
    EditText nome;
    EditText desconto;
    double valor = 0.0;
    String data;
    Switch status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_venda);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        valor = Double.valueOf(bundle.getString("valor"));
        TextView valortotal = (TextView) findViewById(R.id.valor_total);
        valortotal.setText("RS: "+bundle.getString("valor"));
        Button finalizar = (Button)findViewById(R.id.bt_finalizar);
        nome = (EditText)findViewById(R.id.cliente_nome);
        desconto = (EditText)findViewById(R.id.valor_desconto);
        status = (Switch)findViewById(R.id.status);
        Date currenttime = Calendar.getInstance().getTime();
        data = currenttime.toString();



        final BancoController inserir = new BancoController(getBaseContext());
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor produtosvenda = inserir.buscarVendas(numero(getBaseContext()));
                System.out.println("Verificando STATUS");
                if(status.isChecked()){
                    System.out.println(1);
                    pago = 1;
                }else{
                    System.out.println(0);
                    pago = 0;
                }

                produtosvenda.moveToFirst();
                do {
                    Cursor produtos = inserir.carregarPorcodigo(Integer.parseInt(produtosvenda.getString(produtosvenda.getColumnIndexOrThrow("cod"))));
                    System.out.println("Mostrando Produto");
                    System.out.println("Cod:" + produtos.getString(produtos.getColumnIndexOrThrow("cod")));
                    System.out.println("Descricao" + produtos.getString(produtos.getColumnIndexOrThrow("descricao")));
                    inserir.alterarRegistro(Integer.parseInt(produtos.getString(produtos.getColumnIndexOrThrow("_id"))),
                            produtos.getString(produtos.getColumnIndexOrThrow("descricao")),
                            Integer.parseInt(produtos.getString(produtos.getColumnIndexOrThrow("cod"))),
                            Double.parseDouble(produtos.getString(produtos.getColumnIndexOrThrow("valor"))),
                            (Integer.parseInt(produtos.getString(produtos.getColumnIndexOrThrow("quantidade")))-Integer.parseInt(produtosvenda.getString(produtosvenda.getColumnIndexOrThrow("quantidadevenda")))),
                            Double.parseDouble(produtos.getString(produtos.getColumnIndexOrThrow("custo")))
                            );
                }while(produtosvenda.moveToNext());
                String result = inserir.inserirFinalizarVenda(data,
                        numero(getBaseContext()),
                        nome.getText().toString(),pago,
                        Double.parseDouble(desconto.getText().toString()),
                        valor);
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }
        });

    }

    public int numero(Context context){
        BancoController peganum = new BancoController(context);
        return peganum.contarVendas();
    }

}
