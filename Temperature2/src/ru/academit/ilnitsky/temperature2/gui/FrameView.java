package ru.academit.ilnitsky.temperature2.gui;

import ru.academit.ilnitsky.temperature2.common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * View с графическим интерфейсом пользователя (GUI)
 * Created by Mike on 28.01.2017.
 */
public class FrameView implements View {
    private final ArrayList<ViewListener> listeners = new ArrayList<>();

    private final JFrame frame = new JFrame("Преобразование температуры");
    private final JTextField inputText = new JTextField();
    private final JButton okButton = new JButton("OK");
    private final JLabel errorLabel = new JLabel("");

    private final JComboBox<ConvertUnit> unitBox;
    private final JLabel[] groupLabels;
    private final JLabel[] valueLabels;

    private final ConvertUnit[] converters;
    private final UnitGroup[] groups;

    private static final String separator = ":   ";

    public FrameView(UnitConverter converter) {
        converters = converter.getConverters();
        groups = converter.getGroups();

        unitBox = new JComboBox<>(converters);

        groupLabels = new JLabel[groups.length];
        for (int i = 0; i < groups.length; i++) {
            groupLabels[i] = new JLabel(groups[i].getName());
        }

        valueLabels = new JLabel[converters.length];
        for (int i = 0; i < converters.length; i++) {
            valueLabels[i] = new JLabel(converters[i].toString() + separator);
        }
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

    private void initFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(580, 440);
        frame.setMinimumSize(frame.getSize());

        // заставляет фрейм располагаться по центру экрана при запуске
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void initContent() {
        JPanel inputPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(outputPanel, BorderLayout.CENTER);

        errorLabel.setForeground(Color.RED);

        Font groupFont = new Font("TimesRoman", Font.BOLD, 12);
        for (JLabel gl : groupLabels) {
            gl.setFont(groupFont);
        }

        Font labelFont = new Font("Courier New", Font.PLAIN, 14);
        for (JLabel vl : valueLabels) {
            vl.setFont(labelFont);
        }

        inputPanel.setLayout(new GridLayout(1, 3));
        inputPanel.add(unitBox);
        inputPanel.add(inputText);
        inputPanel.add(okButton);

        int numRows = valueLabels.length + groupLabels.length + 1;
        outputPanel.setLayout(new GridLayout(numRows, 1));

        outputPanel.add(errorLabel);
        for (int i = 0; i < valueLabels.length; i++) {
            for (int j = 0; j < groupLabels.length; j++) {
                if (groups[j].getStartIndex() == i) {
                    outputPanel.add(groupLabels[j]);
                    break;
                }
            }
            outputPanel.add(valueLabels[i]);
        }
    }

    private void initEvents() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double value = Double.parseDouble(inputText.getText());
                    Unit unit = converters[unitBox.getSelectedIndex()].getUnit();

                    for (ViewListener listener : listeners) {
                        listener.needConvertValue(value, unit);
                    }
                    errorLabel.setText("");
                } catch (NumberFormatException ex) {
                    errorLabel.setText("Введите корректное число!");
                    onErrorValue();
                }
            }
        });
    }

    @Override
    public void startApplication() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initContent();
                initFrame();
                initEvents();
            }
        });
    }

    @Override
    public void onValueConverted(double[] results) {
        for (int i = 0; i < converters.length; i++) {
            valueLabels[i].setText(converters[i].toString() + separator + results[i]);
        }
    }

    private void onErrorValue() {
        for (int i = 0; i < converters.length; i++) {
            valueLabels[i].setText(converters[i].toString() + separator);
        }
    }

    @Override
    public void close() throws Exception {
        frame.setVisible(false);
    }
}
