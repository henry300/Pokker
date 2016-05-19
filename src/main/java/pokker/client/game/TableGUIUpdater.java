package pokker.client.game;

import pokker.client.gui.Gui;
import pokker.lib.game.table.TableEvent;
import pokker.lib.game.table.TableEventListener;

public class TableGUIUpdater implements TableEventListener<TableClient> {
    private final Gui GUI;

    public TableGUIUpdater(Gui gui) {
        this.GUI = gui;
    }

    @Override
    public void handleTableEvent(TableEvent<TableClient> event) {
        switch (event.getType()) {
            case PLAYER_LEFT:
            case PLAYER_JOINED:
            case BETTING_ROUND_START:
            case PLAYER_ACTED:
                GUI.updatePlayersInSeats();
                break;
            case WAITING_FOR_PLAYER_TO_ACT:
                GUI.activateSeatWithPlayer(event.getTable().getActingPlayer());

                if(event.getTable().getActingPlayer() == event.getTable().getPlayerMe()) {
                    GUI.showActionButtons();
                } else {
                    GUI.hideActionButtons();
                }

                GUI.updatePlayersInSeats();
                break;
        }
    }
}
