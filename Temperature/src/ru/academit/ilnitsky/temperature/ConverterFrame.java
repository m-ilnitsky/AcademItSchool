package ru.academit.ilnitsky.temperature;

import javax.swing.*;
import java.awt.*;

/**
 * Swing-интерфейс для конвертера температуры
 * Created by Mike on 09.12.2016.
 */
public class ConverterFrame extends JFrame {
    ConverterFrame() {
        super("Конвертер");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(250, 250);

        JPanel inputPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);

        JComboBox type = new JComboBox();
        JTextField text = new JTextField();
        JButton button = new JButton();

        type.setSize(80, 20);
        text.setSize(80, 20);
        button.setSize(80, 20);
        button.setText("Ok");

        inputPanel.setLayout(new GridLayout(1,3));
        inputPanel.add(type);
        inputPanel.add(text);
        inputPanel.add(button);

        JLabel labelGroup1      = new JLabel("Заданные температуры:");
        JLabel labelK           = new JLabel("Температура в К : ");
        JLabel labelC           = new JLabel("Температура в C : ");
        JLabel labelF           = new JLabel("Температура в F : ");
        JLabel labelGroup2      = new JLabel("Энергия для данной температуры:");
        JLabel labelJ           = new JLabel("Энергия     в Дж: ");
        JLabel labelErg         = new JLabel("Энергия    в Эрг: ");
        JLabel labelEV          = new JLabel("Энергия     в эВ: ");
        JLabel labelGroup3      = new JLabel("Длина волны макисмума интенсивности излучения для данной температуры:");
        JLabel labelLambdaTm    = new JLabel("Длина волны макисмума интенсивности,   м ");
        JLabel labelLambdaTum   = new JLabel("Длина волны макисмума интенсивности, мкм ");
        JLabel labelGroup4      = new JLabel("Параметры излучения в вакууме для данной энергии:");
        JLabel labelFrequency   = new JLabel("Частота излучения,   Гц ");
        JLabel labelLambdaEm    = new JLabel("Длина волны излучения,   м ");
        JLabel labelLambdaEum   = new JLabel("Длина волны излучения, мкм ");

        JLabel resultGroup1 = new JLabel("");
        JLabel resultGroup2 = new JLabel("");
        JLabel resultGroup3 = new JLabel("");
        JLabel resultGroup4 = new JLabel("");

        JLabel resultK = new JLabel("01234567890");
        JLabel resultC = new JLabel("01234567890");
        JLabel resultF = new JLabel("01234567890");

        outputPanel.setLayout(new GridLayout(8,2));
        outputPanel.add(labelGroup1);
        outputPanel.add(resultGroup1);
        outputPanel.add(labelK);
        outputPanel.add(resultK);
        outputPanel.add(labelC);
        outputPanel.add(resultC);
        outputPanel.add(labelF);
        outputPanel.add(resultF);

        outputPanel.add(labelGroup2);
        outputPanel.add(resultGroup2);
    }

    public static void main(String... arg) {
        JFrame converterWindow = new ConverterFrame();
        converterWindow.setVisible(true);

    }
}
