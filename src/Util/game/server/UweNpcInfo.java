package Util.game.server;

import java.util.HashMap;
import java.util.Map;

import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.quests.uwe.UweNpc;

public class UweNpcInfo {
	public static Map<String,UweNpc> npcs=new HashMap<String,UweNpc>();
	
	public static void setNpcQuestClean(Player player, String npcId)
	{
		player.setQuest(npcId, "blank");
	}
	public static void setNpcQuestStart(Player player, String npcId, String journeyId)
	{
		player.setQuest(npcId, "start_"+journeyId);
	}
	public static void setNpcQuestRead(Player player, String npcId, String journeyId)
	{
		player.setQuest(npcId, "read_"+journeyId);
	}
	public static void setNpcQuestDone(Player player, String npcId)
	{
		player.setQuest(npcId, "done");
	}
	
	public static void callJourneySetected(String npcId)
	{
		UweNpc npc=npcs.get(npcId);
		if(npc==null)
			return;
		npc.journeyChosenCallback();
	}
}