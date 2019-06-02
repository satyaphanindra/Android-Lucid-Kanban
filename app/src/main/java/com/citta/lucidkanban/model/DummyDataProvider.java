package com.citta.lucidkanban.model;

import com.citta.lucidkanban.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DummyDataProvider {

    public List<Member> members = Arrays.asList(new Member("Neo1", "neo1@gmail.com", "1", R.drawable.task_person),
            new Member("Neo2", "neo2@gmail.com", "2", R.drawable.task_person));

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); // yyyy/MM/dd HH:mm:ss
    Date date = new Date();
	//System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43


    public List<Task> tasks = Arrays.asList(new Task("1", "SampleTask1", "this is a sample task 1", dateFormat.format(date), 12 ),
            new Task("2", "SampleTask2", "this is a sample task 2", dateFormat.format(date), 12 ));
}
