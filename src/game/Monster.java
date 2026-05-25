package game;

public abstract class Monster extends Character {
    private int expReward;

    private int goldReward;

    Monster(String name, int maxHp, int attackPower, int expReward, int goldReward) {
        super(name, maxHp, attackPower);

        this.expReward = expReward;
        this.goldReward = goldReward;
    }

    public abstract String describeAttack(Character target, int damage);

    public int getExpReward() {
        return expReward;
    }

    public int getGoldReward() {
        return goldReward;
    }
}
