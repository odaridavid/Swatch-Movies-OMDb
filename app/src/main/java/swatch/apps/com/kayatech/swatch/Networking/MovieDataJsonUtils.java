/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package swatch.apps.com.kayatech.swatch.Networking;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;


public final class MovieDataJsonUtils {

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     * <p/>
     * convert the JSON into human-readable strings.
     *
     * @param movieJsonString JSON response from server
     * @return Array of Strings describing weather data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[] getMovieDetailsStringsFromJson(Context context, String movieJsonString)
            throws JSONException {

        /**
         *
         * Json Parameters
         * @Title Title Of The Movie
         * @Year Year Released
         * @Rated Ratings
         * @Genre Genre Type
         * For More check out oMDB API Reference
         *
         * */
        final String MOVIE_TITLE = "Title";
        final String MOVIE_YEAR = "Year";
        final String MOVIE_RATING = "Rated";
        final String MOVIE_GENRE = "Genre";

        JSONObject movieJson = new JSONObject(movieJsonString);
        /**
         *
         * Get Strings From Json object and parse to a String Array
         * **/

        String Title = movieJson.getString(MOVIE_TITLE);
        String Year = movieJson.getString(MOVIE_YEAR);
        String Rating = movieJson.getString(MOVIE_RATING);
        String Genre = movieJson.getString(MOVIE_GENRE);

        String[] parsedMovieData = {Title, Year, Rating, Genre};


        return parsedMovieData;

    }


}