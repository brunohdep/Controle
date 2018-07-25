package com.controle.vpstec.controle.control;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.controle.vpstec.controle.control.db.BancoController;
import com.controle.vpstec.controle.control.db.ControleContract;

public class NotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        Atualizar();

    }

    public void Atualizar(){
        ListView listView = (ListView) findViewById(R.id.listview_notas);
        BancoController crud = new BancoController(getBaseContext());
        final Cursor cursor = crud.listarVendasPorStatus();
        String[] nomeCampos = new String[]{
                ControleContract.FechaVendaEntry._ID,
                ControleContract.FechaVendaEntry.DATA,
                ControleContract.FechaVendaEntry.NUMEROVENDA,
                ControleContract.FechaVendaEntry.CLIENTE_NOME,
                ControleContract.FechaVendaEntry.STATUS,
                ControleContract.FechaVendaEntry.DESCONTO,
                ControleContract.FechaVendaEntry.VALOR};
        int[] idViews = new int[] {
                R.id.nota_id,
                R.id.nota_data,
                R.id.tv_notanumvenda,
                R.id.tv_nomecliente,
                R.id.nota_status,
                R.id.tv_notadescontovenda,
                R.id.tv_notavalorvenda};
        SimpleCursorAdapter adaptadore = new SimpleCursorAdapter(getBaseContext(),
                R.layout.list_nota,cursor,nomeCampos,idViews,0);
        listView.setAdapter(adaptadore);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position, long id){
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.FechaVendaEntry.NUMEROVENDA));
                Intent intent = new Intent(NotasActivity.this,ReceberNotaActivity.class);
                intent.putExtra("codigo",codigo);
                startActivity(intent);
                finish();
            }
        });
    }

}
