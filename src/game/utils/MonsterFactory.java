package game.utils;

import game.Monster;
import game.enums.MonsterType;

import java.util.Random;

public class MonsterFactory {
    public static Monster createRandomMonster(Random random) {
        MonsterType[] types = MonsterType.values();
        MonsterType chosen = types[random.nextInt(types.length)];
        return chosen.create(random);
    }
}
