package com.controvol.dialogs;

import java.util.ArrayList;
import java.util.List;

public class Messages {

	//setting the dialog elements
	List<String[]> msg; 
	
	public void setMsg(List<FixResource> resources){		
		msg = new ArrayList<String[]>(); 		

		for(FixResource resource:resources){		
			/*@todo
			 * the next line is the correct one to let in the code
			 */
			//String className=resource.getClass().getCanonicalName();

			/*@todo
			 * replace the next line to the correct one above
			 */
			String className="br.ufpr.game.nosql.Player.java";
			for(Fixes fix : resource.getFixes()){
				switch(fix){
				case IGNORE: 
					/*@todo
					 * correct the message below
					 */
					msg.add(new String[]{"Add @Ignore to remove 'level'",className});
					break;
				case ALSO_LOAD: 
					/*@todo
					 * correct the message below
					 */
					msg.add(new String[]{"Add @AlsoLoad to rename 'level' to 'rank'",className});
					break;
				case IGNORE_SAVE: 
					msg.add(new String[]{"Add @IgnoreSave",className});	
					break;
				case IGNORE_LOAD: 
					msg.add(new String[]{"Add @IgnoreLoad",className});	
					break;
				case ID: 
					msg.add(new String[]{"Add @Id",className});	
					break;
				case RESTORE: 
					/*@todo
					 * correct the message below
					 */
					msg.add(new String[]{"Restore attribute 'level'",className});	
					break;
				default:
					msg.add(new String[]{"Default","Unknown"});	
					break;

				}
			}

		}		
	}

	public List<String[]> getMsg(){
		return msg;
	}
}
