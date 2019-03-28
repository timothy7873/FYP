package Util.game.client;

import Util.Management.Reward;
import games.stendhal.client.ClientSingletonRepository;
import marauroa.common.game.RPAction;

public class UweClientAction {
	public static void returnItems()
	{
		RPAction action = new RPAction();
		action.put("type", "UweReturnItem");
		ClientSingletonRepository.getClientFramework().send(action);
	}
	public static void submitQuest(String journeyRowId, Reward[] rewards)
	{
		//cal exp
		int exp=0;
		for(int i=0;i<rewards.length;i++)
			exp+=rewards[i].exp;
		
		//cal karma
		double karma=0;
		for(int i=0;i<rewards.length;i++)
			karma+=rewards[i].karma;
		
		//cal money
		int money=0;
		for(int i=0;i<rewards.length;i++)
			money+=rewards[i].money;
		
		//get items
		String items="";
		for(int i=0;i<rewards.length;i++)
		{
			if(rewards[i].count>0 && rewards[i].itemName!=null)
				items+=rewards[i].itemName+","+rewards[i].count+"\n";
		}
		items=items.substring(0,items.length()-1);
		
		//send
		RPAction action = new RPAction();
		action.put("type", "UweQuestSubmit");
		action.put("journeyRowId", journeyRowId);
		
		action.put("karma", karma);
		action.put("money", money);
		action.put("exp", exp);
		action.put("items", items);
		ClientSingletonRepository.getClientFramework().send(action);
	}
	public static void selectJourney(String npcId, String journeyId)
	{
		//send
		RPAction action = new RPAction();
		action.put("type", "UweSelectJourney");
		action.put("npcId", npcId);
		action.put("journeyId", journeyId);
		ClientSingletonRepository.getClientFramework().send(action);
	}
	
}
