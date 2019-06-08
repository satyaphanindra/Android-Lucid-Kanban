package com.citta.lucidkanban.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.citta.lucidkanban.R;
import com.citta.lucidkanban.data.Storage;
import com.citta.lucidkanban.model.DummyDataProvider;
import com.citta.lucidkanban.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskDetailActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner taskStatusDropdownBar;
    private static final String[] paths = {"NONE", "TODO", "IN PROGRESS", "COMPLETED"};
    private ImageView taskImage, closeTask, saveTask, taskDate, editTask;
    private TextView taskTitle, taskDescription;
    private List<Task> itemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        int position = getIntent().getExtras().getInt("itemNumber");
        taskImage = findViewById(R.id.task_image_bar);
        taskTitle = findViewById(R.id.task_title_view);
        taskDescription = findViewById(R.id.task_description_view);
        taskDate = findViewById(R.id.date_time_bar);
        taskStatusDropdownBar = (Spinner) findViewById(R.id.task_status_dropdown);
        saveTask = findViewById(R.id.save_task_bar);
        closeTask = findViewById(R.id.close_task_bar);
        editTask = findViewById(R.id.edit_task_bar);

        final Storage storage= new Storage(TaskDetailActivity.this);
//        List<Task> itemList = (List<Task>) storage.retrieve("File", Storage.Directory.Documents);

//        final Storage storage = new Storage(TaskDetailActivity.this);

        spinner();

        final Boolean isUserClickedExistingTask = getIntent().getExtras().getBoolean("isUserClickedExistingTask");

        if(isUserClickedExistingTask){
            showExistingTask();

            editTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskTitle.setFocusable(isUserClickedExistingTask);
                    taskTitle.setEnabled(isUserClickedExistingTask);
                    taskTitle.setClickable(isUserClickedExistingTask);
                    taskTitle.setFocusableInTouchMode(isUserClickedExistingTask);
                    taskTitle.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

//                    editTask.setVisibility(View.GONE);
//                    saveTask.setVisibility(View.VISIBLE);
                    saveTask.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveTask();
                        }
                    });
                }
            });
        }
        else {
        }

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = taskTitle.getText().toString();
                String description = taskDescription.getText().toString();

//                int taskId = itemList.size()+1;

//                itemList = new DummyDataProvider().tasks;

                itemList = Arrays.asList(new Task(1, title, description, "date"));
                storage.store(itemList, "File", Storage.Directory.Documents);


                int taskId = itemList.size();
                Toast toast = Toast.makeText(TaskDetailActivity.this, "Saved\n"+"Task Id: "+taskId, Toast.LENGTH_SHORT);
                toast.show();
                finish();

                /*List<Task> itemList = new DummyDataProvider().tasks;
                itemList.add(new Task("9", title, "c", "2/2/2020", 12));*/
            }
        });

        closeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


/*
        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storage.store(new DummyDataProvider().tasks, "File", Storage.Directory.Documents);
            }
        });
        closeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storage.retrieve("File", Storage.Directory.Documents);
            }
        });*/
    }

    //drop down list to ask user where to add the task(todo , inprogress, completed)
    public void spinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaskDetailActivity.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskStatusDropdownBar.setAdapter(adapter);
        taskStatusDropdownBar.setOnItemSelectedListener(this);
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
            case 3:
                // Whatever you want to happen when the thrid item gets selected
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void showExistingTask(){

//        List<Task> itemList = new DummyDataProvider().tasks;

        Storage storage= new Storage(TaskDetailActivity.this);
        List<Task> itemList = (List<Task>) storage.retrieve("File", Storage.Directory.Documents);

        int itemNumber =getIntent().getExtras().getInt("itemNumber");

        taskTitle.setText(itemList.get(itemNumber).taskTitle);
        taskDescription.setText(itemList.get(itemNumber).taskDescription);

        taskTitle.setFocusable(false);
//        taskTitle.setEnabled(false);
        taskTitle.setClickable(false);
        taskTitle.setFocusableInTouchMode(false);
        taskTitle.requestFocus();

        /*taskDescription.setFocusable(false);
//        taskTitle.setEnabled(false);
        taskDescription.setClickable(false);
        taskDescription.setFocusableInTouchMode(false);
        taskDescription.requestFocus();

        taskStatusDropdownBar.setFocusable(false);
//        taskTitle.setEnabled(false);
        taskStatusDropdownBar.setClickable(false);
        taskStatusDropdownBar.setFocusableInTouchMode(false);
        taskStatusDropdownBar.requestFocus();

        taskImage.setFocusable(false);
//        taskTitle.setEnabled(false);
        taskImage.setClickable(false);
        taskImage.setFocusableInTouchMode(false);
        taskImage.requestFocus();

        taskDate.setFocusable(false);
//        taskTitle.setEnabled(false);
        taskDate.setClickable(false);
        taskDate.setFocusableInTouchMode(false);
        taskDate.requestFocus();*/

        /*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);*/




        editTask.setVisibility(View.VISIBLE);
        saveTask.setVisibility(View.GONE);

    }

    public void saveTask(){
        Storage storage = new Storage(TaskDetailActivity.this);
        String title = taskTitle.getText().toString();
        itemList = Arrays.asList(new Task(1, taskTitle.getText().toString(), taskDescription.getText().toString(), "date"));
        storage.store(itemList, "File", Storage.Directory.Documents);

        Toast toast = Toast.makeText(TaskDetailActivity.this, "Task Saved", Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}
