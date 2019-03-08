package com.castle.persistence;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Armory {

	@Id
	String weapon;
	
	@Index
	Integer damage;
	
	Integer defense;
	
	/*@Load
	@Parent
    private Ref<Player> player;*/
	
	public Armory(){}
	
	public Armory(String weapon,Integer damage,Integer defense){
		this.weapon=weapon;
		this.defense=defense;
		this.damage=damage;
	}
	
	public void setWeapon(String weapon){
		this.weapon=weapon;
	}
	
	public void setDefense(Integer defense){
		this.defense=defense;
	}
	
	public void setDamage(Integer damage){
		this.damage=damage;
	}
	
	/**
	 * @return the parent
	 
	public final Player getParent() {
		return player.get();
	}

	/**
	 * @param parent the parent to set
	 
	public final void setParent(Player player) {
		this.player = Ref.create(player.getKey());
	}*/
}
