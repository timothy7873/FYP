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
import java.util.List;

import games.stendhal.common.Direction;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.events.ExamineEvent;
import games.stendhal.server.maps.quests.uwe.actions.MeetNPCChatAction;

public class UweComparisionNPC implements LoadableContent {

	 public static String npcName = "UweComparison";
	private SpeakerNPC npc;
	private List<String> operators = Arrays.asList("<", "<=", ">", ">=", "==", "!=");
	final String images[] = { "lessThan.png", "lessThanEqualsTo.png", "greaterThan.png", 
			"greaterThanEqualsTo.png",
			"equalsTo.png", "notEqualsTo.png" };

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
		npc.setPosition(16, 15);
		npc.setDirection(Direction.UP);
		npc.initHP(100);
		// npc.setSpeed(1.0);
		zone.add(npc);
	}


	
	private void addDialog() {

		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES, null, ConversationStates.ATTENDING,
			 "  I can show you #comparison operator for primitive types.",
				new MeetNPCChatAction(npcName));

		npc.addJob("I am Comparison NPC. Are you interested in comparison operator? ");
		
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("comparison", "compare", "operator"), 
				null,
				ConversationStates.ATTENDING, 
				"Comparison operator compares two different things. The comparison operators are #<, #<=, #>, #>=, #==, and #!=. "
				+ "It tests the value of two #primitive types, but not for #String literals.  ", null);

		npc.add(ConversationStates.ATTENDING, operators, null, ConversationStates.ATTENDING,
				"Here is the comparsion operator for primitive types ",
				new ChatAction() {
					@Override
					public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
						String symbol = sentence.getOriginalText().toLowerCase().trim();
						int index = operators.indexOf(symbol);
						String path = "http://localhost:8888/GameDesign/img/operator/" + images[index];
						player.addEvent(new ExamineEvent(path, symbol, "operator"));
						player.notifyWorldAboutChanges();
					}

				}
		);
		
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("Primitive", "primitive"), 
				null,
				ConversationStates.ATTENDING, 
				" Primitive type are boolean, byte, short, char, int, long, float, and double. "
				+ "Maybe you can go and talk with UweDataType at XXXX .  "
				+ "He will give you information about DataType. ", null);

		
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList( "String", "string" ), 
				null,
				ConversationStates.ATTENDING, 
				"String is not a #Primitive type. You need to use a method such as #equals and #compare to do string comparison.   ", null); 
		
		npc.add(
				ConversationStates.ATTENDING,
				ConversationPhrases.YES_MESSAGES,
				null,
				ConversationStates.ATTENDING,
				"The comparison operatrors are #<, #<=, #>, #>=, #==, and #!=. "+
				 "They test the value of two #primitive types, but are not used to test for #String Literals  ",
				null); 
		
		npc.add(
				ConversationStates.ATTENDING,
				ConversationPhrases.NO_MESSAGES,
				null,
				ConversationStates.IDLE,
				"It is ok. You will figure it out.",
				null);
		
		npc.addQuest("I don't have any quests for you, but I can tell you about the comparison operator");
		
		npc.addGoodbye("Have fun!");
		npc.addHelp("You may want to see if UweSusi needs help");
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
