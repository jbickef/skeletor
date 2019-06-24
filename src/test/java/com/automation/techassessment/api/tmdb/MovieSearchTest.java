/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.techassessment.api.tmdb;

import com.automation.techassessment.api.endpoints.ApiEndpoints;
import com.automation.techassessment.api.endpoints.movie.MovieResponse;
import com.automation.techassessment.api.endpoints.movie.MovieResult;
import com.automation.techassessment.api.endpoints.movie.SearchMovieResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Arrays;
import static com.automation.techassessment.api.errors.RestAssertions.assertCallSucceeds;
import static com.automation.techassessment.api.errors.RestAssertions.assertCallDataError;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.testng.Assert;
/**
 *
 * @author Jeff Bickmore
 */
public class MovieSearchTest {
    protected static Logger log = LogManager.getLogger(MovieSearchTest.class);
    private ApiEndpoints rest;
    @BeforeMethod
    public void setup() throws Exception {
        rest = ApiEndpoints.newBuilder().build();
    }
    /**
    * 
     * Validates that the query parameter has to have a length of 
     * at least 1. Again, this check exists to make sure that a check is
     * done on fields that are required for functionality to work
     */
    @Test
    public void empty_query_string(){
        log.log(Level.ERROR, "Empty query");
        final String query = "";
        SearchMovieResponse searchResponse = assertCallDataError(
                rest.movie.searchMovie(query)).body();
    }
    
    /**
     * This may actually cross over into the movie endpoint as well
     * but this test would try to validate that the search results 
     * are actually valid from the movie databases perspective. I am only going
     * to send 2 for get movies, as there is a limit on the number of times
     * an api key is allowed to be used. For now i will just grab the first 2,
     * but really it might be better to change them up from different tests.
     * Could also be used to verify that the endpoint is working.
     * Also will verify that movies are not returned more then once.
     */
    @Test
    public void returned_ids_are_valid(){
        final String query = "Spider Man";
        List<String>errs = new ArrayList<>();
        SearchMovieResponse searchResponse = assertCallSucceeds(rest.movie.searchMovie(query)).body();
        List<MovieResult> movieResults = searchResponse.getResults();
        //check that each movie result is unique
        for(int i = 0; i < movieResults.size()-1; i++){
            MovieResult current = movieResults.get(i);
            for(int y = i+1; y < movieResults.size(); y++){
                MovieResult compare = movieResults.get(y);
                //System.out.println(current.toString(false, false));
                //System.out.println(compare.toString(false, false));
                if(current.getId() == compare.getId()){
                    System.out.println("2 movies with the same id where found: "+current.toString() 
                            + "\n" +"and "+compare.toString());
                    errs.add("2 movies with the same id where found: "+current.toString() + 
                            "and "+compare.toString());
                }
            }
        }
        //verify that the data for a movie is accurate as well
        for(int i = 0; i < 1; i++){
            MovieResult current = movieResults.get(i);
            MovieResponse movieResponse = assertCallSucceeds(
                   rest.movie.getMovieById(current.getId()+"")).body();
            //check to make sure that the movie returned has the same information
            if(current.getId() != movieResponse.getId() ||
               !current.getTitle().equals(movieResponse.getTitle()) ||
               !current.getOriginal_title().equals(movieResponse.getOriginal_title())){
                errs.add("doing a search with this id: "+current.getId() + "did not return"+
                        current.toString() + " instead got: "+movieResponse.toString());
            }
        }
        if(errs.size() > 0){
            log.log(Level.ERROR, errs);
            log.log(Level.ERROR, "errs.size(): "+errs.size());
            Assert.assertTrue(false, errs.toString());
        }
    }
    /**
     * Validates that the total returned for a search correct.
     * This test is an example of one of the ways to verify that
     * the information passed back is accurate. 
     */
    @Test
    public void validate_total_returned(){
        final String query = "Spider Man";
        List<String>errs = new ArrayList<>();
        SearchMovieResponse searchResponse = assertCallSucceeds(rest.movie.searchMovie(query)).body();
        System.out.println("Got this many pages: "+ searchResponse.getTotalPages());
        if(searchResponse.getTotalPages() > 1){
            int page = searchResponse.getTotalPages();
            SearchMovieResponse searchResponse2 = assertCallSucceeds(
                    rest.movie.searchMovieWithPage(query, page)).body();
            int numberOfMovies = searchResponse2.getResults().size() + 
                    ((page - 1 ) * searchResponse.getResults().size());
            if(numberOfMovies != searchResponse.getTotalResults()){
                errs.add("The number of results dont match the number of movies in the results. "
                    + "Expected: "+searchResponse.getTotalResults() + " found: "+numberOfMovies);
            }
        }else if(searchResponse.getTotalResults() != searchResponse.getResults().size()){
            errs.add("The number of results dont match the number of movies in the results. "
                    + "Expected: "+searchResponse.getTotalResults() + " found: "+searchResponse.getResults().size());
        }
        if(errs.size()> 0){
            log.log(Level.ERROR, errs);
            log.log(Level.ERROR, "errs.size(): "+errs.size());
            Assert.assertTrue(false, errs.toString());
        }
    }
    
