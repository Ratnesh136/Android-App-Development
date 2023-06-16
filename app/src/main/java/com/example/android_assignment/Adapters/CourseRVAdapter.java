package com.example.android_assignment.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_assignment.Modal.CourseModal;
import com.example.android_assignment.R;

import java.io.File;
import com.squareup.picasso.Picasso;

public class CourseRVAdapter extends ListAdapter<CourseModal, CourseRVAdapter.ViewHolder> {

    // creating a variable for on item click listener.
    private OnItemClickListener listener;

    // creating a constructor class for our adapter class.
    public CourseRVAdapter() {
        super(DIFF_CALLBACK);
    }

    // creating a call back for item of recycler view.
    private static final DiffUtil.ItemCallback<CourseModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<CourseModal>() {
        @Override
        public boolean areItemsTheSame(CourseModal oldItem, CourseModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(CourseModal oldItem, CourseModal newItem) {
            // below line is to check the course name, description and course duration.
//            return oldItem.getCourseName().equals(newItem.getCourseName()) &&
//                    oldItem.getCourseDescription().equals(newItem.getCourseDescription()) &&
//                    oldItem.getCourseDuration().equals(newItem.getCourseDuration());
            return false;
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is use to inflate our layout
        // file for each item of our recycler view.
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseModal model = getCourseAt(position);
        if (model.image_path != null) {
            holder.IVPreviewImage.setImageURI(Uri.parse(model.image_path));
        }
        holder.TvTitle.setText(model.title);
        holder.TvStudio.setText(model.studio);
        holder.TvCriticsRating.setText(model.criticsRating + "");
    }

    public CourseModal getCourseAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // view holder class to create a variable for each view.
        TextView TvTitle, TvCriticsRating, TvStudio;
        ImageView IVPreviewImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing each view of our recycler view.
            IVPreviewImage = itemView.findViewById(R.id.IVPreviewImage);
            TvTitle = itemView.findViewById(R.id.TvTitle);
            TvStudio = itemView.findViewById(R.id.TvStudio);
            TvCriticsRating = itemView.findViewById(R.id.TvCriticsRating);

            // adding on click listener for each item of recycler view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // inside on click listener we are passing
                    // position to our item of recycler view.
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CourseModal model, Integer index);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

