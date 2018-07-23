package swatch.apps.com.kayatech.swatch;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {


    private String[] movieSearchResults;


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String movies = movieSearchResults[position];
        holder.mMovieListView.setText(movies);

    }

    @Override
    public int getItemCount() {
        if (null == movieSearchResults) return 0;
        return movieSearchResults.length;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView mMovieListView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            Typeface typeface = ResourcesCompat.getFont(itemView.getContext(), R.font.bohemian_typewriter);

            mMovieListView = itemView.findViewById(R.id.tv_item_no);
            mMovieListView.setTypeface(typeface);
        }
        void bind(int listIndex){
            mMovieListView.setText(String.valueOf(listIndex));
        }
    }

    public void setMovieData(String[] movieData) {
        movieSearchResults = movieData;
        notifyDataSetChanged();
    }
}
