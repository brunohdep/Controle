package com.controle.vpstec.controle.control.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.controle.vpstec.controle.control.Produto;

import java.sql.SQLOutput;

public class BancoController {
    private SQLiteDatabase db;
    private ControleDbHelper banco;

    public BancoController(Context context){
        banco = new ControleDbHelper(context);
    }
    //Insere um produto
    public String inserirProduto(Produto produto){
        ContentValues valores;
        long resultado =-1;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(ControleContract.ProdutoEntry.DESCRICAO,produto.getDescricao());
        valores.put(ControleContract.ProdutoEntry.CODIGO,produto.getCod());
        valores.put(ControleContract.ProdutoEntry.VALOR,produto.getValor());
        valores.put(ControleContract.ProdutoEntry.QUANTIDADE,produto.getQuantidade());
        valores.put(ControleContract.ProdutoEntry.CUSTO,produto.getCusto());

        resultado=db.insert(ControleContract.ProdutoEntry.TABLE_NAME,null,valores);
        db.close();


        if(resultado ==-1){
            return "Não inseriu";
        }else{
            return "Inseriu";
        }
    }
    //Insere um produto na venda
    public String inserirProdutoVenda(int numerovenda,int codprod,int quantidade,String descricao,
                                      double preco){
        ContentValues valores;
        long resultado = -1;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(ControleContract.VendaEntry.NUMEROVENDA,numerovenda);
        valores.put(ControleContract.VendaEntry.CODPROD,codprod);
        valores.put(ControleContract.VendaEntry.QUANTIDADE_VENDA,quantidade);
        valores.put(ControleContract.VendaEntry.DESCRICAO,descricao);
        valores.put(ControleContract.VendaEntry.PRECO,preco);
        resultado =db.insert(ControleContract.VendaEntry.TABLE_NAME,null,valores);
        db.close();
        if(resultado ==-1){
            return "Não inseriu";
        }else{
            return "Inseriur";
        }

    }
    //Salva a Venda Finalizada
    public String inserirFinalizarVenda(String data,int numvenda,String clnome,int status,double desconto, double valor){
        ContentValues valores;
        long resultado = -1;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(ControleContract.FechaVendaEntry.DATA,data);
        valores.put(ControleContract.FechaVendaEntry.NUMEROVENDA,numvenda);
        valores.put(ControleContract.FechaVendaEntry.CLIENTE_NOME,clnome);
        valores.put(ControleContract.FechaVendaEntry.STATUS,status);
        valores.put(ControleContract.FechaVendaEntry.DESCONTO,desconto);
        valores.put(ControleContract.FechaVendaEntry.VALOR,valor);

        resultado = db.insert(ControleContract.FechaVendaEntry.TABLE_NAME,null,valores);
        db.close();
        if(resultado ==-1){
            return "Não Finalizou";
        }else{
            return "Finalizou";
        }
    }

