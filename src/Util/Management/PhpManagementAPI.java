package Util.Management;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Util.Url.QueryString;

public class PhpManagementAPI extends ManagementAPI{
	private String site;
	
	public PhpManagementAPI(String site)
	{
		this.site=site;
	}
	
	public FlowIncQuest getLogicalQuestion(String npcId, String user)
	{
		QueryString qry = new QueryString();
		
		qry.add("npcId", npcId);
		qry.add("characterName", user);
		qry.add("questType", "Logical error");
		JSONObject json=getJson(site+"outputQuestion.php?"+qry);
		
		String code=(String)json.get("code");
		String out=(String)json.get("curout");
		String exp=(String)json.get("expout");
		String ans=(String)json.get("answer");
		
		Reward[] rewards=new Reward[] {};
		return new FlowIncQuest();
	}
	public FlowIncQuest getSyntaxQuestion(String npcId, String user) {return null;}
	public TraceQuest getTraceQuestion(String npcId, String user) {return null;}
	public YesNoTest[] getYesNoTests(String subject) {return null;}
	public QuestHint getHint(String npcId, String user) {return null;}
	public void setQuestStatus(String npcId, String user, String status) {}
	public void stopTimeCount(String npcId, String user) {}
	
	private JSONObject getJson(String urlStr)
	{
		try
		{
			URL url = new URL(urlStr);
			InputStream is = url.openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			
			StringBuilder sb = new StringBuilder();
		    int cp;
			while ((cp = rd.read()) != -1) {
			      sb.append((char) cp);
		    }
			
			String jsonText = sb.toString();
			JSONObject json = (JSONObject)new JSONParser().parse(jsonText);
			
			return json;
		}
		catch(Exception e)
		{}
		
		return null;
	}


}
