/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.techassessment.api.endpoints.movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * @author Jeff Bickmore
 */
public interface MovieEndpoint {
    String BASE_URL = "movie";

    @GET(BASE_URL + "/{movieId}")
    Call<MovieResponse> getMovieById(@Path("movieId") String movieId);

    @GET("search/" + BASE_URL)
    Call<SearchMovieResponse> searchMovie(@Query("query") String query);
    
    @GET("search/"+ BASE_URL)
    Call<SearchMovieResponse> searchMovie(@Query("query")String query, 
        @Query("language") String language, @Query("page")int page, 
        @Query("include_adult") boolean include_adult, @Query("region") String region,
        @Query("year") int year, @Query("primary_release_year")int primary_release_year);
    @GET("search/"+ BASE_URL)
    Call<SearchMovieResponse> searchMovieWithPage(@Query("query")String query, @Query("page")int page);
}
