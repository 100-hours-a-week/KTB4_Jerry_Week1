package game;

public abstract class Character {
    protected String name;

    protected int maxHp;

    protected int hp;

    protected int attackPower;

    Character(String name, int maxHp, int attackPower) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackPower = attackPower;
    }

    public int attack(Character target) {
        target.decreaseHp(attackPower);
        return attackPower;
    }

    public synchronized void decreaseHp(int amount) {
        this.hp = Math.max(0, this.hp - amount);
    }

    public boolean isDead() {
        return hp == 0;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getAttackPower() {
        return attackPower;
    }
}