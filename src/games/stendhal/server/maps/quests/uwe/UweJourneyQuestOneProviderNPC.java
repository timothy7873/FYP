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
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.UweBonusRewardsChatAction;
import games.stendhal.server.entity.npc.action.UweProvideHintAction;
import games.stendhal.server.entity.npc.action.UweQuestCompleteChatAction;
import games.stendhal.server.entity.npc.action.UweInitQuestStateAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.UweHasJourneyQuestCondition;
import games.stendhal.server.entity.npc.condition.UweJourneyQuestDoneCondition;
import games.stendhal.server.entity.npc.condition.UweJourneyQuestTotalCleanCondition;
import games.stendhal.server.entity.npc.condition.UweJourneyQuestReadCondition;
import games.stendhal.server.entity.npc.condition.UweJourneyQuestStartedCondition;
import games.stendhal.server.entity.npc.condition.UweYesNoTestValidCondition;
import games.stendhal.server.events.UweSelectOnDoingJourneyEvent;

public class UweJourneyQuestOneProviderNPC extends UweNpc{
	private String npcName = "UweJourneyQuestOneProviderNPC";
	protected StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("-1_semos_dungeon");
	protected Point spawnPoint = new Point(20,33);
	
	private SpeakerNPC npc;
	private String quester;
	private String tester;
	private String area;
	
	public String getNpcName() {return this.npcName;}
	public void setNpcName(String npcName) {this.npcName=npcName;}
	public String getArea() {return area;}
	public void setArea(String area) {this.area=area;}
	public UweJourneyQuestOneProviderNPC(String zoneName, Point pos, String npcId, String quester, String tester, String area)
	{
		this.zone=SingletonRepository.getRPWorld().getZone(zoneName);
		this.spawnPoint=pos;
		this.npcName=npcId;
		this.quester=quester;
		this.tester=tester;
		this.area=area;
	}
	
	public void journeyChosenCallback()
	{
		npc.say("Please go find #"+quester+" and get the quest about "+area+"\nAfter that, go back and find me.");
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
				"\nI am "+npcName+", I can provide #quest for you.", 
				new UweInitQuestStateAction(npcName));
		//quest
		
		
		//quest blank
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new AndCondition(
						new UweJourneyQuestTotalCleanCondition(npcName),
						new UweHasJourneyQuestCondition(npcName)), 
				ConversationStates.INFORMATION_1, 
				"Are you familar with "+area+"? #Yes/ #No/ #A #bit", 
				null);
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new AndCondition(
						new UweJourneyQuestTotalCleanCondition(npcName),
						new NotCondition(new UweHasJourneyQuestCondition(npcName))), 
				ConversationStates.ATTENDING, 
				"Sorry! We currently have no quests that can provide to you!", 
				null);
		
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("Yes","yes","y"), 
				null, 
				ConversationStates.ATTENDING, 
				null,//"Please go find #UweJourneyQuestOneNPC and get the quest about java code", 
				new FireEventChatAction(new UweSelectOnDoingJourneyEvent(npcName)));
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("A bit","a bit","Abit","abit","ab","a"), 
				new UweYesNoTestValidCondition(npcName), 
				ConversationStates.ATTENDING, 
				"Please go find #"+tester+" to practise java", 
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
				new UweJourneyQuestDoneCondition(npcName), 
				ConversationStates.ATTENDING, 
				"Good job, you have complete the previous quest\nLet me give you some bonus reward\nWe are welcome if you want more #quest", 
				new MultipleActions(
						new UweQuestCompleteChatAction(npcName),
						new SetQuestAction(npcName, "blank"),
						new UweBonusRewardsChatAction()
						));
		
		//quest doing
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new UweJourneyQuestStartedCondition(npcName), 
				ConversationStates.ATTENDING, 
				"Please go find #"+quester+" and get the quest about java code", 
				null);
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new UweJourneyQuestReadCondition(npcName), 
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