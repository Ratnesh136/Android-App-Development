package com.example.android_assignment.Modal;


import androidx.room.Entity;
        import androidx.room.PrimaryKey;

import java.io.Serializable;

// below line is for setting table name.
@Entity(tableName = "course_table")
public class CourseModal implements Serializable {

    // below line is to auto increment
    // id for each course.
    @PrimaryKey(autoGenerate = true)

    // variable for our id.
    private int id;

    // below line is a variable
    // for course name.
//    private String courseName;
    public String courseName;
public String image_path;
    public String movieID;

    public String title;
    public String studio;
    public String genres;
    public String directors;
    public String writers;
    public String actors;
    public String year;
    public String length;
    public String shortDescription ;
    public String mpaRating;
    public String criticsRating;
    // below line is use for
    // course description.
    private String courseDescription;

    // below line is use
    // for course duration.
    private String courseDuration;

    // below line we are creating constructor class.
    // inside constructor class we are not passing
    // our id because it is incrementing automatically
    public CourseModal() {}
    public CourseModal(CourseModal movieModal) {
//        this.courseName = courseName;
//        this.courseDescription = courseDescription;
//        this.courseDuration = courseDuration;
        this.movieID=movieModal.movieID;
        this.image_path=movieModal.image_path;
        this.title=movieModal.title;
        this.studio=movieModal.studio;
        this.genres=movieModal.genres;
        this.directors=movieModal.directors;
        this.writers=movieModal.writers;
        this.actors=movieModal.actors;
        this.year=movieModal.year;
        this.length=movieModal.length;
        this.shortDescription=movieModal.shortDescription ;
        this.mpaRating=movieModal.mpaRating;
        this.criticsRating=movieModal.criticsRating;




    }

    // on below line we are creating
    // getter and setter methods.
//    public String getCourseName() {
//        return courseName;
//    }
//
//    public void setCourseName(String courseName) {
//        this.courseName = courseName;
//    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

