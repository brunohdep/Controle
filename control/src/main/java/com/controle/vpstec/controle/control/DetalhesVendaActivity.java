package com.controle.vpstec.controle.control;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.controle.vpstec.controle.control.db.BancoController;
import com.controle.vpstec.controle.control.db.ControleContract;

public class DetalhesVendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_venda);
        int numerovenda = Integer.parseInt(this.getIntent().getStringExtra("numerovenda"));
        ListView listavenda = (ListView)findViewById(R.id.listview_detalhes_venda);
        BancoController crud = new BancoController(getBaseContext());
        Cursor cursor = crud.buscarVendas(numerovenda);
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
    }

}
