package games.stendhal.server.events;

import org.apache.log4j.Logger;

import marauroa.common.game.Definition;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;
import marauroa.common.game.SyntaxException;
import marauroa.common.game.Definition.DefinitionClass;
import marauroa.common.game.Definition.Type;

public class TestEvent extends RPEvent {
	private static final String RPCLASS_NAME = "test";
	private static final String PATH = "path";
	private static final String CAPTION = "caption";
	private static final String TITLE = "title";
	private static final String IMAGE_PATH = "/data/sprites/";

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(ExamineEvent.class);

	/**
	 * Creates the rpclass.
	 */
	public static void generateRPClass() {
		try {
		final RPClass rpclass = new RPClass(RPCLASS_NAME);
		rpclass.add(DefinitionClass.ATTRIBUTE, PATH, Type.STRING, Definition.PRIVATE);
		rpclass.add(DefinitionClass.ATTRIBUTE, CAPTION, Type.STRING, Definition.PRIVATE);
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
	public TestEvent(final String image, final String title, final String caption) {
		super(RPCLASS_NAME);
		String path = image;
		if (!image.startsWith("http://") && !image.startsWith("https://")) {
			path = IMAGE_PATH + image;
		}
		super.put(PATH, path);
		super.put(TITLE, title);
		super.put(CAPTION, caption);
	}
}
