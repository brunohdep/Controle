package com.controle.vpstec.controle.control.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControleDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;

    static final String DATABASE_NAME = "controle.db";

    public ControleDbHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        System.out.println("Produtos");
        final String SQL_CREATE_PRODUTO =
                        "CREATE TABLE " + ControleContract.ProdutoEntry.TABLE_NAME +" ("  +
                                ControleContract.ProdutoEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        ControleContract.ProdutoEntry.DESCRICAO + " TEXT NOT NULL,"+
                        ControleContract.ProdutoEntry.CODIGO + " INTEGER NOT NULL," +
                        ControleContract.ProdutoEntry.VALOR + " REAL NOT NULL," +
                        ControleContract.ProdutoEntry.QUANTIDADE + " INTEGER NOT NULL," +
                        ControleContract.ProdutoEntry.CUSTO + " REAL NOT NULL" + " )";
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUTO);

        System.out.println("Vendas");

        final String SQL_CREATE_VENDA =
                "CREATE TABLE IF NOT EXISTS " + ControleContract.VendaEntry.TABLE_NAME + " ("+
                        ControleContract.VendaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ControleContract.VendaEntry.NUMEROVENDA +" INTEGER NOT NULL,"+
                        ControleContract.VendaEntry.CODPROD +" INTEGER NOT NULL,"+
                        ControleContract.VendaEntry.QUANTIDADE_VENDA+" INTEGER NOT NULL," +
                        ControleContract.VendaEntry.DESCRICAO + " TEXT NOT NULL," +
                        ControleContract.VendaEntry.PRECO + " REAL NOT NULL" + " )";
        sqLiteDatabase.execSQL(SQL_CREATE_VENDA);

        System.out.println("Finaliza venda");
        final String SQL_CREATE_FECHAVENDA =
                "CREATE TABLE IF NOT EXISTS " + ControleContract.FechaVendaEntry.TABLE_NAME + " ("+
                        ControleContract.FechaVendaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ControleContract.FechaVendaEntry.CLIENTE_NOME + " TEXT NOT NULL,"+
                        ControleContract.FechaVendaEntry.DATA + " TEXT NOT NULL,"+
                        ControleContract.FechaVendaEntry.NUMEROVENDA + " INTEGER NOT NULL," +
                        ControleContract.FechaVendaEntry.DESCONTO + " REAL NOT NULL,"+
                        ControleContract.FechaVendaEntry.STATUS + " INTEGER NOT NULL," +
                        ControleContract.FechaVendaEntry.VALOR + " REAL NOT NULL" + " )";
        sqLiteDatabase.execSQL(SQL_CREATE_FECHAVENDA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int oldVersion, int newVersion){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ControleContract.ProdutoEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ControleContract.VendaEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ControleContract.FechaVendaEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

}
