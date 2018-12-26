/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.common;

/**
 * Constants about slots
 */
public final class Constants {
	/**
	 * All the slots considered to be "with" the entity. Listed in priority
	 * order (i.e. bag first).
	 */
	// TODO: let the slots decide that themselves
	public static final String[] CARRYING_SLOTS = { "bag", "head", "rhand",
			"lhand", "armor", "finger", "cloak", "legs", "feet", "keyring", "back", "belt", "uwebag", "uwequest", 
			"uwepopup0","uwepopup1","uwepopup2","uwepopup3","uwepopup4","uwepopup5","uwepopup6","uwepopup7","uwepopup8","uwepopup9" };

}
