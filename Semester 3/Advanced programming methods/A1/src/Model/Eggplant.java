package Model;

public class Eggplant implements Vegetable {
    private float size;

    public Eggplant(float size) {
        this.size = size;
    }


    @Override
    public float getSize() {
        return size;
    }

    @Override
    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "This eggplant has " + size + " kilograms.";
    }
}
