package game.enums;

import game.Character;
import game.Player;
import game.utils.Message;
import game.utils.Printer;

public enum Job {
    WARRIOR("전사", "소울드라이브"),
    ARCHER("궁수", "수정화살"),
    WIZARD("마법사", "파이어볼"),
    THIEF("도적", "광대대학");

    private final String name;
    private final String skillName;

    Job(String name, String skillName) {
        this.name = name;
        this.skillName = skillName;
    }

    public String getName() {
        return name;
    }

    public String getSkillName() {
        return skillName;
    }

    public void useSkill(Player attacker, Character target) {
        int power = attacker.getAttackPower();
        target.decreaseHp(power);

        Printer.println(Message.useSkill(attacker, target, this, power));
    }
}