    /**
     * Validates that the requirements are checked for the page parameter.
     * There would need to be at least 2 requests, one with page value being 
     * 0 and the other with it being 1001. Additional values that would be invalid could also be 
     * picked, like MAX_INT, or -1. This also validates that the page is an 
     * accepted parameter. Because of the limit on api requests, I am only going to use
     * 3 different page values.
     */
    @Test
    public void page_requirements(){
        final String query = "Spider Man";
        List<Integer> pageNumbersWithErrors = Arrays.asList(0, 1001, -1);
        for(Integer page: pageNumbersWithErrors){
            SearchMovieResponse searchMovieResponse = assertCallDataError(rest.movie.searchMovieWithPage(query, page)).body();
            System.out.println("finished paging request: "+page);
        }
    }
    
    /**
     * @TODO param_language_requirements
     * Validates that the language requirements are checked for.
     * This test will need to have at least 3 different requests sent.
     * One for the minLength, the pattern, and one to make sure that the
     * ISO 639-1 has to be a ISO 639-1
     *
    @Test
    public void param_language_requirements(){
        
    }
    */
    
    
    /**
     * @TODO region_requirements
     * This test makes sure that the input for the region parameter is validated.
     * Multiple calls with the values where: the ISO 3166-1 is not a valid one, 
     * and the code doesn't match the pattern: ^[A-Z][2]$ would be needed
     *
    @Test void region_requirements(){
        
    }
    */
    /**
     * TODO year_requirements
     * This test makes sure that the input for the year parameter is validated.
     * Multiple calls again would be required. One with the year as a 
     * string, a negative number, an unreasonably high number are some of the 
     * values that I would like to test
     *
    @Test void year_requirements(){
        
    }
    */
    /**
     * @TODO primary_release_year_requirements
     * This test makes sure that the input for the release year parameter is validated.
     * Multiple calls again would be required. One with the year as a 
     * string, a negative number, an unreasonably high number are some of the 
     * values that I would like to test
     *
    @Test void primary_release_year_requirements(){
        
    }
    */
    /**
     * @TODO expected_fields_and_types_present
     * This test would check to make sure that the returned fields that are 
     * returned match the given documentation found here: 
     * https://developers.themoviedb.org/3/search/search-movies.
     * Most of the fields are basic types. 
     * This test could also be used to verify that the end point is working. 
     * I will have to look into this further, I believe that the code is already doing at least some of this...
     *
    @Test
    public void expected_fields_and_types_present(){
        //make a simple request
        //validate that the results contain spacific fields
    }    
    */
    /**
     * TODO sql_injection_check
     * This test will attempt a sql injection attack. Given a successful attack
     * not only is the application at risk, but potentially so are the users.
     *
    @Test
    public void sql_injection_check(){
        //A test to check to make sure that sql injection doesn't work
        //An api can be rendered useless if a sql injection is possible
    }
    */
    /**
     * @TODO x_site_scripting_check
     * This test will attempt a cross site scripting attack. Given a successful attack
     * not only is the application at risk, but potentially so are the users. 
     * So this testing will not only work to protect the api, but also the users
     * of the website/api
     *
    @Test
    public void x_site_scripting_check(){
        
    }
    */
    /**
     * @TODO os_injection_check
     * This test will attempt an operating system attack. Given a successful attack
     * not only is the application at risk, but potentially so are the users. 
     * So this testing will not only work to protect the api, but also the users
     * of the website/api
     *
    @Test
    public void os_injection_check(){
        
    }
    * */
    /**
     * @TODO overflow_url_paramater
     * Again another security check. Essentially this test makes sure that
     * if the url is too large in size, that it is handled correctly 
     *
    @Test
    public void overflow_url_paramater(){
        //set query string to be too large
        //send request, check for error
    }
    */
    /**
     * @TODO call_api_more_times_then_allowed
     * This test could need to call the api more times then are allowed.
     * Since there is a limit described in the documentation it becomes
     * important to test to verify this, as future decisions may be 
     * impacted by the limitations described in the documentation. 
     *
    @Test
    public void call_api_more_times_then_allowed(){
        
    }
    * */
    /**
     * @TODO validate_apikey_required
     * validates that the api key is required. Since the api key is how api
     * users are allowed to interact with the api, this is probably a primary way
     * that the movie database can tell who as done what. 
     *
    @Test
    public void validate_apikey_required(){
        
    }
    * */
    /**
     * @TODO validate_invalid_apikey_doesntwork
     * Validates that the apikey provided has to be a valid one. A simple chekc
     * to make sure that it is present is not enough
     *
    @Test
    public void validate_invalid_apikey_doesntwork(){
        //simpley requiring an apikey parameter is not enough.
        //There should be a check to make sure that its a valid apikey
    }
    */
    /**
     * TODO validate_query_required
     *
     * Validates that the query parameter is required. As one of the few 
     * required fields, it is particularly important to make sure that this 
     * field is required, as the search shouldn't work without it. 
     *
    @Test
    public void validate_query_required(){
        
    }
    */
}
