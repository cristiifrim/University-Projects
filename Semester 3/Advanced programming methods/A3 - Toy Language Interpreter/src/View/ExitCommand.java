package View;

public class ExitCommand extends Command {

    ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}
