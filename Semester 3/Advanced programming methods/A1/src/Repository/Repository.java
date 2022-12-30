package Repository;
import Model.Vegetable;

public interface Repository {
    void add(Vegetable veggie);
    void remove(int index);
    Vegetable[] getVegetables();
    int getSize();
}
