package game.enums;

public enum Job {
    WARRIOR("전사", "소울드라이브", 4),
    ARCHER("궁수", "수정화살", 2),
    WIZARD("마법사", "파이어볼", 3),
    THIEF("도적", "광대대학", 1);

    private final String name;
    private final String skillName;
    private final int plusPower;

    Job(String name, String skillName, int plusPower) {
        this.name = name;
        this.skillName = skillName;
        this.plusPower = plusPower;
    }

    public String getName() {
        return name;
    }

    public String getSkillName() {
        return skillName;
    }

    public int getPlusPower() {
        return plusPower;
    }
}
