package View;

public abstract class Command {
    private final String __key, __description;

    public Command(String key, String description) {
        __key = key;
        __description = description;
    }

    public abstract void execute();

    public String getKey() {
        return __key;
    }

    public String getDescription() {
        return __description;
    }

}
