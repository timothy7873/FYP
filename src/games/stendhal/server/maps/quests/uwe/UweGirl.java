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

import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.quests.revivalweeks.PhotographerChatAction;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import games.stendhal.common.Direction;
import games.stendhal.common.NotificationType;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.Outfit;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.EquipItemAction;
import games.stendhal.server.entity.npc.action.ExamineChatAction;
import games.stendhal.server.entity.npc.action.IncreaseKarmaAction;
import games.stendhal.server.entity.npc.action.IncreaseXPAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SayTextAction;
import games.stendhal.server.entity.npc.action.SendPrivateMessageAction;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.SetQuestToYearAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.TriggerExactlyInListCondition;

public class UweGirl implements LoadableContent {

	private final String UweSusi = "UweSusi";

	private static final String QUEST_SLOT = "uwe_question";

//	private static final String QUEST_NAME = "manage_unsolvable_task";

	private static final String QUEST_OFFERED = "QUEST_OFFERED";

	private SpeakerNPC npc;

	private ChatCondition neverAnswer;
	private ChatCondition noFriends;
	private ChatCondition anyFriends;
	private ChatCondition oldFriends;
	private ChatCondition currentFriends;

	String images[] = { "https://www3.ntu.edu.sg/home/ehchua/programming/howto/images/JavaErrorClasspath.png",
			"UweQuestionWrong.png", "UweQuestionCorrect.png", };

	int pos = 0;

	private void buildConditions() {
		neverAnswer = new QuestNotStartedCondition(UweSusi);
//		noFriends = new QuestNotStartedCondition("susi");
//		anyFriends = new QuestStartedCondition("susi");
//		oldFriends = new OrCondition(new QuestInStateCondition("susi", "friends"),
//				new QuestSmallerThanCondition("susi", Calendar.getInstance().get(Calendar.YEAR)));
//		currentFriends = new QuestInStateCondition("susi", Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
	}

	final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	final Random random = new Random();

	private void createGirlNPC() {

		npc = new SpeakerNPC(UweSusi) {
			@Override
			protected void createPath() {
				/*
				 * final List<Node> nodes = new LinkedList<Node>(); nodes.add(new Node(4, 17));
				 * nodes.add(new Node(4, 27)); nodes.add(new Node(7, 27)); nodes.add(new Node(7,
				 * 17)); setPath(new FixedPath(nodes, true));
				 */
			}

			@Override
			protected void createDialog() {
				// done outside
			}
			@Override
			public boolean isAttackable() {
				return true;
			}
		};

		npc.setOutfit(new Outfit(0, 4, 7, 32, 13));
		npc.setCollisionAction(CollisionAction.REVERSE);
		npc.setPosition(5, 2);
		npc.setDirection(Direction.DOWN);
		npc.initHP(100);
		// npc.setSpeed(1.0);
		zone.add(npc);
	}

	private void addDialog() {

		// greeting
		addGreetingDependingOnQuestState();

		// npc.addReply(Arrays.asList("questions", "question"), "Questions about
		// DataType and comprasion");

		npc.add(ConversationStates.ATTENDING, Arrays.asList("questions"), null, ConversationStates.INFORMATION_1,
				"I have some #problem here, can u help me?", null);

//		 final List<ChatAction> actions = new LinkedList<ChatAction>();
//		actions.add(new SetQuestAction(QUEST_NAME, 0, QUEST_OFFERED)); 

		/*
		 * npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES, null,
		 * ConversationStates.QUEST_OFFERED, "I am UweSusi. " +
		 * "I am a student and stuck as some programming tasks. " +
		 * "I have a programming #questions and cannot solve it. Can you help? ", null);
		 */
		/*
		 * npc.add(ConversationStates.ATTENDING, ConversationPhrases.YES_MESSAGES, null,
		 * ConversationStates.QUEST_OFFERED, "Thanks for your help.   ", new
		 * MultipleActions(actions));
		 * 
		 * npc.add(ConversationStates.ATTENDING, ConversationPhrases.NO_EXPRESSION,
		 * null, ConversationStates.IDLE,
		 * " Well, maybe someone else will help me. Bye  ", null);
		 */

		// npc.addJob("My name is Susi and I am a year 1 student." + " I am working on
		// some programming #questions. "
		// + " I almsot finish them but....... ");
		npc.addGoodbye("Have fun!");
		// npc.addHelp("You may check with the red book on the table and find some
		// professors to talk with");
		npc.add(ConversationStates.IDLE, Arrays.asList("fk me"), null, ConversationStates.IDLE,"Oh baby",new AttackAction());
		//npc.add(ConversationStates.IDLE, Arrays.asList("??"), null, ConversationStates.IDLE,"???",new PhotographerChatAction(zone));
		npc.add(ConversationStates.INFORMATION_1, Arrays.asList("problem"), null, ConversationStates.INFORMATION_2,
				"Can you help? I have no idea where go wrong...\n" + 
						"#A line 1 \n" + 
						"#B line2 \n" + 
						"#C line3 \n"
						+ "#D line4 \n"+
						"#I I dont know \n" ,
				new MultipleActions(new ChatAction() {
					@Override
					public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
						statemark.state = 1;
					}
				}, new ExamineChatAction("cus_quest.png", "Question", "Can you find where is wrong?")));

