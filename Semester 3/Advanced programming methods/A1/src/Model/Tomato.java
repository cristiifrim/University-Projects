package Model;

public class Tomato implements Vegetable {
    private float size;

    public Tomato(float size) {
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
        return "This tomato has " + size + " kilograms.";
    }
}
