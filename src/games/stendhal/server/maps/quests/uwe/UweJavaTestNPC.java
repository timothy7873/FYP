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
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.UweYesNoTestValidCondition;
import games.stendhal.server.events.UweYesNoTestEvent;

public class UweJavaTestNPC implements LoadableContent{
	
	private String npcName = "UweJavaTest";
	//private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("-1_semos_dungeon");
	private final Point spawnPoint = new Point(30,36);
	
	private SpeakerNPC npc;
	private String leaderNpc;
	
	public UweJavaTestNPC(String leaderNpc)
	{
		this.leaderNpc=leaderNpc;
	}
	
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
				"\nI am a Java test NPC, I can give #test to you to practise java", 
				null);
		//quest
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("test","t"), 
				new UweYesNoTestValidCondition(leaderNpc), 
				ConversationStates.ATTENDING, 
				"", 
				new FireEventChatAction(new UweYesNoTestEvent(leaderNpc,"Java test"))
				);
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("test","t"), 
				new NotCondition(new UweYesNoTestValidCondition(leaderNpc)), 
				ConversationStates.ATTENDING, 
				"Sorry but I currently have no test for you.", 
				null
				);
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