package ru.academit.ilnitsky.cash_machine_ui.gui;

import ru.academit.ilnitsky.cash_machine_ui.common.View;
import ru.academit.ilnitsky.cash_machine_ui.common.ViewListener;
import ru.academit.ilnitsky.console_ui.MenuLevel;
import ru.academit.ilnitsky.moneybox.RubleBanknote;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * View с графическим интерфейсом пользователя (GUI)
 * Created by Mike on 30.01.2017.
 */
public class FrameView implements View {
    private RubleBanknote[] nominals;
    private int[] offerForRemove;
    private int[] numberBanknotes;

    private int lastValue = 0;
    private MenuLevel lastMenuLevel = MenuLevel.M0;

    private final ArrayList<ViewListener> listeners = new ArrayList<>();

    private final JFrame frame = new JFrame("Банкомат");

    private final MenuLevel[] menuLevels = new MenuLevel[]{
            MenuLevel.M0,
            MenuLevel.M0_1,
            MenuLevel.M0_2, MenuLevel.M0_2_1, MenuLevel.M0_2_2,
            MenuLevel.M0_3, MenuLevel.M0_3_1, MenuLevel.M0_3_2, MenuLevel.M0_3_2_1, MenuLevel.M0_3_2_2
    };
    private final JPanel[] menuPanels = new JPanel[menuLevels.length];
    private final int[] frameLines;
    private final int frameLength = 480;
    private final int lineHeight = 40;

    private final JButton[] buttons0_2;
    private final JButton[] buttons0_3_2;

    private final JLabel[] labels0_1;
    private final JLabel[] labels0_3_2_2;

    private final JLabel label0_2_1 = new JLabel();
    private final JLabel label0_2_2 = new JLabel();

    private final JTextField textField0_3_1 = new JTextField();

    public FrameView(RubleBanknote[] nominals, int[] offerForRemove) {
        this.nominals = nominals;
        this.offerForRemove = offerForRemove;

        if (offerForRemove.length > 8) {
            throw new IllegalArgumentException("offerForRemove.length > 8");
        }

        numberBanknotes = new int[nominals.length];
        for (int i = 0; i < nominals.length; i++) {
            numberBanknotes[i] = 0;
        }

        for (int i = 0; i < menuLevels.length; i++) {
            menuPanels[i] = new JPanel();
        }

        frameLines = new int[]{
                4,
                4 + nominals.length,
                2 + nominals.length,
                4,
                5,
                3 + offerForRemove.length,
                5,
                3 + nominals.length,
                4,
                3 + nominals.length
        };

        buttons0_2 = new JButton[nominals.length];
        for (int i = 0; i < buttons0_2.length; i++) {
            buttons0_2[i] = new JButton();
        }

        buttons0_3_2 = new JButton[nominals.length];
        for (int i = 0; i < buttons0_3_2.length; i++) {
            buttons0_3_2[i] = new JButton();
        }

        labels0_1 = new JLabel[1 + nominals.length];
        for (int i = 0; i < labels0_1.length; i++) {
            labels0_1[i] = new JLabel();
        }

        labels0_3_2_2 = new JLabel[1 + nominals.length];
        for (int i = 0; i < labels0_3_2_2.length; i++) {
            labels0_3_2_2[i] = new JLabel();
        }
    }

    private void initPanel_0() {
        JButton[] buttons = new JButton[]{
                new JButton("1: Распечатать текущий баланс"),
                new JButton("2: Положить на счёт наличнымие"),
                new JButton("3: Снять наличные со счёта")
        };

        menuPanels[0].setLayout(new GridLayout(4, 1));

        menuPanels[0].add(new JLabel("ОСНОВНОЕ МЕНЮ"));
        for (JButton button : buttons) {
            menuPanels[0].add(button);
        }
    }

    private void initPanel_0_1() {
        menuPanels[1].setLayout(new GridLayout(4 + nominals.length, 1));

        menuPanels[1].add(new JLabel("БАЛАНС"));
        menuPanels[1].add(new JLabel("Имеется:"));
        for (JLabel label : labels0_1) {
            menuPanels[1].add(label);
        }

        JButton button = new JButton("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");
        menuPanels[1].add(button);
    }

    private void initPanel_0_2() {
        menuPanels[2].setLayout(new GridLayout(2 + nominals.length, 1));

        menuPanels[2].add(new JLabel("ПРИЁМ НАЛИЧНЫХ"));

        for (JButton button : buttons0_2) {
            menuPanels[2].add(button);
        }

        JButton button = new JButton("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");
        menuPanels[2].add(button);
    }

