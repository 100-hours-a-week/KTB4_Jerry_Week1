package game.utils;

public class Validator {
    public static int parseIntInRange(String input, int min, int max) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("입력이 비어있습니다.");
        }

        int value;
        try {
            value = Integer.parseInt(input.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("숫자가 아닙니다.");
        }

        if (value < min || value > max) {
            throw new IllegalArgumentException("입력 가능한 범위(" + min + "~" + max + ")를 벗어났습니다.");
        }

        return value;
    }
}
