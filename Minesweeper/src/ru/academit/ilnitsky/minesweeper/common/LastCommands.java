package ru.academit.ilnitsky.minesweeper.common;

/**
 * Список последних введённых текстовых команд
 * Created by UserLabView on 16.02.17.
 */
public class LastCommands {
    private String[] commands;
    private int currentNumCommands;

    public LastCommands(int numCommands) {
        commands = new String[numCommands];

        currentNumCommands = 0;
    }

    public void clear() {
        for (int i = 0; i < commands.length; i++) {
            commands[i] = null;
        }
        currentNumCommands = 0;
    }

    public void add(String command) {
        if (currentNumCommands < commands.length) {
            commands[currentNumCommands] = command;
            currentNumCommands++;
        } else {
            System.arraycopy(commands, 1, commands, 0, commands.length - 1);
            commands[commands.length - 1] = command;
        }
    }

    public int getMaxSize() {
        return commands.length;
    }

    public int getCurrentSize() {
        return currentNumCommands;
    }

    public String[] getCommands() {
        return commands;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < currentNumCommands; i++) {
            sb = sb.append("[").append(i + 1).append("] ").append(commands[i]);
            if (i < currentNumCommands - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }
}
