package games.stendhal.server.entity.item.uwe.hints;

import java.util.Map;

import games.stendhal.common.ItemTools;
import games.stendhal.common.Rand;
import games.stendhal.common.grammar.Grammar;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.Item; 
import games.stendhal.server.entity.player.Player;

public class Hint1 extends Hint {
	

	private static final String[] ITEMS = { "greater potion", "pie",
			"sandwich", "carrot", "cherry", "blue elf cloak", "summon scroll" };


	/**
	 * Creates a new present.
	 *
	 * @param name
	 * @param clazz
	 * @param subclass
	 * @param attributes
	 */
	public Hint1(final String name, final String clazz, final String subclass,
			final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);

		setContent(ITEMS[Rand.rand(ITEMS.length)]);
	}
	
	/**
	 * Copy constructor.
	 *
	 * @param item
	 *            item to copy
	 */
	public Hint1(final Hint1 item) {
		super(item);
	}
	/**
	 * Sets content.
	 * @param type of item to be produced.
	 */
	public void setContent(final String type) {
		setInfoString(type);
	}
	
	// this would be overridden in the subclass
	protected boolean useMe(final Player player) {
		this.removeOne();

		final String itemName = getInfoString();
		final Item item = SingletonRepository.getEntityManager().getItem(itemName);
		player.equipOrPutOnGround(item);
		
		
		
		player.sendPrivateText("Congratulations, you've got "
				+ Grammar.a_noun(ItemTools.itemNameToDisplayName(itemName)) + "!");

		player.notifyWorldAboutChanges();

		return true;
	}
}
