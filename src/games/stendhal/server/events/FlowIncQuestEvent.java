package games.stendhal.server.events;

import org.apache.log4j.Logger;

import marauroa.common.game.Definition;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;
import marauroa.common.game.SyntaxException;
import marauroa.common.game.Definition.DefinitionClass;
import marauroa.common.game.Definition.Type;

public class FlowIncQuestEvent extends RPEvent {
	private static final String RPCLASS_NAME = "flow_inc_quest";
	private static final String CODE = "code";
	private static final String ANS = "ans";
	private static final String SIZE = "size";
	private static final String OUT = "out";
	private static final String EXP = "exp";
	private static final String TITLE = "title";

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(FlowIncQuestEvent.class);
	
	//private List codeSave,ansSave;

	/**
	 * Creates the rpclass.
	 */
	public static void generateRPClass() {
		try {
		final RPClass rpclass = new RPClass(RPCLASS_NAME);
		rpclass.add(DefinitionClass.ATTRIBUTE, CODE, Type.STRING, Definition.PRIVATE);
		rpclass.add(DefinitionClass.ATTRIBUTE, ANS, Type.STRING, Definition.PRIVATE);
		rpclass.add(DefinitionClass.ATTRIBUTE, SIZE, Type.INT, Definition.PRIVATE);
		rpclass.add(DefinitionClass.ATTRIBUTE, OUT, Type.STRING, Definition.PRIVATE);
		rpclass.add(DefinitionClass.ATTRIBUTE, EXP, Type.STRING, Definition.PRIVATE);
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
	public FlowIncQuestEvent(String code, String ans, String out, String exp, String title) {
		super(RPCLASS_NAME);

		super.put(CODE, code);
		super.put(ANS, ans);
		
		super.put(OUT, out);
		super.put(EXP, exp);
		super.put(TITLE, title);
	}
}