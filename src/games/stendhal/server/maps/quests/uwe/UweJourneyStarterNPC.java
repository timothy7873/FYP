package games.stendhal.server.maps.quests.uwe;
import java.awt.Point;
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
import games.stendhal.server.entity.npc.action.UweStartQuestAction;
import games.stendhal.server.events.UweShowJourneyListEvent;

public class UweJourneyStarterNPC extends UweNpc{
	public static String npcName = "UweJourneyStarterOneNPC";
	protected final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("-1_semos_dungeon");
	protected final Point spawnPoint = new Point(6,10);
	
	private SpeakerNPC npc;
	
	public UweJourneyStarterNPC(){}
	
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
				"\nI am a journey NPC, I can provide #journey for you.", 
				new UweStartQuestAction(npcName, "blank"));
		//journey
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("journey","j","jn"), 
				null, 
				ConversationStates.ATTENDING,
				null, 
				new FireEventChatAction(new UweShowJourneyListEvent(npcName, true)));
		
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