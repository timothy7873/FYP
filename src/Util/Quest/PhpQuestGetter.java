package Util.Quest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PhpQuestGetter implements QuestGetter{
	public FlowIncQuest getLogicalQuestion(String user)
	{
		String url="";
		JSONObject json=getJson(url);
		
		return new FlowIncQuest();
	}
	public FlowIncQuest getSyntaxQuestion(String user)
	{
		String url="";
		JSONObject json=getJson(url);
		
		return new FlowIncQuest();
	}
	public TraceQuest getTraceQuestion(String user)
	{
		String url="";
		JSONObject json=getJson(url);
		
		return new TraceQuest();
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
	
	public YesNoTest[] getYesNoTests(String subject) {return null;}
}
