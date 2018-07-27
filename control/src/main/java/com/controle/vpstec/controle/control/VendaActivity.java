package com.controle.vpstec.controle.control;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.controle.vpstec.controle.control.db.BancoController;
import com.controle.vpstec.controle.control.db.ControleContract;

import org.w3c.dom.Text;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class VendaActivity extends AppCompatActivity {
    ArrayList<Produto> arrayDeProdutos = new ArrayList<Produto>();
    Double total = 0.00;
    TextView totaltv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);
        //Autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, NomesProdutos(getBaseContext()));
        final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.tv_produto);
        textView.setAdapter(adapter);
        //fim autocomplete

        final EditText qtdtv = (EditText) findViewById(R.id.tv_quantidade_num);
        final TextView tvnumvenda = (TextView)findViewById(R.id.tv_venda_num);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int numeroVenda = Integer.valueOf(bundle.getString("venda"));
        BancoController banco = new BancoController(getBaseContext());
        if(numeroVenda==0){
            tvnumvenda.setText(String.valueOf(numero(getBaseContext())));
            System.out.println("NUMERO :" + numero(getBaseContext()));
            banco.deletarRegistrodeVenda(numero(getBaseContext()));
            Atualizar(numero(getBaseContext()));
        }else{
            Atualizar(numeroVenda);
            tvnumvenda.setText(String.valueOf(numeroVenda));
        }
        totaltv = (TextView) findViewById(R.id.total_valor);
        totaltv.setText(String.valueOf(total));
        Button adicionar = (Button) findViewById(R.id.bt_adicionar_prod);
        Button fechar = (Button)findViewById(R.id.bt_fechar_venda);
        Button adicionarnew = (Button)findViewById(R.id.bt_adicionar);
        adicionarnew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(),ProdutosVenda.class);
                startActivity(intent);
            }
        });
        adicionar.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                totaltv.setText(String.valueOf(total));
                //insere na venda
                BancoController inserir = new BancoController(getBaseContext());
                Cursor cursor = inserir.carregarDadoPorNome(textView.getText().toString());
                double valor = Integer.parseInt(qtdtv.getText().toString())*cursor.getDouble(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.VALOR));
                String result = inserir.inserirProdutoVenda(Integer.parseInt(tvnumvenda.getText().toString()),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.CODIGO)),
                        Integer.parseInt(qtdtv.getText().toString()),
                        cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.DESCRICAO)),
                        valor);
                Atualizar(Integer.parseInt(tvnumvenda.getText().toString()));
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }
        });
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),FinalizarVendaActivity.class);
                String valor = String.valueOf(total);
                Bundle bundle = new Bundle();
                bundle.putString("valor",valor);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public int numero(Context context){
        BancoController peganum = new BancoController(context);
        return peganum.contarVendas();
    }
    public void Atualizar(int idvenda){
            ListView listVenda = (ListView) findViewById(R.id.listview_venda);
            BancoController crud = new BancoController(getBaseContext());
            final Cursor cursor = crud.buscarVendas(idvenda);
            if(cursor.getCount()!=0){

                String[] nomeCampos = new String[]{
                        ControleContract.VendaEntry._ID,
                        ControleContract.VendaEntry.CODPROD,
                        ControleContract.VendaEntry.QUANTIDADE_VENDA,
                        ControleContract.VendaEntry.DESCRICAO,
                        ControleContract.VendaEntry.PRECO};
                int[] idViews = new int[]{
                        R.id.v_id,
                        R.id.v_codigo_num,
                        R.id.v_quantidade_num,
                        R.id.v_descricao_nome,
                        R.id.v_valor_num};
                SimpleCursorAdapter adaptadore = new SimpleCursorAdapter(getBaseContext(),
                        R.layout.item_venda, cursor, nomeCampos, idViews, 0);
                listVenda.setAdapter(adaptadore);
                if (cursor != null) {
                    cursor.moveToFirst();
                }
                System.out.println("Total inicial" + total);
                System.out.println("ID" + cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                total += Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow("preco")));
                System.out.println("Atual1 : " + total);
                while (cursor.moveToNext()) {
                    double preco = 0.00;
                    preco = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow("preco")));
                    System.out.println("Preco:" + preco);
                    if (preco != 0) {
                        System.out.println("ID" + cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                        total += preco;
                        System.out.println("Atual : " + total);
                    } else {
                        break;
                    }
                }

                //totaltv.setText(String.valueOf(total));

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent,View view,int position, long id){
//                String codigo;
//                cursor.moveToPosition(position);
//                codigo = cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry._ID));
//                Intent intent = new Intent(EstoqueActivity.this,AlterarActivity.class);
//                intent.putExtra("codigo",codigo);
//                startActivity(intent);
//                finish();
//            }
//        });
            }
    }
        public ProdutoAdapter produtos(String descricao,Context context, int quantidade){
        BancoController crud = new BancoController(context);
        Cursor cursor = crud.carregarDadoPorNome(descricao);
        Produto p = new Produto();
        p.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.DESCRICAO)));
        p.setCusto(cursor.getDouble(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.CUSTO)));
        p.setCod(cursor.getInt(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.CODIGO)));
        p.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.VALOR)));
        p.setQuantidade(quantidade);
        arrayDeProdutos.add(p);
        total+=quantidade*p.getValor();
        ProdutoAdapter adapter = new ProdutoAdapter(this,arrayDeProdutos);
        return  adapter;
    }

    public String[] NomesProdutos(Context context){
        BancoController crud = new BancoController(context);
        Cursor cursor;
        String[] campos = {
                ControleContract.ProdutoEntry._ID,
                ControleContract.ProdutoEntry.DESCRICAO,
                ControleContract.ProdutoEntry.CODIGO,
                ControleContract.ProdutoEntry.VALOR,
                ControleContract.ProdutoEntry.QUANTIDADE,
                ControleContract.ProdutoEntry.CUSTO};

        cursor = crud.listarProdutos();
        String[] array = new String[cursor.getCount()];
        if(cursor!=null){
            cursor.moveToFirst();
        }
        int i=0;

        while(cursor.moveToNext()){
            String nome = cursor.getString(cursor.getColumnIndex("descricao"));
            if(nome!= null) {
                array[i] = nome;
                i++;
            }
            else{
                break;
            }
        }
        array[array.length-1]="";
        return array;
    }

}
