package com.controle.vpstec.controle.control;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.controle.vpstec.controle.control.db.BancoController;
import com.controle.vpstec.controle.control.db.ControleContract;

public class DetalhesVendaActivity extends AppCompatActivity {
    BancoController bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_venda);
        final int numerovenda = Integer.parseInt(this.getIntent().getStringExtra("numerovenda"));
        ListView listavenda = (ListView)findViewById(R.id.listview_detalhes_venda);
        BancoController crud = new BancoController(getBaseContext());
        final Cursor cursor = crud.buscarVendas(numerovenda);
        do{
            System.out.println(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
        }while(cursor.moveToNext());
        cursor.moveToFirst();
        String[] nomeCampos = new String[]{
                ControleContract.VendaEntry._ID,
                ControleContract.VendaEntry.CODPROD,
                ControleContract.VendaEntry.QUANTIDADE_VENDA,
                ControleContract.VendaEntry.DESCRICAO,
                ControleContract.VendaEntry.PRECO};
        int[] idViews = new int[] {
                R.id.v_id,
                R.id.v_codigo_num,
                R.id.v_quantidade_num,
                R.id.v_descricao_nome,
                R.id.v_valor_num};
        SimpleCursorAdapter adaptadore = new SimpleCursorAdapter(getBaseContext(),
                R.layout.item_venda,cursor,nomeCampos,idViews,0);
        listavenda.setAdapter(adaptadore);
        listavenda.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position,long id){
                String numerovenda;
                cursor.moveToPosition(position);
//                numerovenda = cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.FechaVendaEntry.NUMEROVENDA));
                Intent intent = new Intent(DetalhesVendaActivity.this,AlterarVendaActivity.class);
                intent.putExtra("id",cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                startActivity(intent);
                finish();
            }
        });
        Button cancelar = (Button)findViewById(R.id.bt_excluir_venda);
        cancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bd = new BancoController(getBaseContext());
                Cursor vendas = bd.buscarVendas(numerovenda);
                vendas.moveToFirst();
                do {
                    int codigoproduto =  Integer.parseInt(vendas.getString(vendas.getColumnIndexOrThrow(ControleContract.VendaEntry.CODPROD)));
                    int quantidade = Integer.parseInt(vendas.getString(vendas.getColumnIndexOrThrow(ControleContract.VendaEntry.QUANTIDADE_VENDA)));
                    Cursor produto = bd.carregarPorcodigo(codigoproduto);
                    bd.alterarRegistro(Integer.parseInt(produto.getString(produto.getColumnIndexOrThrow(ControleContract.ProdutoEntry._ID))),
                            produto.getString(produto.getColumnIndexOrThrow(ControleContract.ProdutoEntry.DESCRICAO)),
                            codigoproduto,
                            Double.parseDouble(String.format("%.2f",Double.parseDouble(produto.getString(produto.getColumnIndexOrThrow(ControleContract.ProdutoEntry.VALOR)))).replace(",",".")),
                            (Integer.parseInt(produto.getString(produto.getColumnIndexOrThrow(ControleContract.ProdutoEntry.QUANTIDADE))) + quantidade),
                            Double.parseDouble(String.format("%.2f",Double.parseDouble(produto.getString(produto.getColumnIndexOrThrow(ControleContract.ProdutoEntry.CUSTO)))).replace(",",".")));
                }while(vendas.moveToNext());
                bd.cancelarVenda(numerovenda);
                Intent intent = new Intent(getBaseContext(),ListarVendasActivity.class);
                startActivity(intent);
            }
        });
    }

}
