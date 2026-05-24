package game;

import game.enums.Job;
import game.utils.Message;
import game.utils.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Game {
    private static final long ENCOUNTER_BEAT_MILLIS = 5000;
    private static final long POLL_DELAY_MILLIS = 200;
    private static final int MAX_MONSTERS = 2;

    private Player player;
    private final List<Monster> monsters = new ArrayList<>();
    private final BufferedReader br;
    private final Random random;

    private EncounterTask encounterTask;
    private Thread encounterThread;

    public Game() {
        br = new BufferedReader(new InputStreamReader(System.in));
        random = new Random();
    }

    public void run() throws IOException {
        /* 1. 직업 선택 */
        Printer.println(Message.gameStart());
        player = selectJob();
        Printer.println(Message.jobChosen(player));

        /* 2. 몬스터 출현 스레드 생성 */
        encounterTask = new EncounterTask(random, ENCOUNTER_BEAT_MILLIS);
        encounterThread = new Thread(encounterTask, "encounter-thread");
        encounterThread.start();

        /* 3. 게임 계속 진행 루프 */
        try {
            gameLoop();
        } finally {
            shutdownEncounterThread();
        }

        if (player.isDead()) {
            Printer.println(Message.defeat(player));
        } else {
            Printer.println(Message.approachMaxLevel(player));
        }
    }

    private void gameLoop() throws IOException {
        boolean isExploring = false;

        while (!player.isDead() && player.getLevel() < 3) {
            Monster addedMonster = applyPendingMonster();
            if (addedMonster != null) {
                if (monsters.size() == 1) {
                    Printer.println(Message.encounter(addedMonster));
                } else {
                    Printer.println(Message.encounterExtra(addedMonster));
                }
                isExploring = false;
            }

            if (monsters.isEmpty()) {
                if (!isExploring) {
                    Printer.println(Message.exploring());
                    isExploring = true;
                }

                if (!sleepBetweenPolls(POLL_DELAY_MILLIS)) break;
                continue;
            }

            Printer.println(Message.playerStatus(player));
            playerTurn();
        }
    }

    /**
     *  인카운터 스레드에서 미리 선정해놓은 다음 몬스터를 가져오는 메소드
     *
     *  @return 새로 추가된 몬스터. 추가된 몬스터가 없으면 null 반환
     */
    private Monster applyPendingMonster() {
        Monster newMonster = encounterTask.takePendingMonster();

        if (newMonster != null && monsters.size() < MAX_MONSTERS) {
            monsters.add(newMonster);
            encounterTask.updateMonsterCount(monsters.size());
            return newMonster;
        }
        return null;
    }

    private Player selectJob() throws IOException {
        Printer.println(Message.selectJob());
        int choice = Integer.parseInt(br.readLine());
        Job job = Job.values()[choice - 1];

        return new Player("용사", 100, 20, job);
    }

    private void playerTurn() throws IOException {
        Printer.println(Message.battleStatus(player, monsters));
        Printer.println(Message.actionOption());
        int choice = Integer.parseInt(br.readLine());

        if (choice == 1) {
            attackPhase();
        } else {
            escape();
            return;
        }

        monsterCounterAttack();
    }

    private void attackPhase() throws IOException {
        Monster target = chooseTarget();
        player.attack(target);

        if (target.isDead()) {
            Printer.println(Message.killMonster(target));
            player.gainGold(target.getGoldReward());
            player.gainExp(target.getExpReward());
            monsters.remove(target);
            encounterTask.updateMonsterCount(monsters.size());
            Printer.print("\n");
        }
    }

    private Monster chooseTarget() throws IOException {
        if (monsters.size() == 1)  {
            return monsters.get(0);
        }

        Printer.println(Message.selectTarget(monsters));
        int idx = Integer.parseInt(br.readLine()) - 1;
        return monsters.get(idx);
    }

    private void monsterCounterAttack() {
        List<Monster> aliveMonsters = monsters.stream()
                .filter(m -> !m.isDead())
                .toList();

        if (aliveMonsters.isEmpty()) return;

        /* 2개의 몬스터 스레드 동시에 출발시키기 위한 스레드 출발점 설정 */
        CountDownLatch startGate = new CountDownLatch(1);
        List<Thread> monsterThreads = new ArrayList<>();

        for (Monster m: aliveMonsters) {
            Thread t = new Thread(() -> {
                try {
                    startGate.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                synchronized (player) {
                    if (player.isDead()) return;

                    m.attack(player);
                }
            }, "attack by " + m.getName());
            monsterThreads.add(t);
            t.start();
        }

        startGate.countDown();

        for (Thread t: monsterThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void escape() {
        player.escape();
        monsters.clear();
        encounterTask.updateMonsterCount(0);
    }

    private boolean sleepBetweenPolls(long millis) {
        try {
            Thread.sleep(millis);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }


    private void shutdownEncounterThread() {
        if (encounterTask != null) {
            encounterTask.shutdown();
        }

        if (encounterThread != null) {
            encounterThread.interrupt();
            try {
                encounterThread.join(1000);  // 최대 1초 대기하기
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
