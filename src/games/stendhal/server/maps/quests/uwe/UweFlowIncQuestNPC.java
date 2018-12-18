package games.stendhal.server.maps.quests.uwe;

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
import games.stendhal.server.events.FlowIncQuestEvent;

public class UweFlowIncQuestNPC implements LoadableContent{
	
	private String npcName = "UweFlowIncQuestNPC";
	private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	private String code,ans,out,exp;
	
	private SpeakerNPC npc;
	
	
	public static class Question
	{
		public String code,ans,out,exp;
		public Question(String code, String ans, String out, String exp)
		{this.code=code;this.ans=ans;this.out=out;this.exp=exp;}
	}
	public static Question getRandomQuestion()
	{
		try
		{
			URL url = new URL("http://localhost/FYP/outputQuestion.php?questionType=logicerror");
			InputStream is = url.openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			
			StringBuilder sb = new StringBuilder();
		    int cp;
			while ((cp = rd.read()) != -1) {
			      sb.append((char) cp);
		    }
			
			String jsonText = sb.toString();
			JSONObject json = (JSONObject)new JSONParser().parse(jsonText);
			JSONArray json1=(JSONArray)json.get("code");
			
			return new Question((String)((JSONArray)json.get("code")).get(0),(String)((JSONArray)json.get("code")).get(0),
					(String)((JSONArray)json.get("code")).get(0),(String)((JSONArray)json.get("code")).get(0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public UweFlowIncQuestNPC()
	{
		code="public class test {\n"+
				"\tpublic static void main(String[] args){\n"+
				"\t\tint x=12;\n"+
				"\t\tSystem.out.println(\"x\");\n"+
				"\t}\n"+
				"}";
		ans="public class test {\n"+
				"\tpublic static void main(String[] args){\n"+
				"\t\tint x=12;\n"+
				"\t\tSystem.out.println(x);\n"+
				"\t}\n"+
				"}";
		out="x";
		exp="12";
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
		npc.setPosition(15, 12);
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
				"\nI am a quest NPC of flow incorrect, I can provide #quest for you.", 
				null);
		//quest
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				null,
				ConversationStates.ATTENDING, 
				null, 
				//new FireEventChatAction(new FlowIncQuestEvent(code,ans,out,exp,"Flow incorrect quest"))
				new FireEventChatAction(new FlowIncQuestEvent("Flow incorrect quest"))
				);
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
