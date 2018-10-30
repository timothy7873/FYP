/***************************************************************************
 *                   (C) Copyright 2003-2016 - Stendhal                    *
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.Outfit;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.ExamineChatAction;
import games.stendhal.server.entity.npc.action.IncreaseKarmaAction;
import games.stendhal.server.entity.npc.action.IncreaseXPAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.SetQuestToYearAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.TriggerExactlyInListCondition;

public class UweLily implements LoadableContent {

	private final String UweLily = "UweLily";

	//private static final String QUEST_SLOT ="uwe_question"; 	
	
	//private static final String QUEST_OFFERED = "QUEST_OFFERED";
	
	private SpeakerNPC npc;

	private ChatCondition neverAnswer; 

	int pos = 0;
	
String[] images= {
		"UweConstant.png"
	};

	private void buildConditions() {
		neverAnswer =  new QuestNotStartedCondition(UweLily);
   }

	final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_semos_village_w");
	final Random random = new Random();
	
	private void createGirlNPC() {

		npc = new SpeakerNPC(UweLily) {
			@Override
			protected void createPath() {
 
			}

			@Override
			protected void createDialog() {
				// done outside
			}
		};
 
	//	npc.setOutfit(new Outfit(0, 4, 7, 32, 13));
		
		npc.setEntityClass("noimagenpc");
		npc.setCollisionAction(CollisionAction.REVERSE);
		npc.setPosition(15, 46);
		npc.setDirection(Direction.DOWN);
		npc.initHP(100);
	//	npc.setSpeed(1.0);
		zone.add(npc);
	}

	private void addDialog() {

		// greeting
		 addGreetingDependingOnQuestState();

		 npc.addReply( Arrays.asList("questions", "question"),
					"Questions about Constant and variables");
	
 
		npc.addJob("My name is Lily and I am a year 1 student."
	 
				);
		npc.addGoodbye("Have fun!");
	 

		 addFirstQuest(); 
		addQuest();
	}
	 
	private void addGreetingDependingOnQuestState() {
		npc.add(ConversationStates.IDLE, 
				ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(new GreetingMatchesNameCondition(npc.getName()), 
						neverAnswer),
				ConversationStates.ATTENDING, 
				"I am solving ome programming #questions.", 
				null); 
	}

	private void addQuest() {

		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				neverAnswer,
				ConversationStates.ATTENDING, 
				"Hi again, I am still working on complete some programming #questions. Can you help me?", 
				null);
   }

	private void addFirstQuest() {
		
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("questions", "question", "Questions"), 
				neverAnswer,
				ConversationStates.INFORMATION_1, 
				"Here are few possible scenarios.  "
				+"Which one do you think is correct!\r\n"
						+ "#A Don't know\r\n"
						+ "#B fail to compile\r\n"
						+ "#C The answer shows 'Same'\r\n"
						+ "#D The answer shows 'Not Same'\r\n"
						,
				new ExamineChatAction(images[0], "Question",
								"What is the expected result? A, B, C or D\n"));
		
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("C" ), 
				neverAnswer,
				ConversationStates.INFORMATION_1, "I think it is C too, but it is not the case!!! Wonder why??",
				null);
				
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("D" ), 
				neverAnswer,
				ConversationStates.ATTENDING, 
				"Woh.. it is right. You are great, but which line causes it problem?",
			    new MultipleActions(
					       new IncreaseKarmaAction(10), 
					       new IncreaseXPAction(25)
				)
		);
		
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("B" ), 
				neverAnswer,
				ConversationStates.INFORMATION_1, "No. it compiled successfully.",
				null);
		
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("A" ), 
				neverAnswer,
				ConversationStates.IDLE,
				"Maybe you can help me to ask DataTypeNPC at XXX and ComparisionNPC at XXX. Thanks :)",
				null);
		
	 
		
		 
		}

	
 
	/**
	 * removes an NPC from the world and NPC list
	 *
	 * @param name
	 *            name of NPC
	 */
	private void removeNPC(String name) {
		SpeakerNPC npc = NPCList.get().get(name);
		if (npc == null) {
			return;
		}
		npc.getZone().remove(npc);
	}

	/**
	 * removes Susi from her home in Ados and adds her to the Mine Towns.
	 */
	@Override
	public void addToWorld() {
		removeNPC("UweLily");

		buildConditions();
		createGirlNPC();
		
		addDialog();
	}
	
	
	
	/**
	 * removes Susi from the Mine Town and places her back into her home in Ados.
	 *
	 * @return <code>true</code>, if the content was removed, <code>false</code>
	 *         otherwise
	 */
	@Override
	public boolean removeFromWorld() {
		removeNPC("UweLily");

		// final StendhalRPZone zone =
		// SingletonRepository.getRPWorld().getZone("int_semos_frank_house");
		// new LittleGirlNPC().createGirlNPC(zone);

		return true;
	}
}
