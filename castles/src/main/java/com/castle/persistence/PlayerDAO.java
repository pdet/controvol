package com.castle.persistence;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;

public class PlayerDAO {
	static{
		ObjectifyService.register(Player.class);
	}
	
	//Not a very good practice
	public ArmoryDAO armoryDAO = new ArmoryDAO();
	
	public Player save(Player player) {
		OfyService.ofy().save().entity(player).now();
		return player;
	}
	
	public Player load(String name){
		return OfyService.ofy().load().type(Player.class).id(name).now();	 
	}
	
	public List<Player> findAll() {
		List<Player> players = OfyService.ofy().load().type(Player.class).list();

		return players;
	}
	
	public List<Player> getDeadlyPlayers(Integer damage){
		return OfyService.ofy().load().type(Player.class).filter("gear.damage >", damage).list();
	}
	
}
