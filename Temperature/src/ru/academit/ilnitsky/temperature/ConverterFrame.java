package ru.academit.ilnitsky.temperature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Swing-интерфейс для конвертера температуры
 * Created by Mike on 09.12.2016.
 */
public class ConverterFrame extends JFrame {
    private EnergyConverter energyConverter = new EnergyConverter();

    private String[] outItems;
    private JLabel[] valueLabels;
    private JComboBox type;
    private JTextField text;

    private static final String separator = ":   ";

    private ConverterFrame() {
        super("Конвертер температуры");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(580, 400);
        setMinimumSize(getSize());

        JPanel inputPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);

        String[] items = new String[]{
                "Температура, К",
                "Температура, C",
                "Температура, F",
                "Энергия,    Дж",
                "Энергия,   Эрг",
                "Энергия,    эВ",
                "Длина волны макисмума интенсивности,   м",
                "Длина волны макисмума интенсивности, мкм",
                "Частота излучения,      Гц",
                "Длина волны излучения,   м",
                "Длина волны излучения, мкм"
        };

        outItems = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            outItems[i] = items[i] + separator;
        }

        type = new JComboBox(items);
        text = new JTextField();

        JButton button = new JButton();

        type.setMaximumRowCount(11);
        type.setSize(80, 20);
        text.setSize(80, 20);
        button.setSize(80, 20);
        button.setText("Ok");

        inputPanel.setLayout(new GridLayout(1, 3));
        inputPanel.add(type);
        inputPanel.add(text);
        inputPanel.add(button);

        valueLabels = new JLabel[items.length];

        Font groupFont = new Font("TimesRoman", Font.BOLD, 12);
        Font labelFont = new Font("Courier New", Font.PLAIN, 14);

        for (int i = 0; i < valueLabels.length; i++) {
            valueLabels[i] = new JLabel(outItems[i]);
            valueLabels[i].setFont(labelFont);
        }

        JLabel[] groupLabels = new JLabel[]{
                new JLabel("Заданные температуры"),
                new JLabel("Энергия для данной температуры"),
                new JLabel("Длина волны макисмума интенсивности излучения для данной температуры"),
                new JLabel("Параметры излучения в вакууме для данной энергии фотона")
        };

        for (JLabel gl : groupLabels) {
            gl.setFont(groupFont);
        }

        outputPanel.setLayout(new GridLayout(valueLabels.length + groupLabels.length, 1));
        outputPanel.add(groupLabels[0]);
        outputPanel.add(valueLabels[0]);
        outputPanel.add(valueLabels[1]);
        outputPanel.add(valueLabels[2]);

        outputPanel.add(groupLabels[1]);
        outputPanel.add(valueLabels[3]);
        outputPanel.add(valueLabels[4]);
        outputPanel.add(valueLabels[5]);

        outputPanel.add(groupLabels[2]);
        outputPanel.add(valueLabels[6]);
        outputPanel.add(valueLabels[7]);

        outputPanel.add(groupLabels[3]);
        outputPanel.add(valueLabels[8]);
        outputPanel.add(valueLabels[9]);
        outputPanel.add(valueLabels[10]);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convert();
            }
        });
    }

    private void convert() {
        double number = 0;
        boolean isNumber;

        try {
            number = Double.parseDouble(text.getText());
            isNumber = true;
        } catch (Exception exc) {
            isNumber = false;
        }

        if (isNumber) {
            switch (type.getSelectedIndex()) {
                case 0:
                    energyConverter.setK(number);
                    break;
                case 1:
                    energyConverter.setC(number);
                    break;
                case 2:
                    energyConverter.setF(number);
                    break;
                case 3:
                    energyConverter.setEnergyJ(number);
                    break;
                case 4:
                    energyConverter.setEnergyErg(number);
                    break;
                case 5:
                    energyConverter.setEnergyEV(number);
                    break;
                case 6:
                    energyConverter.setLambdaTm(number);
                    break;
                case 7:
                    energyConverter.setLambdaTum(number);
                    break;
                case 8:
                    energyConverter.setFrequency(number);
                    break;
                case 9:
                    energyConverter.setLambdaEm(number);
                    break;
                case 10:
                    energyConverter.setLambdaEum(number);
                    break;
                default:
                    return;
            }

            valueLabels[0].setText(outItems[0] + energyConverter.getK());
            valueLabels[1].setText(outItems[1] + energyConverter.getC());
            valueLabels[2].setText(outItems[2] + energyConverter.getF());

            valueLabels[3].setText(outItems[3] + energyConverter.getEnergyJ());
            valueLabels[4].setText(outItems[4] + energyConverter.getEnergyErg());
            valueLabels[5].setText(outItems[5] + energyConverter.getEnergyEV());

            valueLabels[6].setText(outItems[6] + energyConverter.getLambdaTm());
            valueLabels[7].setText(outItems[7] + energyConverter.getLambdaTum());

            valueLabels[8].setText(outItems[8] + energyConverter.getFrequency());
            valueLabels[9].setText(outItems[9] + energyConverter.getLambdaEm());
            valueLabels[10].setText(outItems[10] + energyConverter.getLambdaEum());
        } else {
            for (int i = 0; i < valueLabels.length; i++) {
                valueLabels[i].setText(outItems[i]);
            }
        }
    }

    public static void main(String... arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame converterWindow = new ConverterFrame();
                converterWindow.setVisible(true);
            }
        });
    }
}
