package View;

import Controller.Controller;

public class RunExampleCommand extends Command {

    private final Controller __service;

    RunExampleCommand(String key, String description, Controller srv) {
        super(key, description);
        __service = srv;
    }

    @Override
    public void execute() {
        try {
            __service.runProgram();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