    private void initPanel_0_2_1() {
        menuPanels[3].setLayout(new GridLayout(4, 1));

        menuPanels[3].add(new JLabel("ПРИЁМ НАЛИЧНЫХ"));

        menuPanels[3].add(label0_2_1);

        JButton[] buttons = new JButton[]{
                new JButton("1: Продолжить пополнение счёта наличными"),
                new JButton("0: ВЫХОД В ОСНОВНОЕ МЕНЮ"),
        };
        for (JButton button : buttons) {
            menuPanels[3].add(button);
        }
    }

    private void initPanel_0_2_2() {
        menuPanels[4].setLayout(new GridLayout(5, 1));

        menuPanels[4].add(new JLabel("ПРИЁМ НАЛИЧНЫХ"));
        menuPanels[4].add(new JLabel("Извините, счёт не был пополнен"));

        menuPanels[4].add(label0_2_2);

        JButton[] buttons = new JButton[]{
                new JButton("1: Продолжить пополнение счёта наличными другого номинла"),
                new JButton("0: ВЫХОД В ОСНОВНОЕ МЕНЮ"),
        };
        for (JButton button : buttons) {
            menuPanels[4].add(button);
        }
    }

    private void initPanel_0_3() {
        menuPanels[5].setLayout(new GridLayout(11, 1));

        menuPanels[5].add(new JLabel("СНЯТИЕ НАЛИЧНЫХ"));

        JButton[] buttons = new JButton[offerForRemove.length + 2];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
        }

        for (int i = 0; i < offerForRemove.length; i++) {
            buttons[i].setText(String.format("%d: Снять %4d рублей%n", i + 1, offerForRemove[i]));
        }
        buttons[offerForRemove.length].setText("9: Выбрать другую сумму");
        buttons[offerForRemove.length + 1].setText("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");

