package com.citta.lucidkanban.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

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

public class TaskDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Spinner taskStatusDropdownBar, taskPrioriyDropdownBar;
    /*private static final String[] paths = {"NONE", "TODO", "IN PROGRESS", "COMPLETED"};
    private static final String[] priority = {"LOW", "MEDIUM","HIGH"};*/
    private ImageView taskImage, closeTask, saveTask, taskDateTimePicker, editTask, displayImage;
    private TextView taskTitle, taskDescription, taskDate, taskTime;
    private Task itemTask;
    private int year, month, day, hour, minute;
    private String timeSelected;
    private StringBuilder dateSelected;
    private int REQUEST_CAMERA=1, SELECT_FILE=0;


//    private int yearFinal, monthFinal, dayFinal, hoursFinal, minuteFinal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
//        int position = getIntent().getExtras().getInt("itemNumber");
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
        displayImage =(ImageView) findViewById(R.id.selected_image);

        spinner();

        final Boolean isUserClickedExistingTask = getIntent().getExtras().getBoolean("isUserClickedExistingTask");

        if (isUserClickedExistingTask) {
            showExistingTask();

            editTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fieldEditMode(taskTitle, true);
                    fieldEditMode(taskDescription, true);
                    fieldEditMode(taskStatusDropdownBar, true);
                    fieldEditMode(taskPrioriyDropdownBar, true);
                    fieldEditMode(taskImage, true);
                    fieldEditMode(taskDateTimePicker, true);

                    editTask.setVisibility(View.GONE);
                    saveTask.setVisibility(View.VISIBLE);
                }
            });
        } else {
        }

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = taskTitle.getText().toString();
                String description = taskDescription.getText().toString();
                if(!((title.isEmpty()) || (description.isEmpty())))
                {
                    itemTask = new Task(1, title, description, dateSelected, timeSelected);

                    TaskManager.getInstance().addTaskItem(itemTask);
                    Toast toast = Toast.makeText(TaskDetailActivity.this, "Saved\n" + "Task Id: " + itemTask.taskId, Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }

                else {
                    Toast toast = Toast.makeText(TaskDetailActivity.this, "Please enter all fields", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        displayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();

            }
        });

        taskDateTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (year == 0 || month == 0 || day == 0) {
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }

                DatePickerDialog mDatePicker = new DatePickerDialog(TaskDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        year = selectedYear;
                        month = selectedMonth;
                        day = selectedDay;
                        dateSelected = new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day);
                        taskDate.setText(dateSelected);
//                        dateSelected = d.toString();

                        Calendar calendar = Calendar.getInstance();
                        hour = calendar.get(Calendar.HOUR_OF_DAY);
                        minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(TaskDetailActivity.this, TaskDetailActivity.this,
                                hour, minute, DateFormat.is24HourFormat(TaskDetailActivity.this));
                        timePickerDialog.show();
                    }
                }, year, month, day);
                mDatePicker.getDatePicker();
                mDatePicker.show();
            }
        });

        closeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo

                String title = taskTitle.getText().toString();
                String description = taskDescription.getText().toString();
                Boolean check = getIntent().getExtras().getBoolean("isUserClickedExistingTask");
                if((title.isEmpty()) & (description.isEmpty())){
                    finish();
                    }
                else if(check){finish();}
                else {
                    AlertDialog.Builder build= new AlertDialog.Builder(TaskDetailActivity.this);
                    build.setTitle("Discard data entered?");
                    build.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    build.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog= build.create();
                    dialog.show();
                }
            }
        });

    }

    public void showExistingTask() {

        List<Task> itemList = TaskManager.getInstance().tasksList;

        int itemNumber = getIntent().getExtras().getInt("itemNumber");

        taskTitle.setText(itemList.get(itemNumber).taskTitle);
        taskDescription.setText(itemList.get(itemNumber).taskDescription);
        taskDate.setText(itemList.get(itemNumber).taskDate);
        taskTime.setText(itemList.get(itemNumber).taskTime);

        fieldEditMode(taskTitle, false);
        fieldEditMode(taskDescription, false);
        fieldEditMode(taskDate, false);
        fieldEditMode(taskTime, false);
        fieldEditMode(taskStatusDropdownBar, false);
        fieldEditMode(taskPrioriyDropdownBar, false);
        fieldEditMode(taskImage, false);
        fieldEditMode(taskDateTimePicker, false);

        editTask.setVisibility(View.VISIBLE);
        saveTask.setVisibility(View.GONE);

    }

    public void fieldEditMode(View id, Boolean isNonEditMode) {
        if (id == taskTitle || id == taskDescription) {
            id.setFocusable(isNonEditMode);

            id.setEnabled(isNonEditMode);
            id.setClickable(isNonEditMode);
            id.setFocusableInTouchMode(isNonEditMode);
            id.requestFocus();

            if (!isNonEditMode) {
                id.setBackgroundResource(R.color.verylightGray);
            } else {
                id.setBackgroundResource(R.color.white);
            }
        } else if (id == taskStatusDropdownBar || id == taskPrioriyDropdownBar) {
            id.setEnabled(isNonEditMode);

            if (!isNonEditMode) {
                id.setBackgroundResource(R.color.verylightGray);
            } else {
                id.setBackgroundResource(R.color.white);
            }
        } else if (id == taskImage || id == taskDateTimePicker) {
            id.setEnabled(isNonEditMode);
        }
    }

    private void SelectImage(){

        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(TaskDetailActivity.this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
//                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                displayImage.setImageBitmap(bmp);

            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                displayImage.setImageURI(selectedImageUri);
            }

        }

    }

/*
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == REQUEST_CAMERA){
                    Uri selectedImage = imageReturnedIntent.getData();
                    displayImage.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == SELECT_FILE){
                    Uri selectedImage = imageReturnedIntent.getData();
                    displayImage.setImageURI(selectedImage);
                }
                break;
        }
    }
*/

    public void saveTask() {
        TaskManager.getInstance().addTaskItem(itemTask);
        Toast toast = Toast.makeText(TaskDetailActivity.this, "Task Saved", Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }

    //drop down list to ask user where to add the task(todo , inprogress, completed)
    public void spinner() {
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
                // Whatever you want to happen when the third item gets selected
                break;
            case 3:
                // Whatever you want to happen when the third item gets selected
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        this.hour = hourOfDay;
        this.minute = minute;
        timeSelected = String.format("%02d:%02d %s", hour, minute, hourOfDay < 12 ? "am" : "pm");
        taskTime.setText(timeSelected);
    }
}
