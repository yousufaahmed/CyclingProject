package cycling;

import java.io.Serializable;

public class Rider implements Serializable{

    /*
     * Class for the Riders
     */

    private String name;
    private int yearOfBirth;

    public Rider(String name, int yearOfBirth){
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public String getName(){
        return name;
    }

    public int getYearOfBirth(){
        return yearOfBirth;
    }
}
