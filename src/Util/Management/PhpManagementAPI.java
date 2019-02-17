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
		qry.add("questType", "Logic error");
		JSONObject json=getJson(site+"outputQuestion.php?"+qry);
		if(json==null)
		{
			return null;
		}
		
		JSONArray codes=(JSONArray)json.get("code");
		JSONArray curouts=(JSONArray)json.get("curout");
		JSONArray expouts=(JSONArray)json.get("expout");
		JSONArray anss=(JSONArray)json.get("answer");
		
		String code="",curout="",expout="",ans="";
		for(int i=0;i<codes.size();i++)
			code+=codes.get(i)+"\n";
		for(int i=0;i<curouts.size();i++)
			curout+=curouts.get(i)+"\n";
		for(int i=0;i<expouts.size();i++)
			expout+=expouts.get(i)+"\n";
		for(int i=0;i<anss.size();i++)
			ans+=anss.get(i)+"\n";
		code=code.substring(0,code.length()-1);
		curout=curout.substring(0,curout.length()-1);
		expout=expout.substring(0,expout.length()-1);
		ans=ans.substring(0,ans.length()-1);
		
		JSONObject reward=(JSONObject)json.get("reward");
		int exp=Integer.parseInt((String)reward.get("experience"));
		double karma=Double.parseDouble((String)reward.get("karma"));
		int money=Integer.parseInt((String)reward.get("money"));
		JSONArray items=(JSONArray)reward.get("item");
		
		List rewards=new LinkedList();
		rewards.add(new Reward(null,0,exp,karma,money));
		for(int i=0;i<items.size();i++)
		{
			JSONObject item=(JSONObject)items.get(i);
			String itemName=(String)item.get("itemName");
			int qty=Integer.parseInt((String)item.get("qty"));
			rewards.add(new Reward(itemName,qty,0,0,0));
		}
		
		return new FlowIncQuest(code, curout, expout, ans, (Reward[])rewards.toArray(new Reward[0]));
	}
	public FlowIncQuest getSyntaxQuestion(String npcId, String user)
	{
		QueryString qry = new QueryString();
		
		qry.add("npcId", npcId);
		qry.add("characterName", user);
		qry.add("questType", "Syntax error");
		JSONObject json=getJson(site+"outputQuestion.php?"+qry);
		if(json==null)
		{
			return null;
		}
		
		JSONArray codes=(JSONArray)json.get("code");
		JSONArray curouts=(JSONArray)json.get("curout");
		JSONArray expouts=(JSONArray)json.get("expout");
		JSONArray anss=(JSONArray)json.get("answer");
		
		String code="",curout="",expout="",ans="";
		for(int i=0;i<codes.size();i++)
			code+=codes.get(i)+"\n";
		for(int i=0;i<curouts.size();i++)
			curout+=curouts.get(i)+"\n";
		for(int i=0;i<expouts.size();i++)
			expout+=expouts.get(i)+"\n";
		for(int i=0;i<anss.size();i++)
			ans+=anss.get(i)+"\n";
		code=code.substring(0,code.length()-1);
		curout=curout.substring(0,curout.length()-1);
		expout=expout.substring(0,expout.length()-1);
		ans=ans.substring(0,ans.length()-1);
		
		JSONObject reward=(JSONObject)json.get("reward");
		int exp=Integer.parseInt((String)reward.get("experience"));
		double karma=Double.parseDouble((String)reward.get("karma"));
		int money=Integer.parseInt((String)reward.get("money"));
		JSONArray items=(JSONArray)reward.get("item");
		
		List rewards=new LinkedList();
		rewards.add(new Reward(null,0,exp,karma,money));
		for(int i=0;i<items.size();i++)
		{
			JSONObject item=(JSONObject)items.get(i);
			String itemName=(String)item.get("itemName");
			int qty=Integer.parseInt((String)item.get("qty"));
			rewards.add(new Reward(itemName,qty,0,0,0));
		}
		
		return new FlowIncQuest(code, curout, expout, ans, (Reward[])rewards.toArray(new Reward[0]));
	}
	public TraceQuest getTraceQuestion(String npcId, String user) 
	{
		QueryString qry = new QueryString();
		
		qry.add("npcId", npcId);
		qry.add("characterName", user);
		qry.add("questType", "Tracing program");
		JSONObject json=getJson(site+"outputQuestion.php?"+qry);
		if(json==null)
		{
			return null;
		}
		
		JSONArray codes=(JSONArray)json.get("code");
		JSONArray curouts=(JSONArray)json.get("curout");
		JSONArray anss=(JSONArray)json.get("answer");
		
		String code="",curout="",ans="";
		for(int i=0;i<codes.size();i++)
			code+=codes.get(i)+"\n";
		for(int i=0;i<curouts.size();i++)
			curout+=curouts.get(i)+"\n";
		for(int i=0;i<anss.size();i++)
			ans+=anss.get(i)+"\n";
		code=code.substring(0,code.length()-1);
		curout=curout.substring(0,curout.length()-1);
		ans=ans.substring(0,ans.length()-1);
		
		JSONObject reward=(JSONObject)json.get("reward");
		int exp=Integer.parseInt((String)reward.get("experience"));
		double karma=Double.parseDouble((String)reward.get("karma"));
		int money=Integer.parseInt((String)reward.get("money"));
		JSONArray items=(JSONArray)reward.get("item");
		
		List rewards=new LinkedList();
		rewards.add(new Reward(null,0,exp,karma,money));
		for(int i=0;i<items.size();i++)
		{
			JSONObject item=(JSONObject)items.get(i);
			String itemName=(String)item.get("itemName");
			int qty=Integer.parseInt((String)item.get("qty"));
			rewards.add(new Reward(itemName,qty,0,0,0));
		}
		
		return new TraceQuest(code, curout, ans, (Reward[])rewards.toArray(new Reward[0]));
	}
	public YesNoTest[] getYesNoTests(String npcId) 
	{
		List result=new LinkedList();
		
		QueryString qry = new QueryString();
		
		qry.add("npcId", npcId);
		JSONArray json=getJsonArray(site+"getyesnotest.php?"+qry);
		for(int i=0;i<json.size();i++)
		{
			JSONObject test=(JSONObject)json.get(i);
			String question=(String)test.get("question");
			boolean ans=((String)test.get("yes")).equals("1");
			result.add(new YesNoTest(question, ans));
		}
		
		return (YesNoTest[])result.toArray(new YesNoTest[0]);
	}
	public QuestHint getHint(String npcId, String user) 
	{
		QueryString qry = new QueryString();
		
		qry.add("npcId", npcId);
		qry.add("characterName", user);
		JSONObject json=getJson(site+"getHints.php?"+qry);
		QuestHint rs=json==null?null:new QuestHint((String)json.get("hints"));
		
		return rs;
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
	public void setLastStartTime(String npcId, String user)
	{
		QueryString qry = new QueryString();
		
		qry.add("npcId", npcId);
		qry.add("characterName", user);
		doUrl(site+"setLastStartTime.php?"+qry);
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
	private JSONArray getJsonArray(String urlStr)
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
			JSONArray json = (JSONArray)new JSONParser().parse(jsonText);
			
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
	private String getHtml(String urlStr)
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
			
			String html = sb.toString();
			
			return html;
		}
		catch(Exception e)
		{}
		
		return null;
	}


}
