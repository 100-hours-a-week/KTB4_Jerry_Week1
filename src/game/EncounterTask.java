package game;

import game.utils.MonsterFactory;

import java.util.Random;

public class EncounterTask implements Runnable {
    private volatile Monster pendingMonster;
    private volatile int knownMonsterCount;
    private volatile boolean running = true;

    private final Random random;
    private final long beatMillis;

    public EncounterTask(Random random, long beatMillis) {
        this.random = random;
        this.beatMillis = beatMillis;
    }

    @Override
    public void run() {
        while (running) {
            tryRoll();

            try {
                Thread.sleep(beatMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // running = false 또는 break 중 고민했는데 break가 가독성 측면에서 더 좋다고 판단
            }
        }
    }

    private void tryRoll() {
        if (pendingMonster != null) {
            return;
        }

        double probability = probabilityFor(knownMonsterCount);

        if (random.nextDouble() < probability) {
            pendingMonster = MonsterFactory.createRandomMonster(random);
        }
    }

    private double probabilityFor(int count) {
        return switch (count) {
            case 0 -> 1.0;
            case 1 -> 0.2;
            default -> 0.0;
        };
    }

    /**
     * 우편함에서 대기 중인 몬스터를 꺼내고 우편함을 비우는 동작
     * 우편함이 비어 있을 수 있기 때문에 호출부에서 반환값의 null 여부 확인 필요
     * Optional 사용해도 되지만 isPresent만 사용할 것 같으니까 != null 만 해도 될 듯
     *
     * @return 대기 중인 몬스터 | 없으면 null
     */
    public Monster takePendingMonster() {
        Monster monster = pendingMonster;
        pendingMonster = null;
        return monster;
    }

    public void updateMonsterCount(int count) {
        knownMonsterCount = count;
    }

    public void shutdown() {
        running = false;
    }
}
