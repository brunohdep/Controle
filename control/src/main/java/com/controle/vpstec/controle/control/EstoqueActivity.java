package com.controle.vpstec.controle.control;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.controle.vpstec.controle.control.db.BancoController;
import com.controle.vpstec.controle.control.db.ControleContract;

import java.util.ArrayList;

public class EstoqueActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque);
        //ListView listView = (ListView) findViewById(R.id.listview_produtos);
        //listView.setAdapter(produtos());
        Atualizar();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.menu_cad) {
            Cadastrar();
            return true;
        }
        if(id == R.id.menu_atualizar){
            Atualizar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Cadastrar(){
        Intent intent = new Intent(getBaseContext(),CadastrarActivity.class);
        startActivity(intent);
    }
    public void Atualizar(){
        ListView listView = (ListView) findViewById(R.id.listview_produtos);
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
                R.id.p_id_num,
                R.id.p_descricao_nome,
                R.id.p_codigo_num,
                R.id.p_valor_num,
                R.id.p_quantidade_num,
                R.id.p_custo_num};
        SimpleCursorAdapter adaptadore = new SimpleCursorAdapter(getBaseContext(),
                R.layout.list_estoque_item,cursor,nomeCampos,idViews,0);
        listView.setAdapter(adaptadore);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position, long id){
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry._ID));
                Intent intent = new Intent(EstoqueActivity.this,AlterarActivity.class);
                intent.putExtra("codigo",codigo);
                startActivity(intent);
                finish();
            }
        });
    }
//    public ProdutoAdapter produtos(){
//        Produto p1 = new Produto("Camisa Preta",20,2,200,2.0);
//        Produto p2 = new Produto("Camisa Azul",432,2.65,300,2.0);
//
//
//        Produto p3 = new Produto();
//        p3.setCod(951);
//        p3.setValor(20);
//        p3.setDescricao("Calça Preta");
//        p3.setQuantidade(50);
//        p3.setCusto(3.0);
//        arrayDeProdutos.add(p1);
//        arrayDeProdutos.add(p2);
//        arrayDeProdutos.add(p3);
//
//        ProdutoAdapter adapter = new ProdutoAdapter(this,arrayDeProdutos);
//        return  adapter;
//    }
}
