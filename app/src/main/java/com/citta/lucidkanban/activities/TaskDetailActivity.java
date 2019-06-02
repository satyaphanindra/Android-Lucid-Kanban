package com.citta.lucidkanban.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.citta.lucidkanban.R;
import com.citta.lucidkanban.model.DummyDataProvider;

public class TaskDetailActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private static final String[] paths = {"NO", "TODO", "IN PROGRESS", "COMPLETED"};
    private ImageView taskPhoto;
    private TextView taskTitle, taskDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        taskPhoto = findViewById(R.id.task_photo);
        taskTitle = findViewById(R.id.task_title_view);
        taskDescription = findViewById(R.id.task_description_view);

        DummyDataProvider data = new DummyDataProvider();
        spinner();

        Boolean isUserClickedNewTask = getIntent().getExtras().getBoolean("isUserClickedNewTask");

    }

    //drop down list to ask user where to add the task(todo , inprogress, completed)
    public void spinner(){
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaskDetailActivity.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected //todo
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void showExistingTask(){

    }
}
