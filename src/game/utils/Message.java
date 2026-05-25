package game.utils;

import game.*;
import game.Character;
import game.enums.Job;

import java.util.List;

public class Message {
    public static String encounter(Character monster) {
        return monster.getName() + "이 야생에 나타났다!";
    }

    public static String encounterExtra(Character monster) {
        return "\n!![추가] 추가로 " + monster.getName() + "이 야생에 나타났다! 조심하자..!";
    }

    public static String escape() {
        return "무사히 도망쳤다!";
    }

    public static String escapeFailed() {
        return "[도망 실패] 앗! 다리가 후들거려서 도망치지 못했다...";
    }

    public static String escapeLost(int amount) {
        return "도망치느라 골드 " + amount + "원을 흘렸다...";
    }

    public static String levelUp(Player player) {
        return player.getName() + "가 레벨 " + player.getLevel() + "(으)로 올랐다!\n" + "레벨이 올라서 hp를 모두 회복했다!";
    }

    public static String gainGold(int amount) {
        return "골드 " + amount + "원을 획득했다!";
    }

    public static String gainExp(int amount) {
        return "경험치 " + amount + "를 획득했다!";
    }

    public static String useSkill(Player attacker, Character target, Job job, int power) {
        return attacker.getName() + "가 " + job.getSkillName() + "(으)로 " + target.getName() + "에게 " + power + " 피해를 입혔다!";
    }

    public static String monsterAttack(Character monster, Character target, int power) {
        return monster.getName() + "이 " + target.getName() + "에게 " + power + " 피해를 입혔다!";
    }

    public static String goblinAngry(Goblin goblin) {
        return goblin.getName() + "이 분노했다!";
    }

    public static String dragonAttack(Dragon dragon, Character target, int power) {
        return dragon.getElement().getName() + " 속성 " + dragon.getName() + "이 " + target.getName() + "에게 " + power + " 피해를 입혔다!";
    }

    public static String gameStart() {
        return "==== 모험을 시작합니다 ====";
    }

    public static String selectJob() {
        return "직업을 선택하세요:  1.전사  2.궁수  3.마법사  4.도적";
    }

    public static String jobChosen(Player player) {
        return player.getName() + "가 " + player.getJob().getName() + "(으)로 모험을 시작합니다!";
    }

    public static String actionOption() {
        return "행동을 선택하세요:  1.공격한다  2.도망간다";
    }

    public static String selectTarget(List<Monster> monsters) {
        StringBuilder sb = new StringBuilder("공격할 대상을 선택하세요: ");
        for (int i = 0; i < monsters.size(); i++) {
            sb.append(i + 1).append(".").append(monsters.get(i).getName()).append(" ");
        }
        return sb.toString();
    }

    public static String killMonster(Character monster) {
        return monster.getName() + "을 쓰러트렸다!";
    }

    public static String approachMaxLevel(Player player) {
        return player.getName() + "가 만렙을 찍었다! 오예~!";
    }

    public static String defeat(Player player) {
        return player.getName() + "가 쓰러졌다... " + player.getName() + "는 눈앞이 깜깜해졌다...";
    }

    public static String battleStatus(Player player, List<Monster> monsters) {
        StringBuilder sb = new StringBuilder("==========\n").append(player.getName()).append(" hp: ").append(player.getHp()).append("\n");
        for (Monster m : monsters) {
            sb.append(m.getName()).append(" hp: ").append(m.getHp()).append("\n");
        }
        sb.append("==========\n");
        return sb.toString();
    }

    public static String playerStatus(Player player) {
        return "==========\n"
                + player.getName() + " Lv: " + player.getLevel() + " hp: " + player.getHp() + "\n"
                + "==========\n";
    }

    public static String exploring() {
        return "풀숲을 돌아다니는 중...";
    }

    public static String retryInput() {
        return "다시 입력해주세요.";
    }
}
