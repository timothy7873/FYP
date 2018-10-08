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

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import games.stendhal.common.Direction;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.Outfit;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.ExamineChatAction;
import games.stendhal.server.entity.npc.action.IncreaseKarmaAction;
import games.stendhal.server.entity.npc.action.IncreaseXPAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SayTextAction;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.SetQuestToYearAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.TriggerExactlyInListCondition;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPObjectNotFoundException;

public class UweDataNPC implements LoadableContent {

	private String npcName = "UweDataType";
	private SpeakerNPC npc;

	private void buildConditions() {
	}

	final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");

	private void createNPC() {

		npc = new SpeakerNPC(npcName) {

			@Override
			protected void createPath() {

			}

			@Override
			protected void createDialog() {
				// done outside
			}
		};

		// npc.setOutfit(new Outfit(0, 4, 7, 32, 13));
		npc.setEntityClass("noimagenpc");
		npc.setCollisionAction(CollisionAction.REVERSE);
		npc.setPosition(6, 15);
		npc.setDirection(Direction.UP);
		npc.initHP(100);
		// npc.setSpeed(1.0);
		zone.add(npc);
	}
	private void createNPC1() {

		npc = new SpeakerNPC(npcName) {

			@Override
			protected void createPath() {

			}

			@Override
			protected void createDialog() {
				// done outside
			}
		};

		// npc.setOutfit(new Outfit(0, 4, 7, 32, 13));
		npc.setEntityClass("noimagenpc");
		npc.setCollisionAction(CollisionAction.REVERSE);
		npc.setPosition(6, 18);
		npc.setDirection(Direction.UP);
		npc.initHP(100);
		// npc.setSpeed(1.0);
		zone.add(npc);
	}

	private void addDialog() {

		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES, null, ConversationStates.ATTENDING,
				"I am DataType NPC.    I can tell you more #datatypes ", null);
		
		npc.addJob( "I am DataType NPC. Are you interested in Data Type?   I can tell you more #datatypes ");

		
		npc.add(ConversationStates.ATTENDING,
				Arrays.asList("datatypes", "data", "type"),
				null, 
				ConversationStates.ATTENDING, 
				"Two different data types:  #primitive  and #string literals"
			    + " #primitive data types in Java are: boolean, byte, short, char, int, long, float, and double."
				,
				null
		);
		
		
		String images[] = { "http://localhost:8888/GameDesign/api/datatype.php?name=summary" };
		npc.add(ConversationStates.ATTENDING,
				Arrays.asList("primitive"),
				null, ConversationStates.ATTENDING, "Here is the summary ",
				new ExamineChatAction(images[0], "Primitive Types", "Here is the summary")

		);
		  
	 
		npc.add(ConversationStates.ATTENDING,
				Arrays.asList("String", "strings","Literals"  ),
				null, ConversationStates.ATTENDING, 
				"In addition to #primitive types, "
				+ "#String represents character strings." 
				+ " String is a reference or object type, not a primitive type. "
				+ " Various methods #equals() are available."
				, null );

		npc.add(ConversationStates.ATTENDING,
				Arrays.asList("equals()","equals", "equal"  ),
				null, ConversationStates.ATTENDING, 
				" The equals() method acts the same as the == operator, but the test the identity. "
				+ "I can show you an #example. ", null );
		
		npc.add(ConversationStates.ATTENDING,
				Arrays.asList("example"  ),
				null, ConversationStates.ATTENDING, 
				" The equals() method acts the same as the == operator, but the test the identity. "
				+ "I can show you an #example. ", null );


		npc.addQuest("I don't have any quests for you, but I can tell you about the datatypes operator");
		
		npc.addGoodbye("Have fun!");
		npc.addHelp("You may want to see if UweSusi need help");
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
		removeNPC(npcName);

		buildConditions();
		createNPC();
		createNPC1();

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
		removeNPC(npcName);

		// final StendhalRPZone zone =
		// SingletonRepository.getRPWorld().getZone("int_semos_frank_house");
		// new LittleGirlNPC().createGirlNPC(zone);

		return true;
	}
}
