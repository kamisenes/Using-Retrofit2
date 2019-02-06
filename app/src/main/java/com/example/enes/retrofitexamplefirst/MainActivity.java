package com.example.enes.retrofitexamplefirst;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            final RecyclerView recyclerView= (RecyclerView) findViewById(R.id.movies_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            ApiInterface apiInterface= ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<MovieResponse> call=apiInterface.getLastMovies();
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Movies movies = response.body().getData();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie,getApplicationContext()));

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("Errorrrr",t.getMessage());
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(MainActivity.this, "Click position"+position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View v, int position) {
                Toast.makeText(MainActivity.this, "onLongClick position"+position,Toast.LENGTH_LONG).show();

            }
        }));

    }

    public static interface ClickListener{
        public void onClick(View v, int position);
        public void onLongClick(View v, int position);
    }
    class RecyclerTouchListener implements  RecyclerView.OnItemTouchListener{
        private ClickListener clickListener;
        private GestureDetector gestureDetector;
        public RecyclerTouchListener(Context context, final  RecyclerView recyclerView , final  ClickListener clickListener){

            this.clickListener=clickListener;
            gestureDetector= new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return super.onSingleTapUp(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child= recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if (child!=null && clickListener!=null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                    }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View child= recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
                if (child!=null && clickListener!=null && gestureDetector.onTouchEvent(motionEvent)){
                    clickListener.onClick(child, recyclerView.getChildAdapterPosition(child));
                }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }



    }



}
