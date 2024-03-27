
import java.util.Random;

public class Tamagaotchi {

	private static Random rand = new Random();

	private String name;

	private int health;
	private int hunger;
	private int happiness;

	private Item gift;

	private int brainSize;
	private int bodySize;

	public Tamagaotchi(String name) {
		this.name = name;

		health = 100;
		hunger = 45 + rand.nextInt(10);
		happiness = 45 + rand.nextInt(10);

		gift = null;

		brainSize = rand.nextInt(10);
		bodySize = rand.nextInt(10);
	}

	private double getBrainProp() {
		return brainSize / (double)(brainSize + bodySize);
	}

	private double getBodyProp() {
		return bodySize / (double)(brainSize + bodySize);
	}

	public void tick() {
		// day to day fluxuation
		hunger    += 5 + rand.nextInt(10);
		happiness += rand.nextInt(10) - 5;

		hunger = Math.max(0, hunger);
		happiness = Math.min(100, happiness);

		double brainProp = getBrainProp();
		double bodyProp  = getBodyProp();
		int growthPoints = brainSize + bodySize < 150 ? 10 : 5;
		brainSize += (int)(growthPoints * brainProp);
		bodySize  += (int)(growthPoints * bodyProp);

		// randomly generate gift
		if(happiness > 40 && rand.nextInt(150 - happiness) < 2) {
			Item bestGift = hasGift() ? gift : ItemFactory.getRandomItem();

			int tries = happiness / 25;
			for(int t = 0; t < tries; t++) {
				Item item = ItemFactory.getRandomItem();
				if(item.getValue() > bestGift.getValue()) {
					bestGift = item;
				}
			}
			gift = bestGift;
		}
		// randomly get sick
		else if(rand.nextInt(50 + happiness/2) < 5)  {
			health -= 20;
		}

		if(hunger > 75 && rand.nextInt(3) < 1) {
			health -= (hunger + rand.nextInt(hunger/2)) / 10;
		}
		if(happiness < 25  && rand.nextInt(3) < 1) {
			int hapPrime = 100-happiness;
			health -= (hapPrime + rand.nextInt(hapPrime/2)) / 10;
		}

		if(health < 0) {}// (they're dead)
	}

	public void display() {

	}

	public void feed(Food f) {
		hunger -= f.getNutrition();
		happiness += 10;

		brainSize += 5;
		bodySize  += 5;
	}

	public void play(Toy t) {
		int mental = (int)(getBrainProp() * t.getMentalStimulation());
		int phys   = (int)(getBodyProp()  * t.getPhyscialStimulation());

		happiness += mental + phys;
		health    += mental / 8 + phys / 4;
		hunger    += mental / 8 + phys / 4;

		brainSize += mental / 2;
		bodySize  += phys   / 2;
	}

	public void attack(Weapon w) {
		health -= w.getAttackPower();
		happiness = 0;
		gift = null;
	}

	public void heal(Potion p) {
		health += p.getHealingPower();
		happiness += 10;
	}

	public boolean hasGift() {
		return gift != null;
	}

	public Item acceptGift() {
		happiness += 10;

		Item g = gift;
		gift = null;
		return g;
	}


}