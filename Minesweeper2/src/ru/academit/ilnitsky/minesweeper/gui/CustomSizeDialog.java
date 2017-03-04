package ru.academit.ilnitsky.minesweeper.gui;

import ru.academit.ilnitsky.minesweeper.common.GameSize;
import ru.academit.ilnitsky.minesweeper.common.ViewListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Диалог выбора произвольных параметров для Swing-версии игры "Сапёр"
 * Created by UserLabView on 01.03.17.
 */
class CustomSizeDialog {
    private JFrame frame;
    private boolean isNewGameSize;
    private GameSize gameSize;

    private final int minSize;
    private final int maxSize;
    private final double numMinesCoefficient;
    private final int numMinesPercent;

    CustomSizeDialog(JFrame frame, ViewListener core) {
        this.frame = frame;

        minSize = core.getMinSize();
        maxSize = core.getMaxSize();
        numMinesCoefficient = core.getNumMinesCoefficient();

        numMinesPercent = (int) (numMinesCoefficient * 100);
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

        JLabel labelXSize = new JLabel("Введите число ячеек по оси X (от " + minSize + " до " + maxSize + "):");
        JLabel labelYSize = new JLabel("Введите число ячеек по оси Y (от " + minSize + " до " + maxSize + "):");
        JLabel labelNumMines = new JLabel("Введите число мин (максимум " + numMinesPercent + "% от числа ячеек):");

        JTextField inputXSize = new JTextField();
        JTextField inputYSize = new JTextField();
        JTextField inputNumMines = new JTextField();

        JButton buttonCancel = new JButton("Отмена   (Alt+C)");
        JButton buttonOk = new JButton("Применить   (Alt+Enter)");

        buttonCancel.setMnemonic(KeyEvent.VK_C);
        buttonOk.setMnemonic(KeyEvent.VK_ENTER);

        buttonCancel.addActionListener(actionEvent -> {
            isNewGameSize = false;
            dialog.setVisible(false);
        });

        buttonOk.addActionListener(actionEvent -> {
            try {
                int newXSize = Integer.parseInt(inputXSize.getText());
                int newYSize = Integer.parseInt(inputYSize.getText());
                int newNumMines = Integer.parseInt(inputNumMines.getText());

                int maxNumMines = (int) (newXSize * newYSize * numMinesCoefficient);

                if (newXSize < minSize || newYSize < minSize) {
                    showErrorMessage(dialog, "Размер должен быть не меньше " + minSize);
                } else if (newXSize > maxSize || newYSize > maxSize) {
                    showErrorMessage(dialog, "Размер должен быть не больше " + maxSize);
                } else if (newNumMines < 1) {
                    showErrorMessage(dialog, "Число бомб должно быть больше 0");
                } else if (newNumMines >= maxNumMines) {
                    showErrorMessage(dialog, "Число бомб должно быть меньше " + numMinesPercent + "% от числа ячеек, т.е. меньше " + maxNumMines + " штук");
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

