package cycling;

import java.io.Serializable;

public class Rider extends Cycling implements Serializable{

    /**
     * Class for the Riders
     */

    private int yearOfBirth;

    // check this
    public Rider(String name, int yearOfBirth){
        super(name);
        this.yearOfBirth = yearOfBirth;
    }

    public int getYearOfBirth(){
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth){
        this.yearOfBirth = yearOfBirth;
    }
}
