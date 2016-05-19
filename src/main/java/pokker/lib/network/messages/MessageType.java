package pokker.lib.network.messages;

public enum MessageType {
    Acknowledgment,

    UserData,

    GetTableList,
    TableList,
    JoinTable,
    SuccessfulTableJoin,

    PlayerJoined,
    PlayerLeft,
    TableEvent,
    AskForPlayerAct,
    PlayerAct,
    PlayerHandDealt,
    WaitingForPlayerAct,
    CardsDealtOnTable
}
