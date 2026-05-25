package game.enums;

public enum Job {
    WARRIOR(1, "전사", "소울드라이브", 4),
    ARCHER(2, "궁수", "수정화살", 2),
    WIZARD(3, "마법사", "파이어볼", 3),
    THIEF(4, "도적", "광대대학", 1);

    private final int code;
    private final String name;
    private final String skillName;
    private final int plusPower;

    Job(int code, String name, String skillName, int plusPower) {
        this.code = code;
        this.name = name;
        this.skillName = skillName;
        this.plusPower = plusPower;
    }

    public static Job fromCode(int code) {
        for (Job job: values()) {
            if (job.code == code) {
                return job;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 직업 코드: " + code);
    }

    public int getCode() {
        return code;
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
