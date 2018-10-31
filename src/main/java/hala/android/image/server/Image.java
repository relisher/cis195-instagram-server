/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hala.android.image.server;

/**
 *
 * @author arelin
 */
public class Image {
    private String picture;
    private String description;

    public String getPicture() {
         return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
}
