package com.controle.vpstec.controle.control;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.controle.vpstec.controle.control.db.BancoController;
import com.controle.vpstec.controle.control.db.ControleContract;

public class FluxoCaixaActivity extends AppCompatActivity {
    double valor = 0.0;
    double custo = 0.0;
    double lucro = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluxo);
        Atualizar();
        fluxo();
        TextView total = (TextView) findViewById(R.id.edt_total);
        total.setText(String.valueOf(valor));
        TextView lucrotv = (TextView) findViewById(R.id.edt_lucro);
        lucrotv.setText(String.valueOf(lucro));

    }


    public void fluxo() {
        BancoController crud = new BancoController(getBaseContext());
        //Busca todas as vendas
        Cursor vendas = crud.listarVendasNaoCanceladas();

        do {
            //Busca os produtos da venda
            Cursor listaprodutos = crud.buscarVendas(Integer.parseInt(vendas.getString(vendas.getColumnIndexOrThrow("numerovenda"))));
            listaprodutos.moveToFirst();
            do {
                //Adiciona o valor do produto vendido ao total
                valor += Double.parseDouble(listaprodutos.getString(listaprodutos.getColumnIndexOrThrow("preco")));
                //busca o produto por código
                Cursor produto = crud.carregarPorcodigo(Integer.parseInt(listaprodutos.getString(listaprodutos.getColumnIndex("cod"))));
                custo += Integer.parseInt(listaprodutos.getString(listaprodutos.getColumnIndexOrThrow("quantidadevenda")))
                        * Double.parseDouble(produto.getString(produto.getColumnIndexOrThrow("custo")));
            } while (listaprodutos.moveToNext());
            custo += Double.parseDouble(vendas.getString(vendas.getColumnIndexOrThrow("desconto")));
        } while (vendas.moveToNext());


        lucro = valor - custo;

    }

    public void Atualizar() {
        ListView listView = (ListView) findViewById(R.id.listview_venda);
        BancoController crud = new BancoController(getBaseContext());
        final Cursor cursor = crud.listarVendas();
        String[] campos = {
                ControleContract.FechaVendaEntry._ID,
                ControleContract.FechaVendaEntry.DATA,
                ControleContract.FechaVendaEntry.NUMEROVENDA,
                ControleContract.FechaVendaEntry.CLIENTE_NOME,
                ControleContract.FechaVendaEntry.STATUS,
                ControleContract.FechaVendaEntry.DESCONTO,
                ControleContract.FechaVendaEntry.VALOR};

        int[] idViews = new int[]{
                R.id.venda_id,
                R.id.venda_data,
                R.id.tv_numvenda,
                R.id.tv_nomecliente,
                R.id.venda_status,
                R.id.tv_descontovenda,
                R.id.tv_valorvenda};
        SimpleCursorAdapter adaptadore = new SimpleCursorAdapter(getBaseContext(),
                R.layout.list_fluxo, cursor, campos, idViews, 0);
        listView.setAdapter(adaptadore);
    }
}
