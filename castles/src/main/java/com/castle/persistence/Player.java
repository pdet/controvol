package com.castle.persistence;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.castle.types.PlayerTypeMngr;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Entity
public class Player extends AbstractPlayer {
	// version 0

	@Id
	String name;
	
	@Index
	Integer rank;
	
	Double score;
	
	String type; // only for versions 0 and 2
	
	@Load
	List<Armory> gear = new ArrayList<Armory>();
		
	public void setLevel(Integer i){
		this.rank = i;
		setScore();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	private void setScore(){
		Random r = new Random();
		DecimalFormat newFormat = new DecimalFormat("#.##");
		score = Math.abs(rank + Double.valueOf(newFormat.format(r.nextDouble())));
		//score = Math.abs(level + Integer.valueOf(r.nextInt(100)));// version 1 and 2
		
		/**
		 * Comment the following 3 lines at version 1
		 */
		PlayerTypeMngr m = new PlayerTypeMngr(); // only for versions 0 and 2
		m.setType(score); // only for versions 0 and 2 SCORE is now an Integer
		type = m.getType(); // only for versions 0 and 2*/
	}
	
	 public Key<Player> getKey() {
	    	if(name == null){
	    		return null;
	    	}
	        return Key.create(this.getClass(), name);
	    }
	
	public void setArmory(Armory e){
		this.gear.add(e);
	}
	
	/**
	 * @return the armory
	 */
	public List<Armory> getArmory() {
		if(this.gear == null){
			this.gear = new ArrayList<Armory>();
		}
		return gear;
	}
	
	@Override
	public String toString(){
		return "I'm player "+name+" at rank "+rank+" score "+score+" type "+type.toString(); // only for versions 0 and 2
		//return "I'm player "+name+" at level "+rank+" score "+score;  // version 1
	}
}
