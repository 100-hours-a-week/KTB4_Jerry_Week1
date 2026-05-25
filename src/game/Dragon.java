package game;

import game.enums.Element;
import game.utils.Message;

public class Dragon extends Monster {
    private Element element;

    Dragon(String name, int maxHp, int basePower, int expReward, int goldReward, Element element) {
        super(name, maxHp, basePower + element.getBonusPower(), expReward, goldReward);

        this.element = element;
    }

    @Override
    public int attack(Character target) {
        target.decreaseHp(attackPower);
        return attackPower;
    }

    @Override
    public String describeAttack(Character target, int damage) {
        return Message.dragonAttack(this, target, damage);
    }

    public Element getElement() {
        return element;
    }
}
