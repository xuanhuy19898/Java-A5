/**
 * @author XUAN HUY PHAM - 000899551
 */
/**
 * The WeirdCreature class represents a creature object with a current fullness and a target fullness.
 * The creature can be fed with different types of food to increase its current fullness.
 */
public class WeirdCreature {
    private int currentFullness;
    private int targetFullness;

    /**
     * Constructs a WeirdCreature object with the specified target fullness.
     * @param targetFullness the target fullness for the creature
     */
    public WeirdCreature(int targetFullness) {
        this.currentFullness = 0;
        this.targetFullness = targetFullness;
    }
    /**
     * Increases the current fullness of the creature by 1 when fed with a carrot.
     * The current fullness is capped at 100.
     */
    public void feedCarrot() {
        currentFullness += 1;
        if (currentFullness > 100) {
            currentFullness = 100;
        }
    }

    /**
     * Increases the current fullness of the creature by 2 when offered Mac's fries.
     * The fullness is capped at 100.
     */
    public void offerFries() {
        currentFullness += 2;
        if (currentFullness > 100) {
            currentFullness = 100;
        }
    }
    /**
     * Increases the current fullness of the creature by 3 when fed with a steak.
     * The fullness is capped at 100.
     */
    public void feedSteak() {
        currentFullness += 3;
        if (currentFullness > 100) {
            currentFullness = 100;
        }
    }
    /**
     * Increases the current fullness of the creature by 5 when offered beer.
     * The fullness is capped at 100.
     */
    public void offerBeer() {
        currentFullness += 5;
        if (currentFullness > 100) {
            currentFullness = 100;
        }
    }
    /**
     * Returns the current fullness of the creature.
     * @return the current fullness
     */
    public int getCurrentFullness() {
        return currentFullness;
    }

    /**
     * Returns the target fullness of the creature.
     * @return the target fullness
     */
    public int getTargetFullness() {
        return targetFullness;
    }
}
