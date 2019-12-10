package com.eci.cosw.taskplanner.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eci.cosw.taskplanner.Model.Task;
import com.eci.cosw.taskplanner.Model.User;
import com.eci.cosw.taskplanner.R;
import com.eci.cosw.taskplanner.Service.TaskService;
import com.eci.cosw.taskplanner.Service.UserService;
import com.eci.cosw.taskplanner.Util.RetrofitHttp;
import com.eci.cosw.taskplanner.Util.SharedPreference;
import com.eci.cosw.taskplanner.Util.TaskAdapter;
import com.eci.cosw.taskplanner.db.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TOKEN_KEY;
    private String file;
    private String USER_LOGGED;

    private User user;
    private List<Task> taskList;

    private SharedPreference sharedPreference;

    private RetrofitHttp retrofitHttp;

    private UserService userService;
    private TaskService taskService;

    private AppDatabase appDatabase;

    private final ExecutorService executorService = Executors
            .newFixedThreadPool(1);

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TOKEN_KEY = getString(R.string.token_key);
        file = getString(R.string.preference_file_key);

        sharedPreference = new SharedPreference(MainActivity.this, file);

        USER_LOGGED = (String) sharedPreference.getValue(getString(R.string.user_logged));

        retrofitHttp = new RetrofitHttp(sharedPreference
                .getValue(getString(R.string.token_key)));

        userService = retrofitHttp.getRetrofit().create(UserService.class);
        taskService = retrofitHttp.getRetrofit().create(TaskService.class);

        appDatabase = AppDatabase.getInMemoryDatabase(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        taskAdapter = new TaskAdapter();

        obtainUserInfo();
    }

    public void obtainUserInfo() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<User> userResponse = userService.getUserByEmail(USER_LOGGED).execute();
                    if (userResponse.isSuccessful()) {
                        user = userResponse.body();
                    }
                    Response<List<Task>> response = taskService.userTasks(user.getId()).execute();
                    if (response.isSuccessful()) {
                        taskList = response.body();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(taskAdapter);
                            taskAdapter.updateTasks(taskList);
                        }
                    });

                    //            saveTasks();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void saveTasks() {
        for (Task task : taskList) {
            appDatabase.taskModel().insertTask(task);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logOut() {
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(TOKEN_KEY);
        editor.commit();

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
