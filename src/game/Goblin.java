package game;

import game.utils.Message;
import game.utils.Printer;

public class Goblin extends Monster {
    private boolean isAngry;

    Goblin(String name, int maxHp, int attackPower, int expReward, int goldReward) {
        super(name, maxHp, attackPower, expReward, goldReward);

        this.isAngry = false;
    }

    @Override
    public void decreaseHp(int amount) {
        super.decreaseHp(amount);

        if (!isAngry && getHp() <= getMaxHp() / 2 && getHp() > 0) {
            isAngry = true;

            Printer.println(Message.goblinAngry(this));
        }
    }

    @Override
    public int attack(Character target) {
        int basePower = getAttackPower();
        int power = isAngry ? (basePower + 5) : basePower;
        target.decreaseHp(power);
        return power;
    }

    @Override
    public String describeAttack(Character target, int damage) {
        return Message.monsterAttack(this, target, damage);
    }

    public boolean getIsAngry() {
        return isAngry;
    }
}

