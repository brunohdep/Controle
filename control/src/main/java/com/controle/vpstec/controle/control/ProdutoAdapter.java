package com.controle.vpstec.controle.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProdutoAdapter extends ArrayAdapter<Produto> {
    public ProdutoAdapter(Context context, ArrayList<Produto> produtos){
        super(context,0,produtos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Produto produto = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_venda,parent,false);
        }
        //TextView tvid = (TextView) convertView.findViewById(R.id.p_id_num);
        TextView tvCod = (TextView) convertView.findViewById(R.id.v_codigo_num);
        TextView tvValor = (TextView) convertView.findViewById(R.id.v_valor_num);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.v_descricao_nome);
        TextView tvQtd = (TextView) convertView.findViewById(R.id.v_quantidade_num);
        //TextView tvCust = (TextView) convertView.findViewById(R.id.p_custo_num);

        //tvid.setText("2958");
        tvCod.setText(String.valueOf(produto.getCod()));
        tvValor.setText("R$: "+String.valueOf(produto.getValor()));
        tvDesc.setText(produto.getDescricao());
        tvQtd.setText(String.valueOf(produto.getQuantidade()));
        //tvCust.setText(String.valueOf(produto.getCusto()));
//        tvCod.setText("123455");
//        tvValor.setText("2,50");
//        tvDesc.setText("Camisa Preta");
//        tvQtd.setText("200");
        return convertView;
    }
}
