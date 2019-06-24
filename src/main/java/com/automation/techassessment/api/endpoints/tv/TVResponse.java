package com.automation.techassessment.api.endpoints.tv;

import com.automation.techassessment.api.endpoints.movie.MovieGenre;
import com.automation.techassessment.api.model.*;

import java.util.List;

public class TVResponse implements TMDbObject {

    private String backdrop_path;
    private List<CreatedBy> created_by = null;
    private List<Integer> episode_run_time = null;
    private String first_air_date;
    private List<MovieGenre> genres = null;
    private String homepage;
    private int id;
    private boolean in_production;
    private List<String> languages = null;
    private String last_air_date;
    private String name;
    private List<Network> networks = null;
    private int number_of_episodes;
    private int number_of_seasons;
    private List<String> origin_country = null;
    private String original_language;
    private String original_name;
    private String overview;
    private double popularity;
    private String poster_path;
    private List<ProductionCompany> production_companies = null;
    private List<Season> seasons = null;
    private String status;
    private String type;
    private double vote_average;
    private int vote_count;

    public String getBackdropPath() {
        return backdrop_path;
    }

    public void setBackdropPath(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<CreatedBy> getCreatedBy() {
        return created_by;
    }

    public void setCreatedBy(List<CreatedBy> created_by) {
        this.created_by = created_by;
    }

    public List<Integer> getEpisodeRunTime() {
        return episode_run_time;
    }

    public void setEpisodeRunTime(List<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public String getFirstAirDate() {
        return first_air_date;
    }

    public void setFirstAirDate(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public List<MovieGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<MovieGenre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInProduction() {
        return in_production;
    }

    public void setInProduction(boolean in_production) {
        this.in_production = in_production;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getLastAirDate() {
        return last_air_date;
    }

    public void setLastAirDate(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public int getNumberOfEpisodes() {
        return number_of_episodes;
    }

    public void setNumberOfEpisodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumberOfSeasons() {
        return number_of_seasons;
    }

    public void setNumberOfSeasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public List<String> getOriginCountry() {
        return origin_country;
    }

    public void setOriginCountry(List<String> origin_country) {
        this.origin_country = origin_country;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public void setOriginalLanguage(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginalName() {
        return original_name;
    }

    public void setOriginalName(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return production_companies;
    }

    public void setProductionCompanies(List<ProductionCompany> production_companies) {
        this.production_companies = production_companies;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public void setVoteAverage(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVoteCount() {
        return vote_count;
    }

    public void setVoteCount(int vote_count) {
        this.vote_count = vote_count;
    }

}
