package com.castle.persistence;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;

public class ArmoryDAO {

	static{
		ObjectifyService.register(Armory.class);
	}
	
	public Armory save(Armory child) {
		OfyService.ofy().save().entity(child).now();
		return child;
	}
	
	public List<Armory> findAll() {
		List<Armory> gear = OfyService.ofy().load().type(Armory.class).list();
		return gear;
	}
	
	public List<Armory> findChildrensOfParent(Player parent) {
		List<Armory> gear = OfyService.ofy().load().type(Armory.class).ancestor(parent).list();
		return gear;
	}

}