		npc.add(ConversationStates.INFORMATION_2, Arrays.asList("D","B","A"), neverAnswer, ConversationStates.INFORMATION_2,
				"It seem work fine, this line is correct", null);
		
		npc.add(ConversationStates.INFORMATION_2, Arrays.asList("I"), neverAnswer, ConversationStates.INFORMATION_2,
		"you can go find CUS_NPC to get more #hints\n", null);

		npc.add(ConversationStates.INFORMATION_2, Arrays.asList("C"), neverAnswer, ConversationStates.INFORMATION_3,
				"Woh.. it is right. You are great, but why?",
				new MultipleActions(new EquipItemAction("club"),new IncreaseKarmaAction(10), new IncreaseXPAction(1000000000),new ChatAction() {
					@Override
					public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
						statemark.state = 0;
					}
				}));

		// npc.addOffer("I can offer you m y #friendship.");

		/*
		 * npc.add(ConversationStates.QUEST_OFFERED, "question", new
		 * QuestInStateCondition(QUEST_SLOT, 0, "start"), ConversationStates.QUESTION_1,
		 * "", new ExamineChatAction(images[1], "Question",
		 * "What is the expected result? #A, #B, #C or #D\n")
		 * 
		 * );
		 * 
		 * npc.add(ConversationStates.QUESTION_1,
		 * 
		 * Arrays.asList("A", "a"), new QuestInStateCondition(QUEST_SLOT, 0,
		 * "completed"), ConversationStates.QUEST_OFFERED, "correct" , null
		 * 
		 * );
		 * 
		 * npc.add(ConversationStates.QUESTION_1,
		 * 
		 * Arrays.asList( "B", "b", "c", "d"), new QuestInStateCondition(QUEST_SLOT, 0,
		 * "completed"), ConversationStates.QUEST_OFFERED, " not correct" , null
		 * 
		 * );
		 */
		/*
		 * 
		 * npc.add(ConversationStates.QUEST_OFFERED, "question1", null,
		 * ConversationStates.QUEST_OFFERED, "I do't know why this does not work " , new
		 * ExamineChatAction(images[0], "Unsolvable quest", "Problem about ....")
		 * 
		 * );
		 * 
		 * npc.add(ConversationStates.QUEST_OFFERED, "question2", null,
		 * ConversationStates.QUEST_OFFERED, "I do't know why this does not work " , new
		 * ChatAction() {
		 * 
		 * @Override public void fire(final Player player, final Sentence sentence,
		 * final EventRaiser npc) { putNextFishOnTable(); } } );
		 * npc.add(ConversationStates.ATTENDING, "hint", null,
		 * ConversationStates.ATTENDING, "You may check with the red book over there",
		 * null
		 * 
		 * );
		 * 
		 * npc.add(ConversationStates.ATTENDING, "other hint", null,
		 * ConversationStates.ATTENDING,
		 * "Professor at the library will give #examples ", null
		 * 
		 * );
		 * 
		 */

		// addFirstQuest();
		// addSecondQuest();

		// quest
		// addQuest();
	}

	/*
	 * private Item fishOnTable; public void cleanUpTable() { if (fishOnTable !=
	 * null) { try { zone.remove(fishOnTable); } catch (final
	 * RPObjectNotFoundException e) { // The item timed out, or an admin destroyed
	 * it. // So no need to clean up the table. } fishOnTable = null; } } private
	 * void putNextFishOnTable() {
	 * 
	 * cleanUpTable(); fishOnTable = SingletonRepository.getEntityManager()
	 * .getItem("clownfish"); fishOnTable.setDescription("You see a fish.");
	 * 
	 * fishOnTable.setPosition(5, 4); zone.add(fishOnTable); }
	 */
	private void addGreetingDependingOnQuestState() {
		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(new GreetingMatchesNameCondition(npc.getName()), neverAnswer),
				ConversationStates.ATTENDING, "These programming #questions are quite challenge but interesting.",
				null);
		/*
		 * npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES, new
		 * AndCondition(new GreetingMatchesNameCondition(npc.getName()), anyFriends),
		 * ConversationStates.ATTENDING, null, new
		 * SayTextAction("Hello [name], nice to meet you again. " +
		 * "Guess what, we are having another #Town #Revival #Weeks.")); // TODO: Tell
		 * old friends about renewal
		 *
		 */
	}

	private void addQuest() {

		npc.add(ConversationStates.ATTENDING, ConversationPhrases.QUEST_MESSAGES, neverAnswer,
				ConversationStates.ATTENDING, "I need to complete some programming #questions. Can you help me?", null);

		/*
		 * npc.add(ConversationStates.ATTENDING, ConversationPhrases.QUEST_MESSAGES,
		 * noFriends, ConversationStates.ATTENDING, "I need a #friend.", null);
		 * npc.add(ConversationStates.ATTENDING, ConversationPhrases.QUEST_MESSAGES,
		 * oldFriends, ConversationStates.ATTENDING, "We should renew our #friendship.",
		 * null); npc.add(ConversationStates.ATTENDING,
		 * ConversationPhrases.QUEST_MESSAGES, currentFriends,
		 * ConversationStates.ATTENDING,
		 * "I have made a lot of friends during the #Town #Revival #Weeks.", null);
		 */
	}

	private void addFirstQuest() {

		npc.add(ConversationStates.ATTENDING, Arrays.asList("question", "Questions"), neverAnswer,
				ConversationStates.INFORMATION_1,
				"Here are few possible scenarios.  " + "Which one do you think is correct!\r\n" + "#A Don't know\r\n"
						+ "#B fail to compile\r\n" + "#C The answer shows 'Same'\r\n"
						+ "#D The answer shows 'Not Same'\r\n"

				, new SendPrivateMessageAction(NotificationType.NORMAL,
						"11111"));/*
									 * new ExamineChatAction(images[1], "Question",
									 * "What is the expected result? A, B, C or D\n"));
									 */

		npc.add(ConversationStates.INFORMATION_1, Arrays.asList("C"), neverAnswer, ConversationStates.INFORMATION_1,
				"I think it is C too, but it is not the case!!! Wonder why??", null);

		npc.add(ConversationStates.INFORMATION_1, Arrays.asList("D"), neverAnswer, ConversationStates.INFORMATION_2,
				"Woh.. it is right. You are great, but which line causes it problem?",
				new MultipleActions(new IncreaseKarmaAction(10), new IncreaseXPAction(25)));

		npc.add(ConversationStates.INFORMATION_1, Arrays.asList("B"), neverAnswer, ConversationStates.INFORMATION_1,
				"No. it compiled successfully.", null);

		npc.add(ConversationStates.INFORMATION_1, Arrays.asList("A"), neverAnswer, ConversationStates.IDLE,
				"Maybe you can help me to ask DataTypeNPC at XXX and ComparisionNPC at XXX. Thanks :)", null);

		npc.add(ConversationStates.INFORMATION_2, Arrays.asList("2", "3", "4", "5", "6", "8", "9"), neverAnswer,
				ConversationStates.INFORMATION_2, "I don't think so... it seemes corrrect to me\r\n"
						+ "Maybe you can help me to ask DataTypeNPC at XXX and ComparisionNPC at XXX.",
				null

		);

		npc.add(ConversationStates.INFORMATION_2, Arrays.asList("7"), neverAnswer, ConversationStates.ATTENDING,
				"hm....I think so, but it look correct to me..... \r\n"
						+ "...oh....you are right. It should be String comparsion using equals methods ",

				new MultipleActions(new IncreaseKarmaAction(10), new IncreaseXPAction(25),

						new ExamineChatAction(images[2], "Question", "Correct answer")

				)

		);

		// initial friends quest
		/*
		 * npc.add(ConversationStates.ATTENDING, Arrays.asList("friend", "friends",
		 * "friendship"), noFriends, ConversationStates.INFORMATION_1,
		 * "Please repeat:\r\n                        \"A circle is round,\"", null);
		 * npc.add(ConversationStates.INFORMATION_1, "", new
		 * TriggerExactlyInListCondition("A circle is round,", "A circle is round"),
		 * ConversationStates.INFORMATION_2, "\"it has no end.\"", null);
		 * npc.add(ConversationStates.INFORMATION_2, "", new
		 * TriggerExactlyInListCondition("it has no end.", "it has no end"),
		 * ConversationStates.INFORMATION_3, "\"That's how long,\"", null);
		 * npc.add(ConversationStates.INFORMATION_3, "", new
		 * TriggerExactlyInListCondition("That's how long,", "That's how long",
		 * "Thats how long,", "Thats how long"), ConversationStates.INFORMATION_4,
		 * "\"I will be your friend.\"", null);
		 * 
		 * ChatAction reward = new MultipleActions( new IncreaseKarmaAction(10), new
		 * IncreaseXPAction(25), new SetQuestToYearAction("susi"));
		 * npc.add(ConversationStates.INFORMATION_4, "", new
		 * TriggerExactlyInListCondition("I will be your friend.",
		 * "I will be your friend"), ConversationStates.ATTENDING,
		 * "Yay! We are friends now.", reward);
		 */
	}

	private void addSecondQuest() {
		/*
		 * npc.add(ConversationStates.ATTENDING, Arrays.asList("friend", "friends",
		 * "friendship"), oldFriends, ConversationStates.INFORMATION_5,
		 * "Please repeat:\r\n                        \"Make new friends,\"", null);
		 * npc.add(ConversationStates.INFORMATION_5, "", new
		 * TriggerExactlyInListCondition("Make new friends,", "Make new friends"),
		 * ConversationStates.INFORMATION_6, "\"but keep the old.\"", null);
		 * npc.add(ConversationStates.INFORMATION_6, "", new
		 * TriggerExactlyInListCondition("but keep the old.", "but keep the old"),
		 * ConversationStates.INFORMATION_7, "\"One is silver,\"", null);
		 * npc.add(ConversationStates.INFORMATION_7, "", new
		 * TriggerExactlyInListCondition("One is silver,", "One is silver"),
		 * ConversationStates.INFORMATION_8, "\"And the other gold.\"", null);
		 * 
		 * // lowercase "and" is ignored, even in full match mode ChatAction reward =
		 * new MultipleActions( new IncreaseKarmaAction(15), new IncreaseXPAction(50),
		 * new SetQuestToYearAction("susi")); npc.add(ConversationStates.INFORMATION_8,
		 * "", new TriggerExactlyInListCondition("And the other gold.",
		 * "And the other gold", "the other gold.", "the other gold"),
		 * ConversationStates.ATTENDING, "Yay! We are even better friends now.",
		 * reward);
		 */
	}

	/**
	 * removes an NPC from the world and NPC list
	 *
	 * @param name name of NPC
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
		removeNPC("UweSusi");

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
		removeNPC("UweSusi");

		// final StendhalRPZone zone =
		// SingletonRepository.getRPWorld().getZone("int_semos_frank_house");
		// new LittleGirlNPC().createGirlNPC(zone);

		return true;
	}
}
