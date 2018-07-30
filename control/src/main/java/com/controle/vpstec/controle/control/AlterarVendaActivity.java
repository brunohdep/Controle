package com.controle.vpstec.controle.control;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.controle.vpstec.controle.control.db.BancoController;
import com.controle.vpstec.controle.control.db.ControleContract;

public class AlterarVendaActivity extends AppCompatActivity {
    BancoController bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_venda);
        int id = Integer.parseInt(this.getIntent().getStringExtra("id"));
        System.out.println("ID: "+id);
        bd = new BancoController(getBaseContext());
        Cursor cursor = bd.buscarVendasPorid(id);
        TextView desc = (TextView)findViewById(R.id.tv_vendaedtprod);
        TextView preco = (TextView)findViewById(R.id.tv_precoedtvenda);
        EditText qtd = (EditText) findViewById(R.id.edt_quantidade_alterar);

        desc.setText(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.VendaEntry.DESCRICAO)));
        preco.setText((cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.VendaEntry.PRECO))));
        qtd.setText(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.VendaEntry.QUANTIDADE_VENDA)));
    }
}
