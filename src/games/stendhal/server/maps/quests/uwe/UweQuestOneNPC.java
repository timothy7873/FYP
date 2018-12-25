package games.stendhal.server.maps.quests.uwe;
import java.util.Arrays;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.FireEventChatAction;
import games.stendhal.server.events.UweFlowIncQuestEvent;

public class UweQuestOneNPC implements LoadableContent{
	
	private String npcName = "UweQuestOne";
	private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	
	private SpeakerNPC npc;
	
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
		npc.setPosition(15, 10);
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
				"\nI am a quest one NPC, I have problems that need you to #fix.", 
				null);
		//quest
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("fix","f"), 
				null, 
				ConversationStates.INFORMATION_1, 
				"Are you familar with java code? #Yes/#No/#A bit", 
				null);
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("Yes","yes","y"), 
				null, 
				ConversationStates.ATTENDING, 
				"Please go find UweFlowIncQuestNPC and get the quest about java code", 
				null);
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("No","no","n"), 
				null, 
				ConversationStates.ATTENDING, 
				"Please visit http://www.google.com/ to find resources to learn java", 
				null);
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("A bit","a bit","Abit","abit","ab","a"), 
				null, 
				ConversationStates.ATTENDING, 
				"Please go find UweJavaTestNPC for testing of java", 
				null);
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