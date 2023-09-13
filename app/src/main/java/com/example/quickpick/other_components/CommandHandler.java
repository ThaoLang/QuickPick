package com.example.quickpick.other_components;


import com.example.quickpick.constants.GlobalCommandID;
import com.example.quickpick.my_intefaces.ChainHandler;
import com.example.quickpick.my_intefaces.Command;

public class CommandHandler implements ChainHandler {
    private ChainHandler next;
    private Command command;

    private GlobalCommandID commandID;

    public CommandHandler(GlobalCommandID regCommandID)
    {
        next = null;
        command = null;
        commandID = regCommandID;
    }


    @Override
    public void handle(GlobalCommandID commandIDToExecute) {
        if(commandID == commandIDToExecute && command != null)
        {
            command.execute();
        }
        else if(next != null)
        {
            next.handle(commandIDToExecute);
        }
    }

    @Override
    public void setCommand(Command command) {
        this.command = command;
    }

    @Override
    public void setNext(ChainHandler nextHandler)
    {
        this.next = nextHandler;
    }

    @Override
    public Command getCommand(){
        return this.command;
    }
}
