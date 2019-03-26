package games.stendhal.server.events;

import org.apache.log4j.Logger;

import marauroa.common.game.Definition;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;
import marauroa.common.game.SyntaxException;
import marauroa.common.game.Definition.DefinitionClass;
import marauroa.common.game.Definition.Type;

public class UweSelectOnDoingJourneyEvent extends RPEvent{
	public static final String RPCLASS_NAME = "select_ondoing_journey";
	private static final String NPCID = "npcId";
	private static final Logger logger = Logger.getLogger(UweSelectOnDoingJourneyEvent.class);
	
	public static void generateRPClass() {
		try {
		final RPClass rpclass = new RPClass(RPCLASS_NAME);
		rpclass.add(DefinitionClass.ATTRIBUTE, NPCID, Type.STRING, Definition.PRIVATE);
		} catch (final SyntaxException e) {
			logger.error("cannot generateRPClass", e);
		}
	}
	
	public UweSelectOnDoingJourneyEvent(String npcId) {
		super(RPCLASS_NAME);

		super.put(NPCID, npcId);
	}
}
