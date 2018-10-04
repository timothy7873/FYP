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

public class UweConstantNPC implements LoadableContent {

	 public static String npcName = "UweConstant";
	private SpeakerNPC npc;
	//private List<String> operators = Arrays.asList("<", "<=", ">", ">=", "==", "!=");
	//final String images[] = { "lessThan.png", "lessThanEqualsTo.png", "greaterThan.png", 
	//		"greaterThanEqualsTo.png",
	//		"equalsTo.png", "notEqualsTo.png" };

	private void buildConditions() {
	}

	final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_semos_village_w");

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
		npc.setPosition(7, 44);
		npc.setDirection(Direction.UP);
		npc.initHP(100);
		// npc.setSpeed(1.0);
		zone.add(npc);
	}


	
	private void addDialog() {

		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES, null, ConversationStates.ATTENDING,
			 "I am a Constant NPC.\n" +
			 "I can show you information about #constant and #variable as well as their #syntax. \n"  +
			 "I can also tell their #difference.",
				new MeetNPCChatAction(npcName));

		npc.addJob("I am a Constant NPC and I know about #syntax of constant and variables.");
		
		
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("variable	", "var", "variables"), 
				null,
				ConversationStates.ATTENDING, 
				"A variable is a container that holds values that are used in a Java program. Every variable must be declared to use a #data type. \n"+
				"I can show you an #example of each type.", null);
		
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("diff", "difference"), 
				null,
				ConversationStates.ATTENDING, 
				"It is very simple. The values of #variables can be changed anytime; whereas constant is a variable that values cannot be changed after assignment is made ", null);
		
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("constants", "const"), 
				null,
				ConversationStates.ATTENDING, 
				"A constant is a #variable whose value cannot change once it has been assigned. Java doesn't have built-in support for constants, but the variable modifiers static and final can be used to effectively create one.", null);
		

		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("example", "examples", "ex"), 
				null,
				ConversationStates.ATTENDING, 
				"For example, a variable could be declared to use one of the eight primitive #data types: byte, short, int, long, float, double, char or boolean. And, every variable must be given an initial value before it can be used.", null);
		
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("data", "datatypes"), 
				null,
				ConversationStates.ATTENDING, 
				"You can find more detail fromby UweDataType at XXXX.", null);
		
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("syntax", "syn"), 
				null,
				ConversationStates.ATTENDING, 
				"int myAge = 21;\n" + 
				"The variable \"myAge\" is declared to be an int data type and initialized to a value of 21. \n" + 
				"final int DAYS_IN_WEEK = 7; \n" + 
				"The variable \"DAYS_IN_WEEK\" is declared to be an int data type and initialized to a value of 7; However, it cannot changed.", null);
		 
		npc.addGoodbye("Have fun!");
		npc.addHelp("You may want to see if UweLily needs help");

		npc.addQuest("I don't have any quests for you. If you understand about Constant and Variable, you may go and help UweLily");
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
