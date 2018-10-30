/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.semos.guardhouse;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.EquipItemAction;
import games.stendhal.server.entity.npc.action.ExamineChatAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;

/**
 * An old hero (original name: Hayunn Naratha) who players meet when they enter
 * the semos guard house.
 *
 * @see games.stendhal.server.maps.quests.BeerForHayunn
 * @see games.stendhal.server.maps.quests.MeetHayunn
 */
public class StarterNPC implements ZoneConfigurator {

	private static final String QUEST_SLOT = "meet_starter";
	
	// private static final String QUEST_SLOT="meet_FactNPC";
	// private static final String QUEST_SLOT_1="meet_GuideNPC";

	// this is just because he has a 'shop' to sell potions
	private final ShopList shops = SingletonRepository.getShopList();

	@Override
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(StendhalRPZone zone) {
		SpeakerNPC npc = new SpeakerNPC("Starter") {

			@Override
			public void createDialog() {
				// A little trick to make NPC remember if it has met
				// player before and react accordingly
				// NPC_name quest doesn't exist anywhere else neither is
				// used for any other purpose

				final List<ChatAction> actions = new LinkedList<ChatAction>();
				actions.add(new SetQuestAction(QUEST_SLOT, 0, "start")); 
				
				 
				addHelp("Do you want me to arrange quests for you?");
				
				add(ConversationStates.IDLE, 
				    ConversationPhrases.GREETING_MESSAGES, 
				     null,
					 ConversationStates.ATTENDING, 
					 "How can I #help you?",
					 new MultipleActions(actions));
						
				addJob("My job is to help young programmer grow.    .");
		

	
			
				addGoodbye();
				// further behaviour is defined in quests.
			}

			@Override
			protected void createPath() {
				List<Node> nodes = new LinkedList<Node>();
//				nodes.add(new Node(8, 9));
//				nodes.add(new Node(10, 9));
				nodes.add(new Node(10, 9));
				nodes.add(new Node(10, 9));
				nodes.add(new Node(13, 9));
				setPath(new FixedPath(nodes, true));
			}

		};
		npc.setPosition(13, 9);
		npc.setCollisionAction(CollisionAction.REVERSE);
		npc.setEntityClass("wisemannpc");
		npc.setDescription("Director will arrange quests for the player");
		npc.setBaseHP(100);
		npc.setHP(85);
		zone.add(npc);
	}

}