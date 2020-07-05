package com.example.todoapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.todoapp.adapter.TaskAdapter;
import com.example.todoapp.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements  ITaskDAO{
    private SQLiteDatabase write;
    private SQLiteDatabase read;
    private TaskAdapter taskAdapter;

    public TaskDAO(Context context) {
        DBHelper db = new DBHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean save(Task task) {
        ContentValues cv = new ContentValues();
        cv.put("name", task.getTaskName());
        //we don't need to put the id cuz it is automtic incremented

        //THE FIRST PARAMETER IS THE KEY, THE SECOND THE VALUE TO THE KEY
        try{
            write.insert(DBHelper.TABLE_NAME, null, cv);
            Log.e("INFO", "SAVED");
        }catch (Exception e){
            Log.e("INFO", "erro ao salvar");
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Task task) {
        ContentValues cv = new ContentValues();
        cv.put("name", task.getTaskName());
        //we don't need to put the id cuz it is automtic incremented

        //THE FIRST PARAMETER IS THE KEY, THE SECOND THE VALUE TO THE KEY
        try{
            String[] args = {task.getId().toString()};
            //this args we use to substitute whether we want to change sth
            write.update(DBHelper.TABLE_NAME, cv, "id = ?", args);
            //this ? changes with the args, if you put id=?, name = ?, it will trade for each arguments in args
            Log.e("INFO", "SAVED");
        }catch (Exception e){
            Log.e("INFO", "erro ao salvar");
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Task task) {
        ContentValues cv = new ContentValues();
        cv.put("name", task.getTaskName());
        //we don't need to put the id cuz it is automtic incremented

        //THE FIRST PARAMETER IS THE KEY, THE SECOND THE VALUE TO THE KEY
        try{
            String[] args = {task.getId().toString()};
            //this args we use to substitute whether we want to change sth
            delete(task);
            write.delete(DBHelper.TABLE_NAME, "id = ?", args);
            //this ? changes with the args, if you put id=?, name = ?, it will trade for each arguments in args
        }catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public List<Task> toList() {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM " +
                DBHelper.TABLE_NAME + " ;";
        Cursor cursor = read.rawQuery(sql, null);
        while(cursor.moveToNext()){
            Task task = new Task();
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String name = cursor.getString((cursor.getColumnIndex("name")));
            task.setId(id);
            task.setTaskName(name);
            taskList.add(task);
        }
        return taskList;
    }
    //classe usada pra salvar dados, um padrão recomendado


}
