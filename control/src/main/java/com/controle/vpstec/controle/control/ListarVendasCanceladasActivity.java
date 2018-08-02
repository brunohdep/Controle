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

public class ListarVendasCanceladasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_vendas_canceladas);
        AtualizarVendas();
    }
    public void AtualizarVendas(){
        ListView listvendas = (ListView) findViewById(R.id.listview_listavendascanceladas);
        BancoController crud = new BancoController(getBaseContext());
        final Cursor cursor = crud.listarVendasCanceladas();
        String[] nomeCampos = new String[]{
                ControleContract.FechaVendaEntry._ID,
                ControleContract.FechaVendaEntry.DATA,
                ControleContract.FechaVendaEntry.NUMEROVENDA,
                ControleContract.FechaVendaEntry.CLIENTE_NOME,
                ControleContract.FechaVendaEntry.STATUS,
                ControleContract.FechaVendaEntry.DESCONTO,
                ControleContract.FechaVendaEntry.VALOR};
        int[] idViews = new int[] {
                R.id.lista_vendaid,
                R.id.list_data,
                R.id.list_numerovenda,
                R.id.list_nome_cliente,
                R.id.list_status_venda,
                R.id.list_desconto_venda,
                R.id.list_valor_venda};
//        do {
//            System.out.println("Listando Vendas");
//            System.out.println("ID:" + cursor.getString(cursor.getColumnIndexOrThrow("_id")));
//            System.out.println("DATA" + cursor.getString(cursor.getColumnIndexOrThrow("data")));
//            System.out.println("NUMERO" + cursor.getString(cursor.getColumnIndexOrThrow("numerovenda")));
//            System.out.println("NOME:" + cursor.getString(cursor.getColumnIndexOrThrow("nome")));
//            System.out.println("STATUS:" + cursor.getString(cursor.getColumnIndexOrThrow("status")));
//            System.out.println("DESCONTO:" + cursor.getString(cursor.getColumnIndexOrThrow("desconto")));
//            System.out.println("VALOR:" + cursor.getString(cursor.getColumnIndexOrThrow("valor")));
//        }while(cursor.moveToNext());

        SimpleCursorAdapter adaptadorvenda = new SimpleCursorAdapter(getBaseContext(),
                R.layout.lista_vendas,cursor,nomeCampos,idViews,0);
        listvendas.setAdapter(adaptadorvenda);

        listvendas.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String numerovenda;
                cursor.moveToPosition(position);
                numerovenda = cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.FechaVendaEntry.NUMEROVENDA));
                Intent intent = new Intent(ListarVendasCanceladasActivity.this,DetalhesVendasCanceladas.class);
                intent.putExtra("numerovenda",numerovenda);
                startActivity(intent);
                finish();
            }
        });
    }
}
