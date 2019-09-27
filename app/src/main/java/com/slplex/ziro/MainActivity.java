package com.slplex.ziro;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.slplex.ziro.home.HomeAdapter;
import com.slplex.ziro.home.HomePresenter;
import com.slplex.ziro.home.ToDoModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity implements HomePresenter.HomeView, View.OnClickListener,HomeAdapter.HomeInterface{
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    HomeAdapter homeAdapter;
    HomePresenter presenter;
    TextView noTask;
    String name,phone;
    String TAG="MainDebug";
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        floatingActionButton = findViewById(R.id.add_btn);
        noTask= findViewById(R.id.no_task);
        recyclerInit();
        floatingActionButton.setOnClickListener(this::onClick);
        presenter = new HomePresenter(this,this);

    }

    void recyclerInit() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        homeAdapter = new HomeAdapter(this);
        recyclerView.setAdapter(homeAdapter);
        drwaerInit();

    }
    void drwaerInit() {

        DrawerLayout drawer = findViewById(R.id.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this,drawer,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        View headerLayout = navigationView.getHeaderView(0);
        TextView name = headerLayout.findViewById(R.id.name);
        TextView phone = headerLayout.findViewById(R.id.phone);
        name.setText(getIntent().getStringExtra("name"));
        phone.setText(getIntent().getStringExtra("phone"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void add(ToDoModel toDoModel) {
        homeAdapter.getToDoModels().add(toDoModel);
        homeAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
        noTask.setVisibility(View.GONE);


    }

    @Override
    public void delete(ToDoModel toDoModel, int pos) {
        homeAdapter.getToDoModels().remove(pos);
        homeAdapter.notifyDataSetChanged();
        if (homeAdapter.getToDoModels().size()==0)
        {
            recyclerView.setVisibility(View.GONE);
            noTask.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void update(ToDoModel toDoModel, int pos) {
        homeAdapter.getToDoModels().get(pos).setDate(toDoModel.getDate());
        homeAdapter.getToDoModels().get(pos).setTitle(toDoModel.getTitle());
        homeAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add_btn:
                addClick();
                break;

        }
    }

    void addClick() {
        final Calendar resultDateCalender = Calendar.getInstance();
        final Calendar resultTimeCalender = Calendar.getInstance();
        View v = getLayoutInflater().inflate(R.layout.dialog_to_do, null);
        final EditText title = v.findViewById(R.id.title);
        final TextView date = v.findViewById(R.id.date);
        Button ok = v.findViewById(R.id.ok);
        Button cancel = v.findViewById(R.id.cancel);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(v);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                                resultDateCalender.set(Calendar.MONTH, month);
                                resultDateCalender.set(Calendar.YEAR, year);
                                resultDateCalender.set(Calendar.DAY_OF_MONTH, day);
                                TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                                                 @Override
                                                                 public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                                                     resultDateCalender.set(Calendar.HOUR, hourOfDay);
                                                                     resultDateCalender.set(Calendar.MINUTE, minute);
                                                                     date.setText(getFormatDate(resultDateCalender));

                                                                 }
                                                             },
                                        resultDateCalender.get(Calendar.HOUR),
                                        resultDateCalender.get(Calendar.MINUTE), true)
                                .show(getSupportFragmentManager(),"");


                            }
                        }
                        , resultTimeCalender.get(Calendar.YEAR), resultTimeCalender.get(Calendar.MONTH),
                        resultTimeCalender.get(Calendar.DAY_OF_MONTH))
                .show(getSupportFragmentManager(),"date");
            }
        });
        AlertDialog alertDialog = builder.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Add title please", Toast.LENGTH_LONG).show();
                    return;
                }

                if (date.getText().toString().equals(getResources().getString(R.string.date))) {
                    Toast.makeText(getApplicationContext(), "choose date please", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.i(TAG, "onClick: ");
                ToDoModel toDoModel = new ToDoModel();
                toDoModel.setDate(getFormatDate(resultDateCalender));
                toDoModel.setTitle(title.getText().toString());
                presenter.addTodo(toDoModel);
            }
        });

    }

    String getFormatDate(Calendar calendar) {
//        //DatePick
//
//        Calendar today = Calendar.getInstance();
//        Calendar calendar=Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH,dayOfMounth);
//        calendar.set(Calendar.MONTH,mounthOfYere);
//        calendar.set(Calendar.YEAR,year);
//        int res= calendar.compareTo(today);
//        if(res==0)
//        {
//            return getResources().getString(R.string.today);
//        }
//        today.add(Calendar.DAY_OF_MONTH,1);
//         res= calendar.compareTo(today);
//         if(res==0)
//         {
//             return getResources().getString(R.string.tomorrow);
//         }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return simpleDateFormat.format(calendar.getTime());

    }

    @Override
    public void inzlize(List<ToDoModel> models) {
        homeAdapter.setToDoModels(models);
        if(models.size()==0)
        {
            noTask.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onItemChecked(ToDoModel model, int pos) {
        presenter.deleteTodo(model,pos);
    }

    @Override
    public void onItemClicked(ToDoModel model, int pos) {
        addClick(model.getTitle(),model.getDate(),pos);
    }

    void addClick(String titleString,String dateString,int pos) {
        final Calendar resultDateCalender = Calendar.getInstance();
        final Calendar resultTimeCalender = Calendar.getInstance();
        View v = getLayoutInflater().inflate(R.layout.dialog_to_do, null);
        final EditText title = v.findViewById(R.id.title);
        final TextView date = v.findViewById(R.id.date);
        Button ok,cancel;
        ok=v.findViewById(R.id.ok);
        cancel= v.findViewById(R.id.cancel);
        title.setText(titleString);
        date.setText(dateString);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(v);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                                resultDateCalender.set(Calendar.MONTH, month);
                                resultDateCalender.set(Calendar.YEAR, year);
                                resultDateCalender.set(Calendar.DAY_OF_MONTH, day);
                                TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                                                 @Override
                                                                 public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                                                     resultDateCalender.set(Calendar.HOUR, hourOfDay);
                                                                     resultDateCalender.set(Calendar.MINUTE, minute);
                                                                     date.setText(getFormatDate(resultDateCalender));

                                                                 }
                                                             },
                                        resultDateCalender.get(Calendar.HOUR),
                                        resultDateCalender.get(Calendar.MINUTE), true)
                                        .show(getSupportFragmentManager(),"");


                            }
                        }
                        , resultTimeCalender.get(Calendar.YEAR), resultTimeCalender.get(Calendar.MONTH),
                        resultTimeCalender.get(Calendar.DAY_OF_MONTH))
                        .show(getSupportFragmentManager(),"date");
            }
        });
        AlertDialog dialog= builder.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Add title please", Toast.LENGTH_LONG).show();
                    return;
                }


                ToDoModel toDoModel = new ToDoModel();
                toDoModel.setDate(getFormatDate(resultDateCalender));
                toDoModel.setTitle(title.getText().toString());
                presenter.updateTodo(toDoModel,pos);
            }
        });
    }

}
