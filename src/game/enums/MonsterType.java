package game.enums;

import game.Dragon;
import game.Goblin;
import game.Monster;

import java.util.Random;

public enum MonsterType {
    GOBLIN {
        @Override
        public Monster create(Random random) {
            return new Goblin("고블린", 50, 5, 4, 5);
        }
    },
    DRAGON {
        @Override
        public Monster create(Random random) {
            Element element = Element.values()[random.nextInt(Element.values().length)];
            return new Dragon("드래곤", 70, 10, 8, 10, element);
        }
    };

    public abstract Monster create(Random random);
}
