package com.example.enes.retrofitexamplefirst;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {


    private     Movies movies;
    private  int rowLayout;
    Context context;
    public static class MovieViewHolder extends  RecyclerView.ViewHolder{
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView image;
        public MovieViewHolder(View v){
            super(v);

            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
            image = (ImageView) v.findViewById(R.id.imageView);
        }



    }
    public MoviesAdapter(Movies movies, int rowLayout , Context context){
        this.movies=movies;
        this.rowLayout=rowLayout;
        this.context=context;
    }
    @NonNull
    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MovieViewHolder movieViewHolder, int i) {

        movieViewHolder.movieTitle.setText(movies.getMovies().get(i).getTitle());
        movieViewHolder.data.setText(movies.getMovies().get(i).getYear());
        movieViewHolder.movieDescription.setText(movies.getMovies().get(i).getSummary());
        movieViewHolder.rating.setText(movies.getMovies().get(i).getRating());
        Picasso.with(context).load(movies.getMovies().get(i).getImage()).into(movieViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return movies.getMovies().size();
    }

}
