package Util.Management;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
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
		String curout=(String)json.get("curout");
		String expout=(String)json.get("expout");
		String ans=(String)json.get("answer");
		
		JSONObject reward=(JSONObject)json.get("reward");
		int exp=Integer.parseInt((String)reward.get("experience"));
		double karma=Double.parseDouble((String)reward.get("karma"));
		int money=Integer.parseInt((String)reward.get("money"));
		JSONArray items=(JSONArray)reward.get("item");
		
		List rewards=new LinkedList();
		rewards.add(new Reward(null,0,exp,karma,money));
		for(int i=0;i<items.size();i++)
		{
			String item=(String)items.get(i);
			int qty=(int)items.get(i);
			rewards.add(new Reward(item,qty,0,0,0));
		}
		return new FlowIncQuest(code, curout, expout, ans, (Reward[])rewards.toArray());
	}
	public FlowIncQuest getSyntaxQuestion(String npcId, String user) {return null;}
	public TraceQuest getTraceQuestion(String npcId, String user) {return null;}
	public YesNoTest[] getYesNoTests(String npcId) 
	{
		List result=new LinkedList();
		
		QueryString qry = new QueryString();
		
		qry.add("npcId", npcId);
		JSONArray json=(JSONArray)(Object)getJson(site+"getyesnotest.php?"+qry);
		for(int i=0;i<json.size();i++)
		{
			JSONObject test=(JSONObject)json.get(i);
			String question=(String)test.get("question");
			boolean ans=(String)test.get("yes")=="1";
			result.add(new YesNoTest(question, ans));
		}
		
		return (YesNoTest[])result.toArray();
	}
	public QuestHint getHint(String npcId, String user) 
	{
		QueryString qry = new QueryString();
		
		qry.add("npcId", npcId);
		qry.add("characterName", user);
		JSONObject json=(JSONObject)(Object)getJson(site+"getyesnotest.php?"+qry);
		String hints=(String)json.get("hints");
		
		return new QuestHint(hints);
	}
	public void setQuestStatus(String npcId, String user, String status) 
	{
		QueryString qry = new QueryString();
		
		qry.add("npcId", npcId);
		qry.add("characterName", user);
		qry.add("status", status);
		doUrl(site+"setQuestStatus.php?"+qry);
	}
	public void stopTimeCount(String npcId, String user) 
	{
		QueryString qry = new QueryString();
		
		qry.add("npcId", npcId);
		qry.add("characterName", user);
		doUrl(site+"setStopTime.php?"+qry);
	}
	
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
	private void doUrl(String urlStr)
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

		}
		catch(Exception e)
		{}

	}


}
