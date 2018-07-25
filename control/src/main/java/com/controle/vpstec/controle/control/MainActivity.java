package com.controle.vpstec.controle.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mostraEstoque(View view){
        Intent intent = new Intent(getBaseContext(),EstoqueActivity.class);
        startActivity(intent);
    }

    public void fazerVenda(View view){
        Intent intent = new Intent(getBaseContext(),VendaActivity.class);
        startActivity(intent);
    }
    public void mostrarVendas(View view){
        Intent intent = new Intent(getBaseContext(),ListarVendasActivity.class);
        startActivity(intent);
    }

    public void mostrarCaixa(View view){
        Intent intent = new Intent(getBaseContext(),FluxoCaixaActivity.class);
        startActivity(intent);
    }
    public void mostrarNotas(View view){
        Intent intent = new Intent(getBaseContext(),NotasActivity.class);
        startActivity(intent);
    }
}
