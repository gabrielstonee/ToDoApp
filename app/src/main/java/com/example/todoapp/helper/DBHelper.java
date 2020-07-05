package com.example.todoapp.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String NAME_DB = "DB_TASK";
    public static  String TABLE_NAME = "tasks";
    public DBHelper(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //PRA CRIAR a primeira vez o banco de dados
        //esse método só vai ser rodado apenas uma vez, quando o usuário instalar o app
        //quando eu fizer tudo, e eu lancei uma nov versão do app, eu vou lá em cima e mudo a versão
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
        " name TEXT NOT NULL);";
        try{
            db.execSQL(sql);
            Log.i("sucesso", "sokokoojo");
        }catch (Exception e){
            Log.i("msf ", "sjsokok");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ele já tem inst mas tá atualizando os dados
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + " ; ";
        try{

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
