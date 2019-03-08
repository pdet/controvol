package com.castle.types;


public class PlayerTypeMngr {

	PlayerType type;
	
	/**
	 * The following method mimics app version #0
	 * @param score
	 */
	public void setType(Double score){
		if(score < 10.0)
			type = LevelType.BEGINNER;
		else if(score >= 10.0 && score < 20.0)
			type = LevelType.INTERMEDIATE;
		else
			type = LevelType.ADVANCED;
	}
	
	/**
	 * The following method mimics app version #2
	 * @param score
	 */
	public void setType(Integer score){	
		if(score < 10)
			type = CharacterType.WARRIOR;
		else if(score >= 10 && score < 60)
			type = CharacterType.WIZARD;
		else 
			type = CharacterType.MENTALIST;
	}
	
	public String getType(){
		return type.toString();
	}
	
}
