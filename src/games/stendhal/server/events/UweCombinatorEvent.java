package games.stendhal.server.events;

import org.apache.log4j.Logger;

import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;
import marauroa.common.game.SyntaxException;

public class UweCombinatorEvent extends RPEvent {
	public static final String RPCLASS_NAME = "combinator";
	
	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(UweFlowIncQuestEvent.class);
	
	//private List codeSave,ansSave;

	/**
	 * Creates the rpclass.
	 */
	public static void generateRPClass() {
		try {
		final RPClass rpclass = new RPClass(RPCLASS_NAME);
		} catch (final SyntaxException e) {
			logger.error("cannot generateRPClass", e);
		}
	}


	/**
	 * Creates a new ExamineEvent.
	 *
	 */
	public UweCombinatorEvent() {
		super(RPCLASS_NAME);
		
	}

}