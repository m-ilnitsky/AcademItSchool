package ru.academit.ilnitsky.minesweeper.gui;

import ru.academit.ilnitsky.minesweeper.common.GameSize;

import javax.swing.*;
import java.awt.*;

/**
 * Диалог выбора произвольных параметров для Swing-версии игры "Сапёр"
 * Created by UserLabView on 01.03.17.
 */
class CustomSize {
    private Frame frame;
    private boolean isNewGameSize;
    private GameSize gameSize;

    CustomSize(Frame frame) {
        this.frame = frame;
    }

    boolean showDialog(GameSize gameSize) {
        this.gameSize = gameSize;
        isNewGameSize = false;

        JDialog dialog = new JDialog(frame);

        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setTitle("Выбор произвольных параметров игры");
        dialog.setModal(true);
        dialog.setSize(640, 160);
        dialog.setMinimumSize(dialog.getSize());

        JPanel panel = new JPanel();

        JLabel labelXSize = new JLabel("Введите число ячеек по оси X (от 5 до 64):");
        JLabel labelYSize = new JLabel("Введите число ячеек по оси Y (от 5 до 64):");
        JLabel labelNumMines = new JLabel("Введите число мин (максимум 30% от числа ячеек):");

        JTextField inputXSize = new JTextField();
        JTextField inputYSize = new JTextField();
        JTextField inputNumMines = new JTextField();

        JButton buttonCancel = new JButton("Отмена");
        JButton buttonOk = new JButton("Применить");

        buttonCancel.addActionListener(actionEvent -> {
            isNewGameSize = false;
            dialog.setVisible(false);
        });

        buttonOk.addActionListener(actionEvent -> {
            try {
                int newXSize = Integer.parseInt(inputXSize.getText());
                int newYSize = Integer.parseInt(inputYSize.getText());
                int newNumMines = Integer.parseInt(inputNumMines.getText());

                int maxNumMines = (int) (newXSize * newYSize * 0.3);

                if (newXSize < 5 || newYSize < 5) {
                    showErrorMessage(dialog, "Размер должен быть больше 4");
                } else if (newXSize > 64 || newYSize > 64) {
                    showErrorMessage(dialog, "Размер должен быть меньше 65");
                } else if (newNumMines < 1) {
                    showErrorMessage(dialog, "Число бомб должно быть больше 0");
                } else if (newNumMines >= maxNumMines) {
                    showErrorMessage(dialog, "Число бомб должно быть меньше 30% от числа ячеек, т.е. меньше " + maxNumMines + " штук");
                } else {
                    isNewGameSize = true;

                    this.gameSize = new GameSize(newXSize, newYSize, newNumMines);

                    dialog.setVisible(false);
                }
            } catch (NumberFormatException ex) {
                showErrorMessage(dialog, "Вводить нужно целые положительные числа");
            }
        });

        panel.setLayout(new GridLayout(4, 2));
        panel.add(labelXSize);
        panel.add(inputXSize);
        panel.add(labelYSize);
        panel.add(inputYSize);
        panel.add(labelNumMines);
        panel.add(inputNumMines);
        panel.add(buttonCancel);
        panel.add(buttonOk);

        dialog.add(panel);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return isNewGameSize;
    }

    GameSize getGameSize() {
        return gameSize;
    }

    private void showErrorMessage(Window fromFrame, String message) {
        JOptionPane.showMessageDialog(fromFrame,
                message,
                "Ошибка",
                JOptionPane.INFORMATION_MESSAGE);
    }
}

