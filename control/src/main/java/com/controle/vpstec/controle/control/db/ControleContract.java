package com.controle.vpstec.controle.control.db;

import android.provider.BaseColumns;

public class ControleContract {

    private ControleContract(){

    }

    public static class ProdutoEntry implements BaseColumns{
        public static final String TABLE_NAME = "produtos";
        public static final String DESCRICAO = "descricao";
        public static final String CODIGO = "cod";
        public static final String VALOR = "valor";
        public static final String QUANTIDADE ="quantidade";
        public static final String CUSTO = "custo";

    }

    public static class FechaVendaEntry implements BaseColumns{
        public static final String TABLE_NAME = "fechavenda";
        public static final String DATA = "data";
        public static final String NUMEROVENDA = "numerovenda";
        public static final String CLIENTE_NOME = "nome";
        public static final String STATUS = "status";
        public static final String DESCONTO = "desconto";
        public static final String VALOR = "valor";
    }

    public static class VendaEntry implements BaseColumns{
        public static final String TABLE_NAME = "venda";
        public static final String NUMEROVENDA = "numerovenda";
        public static final String CODPROD = "cod";
        public static final String QUANTIDADE_VENDA = "quantidadevenda";
        public static final String DESCRICAO = "descricao";
        public static final String PRECO = "preco";

    }



}
