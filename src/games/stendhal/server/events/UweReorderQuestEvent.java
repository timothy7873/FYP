package games.stendhal.server.events;

import org.apache.log4j.Logger;

import games.stendhal.server.maps.quests.uwe.*;
import marauroa.common.game.Definition;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;
import marauroa.common.game.SyntaxException;
import marauroa.common.game.Definition.DefinitionClass;
import marauroa.common.game.Definition.Type;

public class UweReorderQuestEvent extends RPEvent {
	public static final String RPCLASS_NAME = "reorder_quest";
	private static final String TITLE = "title";
	private static final String NPCID = "npcId";
	
	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(UweReorderQuestEvent.class);
	
	//private List codeSave,ansSave;

	/**
	 * Creates the rpclass.
	 */
	public static void generateRPClass() {
		try {
		final RPClass rpclass = new RPClass(RPCLASS_NAME);
		rpclass.add(DefinitionClass.ATTRIBUTE, TITLE, Type.STRING, Definition.PRIVATE);
		rpclass.add(DefinitionClass.ATTRIBUTE, NPCID, Type.STRING, Definition.PRIVATE);
		} catch (final SyntaxException e) {
			logger.error("cannot generateRPClass", e);
		}
	}


	/**
	 * Creates a new ExamineEvent.
	 *
	 * @param image image file
	 * @param title title of image viewer
	 * @param caption text to display along the image
	 */
	public UweReorderQuestEvent(String title, String npcId) {
		super(RPCLASS_NAME);

		super.put(TITLE, title);
		super.put(NPCID, npcId);
		
	}

}