package com.example.quickpick.my_intefaces;

import com.example.quickpick.constants.GlobalCommandID;

public interface ChainHandler {
    void setNext(ChainHandler nextHandler);
    void handle(GlobalCommandID commandId);
    void setCommand(Command command);

    Command getCommand();

}