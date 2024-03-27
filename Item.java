

public abstract class Item {

	private static int nextID = 0;

	private int id;
	private int age = 0;

	public Item() {
		id = nextID;
		nextID++;

		age = 0;
	}

	public void tick() {
		age++;
	}

	public abstract int getValue();

	public int getID() {
		return id;
	}
}