    //Lista os produtos
    public Cursor listarProdutos(){
        Cursor cursor;
        String[] campos = {
                ControleContract.ProdutoEntry._ID,
                ControleContract.ProdutoEntry.DESCRICAO,
                ControleContract.ProdutoEntry.CODIGO,
                ControleContract.ProdutoEntry.VALOR,
                ControleContract.ProdutoEntry.QUANTIDADE,
                ControleContract.ProdutoEntry.CUSTO};
        db = banco.getReadableDatabase();
        cursor = db.query(ControleContract.ProdutoEntry.TABLE_NAME,campos,null,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    //Lista as vendas finalizadas
    public Cursor listarVendas(){
        Cursor cursor;
        String[] campos = {
                ControleContract.FechaVendaEntry._ID,
                ControleContract.FechaVendaEntry.DATA,
                ControleContract.FechaVendaEntry.NUMEROVENDA,
                ControleContract.FechaVendaEntry.CLIENTE_NOME,
                ControleContract.FechaVendaEntry.STATUS,
                ControleContract.FechaVendaEntry.DESCONTO,
                ControleContract.FechaVendaEntry.VALOR};
        db = banco.getReadableDatabase();
        cursor = db.query(ControleContract.FechaVendaEntry.TABLE_NAME,campos,null,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            System.out.println("FOI PRO COMEÇO");
        }
        db.close();
        return cursor;
    }
    //Lista as vendas finalizadas que não foram pagas
    public Cursor listarVendasPorStatus(){
        Cursor cursor;
        String[] campos = {
                ControleContract.FechaVendaEntry._ID,
                ControleContract.FechaVendaEntry.DATA,
                ControleContract.FechaVendaEntry.NUMEROVENDA,
                ControleContract.FechaVendaEntry.CLIENTE_NOME,
                ControleContract.FechaVendaEntry.STATUS,
                ControleContract.FechaVendaEntry.DESCONTO,
                ControleContract.FechaVendaEntry.VALOR};
        String where = ControleContract.FechaVendaEntry.STATUS + "=" + 0;
        db = banco.getReadableDatabase();
        cursor = db.query(ControleContract.FechaVendaEntry.TABLE_NAME,campos,where,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            System.out.println("FOI PRO COMEÇO");
        }
        db.close();
        return cursor;
    }
    //Busca a venda finalizada pelo numero da venda
    public Cursor listarVendaPorNumerodaVenda(int numvenda){
        Cursor cursor;
        String[] campos = {
                ControleContract.FechaVendaEntry._ID,
                ControleContract.FechaVendaEntry.DATA,
                ControleContract.FechaVendaEntry.NUMEROVENDA,
                ControleContract.FechaVendaEntry.CLIENTE_NOME,
                ControleContract.FechaVendaEntry.STATUS,
                ControleContract.FechaVendaEntry.DESCONTO,
                ControleContract.FechaVendaEntry.VALOR};
        String where = ControleContract.FechaVendaEntry.NUMEROVENDA+ "=" + numvenda;
        db = banco.getReadableDatabase();
        cursor = db.query(ControleContract.FechaVendaEntry.TABLE_NAME,campos,where,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            System.out.println("FOI PRO COMEÇO");
        }
        db.close();
        return cursor;
    }
    public Cursor listarProdutosVenda(){
        Cursor cursor;
        String[] campos = {
                ControleContract.ProdutoEntry._ID,
                ControleContract.ProdutoEntry.DESCRICAO,
                ControleContract.ProdutoEntry.VALOR,
                ControleContract.ProdutoEntry.QUANTIDADE};
        db = banco.getReadableDatabase();
        cursor = db.query(ControleContract.ProdutoEntry.TABLE_NAME,campos,null,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


    public Cursor carregarDadoById(int id){
        Cursor cursor;
        String[] campos = {
                ControleContract.ProdutoEntry._ID,
                ControleContract.ProdutoEntry.DESCRICAO,
                ControleContract.ProdutoEntry.CODIGO,
                ControleContract.ProdutoEntry.VALOR,
                ControleContract.ProdutoEntry.QUANTIDADE,
                ControleContract.ProdutoEntry.CUSTO};
        String where = ControleContract.ProdutoEntry._ID + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(ControleContract.ProdutoEntry.TABLE_NAME,campos,where,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public Cursor carregarPorcodigo(int codigo){
        Cursor cursor;
        String[] campos = {
                ControleContract.ProdutoEntry._ID,
                ControleContract.ProdutoEntry.DESCRICAO,
                ControleContract.ProdutoEntry.CODIGO,
                ControleContract.ProdutoEntry.VALOR,
                ControleContract.ProdutoEntry.QUANTIDADE,
                ControleContract.ProdutoEntry.CUSTO};
        String where = ControleContract.ProdutoEntry.CODIGO + "=" + codigo;
        db = banco.getReadableDatabase();
        cursor = db.query(ControleContract.ProdutoEntry.TABLE_NAME,campos,where,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public Cursor carregarDadoPorNome(String descricao){
        Cursor cursor;
        String[] campos = {
                ControleContract.ProdutoEntry._ID,
                ControleContract.ProdutoEntry.DESCRICAO,
                ControleContract.ProdutoEntry.CODIGO,
                ControleContract.ProdutoEntry.VALOR,
                ControleContract.ProdutoEntry.QUANTIDADE,
                ControleContract.ProdutoEntry.CUSTO};
        String where = ControleContract.ProdutoEntry.DESCRICAO + " like " + "'%"+descricao+"%'";
        db = banco.getReadableDatabase();
        cursor = db.query(ControleContract.ProdutoEntry.TABLE_NAME,campos,where,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public void alterarRegistro(int id,String descricao,int codigo,double valor,int quantidade,double custo){
        ContentValues valores;
        String where;
        db = banco.getWritableDatabase();
        where = ControleContract.ProdutoEntry._ID + "=" + id;
        valores = new ContentValues();
        valores.put(ControleContract.ProdutoEntry.DESCRICAO,descricao);
        valores.put(ControleContract.ProdutoEntry.CODIGO,codigo);
        valores.put(ControleContract.ProdutoEntry.VALOR,valor);
        valores.put(ControleContract.ProdutoEntry.QUANTIDADE,quantidade);
        valores.put(ControleContract.ProdutoEntry.CUSTO,custo);
        db.update(ControleContract.ProdutoEntry.TABLE_NAME,valores,where,null);
        db.close();
    }
    public void alterarRegistroVenda(int numvenda,double valor){
        ContentValues valores;
        String where;
        db = banco.getWritableDatabase();
        where = ControleContract.FechaVendaEntry.NUMEROVENDA + "=" + numvenda;
        valores = new ContentValues();
        valores.put(ControleContract.FechaVendaEntry.VALOR,valor);

        db.update(ControleContract.FechaVendaEntry.TABLE_NAME,valores,where,null);
        db.close();
    }
    public void receberVenda(int numvenda){
        ContentValues valores;
        String where;
        db = banco.getWritableDatabase();
        where = ControleContract.FechaVendaEntry.NUMEROVENDA + "=" + numvenda;
        valores = new ContentValues();
        valores.put(ControleContract.FechaVendaEntry.STATUS,1);
        db.update(ControleContract.FechaVendaEntry.TABLE_NAME,valores,where,null);
        db.close();
    }

    public void deletarRegistro(int id){
        String where = ControleContract.ProdutoEntry._ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(ControleContract.ProdutoEntry.TABLE_NAME,where,null);
        db.close();
    }

    public void deletarRegistrodeVenda(int id){
        String where = ControleContract.VendaEntry.NUMEROVENDA + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(ControleContract.VendaEntry.TABLE_NAME,where,null);
        db.close();
    }
    public void deletarRegistrodeVendaporid(int id){
        String where = ControleContract.VendaEntry._ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(ControleContract.VendaEntry.TABLE_NAME,where,null);
        db.close();
    }

    //FimVenda

    public int contarVendas(){
        int numvendas = -1;
        db = banco.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("Select count(*) from fechavenda", null);
            cursor.moveToFirst();
            if(cursor!= null){
                numvendas =cursor.getInt(0);
                numvendas++;
            }else{
                numvendas = 1;
            }
        }catch (Exception e){
            System.out.println(e);
            numvendas = 1;
        }
        db.close();
        return numvendas;
    }

    public Cursor buscarVendas(int numerovenda){
        Cursor cursor;
        String[] campos = {
                ControleContract.VendaEntry._ID,
                ControleContract.VendaEntry.CODPROD,
                ControleContract.VendaEntry.QUANTIDADE_VENDA,
                ControleContract.VendaEntry.DESCRICAO,
                ControleContract.VendaEntry.PRECO};
        String where = ControleContract.VendaEntry.NUMEROVENDA + "=" + numerovenda;
        db=banco.getReadableDatabase();
        cursor = db.query(ControleContract.VendaEntry.TABLE_NAME,campos,where,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public Cursor buscarVendasPorid(int id){
        Cursor cursor;
        String[] campos = {
                ControleContract.VendaEntry._ID,
                ControleContract.VendaEntry.CODPROD,
                ControleContract.VendaEntry.QUANTIDADE_VENDA,
                ControleContract.VendaEntry.DESCRICAO,
                ControleContract.VendaEntry.PRECO};
        String where = ControleContract.VendaEntry._ID + "=" + id;
        db=banco.getReadableDatabase();
        cursor = db.query(ControleContract.VendaEntry.TABLE_NAME,campos,where,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public Cursor buscarVendasPoridFull(int id){
        Cursor cursor;
        String[] campos = {
                ControleContract.VendaEntry._ID,
                ControleContract.VendaEntry.NUMEROVENDA,
                ControleContract.VendaEntry.CODPROD,
                ControleContract.VendaEntry.QUANTIDADE_VENDA,
                ControleContract.VendaEntry.DESCRICAO,
                ControleContract.VendaEntry.PRECO};
        String where = ControleContract.VendaEntry._ID + "=" + id;
        db=banco.getReadableDatabase();
        cursor = db.query(ControleContract.VendaEntry.TABLE_NAME,campos,where,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public boolean verificarEstoque(int quantidade,int codigo){
        Cursor cursor = carregarPorcodigo(codigo);
        if(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ControleContract.ProdutoEntry.QUANTIDADE)))>=quantidade){
            return true;
        }else{
            return false;
        }
    }
}
