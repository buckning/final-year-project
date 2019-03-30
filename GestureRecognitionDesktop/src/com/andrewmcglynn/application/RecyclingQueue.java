/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.andrewmcglynn.application;

import java.util.ArrayList;
/**
 *
 * @author andrew
 */
public class RecyclingQueue {
    ArrayList<Photo> queue;

    public RecyclingQueue(){
        queue = new ArrayList<Photo>();
    }

    public void addToQueue(Photo p){
        queue.add(p);
    }

    public void removeFromQueue(Photo p){
        queue.remove(p);
    }
}
