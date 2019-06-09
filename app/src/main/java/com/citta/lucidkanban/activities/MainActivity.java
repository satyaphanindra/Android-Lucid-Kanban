package com.citta.lucidkanban.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.citta.lucidkanban.fragments.TasksFragment;
import com.citta.lucidkanban.R;
import com.citta.lucidkanban.managers.TaskManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_task_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
                startActivity(intent);

            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_tasks);
        setNavItem(R.id.nav_tasks);

        //navigationView.setCheckedItem(R.id.nav_tasks);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {
            showDeleteAllWarningDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        setNavItem(id);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavItem(int id) {
        switch (id){
            case R.id.nav_tasks:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TasksFragment()).commit();
                    break;

                case R.id.nav_share:
                    openSharingMenuWindow();
                    break;

                case R.id.nav_feedback:
                    openFeedbackWindow();
                    break;
        }
    }

    protected void openFeedbackWindow() {

        String to = "cittagroup@gmail.com";
        String subject = "Feedback for Lucid Kanban App";
        String message = "Thank you for taking interest in providing feeedback!\n\n Please provide your feedback here.\n";

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        // need this to prompts email client only
        email.setType("message/rfc822");
        try {
            startActivity(Intent.createChooser(email, "Choose an Email client :"));
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }

    private void openSharingMenuWindow() {
        Intent intentInvite = new Intent(Intent.ACTION_SEND);
        intentInvite.setType("text/plain");
        String link = "Enjoy free Note saving and tracking app\n\nhttps://play.google.com/store/apps/details?id=com.citta.lucidkanban";
        String subject = "Lucid Kanban App";
        intentInvite.putExtra(Intent.EXTRA_SUBJECT, subject);
        intentInvite.putExtra(Intent.EXTRA_TEXT, link);
        startActivity(Intent.createChooser(intentInvite, "Share using"));
    }

    private void showDeleteAllWarningDialog() {

            AlertDialog.Builder build= new AlertDialog.Builder(this);
            build.setTitle("Are you sure you want to delete all your tasks?");
            build.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TaskManager.getInstance().removeAllTasks();
                    fragmentInterfaceListener.onDeleteClicked();
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

    public OnDeleteAllClickListener fragmentInterfaceListener;

    public interface OnDeleteAllClickListener{
        void onDeleteClicked();
    }
}




/********if (id == R.id.nav_camera) {
 // Handle the camera action
 } else if (id == R.id.nav_gallery) {

 }
 //else if (id == R.id.nav_slideshow) {
 //} else if (id == R.id.nav_manage) {
 // }
 else if (id == R.id.nav_share) {

 } else if (id == R.id.nav_send) {

 }*********/