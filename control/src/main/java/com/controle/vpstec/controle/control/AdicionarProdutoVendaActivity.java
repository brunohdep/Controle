package com.controle.vpstec.controle.control;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.controle.vpstec.controle.control.db.BancoController;
import com.controle.vpstec.controle.control.db.ControleContract;

public class AdicionarProdutoVendaActivity extends AppCompatActivity {
    TextView id;
    TextView valor;
    TextView descricao;
    EditText quantidade;
    Button adicionar;
    String codigo;
    BancoController crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Cursor cursor;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_produto_venda);
        codigo = this.getIntent().getStringExtra("codigo");
        crud = new BancoController(getBaseContext());
        //Atribuindo ids
        descricao = (TextView)findViewById(R.id.tv_adicionar_descricao);
        valor = (TextView)findViewById(R.id.tv_adicionar_preco);
        quantidade = (EditText)findViewById(R.id.ed_adicionar_qtd);
        quantidade.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    quantidade.setText("");
                }
                return false;
            }
        });
        adicionar = (Button)findViewById(R.id.bt_adicionar_botao);
        //buscando no banco de dados
        cursor = crud.carregarDadoById(Integer.parseInt(codigo));
        //Mostrando os Dados na Tela
        descricao.setText(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.DESCRICAO)));
        valor.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.VALOR))));
        quantidade.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.QUANTIDADE))));

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                BancoController inserir = new BancoController(getBaseContext());
                Cursor cursor = inserir.carregarDadoPorNome(descricao.getText().toString());
                double valor = Integer.parseInt(quantidade.getText().toString()) * cursor.getDouble(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.VALOR));
                String result = inserir.inserirProdutoVenda(inserir.contarVendas(),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.CODIGO)),
                        Integer.parseInt(quantidade.getText().toString()),
                        cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.DESCRICAO)),
                        valor);
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), VendaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("venda",String.valueOf(inserir.contarVendas()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
