package com.controle.vpstec.controle.control;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.controle.vpstec.controle.control.db.BancoController;
import com.controle.vpstec.controle.control.db.ControleContract;

import org.w3c.dom.Text;

import java.sql.SQLOutput;

public class AlterarVendaActivity extends AppCompatActivity {
    BancoController bd;
    TextView qtd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_venda);
        final int id = Integer.parseInt(this.getIntent().getStringExtra("id"));
        System.out.println("ID: "+id);
        bd = new BancoController(getBaseContext());
        final Cursor cursor = bd.buscarVendasPoridFull(id);
        TextView desc = (TextView)findViewById(R.id.tv_vendaedtprod);
        TextView preco = (TextView)findViewById(R.id.tv_precoedtvenda);
        qtd = (TextView) findViewById(R.id.edt_quantidade_alterar);

        desc.setText(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.VendaEntry.DESCRICAO)));
        preco.setText((cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.VendaEntry.PRECO))));
        qtd.setText(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.VendaEntry.QUANTIDADE_VENDA)));

        Button remover = (Button)findViewById(R.id.bt_removervenda);
        remover.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int numvenda = bd.contarVendasItens(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.VendaEntry.NUMEROVENDA))));
                if(numvenda>1) {
                    int idvenda = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.VendaEntry._ID)));
                    int quantidade = Integer.parseInt(qtd.getText().toString());
                    int codigoproduto = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.VendaEntry.CODPROD)));
                    Cursor produto = bd.carregarPorcodigo(codigoproduto);
                    Cursor venda = bd.listarVendaPorNumerodaVenda(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.FechaVendaEntry.NUMEROVENDA))));
                    bd.alterarRegistro(Integer.parseInt(produto.getString(produto.getColumnIndexOrThrow(ControleContract.ProdutoEntry._ID))),
                            produto.getString(produto.getColumnIndexOrThrow(ControleContract.ProdutoEntry.DESCRICAO)),
                            codigoproduto,
                            Double.parseDouble(produto.getString(produto.getColumnIndexOrThrow(ControleContract.ProdutoEntry.VALOR))),
                            (Integer.parseInt(produto.getString(produto.getColumnIndexOrThrow(ControleContract.ProdutoEntry.QUANTIDADE))) + quantidade),
                            Double.parseDouble(produto.getString(produto.getColumnIndexOrThrow(ControleContract.ProdutoEntry.CUSTO))));
                    System.out.println("Valor Venda : ");
                    double valorvenda = Double.parseDouble(venda.getString(venda.getColumnIndexOrThrow(ControleContract.FechaVendaEntry.VALOR)));
                    double valorprod = Double.parseDouble(produto.getString(produto.getColumnIndexOrThrow(ControleContract.ProdutoEntry.VALOR)));
                    bd.alterarRegistroVenda(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.VendaEntry.NUMEROVENDA))), valorvenda - (quantidade * valorprod));
                    bd.deletarRegistrodeVendaporid(idvenda);
                    Intent intent = new Intent(getBaseContext(), ListarVendasActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"A venda contem apenas um item, Tente deletar a venda",Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}
