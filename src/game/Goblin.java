package game;

import game.utils.Message;

public class Goblin extends Monster {
    private boolean isAngry;

    Goblin(String name, int maxHp, int attackPower, int expReward, int goldReward) {
        super(name, maxHp, attackPower, expReward, goldReward);

        this.isAngry = false;
    }

    @Override
    public void decreaseHp(int amount) {
        super.decreaseHp(amount);

        if (!isAngry && hp <= maxHp / 2 && hp > 0) {
            isAngry = true;

            System.out.println(Message.goblinAngry(this));
        }
    }

    @Override
    public void attack(Character target) {
        int power = isAngry ? (attackPower + 5) : attackPower;
        target.decreaseHp(power);

        System.out.println(Message.monsterAttack(this, target, power));
    }

    public boolean getIsAngry() {
        return isAngry;
    }
}

