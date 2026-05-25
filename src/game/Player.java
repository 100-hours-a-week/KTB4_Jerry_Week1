package game;

import game.enums.Job;
import game.utils.Message;
import game.utils.Printer;

import java.util.Random;

public class Player extends Character {
    private int level;

    private int exp;

    private int gold;

    private Job job;

    private final Random random;

    Player(String name, int maxHp, int attackPower, Job job, Random random) {
        super(name, maxHp, attackPower + job.getPlusPower());
        this.level = 1;
        this.exp = 0;
        this.gold = 0;
        this.job = job;
        this.random = random;
    }

    public boolean escape() {
        if (getHp() < 50 && !random.nextBoolean()) {
            return false;
        }
        gold = Math.max(gold - 3, 0);
        return true;
    }

    public void gainExp(int expAmount) {
        exp += expAmount;

        if (exp >= 10) {
            levelUp();
        }
    }

    public void gainGold(int goldAmount) {
        gold += goldAmount;
    }

    private void levelUp() {
        level++;
        exp -= 10;
        increaseAttackPower(5);
        healToMax();

        Printer.println(Message.levelUp(this));
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public int getGold() {
        return gold;
    }

    public Job getJob() {
        return job;
    }
}