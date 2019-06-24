/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.techassessment.api.model;

/**
 *
 * @author Jeff Bickmore
 */
public class ProductionCountry {
    private String iso_3166_1;
    private String name;
    
    
    public String getIso_3166_1(){
        return this.iso_3166_1;
    }
    private String getName(){
        return this.name;
    }
    
    private void setIso_3166_1(String iso_3166_1){
        this.iso_3166_1 = iso_3166_1;
    };
    private void setName(String name){
        this.name = name;
    };
}
