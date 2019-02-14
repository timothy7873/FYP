package games.stendhal.client.gui;

import Util.Management.HardcodeManagementAPI;
import Util.Management.ManagementAPI;
import Util.Management.YesNoTest;
import marauroa.common.game.RPEvent;

public class UweYesNoTestViewer {
	private String npcId;
	private String title;
	private String[] question;
	private boolean[] ans;
	
	public UweYesNoTestViewer(final RPEvent e)
	{
		npcId="";
		if (e.has("npcId")) {
			npcId = e.get("npcId");
		}
		title="";
		if (e.has("title")) {
			title = e.get("title");
		}
		
		YesNoTest[] qs=ManagementAPI.api.getYesNoTests(npcId);
		if(qs.length==0)
			return;
		
		question=new String[qs.length];
		ans=new boolean[qs.length];
		for(int i=0;i<qs.length;i++)
		{
			question[i]=qs[i].question;
			ans[i]=qs[i].ans;
		}
		
		view();
	}
	
	public static void viewTest(final RPEvent e) {
		new UweYesNoTestViewer(e);
	}
	public void view()
	{
		UweYesNoTestViewerViewPanel vp=new UweYesNoTestViewerViewPanel(question,ans);
		new UweYesNoTestWindow(vp,title);
	}
}
