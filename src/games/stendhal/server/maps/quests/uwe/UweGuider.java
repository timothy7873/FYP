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
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import games.stendhal.common.Direction;
import games.stendhal.common.grammar.Grammar;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
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
import games.stendhal.server.entity.npc.action.SayNPCNamesForUnstartedQuestsAction;
import games.stendhal.server.entity.npc.action.SayTextAction;
import games.stendhal.server.entity.npc.action.SayUnstartedQuestDescriptionFromNPCNameAction;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.SetQuestToYearAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.OrCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.QuestSmallerThanCondition;
import games.stendhal.server.entity.npc.condition.QuestStartedCondition;
import games.stendhal.server.entity.npc.condition.TriggerExactlyInListCondition;
import games.stendhal.server.entity.npc.condition.TriggerIsNPCNameForUnstartedQuestCondition;
import games.stendhal.server.maps.Region;
import games.stendhal.server.maps.ados.rosshouse.LittleGirlNPC;

public class UweGuider implements LoadableContent {

//	private static final String QUEST_NAME = "manage_unsolvable_task";
//	private static final String QUEST_OFFERED = "QUEST_OFFERED";

	private final List<String> regions = Arrays.asList(Region.SEMOS_CITY, Region.SEMOS_SURROUNDS);

	private SpeakerNPC npc;
	private ChatCondition noFriends;
	private ChatCondition anyFriends;
	private ChatCondition oldFriends;
	private ChatCondition currentFriends;

	private void buildConditions() {
//		noFriends = new QuestNotStartedCondition("susi");
//		anyFriends = new QuestStartedCondition("susi");
//		oldFriends = new OrCondition(new QuestInStateCondition("susi", "friends"),
//				new QuestSmallerThanCondition("susi", Calendar.getInstance().get(Calendar.YEAR)));
//		currentFriends = new QuestInStateCondition("susi", Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
	}

	final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_semos_village_w");
	final Random random = new Random();
	
	private void createGirlNPC() {

		npc = new SpeakerNPC("UweGuider") {

			@Override
			protected void createPath() {
	/*
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(4, 17));
				nodes.add(new Node(4, 27));
				nodes.add(new Node(7, 27));
				nodes.add(new Node(7, 17));
				setPath(new FixedPath(nodes, true));
*/
			}

			@Override
			protected void createDialog() {
				// done outside
			}
		};

		// npcs.add(npc);
		npc.setEntityClass("oracle1npc");
		// npc.setCollisionAction(CollisionAction.REVERSE);
		npc.setPosition(26, 40);
		npc.setDirection(Direction.DOWN);
	//	npc.initHP(100);
	//	npc.setSpeed(1.0);
		zone.add(npc);
	}

	private void addDialog() {

		// greeting
		// addGreetingDependingOnQuestState();


		npc.add(ConversationStates.IDLE, 
				ConversationPhrases.GREETING_MESSAGES, 
				null, 
				ConversationStates.ATTENDING,
				"A lot of people needs #help, what can you do?\" ",  
				null);
		 

			// use a standard action to list the names of NPCs for quests which haven't been started in this region
		
		npc.addReply(ConversationPhrases.HELP_MESSAGES,  
				 "In Semos City #abc, #xyz, #peter, #mary,#paul need your help."
				 + " In Semos Surrounds Gamblos needs your help."
				);

			// if the player says an NPC name, describe the quest (same description as in the travel log)
		/*	npc.add(ConversationStates.ATTENDING,
					"",
					new TriggerIsNPCNameForUnstartedQuestCondition(regions),
					ConversationStates.ATTENDING,
					null,
					new SayUnstartedQuestDescriptionFromNPCNameAction(regions));
*/
			npc.addQuest("Oh, there are so many others who may need #help in " + Grammar.enumerateCollection(regions) + ", I wouldn't ask you anything new.");
		//	npc.addJob("I have no real occupation, my skill is in guiding you in how to #help others, especially in " + Grammar.enumerateCollection(regions) + ".");
	//		npc.addOffer("*giggles* I don't sell anything. I can tell you about my #sisters or my #name, if you like.");
	//		npc.addReply("sisters", "My sisters live in other cities. Find them to learn how to #help those nearest them.");
	//		npc.addReply("name", "Me and my #sisters all have names of flowers. " +
	//				"My mother loved the little blooms of the forget-me-not plants.  Well, she couldn't name me Forget-me-not, so she named me Periwinkle cause the blooms looked somewhat alike.   Please, don't forget me...");

			// just to be nice :)
	//		addEmotionReply("hugs", "hugs");
			npc.addGoodbye("Thank you for stopping by.");
	 
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
		removeNPC("UweGuider");

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
		removeNPC("UweGuider");

		// final StendhalRPZone zone =
		// SingletonRepository.getRPWorld().getZone("int_semos_frank_house");
		// new LittleGirlNPC().createGirlNPC(zone);

		return true;
	}
}
