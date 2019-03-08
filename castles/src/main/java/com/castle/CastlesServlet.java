package com.castle;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.castle.persistence.Armory;
import com.castle.persistence.ArmoryDAO;
import com.castle.persistence.Player;
import com.castle.persistence.PlayerDAO;

public class CastlesServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		if(req.getRequestURI().equals("/go")){
			PlayerDAO playerDao = new PlayerDAO();
			ArmoryDAO armoryDao = new ArmoryDAO();
			
			Player player=null;

			String level = req.getParameter("level"); // text from fields
			String name = req.getParameter("name"); // text from fields
			String action = req.getParameter("action"); // action from buttons
			

			if (action != null) //if action is not null
			{
				if (action.equals("add")) //add entity button clicked
				{
					player= new Player();	
					player.setName(name);
				
					try{
						
						Integer value = Integer.valueOf(level);
						player.setLevel(value);
					} catch (NumberFormatException e) {
						player.setLevel(0);
					}
					
					Armory sword = new Armory("sword",10,2);
					Armory helmet = new Armory("helmet",0,8);
					armoryDao.save(sword);
					armoryDao.save(helmet);
					//sword.setParent(player);
					//helmet.setParent(player);
					
					player.setArmory(helmet);
					player.setArmory(sword);
					
					playerDao.save(player);
					resp.sendRedirect("/castle.jsp?guestbookName=" + player.toString());
				}
				else if (action.equals("query")) //query button clicked
				{	            	
					
					player = playerDao.load(name);
					
					List<Player> swordMasters = playerDao.getDeadlyPlayers(5);

					if(player != null){
						resp.sendRedirect("/castle.jsp?guestbookName=" + player.toString()+" "+swordMasters.toString());
					}else{
						System.out.println("No Player: "+name);
						resp.sendRedirect("/castle.jsp?guestbookName=NoPlayerFound");
					}

				}
			}
		}
	}
}
