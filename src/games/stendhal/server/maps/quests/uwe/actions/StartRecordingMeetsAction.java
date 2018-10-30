package games.stendhal.server.maps.quests.uwe.actions;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.annotations.Dev;
import games.stendhal.server.core.config.annotations.Dev.Category;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.action.StartRecordingKillsAction;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.util.RequiredKillsInfo;
import marauroa.common.Pair;

@Dev(category = Category.MEETS, label = "State")
public class StartRecordingMeetsAction implements ChatAction {
	// first number in pair is required solo kills, second is required shared kills
		private final Map<String, Pair<Integer, Integer>> toMeet;
		private final String questname;
		private final int index;

		/**
		 * Creates a new StartRecordingKillsAction.
		 *
		 * @param questSlot name of quest slot
		 * @param index index within quest slot
		 * @param toKill creatures which should be killed by the player (name, required solo kills, required solo/shared kills)
		 */
		public StartRecordingMeetsAction(
				final String questSlot, @Dev(defaultValue="1") final int index,
				final Map<String, Pair<Integer, Integer>> toMeet) {
			this.toMeet = checkNotNull(toMeet);
			this.questname = checkNotNull(questSlot);
			this.index = index;
		}

		/**
		 * Creates a new StartRecordingKillsAction.
		 *
		 * @param questSlot name of quest slot
		 * @param index index within quest slot
		 * @param requiredKills creatures which should be killed by the player (name, required solo kills, required solo/shared kills)
		 */
		@Dev
		public StartRecordingMeetsAction(
				final String questSlot, @Dev(defaultValue="1") final int index,
				final RequiredKillsInfo... requiredKills) {
			this.toMeet = new HashMap<String, Pair<Integer, Integer>>();
			for (RequiredKillsInfo info : requiredKills) {
				toMeet.put(info.getName(), new Pair<Integer, Integer>(info.getRequiredSolo(), info.getRequiredMaybeShared()));
			}
			this.questname = checkNotNull(questSlot);
			this.index = index;
		}

		/**
		 * Creates a new StartRecordingKillsAction.
		 *
		 * @param questSlot name of quest slot
		 * @param index index within questslot
		 * @param creature Creature
		 * @param requiredSolo number of creatures that have to be killed solo
		 * @param requiredShared number of creatures that may be killed with help by other players
		 */
		public StartRecordingMeetsAction(final String questSlot, 
				final int index, 
				String creature,
				int requiredSolo, int requiredShared) {
			this.toMeet = new HashMap<String, Pair<Integer, Integer>>();
			toMeet.put(creature, new Pair<Integer, Integer>(requiredSolo, requiredShared));
			this.questname = checkNotNull(questSlot);
			this.index = index;
		}
		
		
		@Override
		public String toString() {
			return "StartRecordingMeetsActions <" + toMeet.toString() + ">";
		}

		@Override
		public int hashCode() {
			return 5573 * (questname.hashCode() + 5581 * (index + 5591 * toMeet.hashCode()));
		}

		@Override
		public boolean equals(final Object obj) {
			if (!(obj instanceof StartRecordingMeetsAction)) {
				return false;
			}
			StartRecordingMeetsAction other = (StartRecordingMeetsAction) obj;
			return (index == other.index)
				&& questname.equals(other.questname)
				&& toMeet.equals(other.toMeet);
		}

		@Override
		public void fire(Player player, Sentence sentence, EventRaiser npc) {
			// TODO Auto-generated method stub
			final StringBuilder sb = new StringBuilder("");
			for (final String creature : toMeet.keySet()) {
				final int requiredSolo = toMeet.get(creature).first();
				final int requiredShared = toMeet.get(creature).second();
				final int soloKills = player.getSoloKill(creature);
				final int sharedKills = player.getSharedKill(creature);
				sb.append(creature + "," + requiredSolo + "," + requiredShared + "," + soloKills + ","
						+ sharedKills + ",");
			}
			final String result = sb.toString().substring(0, sb.toString().length() - 1);
		
			
			//update Player quest			
			player.setQuest(questname, index, result);
			
		}
}
