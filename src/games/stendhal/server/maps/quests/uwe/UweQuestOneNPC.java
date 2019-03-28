package games.stendhal.server.maps.quests.uwe;
import java.awt.Point;
import java.util.Arrays;

import Util.Management.HardcodeManagementAPI;
import Util.Management.ManagementAPI;
import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.UweProvideHintAction;
import games.stendhal.server.entity.npc.action.UweInitQuestStateAction;
import games.stendhal.server.entity.npc.action.UweTouchLogicalQuestChatAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestStartedCondition;
import games.stendhal.server.entity.npc.condition.UweYesNoTestValidCondition;

public class UweQuestOneNPC implements LoadableContent{
	
	public static String npcName = "UweQuestOne";
	//private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("-1_semos_dungeon");
	private final Point spawnPoint = new Point(20,33);
	
	public SpeakerNPC npc;
	
	public UweQuestOneNPC(){}
	
	@Override
	public void addToWorld() {
		//removeNPC(npcName);

		createNPC();
		addDialog();
		buildConditions();
	}
	private void createNPC() {

		npc = new SpeakerNPC(npcName) {
			@Override
			protected void createPath() {}
			@Override
			protected void createDialog() {}
		};

		npc.setEntityClass("noimagenpc");
		npc.setCollisionAction(CollisionAction.REVERSE);
		npc.setPosition(spawnPoint.x, spawnPoint.y);
		npc.setDirection(Direction.UP);
		npc.initHP(100);
		zone.add(npc);
	}
	private void addDialog()
	{
		//hi
		npc.add(ConversationStates.IDLE, 
				ConversationPhrases.GREETING_MESSAGES, 
				null, 
				ConversationStates.ATTENDING,
				"\nI am a quest one NPC, I can provide #quest for you.", 
				new UweInitQuestStateAction(npcName));
		//quest
		
		
		//quest blank
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new AndCondition(
						new QuestStartedCondition(npcName), 
						new QuestInStateCondition(npcName, "blank")), 
				ConversationStates.ATTENDING, 
				null, 
				new UweTouchLogicalQuestChatAction(npc, 
						"Are you familar with java code? #Yes/ #No/ #A #bit",
						"Sorry! We currently have no quests that can provide to you!",
						ConversationStates.INFORMATION_1));
		
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("Yes","yes","y"), 
				null, 
				ConversationStates.ATTENDING, 
				"Please go find #UweLogicalErrorQuestNPC and get the quest about java code", 
				new SetQuestAction(npcName, "started"));
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("A bit","a bit","Abit","abit","ab","a"), 
				new UweYesNoTestValidCondition(npcName), 
				ConversationStates.ATTENDING, 
				"Please go find #UweJavaTestNPC to practise java", 
				null);
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("A bit","a bit","Abit","abit","ab","a"), 
				new NotCondition(new UweYesNoTestValidCondition(npcName)), 
				ConversationStates.ATTENDING, 
				"Please visit #http://www.google.com/ to find resources to learn java", 
				null);
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("No","no","n"), 
				null, 
				ConversationStates.ATTENDING, 
				"Please visit #http://www.google.com/ to find resources to learn java", 
				null);
		
		
		//quest done
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new AndCondition(
						new QuestStartedCondition(npcName), 
						new QuestInStateCondition(npcName, "done")), 
				ConversationStates.ATTENDING, 
				"Good job, you have complete the previous quest\nLet me give you some bonus reward\nWe are welcome if you want more #quest", 
				new SetQuestAction(npcName, "blank"));
		
		//quest doing
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new AndCondition(
						new QuestStartedCondition(npcName), 
						new QuestInStateCondition(npcName, "started")), 
				ConversationStates.ATTENDING, 
				"Please go find #UweLogicalErrorQuestNPC and get the quest about java code", 
				null);
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new AndCondition(
						new QuestStartedCondition(npcName), 
						new QuestInStateCondition(npcName, "read")), 
				ConversationStates.INFORMATION_2, 
				"Oh you came back, if you think the quest is too hard, we can provide some #hints to you", 
				null);
		npc.add(ConversationStates.INFORMATION_2, 
				Arrays.asList("hints","hint","h"), 
				null, 
				ConversationStates.IDLE, 
				null, 
				new UweProvideHintAction(npc));
		
		//bye
		npc.addGoodbye("Have fun!");
		
	}
	private void buildConditions(){}
	
	
	@Override
	public boolean removeFromWorld() {
		removeNPC(npcName);
		return true;
	}
	private void removeNPC(String name) {
		npc = NPCList.get().get(name);
		if (npc == null) {
			return;
		}
		npc.getZone().remove(npc);
	}
}