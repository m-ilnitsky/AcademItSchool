package ru.academit.ilnitsky.temperature;

import javax.swing.*;
import java.awt.*;

/**
 * Swing-интерфейс для конвертера температуры
 * Created by Mike on 09.12.2016.
 */
public class ConverterFrame extends JFrame {
    ConverterFrame() {
        super("Преобразование температур");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(250, 250);

        JPanel inputPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.SOUTH);

        JComboBox type = new JComboBox();
        JTextField text = new JTextField();
        JButton button = new JButton();

        type.setSize(80, 20);
        text.setSize(80, 20);
        button.setSize(80, 20);
        button.setText("Ok");

        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(type);
        inputPanel.add(text);
        inputPanel.add(button);

        JLabel labelK = new JLabel("Температура в К : ");
        JLabel labelC = new JLabel("Температура в C : ");
        JLabel labelF = new JLabel("Температура в F : ");

        JLabel resultK = new JLabel("0");
        JLabel resultC = new JLabel("0");
        JLabel resultF = new JLabel("0");

        outputPanel.setLayout(new GridLayout(3,2));
        outputPanel.add(labelK);
        outputPanel.add(resultK);
        outputPanel.add(labelC);
        outputPanel.add(resultC);
        outputPanel.add(labelF);
        outputPanel.add(resultF);
    }

    public static void main(String... arg) {
        JFrame converterWindow = new ConverterFrame();
        converterWindow.setVisible(true);

    }
}
