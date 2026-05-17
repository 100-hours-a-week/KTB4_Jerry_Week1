package game;

import game.enums.Element;
import game.enums.Job;
import game.utils.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Game {
    private Player player;
    private Monster currentMonster;
    private final BufferedReader br;
    private final Random random;

    public Game() {
        br = new BufferedReader(new InputStreamReader(System.in));
        random = new Random();
    }

    public void run() throws IOException {
        /* 1. 직업 선택 */
        System.out.println(Message.gameStart());
        player = selectJob();
        System.out.println(Message.jobChosen(player));

        /* 2. 게임 계속 진행하기 위한 조건 */
        while (!player.isDead() && player.getLevel() < 3) {
            if (currentMonster == null) {
                System.out.println(Message.playerStatus(player));

                currentMonster = encounterMonster();
                System.out.println(Message.encounter(currentMonster));
            }

            playerTurn();
        }

        if (player.isDead()) {
            System.out.println(Message.defeat(player));
        } else {
            System.out.println(Message.approachMaxLevel(player));
        }
    }

    private Player selectJob() throws IOException {
        System.out.println(Message.selectJob());
        int choice = Integer.parseInt(br.readLine());
        Job job = Job.values()[choice - 1];

        return new Player("용사", 100, 20, job);
    }

    private Monster encounterMonster() {
        if (random.nextInt(2) == 0) {
            return new Goblin("고블린", 50, 5, 4, 5);
        } else {
            Element element = Element.values()[random.nextInt(Element.values().length)];
            return new Dragon("드래곤", 70, 10, 8, 10, element);
        }
    }

    private void playerTurn() throws IOException {
        System.out.println(Message.battleStatus(player, currentMonster));

        System.out.println(Message.actionOption());
        int choice = Integer.parseInt(br.readLine());

        if (choice == 1) {
            player.attack(currentMonster);

            if (currentMonster.isDead()) {
                System.out.println(Message.killMonster(currentMonster));
                player.gainGold(currentMonster.getGoldReward());
                player.gainExp(currentMonster.getExpReward());
                currentMonster = null;

                System.out.print("\n");
            } else {
                currentMonster.attack(player);
            }
        } else {
            player.escape();
            currentMonster = null;
        }
    }
}
