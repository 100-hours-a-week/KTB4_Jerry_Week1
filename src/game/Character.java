package game;

public abstract class Character {
    private String name;

    private int maxHp;

    private int hp;

    private int attackPower;

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

    protected void increaseAttackPower(int amount) {
        this.attackPower += amount;
    }

    protected void healToMax() {
        this.hp = maxHp;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getAttackPower() {
        return attackPower;
    }
}