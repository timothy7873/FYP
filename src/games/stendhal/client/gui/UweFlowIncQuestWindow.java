package games.stendhal.client.gui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.swing.SwingUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UweFlowIncQuestWindow extends InternalManagedWindow{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1331241549198527966L;

	public UweFlowIncQuestWindow(UweFlowIncQuestViewPanel vp,String title)
	{
		super("FlowIncQuest",title);
		
		vp.setWindow(this);
		vp.prepare();
		setContent(vp);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				j2DClient.get().addWindow(UweFlowIncQuestWindow.this);
				center();
				getParent().validate();
			}
		});
		
	}
	
}
