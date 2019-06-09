package com.citta.lucidkanban.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.text.format.DateFormat;

import com.citta.lucidkanban.R;
import com.citta.lucidkanban.managers.TaskManager;
import com.citta.lucidkanban.model.Card;
import com.citta.lucidkanban.model.Task;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static com.citta.lucidkanban.fragments.TasksFragment.EXISTING_ID;

public class TaskDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Spinner taskStatusDropdownBar, taskPrioriyDropdownBar;
    /*private static final String[] paths = {"NONE", "TODO", "IN PROGRESS", "COMPLETED"};
    private static final String[] priority = {"LOW", "MEDIUM","HIGH"};*/
    private ImageView taskImage, closeTask, saveTask, taskDateTimePicker, editTask;
    private TextView taskTitle, taskDescription, taskDate, taskTime;
    private Task itemTask = null;
    private int year, month, day, hour, minute;
    private String existingTaskId = null;
    //private Boolean isUserClickedExistingTask = false;
//    private int yearFinal, monthFinal, dayFinal, hoursFinal, minuteFinal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        if (getIntent().getExtras() != null) {
            existingTaskId = getIntent().getExtras().getString(EXISTING_ID, null);
            //isUserClickedExistingTask = getIntent().getExtras().getBoolean("isUserClickedExistingTask", false);
        }

        taskImage = findViewById(R.id.task_image_bar);
        taskTitle = findViewById(R.id.task_title_view);
        taskDescription = findViewById(R.id.task_description_view);
        taskDateTimePicker = findViewById(R.id.date_time_bar);
        taskStatusDropdownBar = (Spinner) findViewById(R.id.task_status_dropdown);
        taskPrioriyDropdownBar = (Spinner) findViewById(R.id.task_priority_dropdown);
        saveTask = findViewById(R.id.save_task_bar);
        closeTask = findViewById(R.id.close_task_bar);
        editTask = findViewById(R.id.edit_task_bar);
        taskDate = findViewById(R.id.task_date_view);
        taskTime = findViewById(R.id.task_time_view);

        initSpinner();

        if ( existingTaskId!=null ) {
            showExistingTask();
            enableViews(false);
        }

        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableViews(true);
                taskTitle.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });


        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO date and others
                String title = taskTitle.getText().toString();
                String description = taskDescription.getText().toString();
                Card.CardPriority priority = (Card.CardPriority) taskPrioriyDropdownBar.getSelectedItem();
                Card.CardStatus status = (Card.CardStatus) taskStatusDropdownBar.getSelectedItem();

                if ( existingTaskId!=null ) {

                    itemTask.taskTitle = title;
                    itemTask.taskDescription = description;
                    itemTask.cardPriority = priority;
                    itemTask.cardStatus = status;

                    TaskManager.getInstance().replaceExistingTaskItem(itemTask.taskId, itemTask);

                } else {

                    String id = UUID.randomUUID().toString();
                    itemTask = new Task(id, title, description, "date", priority, status);
                    TaskManager.getInstance().addTaskItem(itemTask);
                }

                Toast.makeText(TaskDetailActivity.this, "Saved\n" + "Task Id: " + itemTask.taskId, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        closeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        taskDateTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskDetailActivity.this, TaskDetailActivity.this,
                        year, month, day);
                datePickerDialog.show();

                taskDate.setText(day + "-" + month + "-" + year);
                taskTime.setText(hour + ":" + minute);
            }
        });

    }

    private void enableViews(boolean enable) {

        if (enable) {
            taskTitle.setFocusable(true);
            taskTitle.setClickable(true);
            taskTitle.setFocusableInTouchMode(true);
            taskTitle.requestFocus();

            editTask.setVisibility(View.GONE);
            saveTask.setVisibility(View.VISIBLE);

        } else {

            taskTitle.setFocusable(false);
            taskTitle.setClickable(false);
            taskTitle.setFocusableInTouchMode(false);
            taskTitle.requestFocus();

            editTask.setVisibility(View.VISIBLE);
            saveTask.setVisibility(View.GONE);
        }
    }

    public void showExistingTask() {

        if (existingTaskId == null) return;

        itemTask = TaskManager.getInstance().getTaskWithId(existingTaskId);

        if (itemTask == null) return;

        // TODO date etc
        taskTitle.setText(itemTask.taskTitle);
        taskDescription.setText(itemTask.taskDescription);
        taskPrioriyDropdownBar.setSelection(TaskManager.getInstance().getPriorityNumber(itemTask.cardPriority));
    }

    //drop down list to ask user where to add the task(todo , inprogress, completed)
    public void initSpinner() {
        ArrayAdapter<Card.CardStatus> adapterCardStatus = new ArrayAdapter<Card.CardStatus>(TaskDetailActivity.this,
                android.R.layout.simple_spinner_item, Card.CardStatus.values());

        adapterCardStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskStatusDropdownBar.setAdapter(adapterCardStatus);
        taskStatusDropdownBar.setOnItemSelectedListener(this);

        ArrayAdapter<Card.CardPriority> adapterCardPriority = new ArrayAdapter<Card.CardPriority>(TaskDetailActivity.this,
                android.R.layout.simple_spinner_item, Card.CardPriority.values());

        adapterCardPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskPrioriyDropdownBar.setAdapter(adapterCardPriority);
        taskPrioriyDropdownBar.setOnItemSelectedListener(this);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;

        Calendar calendar = Calendar.getInstance();

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(TaskDetailActivity.this, TaskDetailActivity.this,
                hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        this.hour = hourOfDay;
        this.minute = minute;

    }
}




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
