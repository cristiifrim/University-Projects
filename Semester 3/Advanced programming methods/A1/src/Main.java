import Controller.*;
import Repository.MemoryRepository;
import View.View;

public class Main {
    public static void main(String[] args) {
        MemoryRepository repo = new MemoryRepository(50);
        Controller service = new Controller(repo);
        View UI = new View(service);

        UI.run();
    }
}