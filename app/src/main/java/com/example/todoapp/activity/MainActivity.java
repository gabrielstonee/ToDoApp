package com.example.todoapp.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.todoapp.R;
import com.example.todoapp.helper.DBHelper;
import com.example.todoapp.helper.RecyclerItemClickListener;
import com.example.todoapp.adapter.TaskAdapter;
import com.example.todoapp.helper.TaskDAO;
import com.example.todoapp.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewList;
    private TaskAdapter taskAdapter;
    private List<Task> taskList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerViewList = findViewById(R.id.recyclerView);

        DBHelper db = new DBHelper(getApplicationContext());
        ContentValues cv = new ContentValues();
        //a contentvalues nos permite slvar itens como se fossem um array
        db.getWritableDatabase().insert("tasks", null, cv);
                //getWRItable pra escrever no bd

        recyclerViewList.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerViewList,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //recover to edit the task:
                        Task selectedTask = taskList.get(position);
                        Intent intent = new Intent(MainActivity.this, Add.class);
                        intent.putExtra("selectedTask", selectedTask);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        final Task selectedTask = taskList.get(position);
                        Toast.makeText(getApplicationContext(), selectedTask.getTaskName().toString(), Toast.LENGTH_LONG).show();
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        //confugure the title and message
                        dialog.setTitle("Confirm exclusion?");
                        dialog.setMessage("Do you want to delete the task? " + selectedTask.getTaskName() + "?");
                        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TaskDAO taskDAO = new TaskDAO(getApplicationContext());
                                if(taskDAO.delete(selectedTask)){
                                    LoadList();
                                    Toast.makeText(getApplicationContext(), "Task sucessfully deleted", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Task could not be deleted", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.setNegativeButton("No", null);
                        //o null não vai dar nenhum evento caso apertado
                        dialog.create();
                        dialog.show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Add.class);
                startActivity(intent);
            }
        });
    }

    public void LoadList(){
        //list tasks
        TaskDAO taskDAO = new TaskDAO(getApplicationContext());
        taskList = taskDAO.toList();


        //configure the adapter
        taskAdapter = new TaskAdapter(taskList);

        //configure recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewList.setLayoutManager(layoutManager);
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewList.setAdapter(taskAdapter);
    }

    @Override
    protected void onStart() {
        //é o método do ciclo de vida de um activity, só assim carrega o recyclerview
        LoadList();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}