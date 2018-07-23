package swatch.apps.com.kayatech.swatch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import swatch.apps.com.kayatech.swatch.Networking.NetworkUtils;
import swatch.apps.com.kayatech.swatch.Networking.MovieDataJsonUtils;

public class MainActivity extends AppCompatActivity {



    private EditText mSearchBoxEditText;
    private EditText mSearchBoxYearEditText;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mMovieListRecycler;
    private MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchBoxEditText = findViewById(R.id.et_search_title);

        mSearchBoxYearEditText = findViewById(R.id.et_search_year);

      //  mSearchResultsTextView = findViewById(R.id.tv_movie_list);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);


        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mMovieListRecycler = findViewById(R.id.rv_movie_view);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mMovieListRecycler.setLayoutManager(layoutManager);


        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mMovieListRecycler.setHasFixedSize(true);


        /*
         * The MoviesAdapter is responsible for linking movies data with the Views that
         * will end up displaying our movie data.
         */
        mMoviesAdapter = new MoviesAdapter();


        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mMovieListRecycler.setAdapter(mMoviesAdapter);
    }

    /**
     * This method retrieves the search text from the EditText, constructs the
     * URL (using {@link NetworkUtils}) for the movie you'd like to find
     * and finally fires off an AsyncTask to perform the GET request using
     * our {@link MovieQueryTask}
     */
    private void makeGithubSearchQuery() {
        String movieTitleQuery = mSearchBoxEditText.getText().toString();
        String movieReleaseYear = mSearchBoxYearEditText.getText().toString();
        URL movieSearchUrl = NetworkUtils.buildUrl(movieTitleQuery,movieReleaseYear);



        new MovieQueryTask().execute(movieSearchUrl);

    }

    /**
     * This method will make the View for the JSON data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showJsonDataView() {
        // The error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // JSON data is visible
        mMovieListRecycler.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the JSON
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        //Hide the currently visible data
        mMovieListRecycler.setVisibility(View.INVISIBLE);
        //Show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }



    public class MovieQueryTask extends AsyncTask<URL, Void, String[]> {


        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);

        }

        @Override
        protected String[] doInBackground(URL... params) {
            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            URL searchUrl = params[0];
            String movieSearchResults = null;
            try {
                movieSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                String[] simpleJsonWeatherData = MovieDataJsonUtils
                        .getMovieDetailsStringsFromJson(MainActivity.this, movieSearchResults);
                return simpleJsonWeatherData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }


        @Override
        protected void onPostExecute(String[] movieSearchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (movieSearchResults != null && !movieSearchResults.equals("")) {

                mMoviesAdapter.setMovieData(movieSearchResults);

                showJsonDataView();

            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
