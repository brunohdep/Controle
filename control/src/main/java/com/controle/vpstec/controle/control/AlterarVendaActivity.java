package com.controle.vpstec.controle.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AlterarVendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_venda);
        int id = Integer.parseInt(this.getIntent().getStringExtra("id"));
        int codigovenda = Integer.parseInt(this.getIntent().getStringExtra("numerovenda"));
        System.out.println("ID: "+id);
        System.out.println("Venda: "+codigovenda);
    }
}
