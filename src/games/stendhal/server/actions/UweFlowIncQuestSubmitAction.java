package games.stendhal.server.actions;

import static games.stendhal.common.constants.Actions.UWEFLOWINCQUESTSUBMIT;

import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPAction;

public class UweFlowIncQuestSubmitAction implements ActionListener{

	@Override
	public void onAction(Player player, RPAction action) {
		// TODO Auto-generated method stub
		
	}

	public static void register() {
		CommandCenter.register(UWEFLOWINCQUESTSUBMIT, new UweFlowIncQuestSubmitAction());
	}
}
