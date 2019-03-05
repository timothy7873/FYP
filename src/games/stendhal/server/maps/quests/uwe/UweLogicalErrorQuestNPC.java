package games.stendhal.server.maps.quests.uwe;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.FireEventChatAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.UweFireQuestEventChatAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.OrCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.QuestStartedCondition;
import games.stendhal.server.events.UweFlowIncQuestEvent;

public class UweLogicalErrorQuestNPC implements LoadableContent{
	private String npcName = "UweLogicalErrorQuestNPC";
	//private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("-1_semos_dungeon");
	private final Point spawnPoint = new Point(20,40);
	
	private String leaderNpc;
	private SpeakerNPC npc;
	
	public UweLogicalErrorQuestNPC(String leaderNpc)
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
		npc.setPosition(spawnPoint.x, spawnPoint.y);
		npc.setDirection(Direction.UP);
		npc.initHP(100);
		// npc.setSpeed(1.0);
		zone.add(npc);
	}
	private void addDialog()
	{
		//hi
		npc.add(ConversationStates.IDLE, 
				ConversationPhrases.GREETING_MESSAGES, 
				null, 
				ConversationStates.ATTENDING,
				"\nI am a quest NPC of logical error fixing, are you looking for #quest?.", 
				null);
		//quest
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				new AndCondition(
						new QuestStartedCondition(leaderNpc), 
						new OrCondition(
								new QuestInStateCondition(leaderNpc, "started"), 
								new QuestInStateCondition(leaderNpc, "read"))),
				ConversationStates.ATTENDING, 
				"Please help me fix the code", 
				new MultipleActions(
						new SetQuestAction(leaderNpc, "read"),
						new UweFireQuestEventChatAction(new UweFlowIncQuestEvent("logical", "Logical error fixing quest", leaderNpc)))
				);
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				new OrCondition(
						new QuestNotStartedCondition(leaderNpc),
						new QuestInStateCondition(leaderNpc, "blank"), 
						new QuestInStateCondition(leaderNpc, "done")),
				ConversationStates.ATTENDING, 
				"Oh i have no quest in here currently", 
				null);
		
		
		//bye
		npc.addGoodbye("Have fun!");
		
	}
	private void buildConditions()
	{}
	
	
	@Override
	public boolean removeFromWorld() {
		removeNPC(npcName);

		// final StendhalRPZone zone =
		// SingletonRepository.getRPWorld().getZone("int_semos_frank_house");
		// new LittleGirlNPC().createGirlNPC(zone);

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
