package Util.game.server;

import games.stendhal.server.entity.player.Player;

public class UweNpcInfo {
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
}
