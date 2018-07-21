package swatch.apps.com.kayatech.swatch

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import swatch.apps.com.kayatech.swatch.Networking.NetworkUtils
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var mSearchBoxEditText: EditText? = null

    private var mSearchResultsTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSearchBoxEditText = findViewById(R.id.et_search) as EditText

        mSearchResultsTextView = findViewById(R.id.tv_movie_list) as TextView
    }

    internal fun makeMovieSearchQuery() {

        val movieQuery = mSearchBoxEditText?.getText().toString()
        val movieSearchUrl = NetworkUtils.buildUrl(movieQuery)


        var movieSearchResults: String? = null

        try {
            movieSearchResults = NetworkUtils.getResponseFromHttpUrl(movieSearchUrl)
            mSearchResultsTextView?.setText(movieSearchResults.toString())
        } catch ( e: IOException) {
            e.printStackTrace()
        }

    }
    
    class MoviQueryTask(): AsyncTask<URL, Unit, String>() {
        override fun doInBackground(vararg params: URL?): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }
    }


    //Inflate Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Get Item Clicked on Menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId
        if (itemId == R.id.action_search) {
            //Toast.makeText(this, "Works", Toast.LENGTH_LONG).show()
            makeMovieSearchQuery()
        }
        return super.onOptionsItemSelected(item)
    }
}
