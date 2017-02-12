package ru.academit.ilnitsky.minesweeper.scores;

import ru.academit.ilnitsky.minesweeper.common.GameBoardSize;
import ru.academit.ilnitsky.minesweeper.common.GameInfo;
import ru.academit.ilnitsky.minesweeper.common.GameSize;
import ru.academit.ilnitsky.minesweeper.common.TopScoresInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Лучшие результаты игры "Сапёр"
 * Created by Mike on 12.02.2017.
 */
public class TopScores implements TopScoresInterface {
    private static final String code = "windows-1251";

    private final int xSize;
    private final int ySize;
    private final int numMines;
    private final int maxLength;
    private final String topName;
    private final String fileName;

    private int currentLength;
    private GameInfo[] games;

    public TopScores(GameSize gameSize, int maxLength, String topName) {
        this(gameSize.getXSize(), gameSize.getYSize(), gameSize.getNumMines(), maxLength, topName);
    }

    public TopScores(GameBoardSize gameBoardSize, int numMines, int maxLength, String topName) {
        this(gameBoardSize.getXSize(), gameBoardSize.getYSize(), numMines, maxLength, topName);
    }

    public TopScores(int xSize, int ySize, int numMines, int maxLength, String topName) {
        this(xSize, ySize, numMines, maxLength, topName,
                "./x" + xSize + "_y" + ySize + "_m" + numMines + "_top" + maxLength + ".topResults");
    }

    public TopScores(GameSize gameSize, int maxLength, String topName, String fileName) {
        this(gameSize.getXSize(), gameSize.getYSize(), gameSize.getNumMines(), maxLength, topName, fileName);
    }

    public TopScores(GameBoardSize gameBoardSize, int numMines, int maxLength, String topName, String fileName) {
        this(gameBoardSize.getXSize(), gameBoardSize.getYSize(), numMines, maxLength, topName, fileName);
    }

    public TopScores(int xSize, int ySize, int numMines, int maxLength, String topName, String fileName) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.numMines = numMines;
        this.maxLength = maxLength;
        this.topName = topName;
        this.fileName = fileName;

        try {
            readFromFile();
        } catch (FileNotFoundException e) {
            currentLength = 0;
            games = new GameInfo[maxLength];
        } catch (IOException e) {
            System.out.println("Ошибка открытия файла с результатами игры! (" + e + ")");
        }

        System.out.println("Constructor currentLength=" + currentLength);
    }

    private void readFromFile() throws IOException {
        try (
                Scanner fileScanner = new Scanner(new FileInputStream(fileName), code)
        ) {
            GameInfo[] oldGames = new GameInfo[maxLength];
            int count = -1;

            while (fileScanner.hasNextLine()) {
                count++;
                if (count == maxLength) {
                    count--;
                    break;
                }

                int time;
                int numActions;
                String userName;

                String line = fileScanner.nextLine().trim();
                int index = 0;

                if (index < line.length() && line.substring(index, index + 1).equals("T")) {
                    StringBuilder sb = new StringBuilder();
                    index++;
                    while (index < line.length() && Character.isDigit(line.substring(index, index + 1).toCharArray()[0])) {
                        sb.append(line.substring(index, index + 1));
                        index++;
                    }
                    time = Integer.parseInt(sb.toString());
                } else {
                    return;
                }

                while (index < line.length() && !line.substring(index, index + 1).equals("A")) {
                    index++;
                }

                if (index < line.length() && line.substring(index, index + 1).equals("A")) {
                    StringBuilder sb = new StringBuilder();
                    index++;
                    while (index < line.length() && Character.isDigit(line.substring(index, index + 1).toCharArray()[0])) {
                        sb.append(line.substring(index, index + 1));
                        index++;
                    }
                    numActions = Integer.parseInt(sb.toString());
                } else {
                    return;
                }

                while (index < line.length() && !line.substring(index, index + 1).equals("@")) {
                    index++;
                }

                if (index < line.length() && line.substring(index, index + 1).equals("@")) {
                    StringBuilder sb = new StringBuilder();
                    index++;
                    while (index < line.length() && !line.substring(index, index + 1).equals("\n")) {
                        sb.append(line.substring(index, index + 1));
                        index++;
                    }
                    userName = sb.toString();
                } else {
                    return;
                }

                oldGames[count] = new GameInfo(xSize, ySize, numMines, numActions, time, userName);
            }

            games = oldGames;
            currentLength = count + 1;
            System.out.println("readFromFile currentLength=" + currentLength);
        }
    }

    private void writeToFile() throws IOException {
        try (
                PrintWriter writer = new PrintWriter(fileName, code)
        ) {
            for (int i = 0; i < currentLength; i++) {
                writer.println("T" + games[i].getTime() + " A" + games[i].getNumActions() + " @" + games[i].getUserName());
            }
        }
    }

    @Override
    public void addResult(GameInfo gameInfo) {
        if (currentLength == 0) {
            games[0] = gameInfo;
            currentLength = 1;
        } else {
            for (int i = currentLength - 1; i >= 0; i--) {
                if (games[i].getTime() > gameInfo.getTime()) {
                    if (i + 1 < maxLength) {
                        games[i + 1] = games[i];
                    }
                    if (i == 0) {
                        games[i] = gameInfo;
                        break;
                    }
                } else if (games[i].getTime() == gameInfo.getTime()) {
                    if (games[i].getNumActions() > gameInfo.getNumActions()) {
                        if (i + 1 < maxLength) {
                            games[i + 1] = games[i];
                        }
                        if (i == 0) {
                            games[i] = gameInfo;
                            break;
                        }
                    } else {
                        if (i + 1 < maxLength) {
                            games[i + 1] = gameInfo;
                        }
                        break;
                    }
                } else {
                    if (i + 1 < maxLength) {
                        games[i + 1] = gameInfo;
                    }
                    break;
                }
            }

            for (int i = maxLength - 1; i >= 0; i--) {
                if (games[i] != null) {
                    currentLength = i + 1;
                    break;
                }
            }

            System.out.println("addResult currentLength=" + currentLength);
        }

        try {
            writeToFile();
        } catch (IOException e) {
            System.out.println("Ошибка сохранения в файл результатов игры! (" + e + ")");
        }
    }

    @Override
    public GameInfo[] getTopScores() {
        return games;
    }

    @Override
    public String getTopScoresName() {
        return topName;
    }

    @Override
    public int getXSize() {
        return xSize;
    }

    @Override
    public int getYSize() {
        return ySize;
    }

    @Override
    public int getNumMines() {
        return numMines;
    }
}
