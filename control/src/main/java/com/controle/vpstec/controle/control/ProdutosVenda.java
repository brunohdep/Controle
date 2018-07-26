package com.controle.vpstec.controle.control;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.controle.vpstec.controle.control.db.BancoController;
import com.controle.vpstec.controle.control.db.ControleContract;

public class ProdutosVenda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_venda);
        Atualizar();
    }
    public void Atualizar(){
        ListView listView = (ListView) findViewById(R.id.listview_produtosvenda);
        BancoController crud = new BancoController(getBaseContext());
        final Cursor cursor = crud.listarProdutos();
        String[] nomeCampos = new String[]{
                ControleContract.ProdutoEntry._ID,
                ControleContract.ProdutoEntry.DESCRICAO,
                ControleContract.ProdutoEntry.CODIGO,
                ControleContract.ProdutoEntry.VALOR,
                ControleContract.ProdutoEntry.QUANTIDADE,
                ControleContract.ProdutoEntry.CUSTO};
        int[] idViews = new int[] {
                R.id.p_id_pvenda,
                R.id.p_descricao_pvenda,
                R.id.p_codigo_pvenda,
                R.id.p_valor_pvenda,
                R.id.p_quantidade_pvenda,
                R.id.p_custo_pvenda};
        SimpleCursorAdapter adaptadore = new SimpleCursorAdapter(getBaseContext(),
                R.layout.list_produtosvenda,cursor,nomeCampos,idViews,0);
        listView.setAdapter(adaptadore);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry._ID));
                Intent intent = new Intent(ProdutosVenda.this,AdicionarProdutoVendaActivity.class);
                intent.putExtra("codigo",codigo);
                startActivity(intent);
                finish();
            }
        });
    }
}
