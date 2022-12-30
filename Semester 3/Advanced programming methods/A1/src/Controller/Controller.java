package Controller;
import Model.*;
import Repository.*;

public class Controller {

    private Repository repo;

    public Controller(Repository r) {
        this.repo = r;
    }

    public void add(String type, float size) throws Exception {

        if(size <= 0)
            throw new Exception("Weight cannot be zero or negative.");

        switch(type) {
            case "tomato": {
                Tomato veggie = new Tomato(size);
                this.repo.add(veggie);
                break;
            }
            case "pepper": {
                Pepper veggie = new Pepper(size);
                this.repo.add(veggie);
                break;
            }
            case "eggplant": {
                Eggplant veggie = new Eggplant(size);
                this.repo.add(veggie);
                break;
            }
            default:
                throw new Exception("Invalid vegetable type.");
        }
    }

    public void remove(int index) {
        this.repo.remove(index);
    }

    public Vegetable[] getVegetables() throws Exception {
        if(this.repo.getSize() == 0)
            throw new Exception("There are no vegetables in the repository");

        return this.repo.getVegetables();
    }

    public Vegetable[] getVegetablesFilteredBySize(float size) throws Exception {

        if(this.repo.getSize() == 0)
            throw new Exception("There are no vegetables in the repository");

        Vegetable[] veggies = this.repo.getVegetables();
        Vegetable[] filteredVeggies = new Vegetable[this.repo.getSize()];
        int addedElems = 0;

        for(int i = 0; i < veggies.length; ++i) {
            if(veggies[i] != null && veggies[i].getSize() >= size)
                filteredVeggies[addedElems++] = veggies[i];
        }
        if(addedElems == 0)
            throw new Exception("There are no vegetables that have their weight greater than " + size + ".");

        return filteredVeggies;
    }

}
