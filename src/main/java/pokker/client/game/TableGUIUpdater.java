package pokker.client.game;

import pokker.client.gui.GUIEvent;
import pokker.client.gui.GUIEventListener;
import pokker.client.gui.Gui;
import pokker.lib.game.table.TableEvent;
import pokker.lib.game.table.TableEventListener;

import java.util.LinkedList;
import java.util.Queue;

public class TableGUIUpdater implements TableEventListener<TableClient>, GUIEventListener {
    private final Gui GUI;
    private final Queue<TableEvent<TableClient>> tableEventBacklog = new LinkedList<>();
    private boolean tableDrawn = false;

    public TableGUIUpdater(Gui gui) {
        this.GUI = gui;
    }

    @Override
    public void handleTableEvent(TableEvent<TableClient> event) {
        if(!tableDrawn) {
            tableEventBacklog.add(event);
            return;
        }

        System.out.println("TableGUIUpdater: " +event.getType());
        switch (event.getType()) {
            case PLAYER_LEFT:
            case PLAYER_JOINED:
            case BETTING_ROUND_START:
            case PLAYER_ACTED:
                GUI.getTableView().updatePlayersInSeats();
                break;
            case HANDS_DEALT:
                GUI.getTableView().updatePlayerCardViewBox();
                break;
            case WAITING_FOR_PLAYER_TO_ACT:
                GUI.getTableView().activateSeatWithPlayer(event.getTable().getActingPlayer());

                if(event.getTable().getActingPlayer() == event.getTable().getPlayerMe()) {
                    GUI.getTableView().showActionButtons();
                } else {
                    GUI.getTableView().hideActionButtons();
                }

                GUI.getTableView().updatePlayersInSeats();
                break;
            case CARDS_DEALT_ON_TABLE:
                GUI.getTableView().updateBoardCards();
                break;
        }
    }

    @Override
    public void handleGUIEvent(GUIEvent event) {
        switch (event.getEventType()) {
            case TABLE_DRAWN:
                tableDrawn = true;
                clearBacklog();
                break;
            case TABLELIST_DRAWN:
                tableDrawn = false;
                break;
        }
    }

    private void clearBacklog() {
        TableEvent<TableClient> event = null;
        while((event = tableEventBacklog.poll()) != null) {
            handleTableEvent(event);
        }
    }
}
