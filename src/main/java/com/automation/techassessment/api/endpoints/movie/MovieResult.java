/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.techassessment.api.endpoints.movie;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeff Bickmore
 */
public class MovieResult {
   
    private String poster_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private List<Integer> genre_ids = null;
    private int id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private double popularity;
    private String vote_count;
    private String video;
    private String vote_average;

    public String toString(){
        return toString(true);
    }
    
    public String toString(boolean pretty){
        return toString(pretty, false);
    }
    
    public String toString(boolean pretty, boolean extensive){
       String result = "";
       List<String>results = new ArrayList<>();
       if(!extensive){
           results.add("title: "+this.title);
           results.add("id: "+this.id);
       }
       else{
           //todo, add all the other fields in
       }
       if(pretty){
           result = "{\n"+String.join(",\n", results)+"\n}";
       }
       else{
           result ="{"+String.join(",", results)+"\n";
       }
       return result;
    }
    public String getPoster_path(){
        return this.poster_path;
    };
    public void setPoster_path(String path){
        this.poster_path = path;
    };
    public boolean getAdult(){
        return this.adult;
    };
    public void setAdult(boolean adult){
        this.adult = adult;
    };
    public String getOverview(){
        return this.overview;
    };
    public void getOverview(String overview){
        this.overview = overview;
    };
    public String getRelease_date(){
        return this.release_date;
    };
    public void setRelease_date(String release_date){
        this.release_date =release_date;
    };
    public List<Integer> getGenre_ids(){
        return this.genre_ids;
    };
    public void setGenre_ids(List<Integer> genre_ids){
        this.genre_ids = genre_ids;
    };
    public void setId(int id){
        this.id = id;
    };
    public int getId(){
        return this.id;
    };
    public String getOriginal_title(){
        return this.original_title;
    };
    public void setOriginal_title(String original_title){
        this.original_title = original_title;
    };
    public String getOriginal_language(){
        return this.original_language;
    };
    public void setOriginal_language(String original_language){
        this.original_language = original_language;
    };
    public String getTitle(){
        return this.title;
    };
    public void setTitle(String title){
        this.title = title;
    };
    public String getBackdrop_path(){
        return this.backdrop_path;
    };
    public void setBackdrop_path(String backdrop_path){
        this.backdrop_path =backdrop_path;
    };
    public double getPopularity(){
        return this.popularity;
    };
    public void setPopularity(double popularity){
        this.popularity = popularity;
    };
    public String getVote_count(){
        return this.vote_count;
    };
    public void setVote_count(String vote_count){
        this.vote_count = vote_count;
    };
    public String getVideo(){
        return this.video;
    };
    public void setVideo(String video){
        this.video = video;
    };
    public String getVote_average(){
        return this.vote_average;
    };
    public void getVote_average(String vote_avarage){
        this.vote_average = vote_average;
    };
    
}
