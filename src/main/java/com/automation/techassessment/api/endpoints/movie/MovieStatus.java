/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.techassessment.api.endpoints.movie;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jeff Bickmore
 */
public enum MovieStatus {
    Rumored("Rumored"),
    Planned("Planned"),
    In_Production("In Production"),
    Post_Production("Post Production"),
    Released("Released"),
    Canceled("Canceled");
   
    private final String status;
    
    private MovieStatus(final String status){
        this.status = status;
    }
    
    public String toString(String status){
        return this.status;
    }
    /*to make the enum easier to use*/
    private static final Map<String, MovieStatus> lookup = new HashMap<>();
    //populate the look up table
    static{
        for(MovieStatus status: MovieStatus.values()){
            lookup.put(status.toString(), status);
        }
    }
   
    public static MovieStatus get(String status){
        return lookup.get(status);
    }
}
