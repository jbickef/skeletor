/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.techassessment.api.endpoints.movie;

import com.automation.techassessment.api.model.ProductionCompany;
import com.automation.techassessment.api.model.ProductionCountry;
import com.automation.techassessment.api.model.SpokenLanguage;
import com.automation.techassessment.api.model.TMDbObject;
import java.util.List;

/**
 *
 * @author Jeff Bickmore
 */
public class MovieResponse implements TMDbObject{
    private boolean adult;
    private String backdrop_path;
    private Object belongs_to_collection;
    private int budget;
    private List<MovieGenre> genres;
    private String homepage;
    private int id;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private List<ProductionCompany> production_companies;
    private List<ProductionCountry> production_countries;
    private String release_date;
    private int revenue;
    private int runtime;
    private List<SpokenLanguage> spoken_languages;
    private MovieStatus status;
    private String tagline;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;

    
    //getters
    public boolean getAdult(){
        return this.adult;
    };
    public String getBackdrop_path(){
        return this.backdrop_path;
    };
    public  Object getBelongs_to_collection(){
        return this.belongs_to_collection;
    };
    public  int getBudget(){
        return this.budget;
    };
    public  List<MovieGenre> getGenres(){
        return this.genres;
    };
    public  String getHomepage(){
        return this.homepage;
    };
    public int getId(){
        return this.id;
    };
    public  String getImdb_id(){
        return this.imdb_id;
    };
    public  String getOriginal_language(){
        return this.original_language;
    };
    public  String getOriginal_title(){
        return this.original_title;
    };
    public  String getOverview(){
        return this.overview;
    };
    public  double getPopularity(){
        return this.popularity;
    };
    public  String getPoster_path(){
        return this.poster_path;
    };
    public  List<ProductionCompany> getProduction_companies(){
        return this.production_companies;
    };
    public  List<ProductionCountry> getProduction_countries(){
        return this.production_countries;
    };
    public  String getRelease_date(){
        return this.release_date;
    };
    public  int getRevenue(){
        return this.revenue;
    };
    public  int getRuntime(){
        return this.runtime;
    };
    public  List<SpokenLanguage> getSpoken_languages(){
        return this.spoken_languages;
    };
    public  MovieStatus getStatus(){
        return this.status;
    };
    public  String getTagline(){
        return this.tagline;
    };
    public  String getTitle(){
        return this.title;
    };
    public  boolean getVideo(){
        return this.video;
    };
    public  double getVote_average(){
        return this.vote_average;
    };
    public  int getVote_count(){
        return this.vote_count;
    };
    //setters
    public void setAdult(boolean adult){
        this.adult = adult;
    };
    public void setBackdrop_path(String backdrop_path){
        this.backdrop_path = backdrop_path;
    };
    public void setBelongs_to_collection(Object belongs_to_collection){
        this.belongs_to_collection = belongs_to_collection;
    };
    public  void setBudget(int budget){
        this.budget =budget;
    };
    public  void setGenres(List<MovieGenre>genres) {
        this.genres = genres;
    };
    public  void setHomepage(String homepage){
        this.homepage = homepage;
    };
    public void setId(int id){
        this.id = id;
    };
    public  void setImdb_id(String imdb_id){
        this.imdb_id =imdb_id;
    };
    public  void setOriginal_language(String original_language){
        this.original_language = original_language;
    };
    public  void setOriginal_title(String original_title){
        this.original_title = original_title;
    };
    public  void setOverview(String overview){
        this.overview = overview;
    };
    public  void setPopularity(double popularity){
        this.popularity = popularity;
    };
    public  void setPoster_path(String poster_path){
        this.poster_path = poster_path;
    };
    public  void setProduction_companies(List<ProductionCompany> production_companies){
        this.production_companies =production_companies;
    };
    public void setProduction_countries(List<ProductionCountry>production_countries ){
        this.production_countries = production_countries;
    };
    public  void setRelease_date(String release_date){
        this.release_date =release_date;
    };
    public  void setRevenue(int revenue){
        this.revenue =revenue;
    };
    public void  setRuntime(int runtime){
        this.runtime = runtime;
    };
    public void setSpoken_languages(List<SpokenLanguage> spoken_languages){
        this.spoken_languages = spoken_languages;
    };
    public void  setStatus(MovieStatus status){
        this.status = status;
    };
    public void  setTagline(String tagline){
        this.tagline =tagline;
    };
    public void  setTitle(String title){
        this.title = title;
    };
    public void  setVideo(boolean video){
        this.video =video;
    };
    public void  setVote_average(double vote_average){
        this.vote_average = vote_average;
    };
    public void  setVote_count(int vote_count){
        this.vote_count = vote_count;
    };

}
