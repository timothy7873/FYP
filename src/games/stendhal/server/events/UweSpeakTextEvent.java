package games.stendhal.server.events;

import org.apache.log4j.Logger;

import marauroa.common.game.Definition;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;
import marauroa.common.game.SyntaxException;
import marauroa.common.game.Definition.DefinitionClass;
import marauroa.common.game.Definition.Type;

public class UweSpeakTextEvent extends RPEvent{
	public static final String RPCLASS_NAME = "speak_text";
	private static final String TEXT = "text";

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(UweYesNoTestEvent.class);
	
	//private List codeSave,ansSave;

	/**
	 * Creates the rpclass.
	 */
	public static void generateRPClass() {
		try {
		final RPClass rpclass = new RPClass(RPCLASS_NAME);
		rpclass.add(DefinitionClass.ATTRIBUTE, TEXT, Type.STRING, Definition.PRIVATE);
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
	public UweSpeakTextEvent(String text) {
		super(RPCLASS_NAME);

		super.put(TEXT, text);
	}
}
