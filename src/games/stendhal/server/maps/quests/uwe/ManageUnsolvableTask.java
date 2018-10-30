/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2011 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests.uwe;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.Region;
import games.stendhal.server.maps.quests.AbstractQuest;

/**
 * QUEST: Speak with Hayunn
 * <p>
 * PARTICIPANTS: <ul><li> Hayunn Naratha</ul>
 *
 * STEPS: <ul>
 * <li> Talk to Hayunn to activate the quest.
 * <li> He asks you to kill a rat, also offering to teach you how
 * <li> Return and get directions to Semos
 * <li> Return and learn how to click move, and get some URLs
 * </ul>
 *
 * REWARD: <ul><li> 20 XP <li> 5 gold coins <li> studded shield </ul>
 *
 * REPETITIONS: <ul><li> Get the URLs as much as wanted but you only get the reward once.</ul>
 */
public class ManageUnsolvableTask extends AbstractQuest {

	private static final String QUEST_NAME = "manage_unsolvable_task";
	private static final String QUEST_OFFERED = "QUEST_OFFERED";

	private static final int TIME_OUT = 60;

	private static Logger logger = Logger.getLogger(ManageUnsolvableTask.class);

	@Override
	public String getSlotName() {
		return QUEST_NAME;
	}

	@Override
	public List<String> getHistory(final Player player) {
		final List<String> res = new ArrayList<String>();
		if (!player.hasQuest(QUEST_NAME)) {
			return res;
		}
		final String questState = player.getQuest(QUEST_NAME);
		
		res.add("UWE Susi has problem and I am going to help her" );
		if (player.getQuest(QUEST_NAME, 0).equals(QUEST_OFFERED) )  {
			res.add("After I found some information, I should go back to tell her!");
		}
		if (player.getQuest(QUEST_NAME, 0).equals(QUEST_OFFERED)) {
			return res;
		}
//		res.add("I killed the rat. Hayunn will teach me more about the world now.");
//		if ("killed".equals(questState)) {
//			return res;
//		}
//		res.add("Hayunn gave me a bit of money and told me to go find Monogenes in Semos City, who will give me a map.");
//		if ("taught".equals(questState)) {
//			return res;
//		}
//		res.add("Hayunn told me lots of useful information about how to survive, and gave me a studded shield and some money.");
//		if (isCompleted(player)) {
//			return res;
//		}
		// if things have gone wrong and the quest state didn't match any of the above, debug a bit:
		final List<String> debug = new ArrayList<String>();
		
		debug.add("Quest state is: " + questState);
		logger.error("History doesn't have a matching quest state for " + questState);
		return debug;
	}

	private void prepareQuest() {

		final SpeakerNPC npc = npcs.get("UweSusi");

		// player wants to learn how to attack
		npc.add(
				ConversationStates.ATTENDING,
				ConversationPhrases.YES_MESSAGES,
				new AndCondition(new GreetingMatchesNameCondition(getName()),
						new QuestInStateCondition(QUEST_NAME, 0, QUEST_OFFERED)),
				ConversationStates.ATTENDING,
				"Checkout my #questions and help me to answer it",
		 		null);

		//player doesn't want to learn how to attack
		npc.add(
				ConversationStates.ATTENDING,
				ConversationPhrases.NO_MESSAGES,
				new AndCondition(new GreetingMatchesNameCondition(getName()),
						new QuestInStateCondition(QUEST_NAME, 0, QUEST_OFFERED)),
				ConversationStates.IDLE,
				"It seems that you are not helpful : (. Bye",
				null);  
		
		npc.setPlayerChatTimeout(TIME_OUT);
	}

	@Override
	public void addToWorld() {
		fillQuestInfo(
				"Uwe Susi",
				"Solving problem.....",
				false);
		prepareQuest();
	}

	@Override
	public String getName() {
		return "ManageUnsolvableTask";
	}

	@Override
	public String getRegion() {
		return Region.SEMOS_CITY;
	}

	@Override
	public String getNPCName() {
		return "UweSusi";
	}
}
