package ru.academit.ilnitsky.gui;

import ru.academit.ilnitsky.common.View;
import ru.academit.ilnitsky.common.ViewListener;
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
    RubleBanknote[] nominals;
    int[] remove = {50, 100, 200, 500, 1000, 2000, 3000, 5000};

    private final ArrayList<ViewListener> listeners = new ArrayList<>();

    private final JFrame frame = new JFrame("Преобразование температуры");

    private final MenuLevel[] menuLevels = new MenuLevel[]{
            MenuLevel.M0,
            MenuLevel.M0_1,
            MenuLevel.M0_2, MenuLevel.M0_2_1, MenuLevel.M0_2_2,
            MenuLevel.M0_3, MenuLevel.M0_3_1, MenuLevel.M0_3_2, MenuLevel.M0_3_2_1, MenuLevel.M0_3_2_2
    };
    private final JPanel[] menuPanels = new JPanel[menuLevels.length];

    private final JButton[] buttons0_2;
    private final JButton[] buttons0_3_2;

    private final JLabel[] labels0_1;
    private final JLabel[] labels0_3_2_2;
    private final JLabel label0_2_1 = new JLabel();
    private final JLabel label0_2_2 = new JLabel();

    private final JTextField textField0_3_1 = new JTextField();

    public FrameView(RubleBanknote[] nominals) {
        this.nominals = nominals;

        buttons0_2 = new JButton[nominals.length];
        buttons0_3_2 = new JButton[nominals.length];

        labels0_1 = new JLabel[1 + nominals.length];
        labels0_3_2_2 = new JLabel[1 + nominals.length];
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

        JButton[] buttons = new JButton[remove.length + 2];
        for (int i = 0; i < remove.length; i++) {
            buttons[i].setText(String.format("%d: Снять %4d рублей%n", i + 1, remove[i]));
        }
        buttons[remove.length].setText("9: Выбрать другую сумму");
        buttons[remove.length + 1].setText("0: ВЫХОД В ОСНОВНОЕ МЕНЮ");

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

        frame.add(menuPanels[0], BorderLayout.CENTER);
    }

    private void initFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(580, 440);
        frame.setMinimumSize(frame.getSize());

        // заставляет фрейм располагаться по центру экрана при запуске
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    @Override
    public void startApplication() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initFrame();
                initPanels();
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
