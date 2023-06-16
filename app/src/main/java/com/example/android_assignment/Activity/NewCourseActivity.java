package com.example.android_assignment.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;
import com.example.android_assignment.Adapters.ViewModal;
import com.example.android_assignment.Modal.CourseModal;
import com.example.android_assignment.R;
import androidx.lifecycle.ViewModelProviders;
public class NewCourseActivity extends AppCompatActivity {
    public static String Index = "index";
ImageView IVPreviewImage;
String image_path;
    // One Button
    Button BSelectImage;

    // One Preview Image
//    ImageView IVPreviewImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    public boolean isEditable=false;
    Button idBtnSaveCourse;
    String regex = ".*[,].*";
    // creating a variables for our button and edittext.
    private EditText courseNameEdt, courseDescEdt, courseDurationEdt, idMovie, idTitle,
            idStudio,
            idGenres,
            idDirectors,
            idWriters,
            idActors,
            idYear,
            idLength,
            mpaRating,
            idShortDescription,
            criticsRating;
    private Button courseBtn;
    CourseModal movieModal;
    private ViewModal viewmodal;
    // creating a constant string variable for our
    // course name, description and duration.
    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_COURSE_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_NAME";
    public static final String EXTRA_DESCRIPTION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DESCRIPTION";
    public static final String EXTRA_DURATION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DURATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        movieModal =new CourseModal();
        idMovie = findViewById(R.id.idMovie);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
        idTitle = findViewById(R.id.idTitle);
        idStudio = findViewById(R.id.idStudio);
        idGenres = findViewById(R.id.idGenres);
        idDirectors = findViewById(R.id.idDirectors);
        idWriters = findViewById(R.id.idWriters);
        idActors = findViewById(R.id.idActors);
        idYear = findViewById(R.id.idYear);
        idLength = findViewById(R.id.idLength);
        mpaRating = findViewById(R.id.mpaRating);
        idBtnSaveCourse = findViewById(R.id.idBtnSaveCourse);
        idShortDescription = findViewById(R.id.idShortDescription);
        criticsRating = findViewById(R.id.criticsRating);
        IVPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        // below line is to get intent as we
        // are getting data via an intent.
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            // if we get id for our data then we are
            // setting values to our edit text fields.

            movieModal= (CourseModal)intent.getSerializableExtra(EXTRA_ID);
            Integer index= (Integer)intent.getSerializableExtra(Index);
            isEditable=true;
            if(movieModal.image_path != null) {
                IVPreviewImage.setImageURI(Uri.parse(movieModal.image_path));
            }

            idMovie.setText(movieModal.movieID);
            idTitle.setText(movieModal.title);
            idStudio.setText(movieModal.studio);
            idGenres.setText(movieModal.genres);
            idDirectors.setText(movieModal.directors);
            idWriters.setText(movieModal.writers);
            idActors.setText(movieModal.actors);
            idYear.setText(movieModal.year);
            idLength.setText(movieModal.length);
            mpaRating.setText(movieModal.mpaRating);
            idShortDescription.setText(movieModal.shortDescription);
            criticsRating.setText(movieModal.criticsRating + "");
        }
        // adding on click listener for our save button.
        idBtnSaveCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text value from edittext and validating if
                // the text fields are empty or not.
movieModal.image_path=image_path;
                movieModal.movieID = idMovie.getText().toString();
                movieModal.title= idTitle.getText().toString();
                movieModal.studio = idStudio.getText().toString();
                movieModal.genres = idGenres.getText().toString();
                movieModal.directors = idDirectors.getText().toString();
                movieModal.writers = idWriters.getText().toString();
                movieModal.actors = idActors.getText().toString();
                movieModal.year = idYear.getText().toString()+"";
                movieModal.length = idLength.getText().toString()+"";
                movieModal.mpaRating = mpaRating.getText().toString();
                movieModal.criticsRating = criticsRating.getText().toString()+"";


                //  Check for input containe comma or not
                if ( movieModal.movieID.toString().matches(regex) ||  movieModal.title.matches(regex) || movieModal.studio.matches(regex) ||
                        movieModal.genres.matches(regex)  || movieModal.directors.matches(regex)  || movieModal.writers.matches(regex)  ||
                        movieModal.actors.matches(regex)  || movieModal.year.toString().matches(regex)  || movieModal.length.toString().matches(regex)
                        || movieModal.mpaRating.matches(regex)  || movieModal.criticsRating.toString().matches(regex) ) {
                    Toast.makeText(NewCourseActivity.this, "Please remove ',' use a single space instead.", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Check for empty input
                if ( movieModal.movieID.toString().isEmpty() ||  movieModal.title.isEmpty() || movieModal.studio.isEmpty() ||
                        movieModal.genres.isEmpty() || movieModal.directors.isEmpty() || movieModal.writers.isEmpty() ||
                        movieModal.actors.isEmpty() || movieModal.year.toString().isEmpty() || movieModal.length.toString().isEmpty()
                        || movieModal.mpaRating.isEmpty() || movieModal.criticsRating.toString().isEmpty()) {
                    Toast.makeText(NewCourseActivity.this, "Please enter the valid course details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our course.
                saveCourse(movieModal);
            }
        });
    }

    private void saveCourse(CourseModal movieModal) {
        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);

        if(isEditable)
        {
            viewmodal.update(movieModal);
            finish();

        }else {
            viewmodal.insert(movieModal);
            finish();

        }
         }

    void imageChooser() {

        // create an instance of the
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE
            ) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                    image_path=selectedImageUri.toString();

                }
            }
        }
    }




}
