package com.example.android_assignment.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.android_assignment.Adapters.CourseRVAdapter;
import com.example.android_assignment.Adapters.ViewModal;
import com.example.android_assignment.Modal.CourseModal;
import com.example.android_assignment.Modal.Dao;
import com.example.android_assignment.Modal.MovieModal;
import com.example.android_assignment.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
public class HomeActivity<User> extends AppCompatActivity {
    // creating a constant string variable for our
    // course name, description and duration.
    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_COURSE_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_NAME";
    public static final String EXTRA_DESCRIPTION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DESCRIPTION";
    public static final String EXTRA_DURATION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DURATION";

    // creating a variables for our recycler view.
    private RecyclerView coursesRV;
    Integer inde=0;
    FloatingActionButton fab,download;
    LinearLayout llNoImageFound,llMovieContainer;
    private static final int ADD_COURSE_REQUEST = 1;
    private static final int EDIT_COURSE_REQUEST = 2;
    private ViewModal viewmodal;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bindData();
        setClickLister();
        // passing a data from view modal.
        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);


        // setting layout manager to our adapter class.
        coursesRV.setLayoutManager(new LinearLayoutManager(this));
        coursesRV.setHasFixedSize(true);

        // initializing adapter for recycler view.
        final CourseRVAdapter adapter = new CourseRVAdapter();

        // setting adapter class for recycler view.
        coursesRV.setAdapter(adapter);


        // below line is use to get all the courses from view modal.
        viewmodal.getAllCourses().observe(this, new Observer<List<CourseModal>>() {
            @Override
            public void onChanged(List<CourseModal> models) {
                // when the data is changed in our models we are
                // adding that list to our adapter class.
                adapter.submitList(models);
            }
        });
        // below method is use to add swipe to delete method for item of recycler view.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view.
                viewmodal.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(HomeActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).
                // below line is use to attach this to recycler view.
                        attachToRecyclerView(coursesRV);
        // below line is use to set item click listener for our item of recycler view.
        adapter.setOnItemClickListener(new CourseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CourseModal model,Integer index) {
                // after clicking on item of recycler view
                // we are opening a new activity and passing
                // a data to our activity.
                Intent intent = new Intent(HomeActivity.this, NewCourseActivity.class);
                intent.putExtra(NewCourseActivity.EXTRA_ID, model);
                intent.putExtra(NewCourseActivity.Index, index);


                // below line is to start a new activity and
                // adding a edit course constant.
                startActivityForResult(intent, EDIT_COURSE_REQUEST);
            }
        });
    }
private void setClickLister()
{

    // adding on click listener for floating action button.
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // starting a new activity for adding a new course
            // and passing a constant value in it.
            Intent intent = new Intent(HomeActivity.this, NewCourseActivity.class);
            startActivityForResult(intent, ADD_COURSE_REQUEST);
        }
    });

    // adding on click listener for floating action button.
    download.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            importCsv();

            llNoImageFound.setVisibility(View.GONE);
            llMovieContainer.setVisibility(View.VISIBLE);
        }
    });

}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {

        } else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NewCourseActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Movies can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            llNoImageFound.setVisibility(View.GONE);
            llMovieContainer.setVisibility(View.VISIBLE);

            Toast.makeText(this, "Records  updated", Toast.LENGTH_SHORT).show();
        } else {

            llNoImageFound.setVisibility(View.GONE);
            llMovieContainer.setVisibility(View.VISIBLE);

            Toast.makeText(this, "Records saved", Toast.LENGTH_SHORT).show();
        }



    }

    public void bindData()
    {
        coursesRV = findViewById(R.id.idRVCourses);
        llNoImageFound=findViewById(R.id.llNoImageFound);
        llMovieContainer=findViewById(R.id.llMovieContainer);
        fab = findViewById(R.id.idFABAdd);
        download = findViewById(R.id.idDownload);
    }
private void importCsv()
{// Read the CSV file
    try {
        BufferedReader br = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.data))); // Replace "data" with your CSV file name

        String line;
        List<CourseModal> movieList = new ArrayList<>();

        while ((line = br.readLine()) != null) {
//            Exccluding
            if( index == 0)
            {index++;

            }else{
            String[] data = line.split(",");


            CourseModal movieModal = new CourseModal();

            movieModal.movieID=data[0];
            movieModal.title=data[1];
            movieModal.studio=data[2];
            movieModal.genres=data[3];
            movieModal.directors=data[4];
            movieModal.writers=data[5];
            movieModal.actors=data[6];
            movieModal.year=data[7];
            movieModal.length=data[8];
            movieModal.shortDescription=data[9];
            movieModal.mpaRating=data[10];
            movieModal.criticsRating=data[11];

    CourseModal model = new CourseModal(movieModal);

    viewmodal.insert(model);
    movieList.add(movieModal);
 }

        }

        // Insert data into the database
//        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "assignment").build(); // Replace "database-name" with your desired database name
//        Dao userDao = db.userDao();
//        userDao.insertAll(userList);

        br.close();
    } catch (IOException e) {
        e.printStackTrace();
    }}
    private void saveCourse(MovieModal movieModal) {

        // inside this method we are passing
        // all the data via an intent.
        Intent data = new Intent();

        // in below line we are passing all our course detail.
        data.putExtra(EXTRA_COURSE_NAME, movieModal.title);
        data.putExtra(EXTRA_DESCRIPTION, movieModal.shortDescription);
        data.putExtra(EXTRA_DURATION, movieModal.actors);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }

        // at last we are setting result as data.
//        setResult(RESULT_OK, data);

        // displaying a toast message after adding the data
        Toast.makeText(this, "Course has been saved to Room Database. ", Toast.LENGTH_SHORT).show();
    }
}
