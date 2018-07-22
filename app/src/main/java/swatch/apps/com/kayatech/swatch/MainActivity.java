package swatch.apps.com.kayatech.swatch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


    private TextView mSearchResultsTextView;


    private TextView mErrorMessageDisplay;


    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchBoxEditText = findViewById(R.id.et_search_title);

        mSearchBoxYearEditText = findViewById(R.id.et_search_year);

        mSearchResultsTextView = findViewById(R.id.tv_movie_list);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        // COMPLETED (25) Get a reference to the ProgressBar using findViewById
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }

    /**
     * This method retrieves the search text from the EditText, constructs the
     * URL (using {@link NetworkUtils}) for the github repository you'd like to find, displays
     * that URL in a TextView, and finally fires off an AsyncTask to perform the GET request using
     * our {@link MovieQueryTask}
     */
    private void makeGithubSearchQuery() {
        String movieTitleQuery = mSearchBoxEditText.getText().toString();
        String movieReleaseYear = mSearchBoxYearEditText.getText().toString();
        URL movieSearchUrl = NetworkUtils.buildUrl(movieTitleQuery,movieReleaseYear);

        // COMPLETED (4) Create a new MovieQueryTask and call its execute method, passing in the url to query

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
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the JSON
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        // First, hide the currently visible data
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    // COMPLETED (1) Create a class called MovieQueryTask that extends AsyncTask<URL, Void, String>
    public class MovieQueryTask extends AsyncTask<URL, Void, String[]> {


        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);

        }

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
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

        // COMPLETED (3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String[] movieSearchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                for (String movieString : movieSearchResults) {
                    mSearchResultsTextView.append((movieString)+"\n");

                }
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
