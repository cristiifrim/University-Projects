package Repository;
import Model.Vegetable;

public class MemoryRepository implements Repository {
    private int size;
    private int elems;
    private Vegetable[] data;

    public MemoryRepository(int size) {
        this.data = new Vegetable[size];
        this.elems = 0;
        this.size = size;
    }

    @Override
    public void add(Vegetable veggie) {
        this.data[this.elems++] = veggie;
    }

    @Override
    public void remove(int index) throws ArrayIndexOutOfBoundsException {
        if(index < 0 || index >= this.elems)
            throw new ArrayIndexOutOfBoundsException("The given element is not in the repository.");

        this.data[index] = this.data[--this.elems];
        this.data[this.elems + 1] = null;
    }

    @Override
    public Vegetable[] getVegetables() {
        Vegetable[] newData = new Vegetable[this.elems];
        int counter = 0;
        for(int i = 0; i < this.elems; ++i)
            if(this.data[i] != null)
                newData[counter++] = this.data[i];
        return newData;
    }

    @Override
    public int getSize() {
        return this.elems;
    }
}
