package game.enums;

public enum Element {
    FIRE("불", 3),
    WATER("물", 0),
    ELECTRICITY("전기", 5);

    private final String name;

    private final int bonusPower;

    Element(String name, int bonusPower) {
        this.name = name;
        this.bonusPower = bonusPower;
    }

    public String getName() {
        return name;
    }

    public int getBonusPower() {
        return bonusPower;
    }
}
