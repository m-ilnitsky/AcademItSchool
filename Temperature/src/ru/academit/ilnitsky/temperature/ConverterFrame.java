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
    private double number = 0;
    private boolean isNumber = false;
    private EnergyConverter energyConverter = new EnergyConverter();

    private final String separator = ":   ";

    ConverterFrame() {
        super("Конвертер");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(540, 400);
        setMinimumSize(getSize());

        JPanel inputPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);

        String[] item = new String[11];
        item[0] = "Температура, К";
        item[1] = "Температура, C";
        item[2] = "Температура, F";
        item[3] = "Энергия,    Дж";
        item[4] = "Энергия,   Эрг";
        item[5] = "Энергия,    эВ";
        item[6] = "Длина волны макисмума интенсивности,   м";
        item[7] = "Длина волны макисмума интенсивности, мкм";
        item[8] = "Частота излучения,      Гц";
        item[9] = "Длина волны излучения,   м";
        item[10] = "Длина волны излучения, мкм";

        String[] itemOut = new String[item.length];
        for (int i = 0; i < item.length; i++) {
            itemOut[i] = item[i] + separator;
        }

        JComboBox type = new JComboBox(item);
        JTextField text = new JTextField();
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

        JLabel[] groupLabel = new JLabel[4];
        JLabel[] valueLabel = new JLabel[11];

        Font groupFont = new Font("TimesRoman", Font.BOLD, 14);
        Font labelFont = new Font("Courier New", Font.PLAIN, 14);

        for (int i = 0; i < valueLabel.length; i++) {
            valueLabel[i] = new JLabel(itemOut[i]);
            valueLabel[i].setFont(labelFont);
        }

        groupLabel[0] = new JLabel("Заданные температуры");
        groupLabel[1] = new JLabel("Энергия для данной температуры");
        groupLabel[2] = new JLabel("Длина волны макисмума интенсивности излучения для данной температуры");
        groupLabel[3] = new JLabel("Параметры излучения в вакууме для данной энергии фотона");

        outputPanel.setLayout(new GridLayout(valueLabel.length + groupLabel.length, 1));
        outputPanel.add(groupLabel[0]);
        outputPanel.add(valueLabel[0]);
        outputPanel.add(valueLabel[1]);
        outputPanel.add(valueLabel[2]);

        outputPanel.add(groupLabel[1]);
        outputPanel.add(valueLabel[3]);
        outputPanel.add(valueLabel[4]);
        outputPanel.add(valueLabel[5]);

        outputPanel.add(groupLabel[2]);
        outputPanel.add(valueLabel[6]);
        outputPanel.add(valueLabel[7]);

        outputPanel.add(groupLabel[3]);
        outputPanel.add(valueLabel[8]);
        outputPanel.add(valueLabel[9]);
        outputPanel.add(valueLabel[10]);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                    valueLabel[0].setText(itemOut[0] + energyConverter.getK());
                    valueLabel[1].setText(itemOut[1] + energyConverter.getC());
                    valueLabel[2].setText(itemOut[2] + energyConverter.getF());

                    valueLabel[3].setText(itemOut[3] + energyConverter.getEnergyJ());
                    valueLabel[4].setText(itemOut[4] + energyConverter.getEnergyErg());
                    valueLabel[5].setText(itemOut[5] + energyConverter.getEnergyEV());

                    valueLabel[6].setText(itemOut[6] + energyConverter.getLambdaTm());
                    valueLabel[7].setText(itemOut[7] + energyConverter.getLambdaTum());

                    valueLabel[8].setText(itemOut[8] + energyConverter.getFrequency());
                    valueLabel[9].setText(itemOut[9] + energyConverter.getLambdaEm());
                    valueLabel[10].setText(itemOut[10] + energyConverter.getLambdaEum());
                } else {
                    for (int i = 0; i < valueLabel.length; i++) {
                        valueLabel[i].setText(itemOut[i]);
                    }
                }
            }
        });
    }

    public static void main(String... arg) {
        JFrame converterWindow = new ConverterFrame();
        converterWindow.setVisible(true);
    }
}
