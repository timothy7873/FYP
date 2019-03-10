package games.stendhal.server.events;

import org.apache.log4j.Logger;

import marauroa.common.game.Definition;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;
import marauroa.common.game.SyntaxException;
import marauroa.common.game.Definition.DefinitionClass;
import marauroa.common.game.Definition.Type;

public class UweShowJourneyListEvent extends RPEvent{
	public static final String RPCLASS_NAME = "journey_list";
	private static final String NPCID = "npcId";
	private static final String SHOWNEW = "showNew";

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(UweShowJourneyListEvent.class);

	/**
	 * Creates the rpclass.
	 */
	public static void generateRPClass() {
		try {
		final RPClass rpclass = new RPClass(RPCLASS_NAME);
		rpclass.add(DefinitionClass.ATTRIBUTE, NPCID, Type.STRING, Definition.PRIVATE);
		rpclass.add(DefinitionClass.ATTRIBUTE, SHOWNEW, Type.STRING, Definition.PRIVATE);
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
	public UweShowJourneyListEvent(String npcId, boolean showNew) {
		super(RPCLASS_NAME);
		
		super.put(NPCID, npcId);
		super.put(SHOWNEW, showNew?"new":"old");
	}
}
