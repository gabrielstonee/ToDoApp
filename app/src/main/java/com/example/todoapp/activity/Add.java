package com.example.todoapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.helper.TaskDAO;
import com.example.todoapp.model.Task;
import com.google.android.material.textfield.TextInputEditText;

public class Add extends AppCompatActivity {
    private TextInputEditText editTask;
    private Task actualTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        editTask = findViewById(R.id.textTask);

        //recover the intent passe through the MainActivity
        actualTask = (Task) getIntent().getSerializableExtra("selectedTask");

        //configure the textbox
        if(actualTask!=null){
            editTask.setText(actualTask.getTaskName());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actions_save:
                TaskDAO taskDAO = new TaskDAO(getApplicationContext());
                if(actualTask!=null){//edit

                    String nameTask = editTask.getText().toString();
                    if(!nameTask.isEmpty()){
                        Task task = new Task();
                        task.setTaskName(nameTask);
                        task.setId(actualTask.getId());
                        if(taskDAO.update(task)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Task sucessfully updated", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Task sucessfully updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{//save
                    String nameTask = editTask.getText().toString();
                    if(!nameTask.isEmpty()){
                        Task task = new Task();
                        task.setTaskName(nameTask);
                        if(taskDAO.save(task)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Saving was successful", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Saving Bumped into an Error", Toast.LENGTH_LONG).show();
                        }
                        taskDAO.save(task);
                        finish();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}