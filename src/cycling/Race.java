package cycling;

import java.io.Serializable;

public class Race implements Serializable {

	/*
	 * Class for the Race
	 */

	private String name;
	private String description;

	public Race(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}

