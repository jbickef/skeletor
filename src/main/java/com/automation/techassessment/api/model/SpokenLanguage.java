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
public class SpokenLanguage {
    private String iso_639_1;
    private String name;
    
    public String getIso_639_1(){
        return this.iso_639_1;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setIso_639_1(String iso_639_1){
        this.iso_639_1 = iso_639_1;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
