package game;

import game.enums.Job;
import game.utils.Message;
import game.utils.Printer;

public class Player extends Character {
    private int level;

    private int exp;

    private int gold;

    private Job job;

    Player(String name, int maxHp, int attackPower, Job job) {
        super(name, maxHp, attackPower + job.getPlusPower());
        this.level = 1;
        this.exp = 0;
        this.gold = 0;
        this.job = job;
    }

    @Override
    public int attack(Character target) {
        target.decreaseHp(attackPower);
        return attackPower;
    }

    public int escape() {
        int result = Math.max(gold - 3, 0);
        int lostGold = gold - result;
        this.gold = result;

        return lostGold;
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
        attackPower += 5;
        hp = maxHp;

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