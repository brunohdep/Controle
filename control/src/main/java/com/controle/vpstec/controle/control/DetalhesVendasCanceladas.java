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

public class DetalhesVendasCanceladas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_vendas_canceladas);
        final int numerovenda = Integer.parseInt(this.getIntent().getStringExtra("numerovenda"));
        ListView listavenda = (ListView)findViewById(R.id.listview_detalhes_venda_cancel);
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
//        listavenda.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
//                String numerovenda;
//                cursor.moveToPosition(position);
////                numerovenda = cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.FechaVendaEntry.NUMEROVENDA));
//                Intent intent = new Intent(DetalhesVendasCanceladas.this,AlterarVendaActivity.class);
//                intent.putExtra("id",cursor.getString(cursor.getColumnIndexOrThrow("_id")));
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}
