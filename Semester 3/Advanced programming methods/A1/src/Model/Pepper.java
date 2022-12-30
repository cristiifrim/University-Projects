package Model;

public class Pepper implements Vegetable {
    private float size;

    public Pepper(float size) {
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
        return "This pepper has " + size + " kilograms.";
    }
}