        for (JButton button : buttons) {
            menuPanels[5].add(button);
        }
    }

    private void initPanel_0_3_1() {
        menuPanels[6].setLayout(new GridLayout(5, 1));

        menuPanels[6].add(new JLabel("СНЯТИЕ НАЛИЧНЫХ"));
        menuPanels[6].add(new JLabel("Введите необходимую сумму:"));

        menuPanels[6].add(textField0_3_1);

        JButton[] buttons = new JButton[]{
                new JButton("1: Подтвердить сумму"),
                new JButton("0: ВЫХОД В ОСНОВНОЕ МЕНЮ"),
        };
        for (JButton button : buttons) {
            menuPanels[6].add(button);
        }
    }

    private void initPanel_0_3_2() {
        menuPanels[7].setLayout(new GridLayout(3 + nominals.length, 1));

        menuPanels[7].add(new JLabel("СНЯТИЕ НАЛИЧНЫХ"));
        menuPanels[7].add(new JLabel("Выберете приоритетные купюры"));

        for (int i = nominals.length - 1; i >= 0; i--) {
            int j = nominals.length - i;
            buttons0_3_2[j - 1].setText(String.format("%d: Снять по возможности купюрами по %4d рублей%n", j, nominals[i].getValue()));
        }

        for (JButton button : buttons0_3_2) {
            menuPanels[7].add(button);
        }

        JButton button = new JButton("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");
        menuPanels[7].add(button);
    }

    private void initPanel_0_3_2_1() {
        menuPanels[8].setLayout(new GridLayout(4, 1));

        menuPanels[8].add(new JLabel("СНЯТИЕ НАЛИЧНЫХ"));
        menuPanels[8].add(new JLabel("Выбранная сумма не может быть выдана имеющимися купюрами"));

        JButton[] buttons = new JButton[]{
                new JButton("1: Выход в меню снятия наличных"),
                new JButton("0: ВЫХОД В ОСНОВНОЕ МЕНЮ"),
        };
        for (JButton button : buttons) {
            menuPanels[8].add(button);
        }
    }

    private void initPanel_0_3_2_2() {
        menuPanels[9].setLayout(new GridLayout(3 + nominals.length, 1));

        menuPanels[9].add(new JLabel("СНЯТИЕ НАЛИЧНЫХ"));
        for (JLabel label : labels0_3_2_2) {
            menuPanels[9].add(label);
        }

        JButton button = new JButton("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");
        menuPanels[9].add(button);
    }

    private void initPanels() {
        initPanel_0();
        initPanel_0_1();
        initPanel_0_2();
        initPanel_0_2_1();
        initPanel_0_2_2();
        initPanel_0_3();
        initPanel_0_3_1();
        initPanel_0_3_2();
        initPanel_0_3_2_1();
        initPanel_0_3_2_2();

        //frame.add(menuPanels[0], BorderLayout.CENTER);
    }

    private String printBanknotes(int numBanknotes) {
        int num = numBanknotes % 10;
        if (numBanknotes > 4 && numBanknotes < 21) {
            return "купюр";
        } else if (num == 1) {
            return "купюра";
        } else if (num > 1 && num < 5) {
            return "купюры";
        } else {
            return "купюр";
        }
    }

    private String printBanknotes2(int numBanknotes) {
        int num = numBanknotes % 10;
        if (numBanknotes > 4 && numBanknotes < 21) {
            return "купюр";
        } else if (num == 1) {
            return "купюру";
        } else if (num > 1 && num < 5) {
            return "купюры";
        } else {
            return "купюр";
        }
    }

    private void setMenu(MenuLevel newMenuLevel) {
        switch (newMenuLevel) {
            case M0_1:
                for (int i = 0; i < nominals.length; i++) {
                    labels0_1[i].setText(String.format("%5d %-6s номиналом %4d рублей.%n", numberBanknotes[i], printBanknotes(numberBanknotes[i]), nominals[i].getValue()));
                }
                labels0_1[nominals.length].setText(String.format("Всего имеется: %d руб.%n", lastValue));

                break;
            case M0_2:
                for (int i = nominals.length - 1; i >= 0; i--) {
                    int j = nominals.length - i;
                    buttons0_2[j - 1].setText(String.format("%d: Пополнить счёт на %4d рублей%n", j, nominals[i].getValue()));
                }

                break;
            case M0_2_1:
                label0_2_1.setText(String.format("Счёт пополнен на %d рублей%n", lastValue));

                break;
            case M0_2_2:
                label0_2_2.setText(String.format("В данный момент, банкомат не может принимать купюры номиналом %d рублей%n", lastValue));

                break;
            case M0_3_2:
                for (int i = 0; i < nominals.length; i++) {
                    if (lastValue >= nominals[i].getValue() && (numberBanknotes[i] > 0)) {
                        buttons0_3_2[i].setEnabled(true);
                    } else {
                        buttons0_3_2[i].setEnabled(false);
                    }
                }

                break;
            case M0_3_2_2:
                labels0_3_2_2[0].setText(String.format("К выдаче подготовлено %d рублей%n", lastValue));
                for (int i = 1; i < nominals.length; i++) {
                    if (numberBanknotes[i - 1] > 0) {
                        labels0_3_2_2[i].setText(String.format("Возьмите %d %s номиналом %3d рублей%n", numberBanknotes[i - 1], printBanknotes2(numberBanknotes[i - 1]), nominals[i - 1].getValue()));
                    }
                }
        }

        int index = 0;
        for (int i = 0; i < menuLevels.length; i++) {
            if (menuLevels[i] == newMenuLevel) {
                index = i;
                break;
            }
        }

        for (int i = 0; i < menuLevels.length; i++) {
            frame.remove(menuPanels[i]);
        }

        frame.setSize(frameLength, frameLines[index] * lineHeight);
        frame.setMinimumSize(frame.getSize());
        frame.setMaximumSize(frame.getSize());
        frame.add(menuPanels[index], BorderLayout.CENTER);
    }

    public void showMenu(MenuLevel menuLevel) {
        setMenu(menuLevel);
    }

    public void showMenu(MenuLevel menuLevel, int value) {
        lastValue = value;
        setMenu(menuLevel);
    }

    public void showMenu(MenuLevel menuLevel, int[] numberBanknotes) {
        this.numberBanknotes = numberBanknotes;
        setMenu(menuLevel);
    }

    public void showMenu(MenuLevel menuLevel, int value, int[] numberBanknotes) {
        lastValue = value;
        this.numberBanknotes = numberBanknotes;
        setMenu(menuLevel);
    }

    private void initFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(frameLength, frameLines[0] * lineHeight);

        // заставляет фрейм располагаться по центру экрана при запуске
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    @Override
    public void startApplication() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initPanels();
                initFrame();
                setMenu(MenuLevel.M0);
            }
        });
    }

    @Override
    public void close() throws Exception {
        frame.setVisible(false);
    }

    @Override
    public void addViewListener(ViewListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeViewListener(ViewListener listener) {
        listeners.remove(listener);
    }
}
