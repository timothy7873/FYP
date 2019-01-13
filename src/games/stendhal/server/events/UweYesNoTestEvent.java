package games.stendhal.server.events;

import org.apache.log4j.Logger;

import marauroa.common.game.Definition;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;
import marauroa.common.game.SyntaxException;
import marauroa.common.game.Definition.DefinitionClass;
import marauroa.common.game.Definition.Type;

public class UweYesNoTestEvent extends RPEvent {
	private static final String RPCLASS_NAME = "yes_no_test";
	private static final String NPCID = "npcId";
	private static final String TITLE = "title";

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(UweFlowIncQuestEvent.class);
	
	//private List codeSave,ansSave;

	/**
	 * Creates the rpclass.
	 */
	public static void generateRPClass() {
		try {
		final RPClass rpclass = new RPClass(RPCLASS_NAME);
		rpclass.add(DefinitionClass.ATTRIBUTE, NPCID, Type.STRING, Definition.PRIVATE);
		rpclass.add(DefinitionClass.ATTRIBUTE, TITLE, Type.STRING, Definition.PRIVATE);
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
	public UweYesNoTestEvent(String npcId, String title) {
		super(RPCLASS_NAME);

		super.put(NPCID, npcId);
		super.put(TITLE, title);
	}

}