package ru.academit.ilnitsky.temperature2.console;

import ru.academit.ilnitsky.temperature2.common.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Конслольное представление View для конвертера температуры и энергии
 * Created by UserLabView on 02.03.17.
 */
public class ConsoleView implements View {
    private ViewListener core;

    private boolean continued;

    private String[] groupNames;
    private String[] valueNames;
    private String[] enUnitNames;
    private String[] ruUnitNames;

    private Unit[] units;
    private int[] groupStartIndex;

    private Pattern pattern;

    private static final String separator = ":   ";

    public ConsoleView(UnitConverter converter) {
        ConvertUnit[] converters = converter.getConverters();
        UnitGroup[] groups = converter.getGroups();

        groupNames = new String[groups.length];
        groupStartIndex = new int[groups.length];
        for (int i = 0; i < groups.length; i++) {
            groupNames[i] = groups[i].getName();
            groupStartIndex[i] = groups[i].getStartIndex();
        }

        StringBuilder sb = new StringBuilder();

        units = new Unit[converters.length];
        valueNames = new String[converters.length];
        enUnitNames = new String[converters.length];
        ruUnitNames = new String[converters.length];

        for (int i = 0; i < converters.length; i++) {
            valueNames[i] = converters[i].toString() + separator;
            enUnitNames[i] = converters[i].getUnit().getEnName();
            ruUnitNames[i] = converters[i].getUnit().getRuName();
            units[i] = converters[i].getUnit();

            sb.append(enUnitNames[i]).append("|").append(ruUnitNames[i]);
            if (i < converters.length - 1) {
                sb.append("|");
            }
        }

        String unitMask = sb.toString();
        pattern = Pattern.compile("^(\\d+[.,]?\\d*)\\s*(" + unitMask + ")$");
    }

    @Override
    public void addViewListener(ViewListener listener) {
        core = listener;
    }

    @Override
    public void removeViewListener(ViewListener listener) {
        if (core == listener) {
            core = null;
        }
    }

    @Override
    public void startApplication() {

        showHelp();

        continued = true;
        while (continued) {
            readCommand();
        }
    }

    private void readCommand() {

        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine().trim();
        String lineLowerCase = line.toLowerCase();

        if (lineLowerCase.equals("exit")) {
            continued = false;

        } else if (lineLowerCase.equals("about")) {
            showAbout();

        } else if (lineLowerCase.equals("help")) {
            showHelp();

        } else if (!line.isEmpty()) {
            Matcher matcher = pattern.matcher(line);

            String strValue;
            String strUnit;

            if (matcher.matches()) {
                strValue = matcher.group(1).replace(',', '.');
                strUnit = matcher.group(2);

                Unit unit = null;

                for (int i = 0; i < enUnitNames.length; i++) {
                    if (strUnit.equals(enUnitNames[i]) || strUnit.equals(ruUnitNames[i])) {
                        unit = units[i];
                        break;
                    }
                }

                if (unit == null) {
                    showMessage("ОШИБКА: Задана неизвестная единица измерения! (strUnit =" + strUnit + ")");
                } else {
                    try {
                        double value = Double.parseDouble(strValue);
                        core.needConvertValue(value, unit);

                    } catch (NumberFormatException e) {
                        showMessage("ОШИБКА: Неправильный формат числа! (strValue =" + strValue + ") :" + e);
                    }
                }
            } else {
                showMessage("ОШИБКА: Неизвестная команда (чтоб посмотреть список команд введите: help)!");
            }
        }
    }

    private void showMessage(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println();
    }

    private void showHelp() {
        System.out.println("******************************* Помощь *******************************************");
        System.out.print("* Доступные единицы:");
        for (int i = 0; i < enUnitNames.length; i++) {
            System.out.print(" " + enUnitNames[i]);
            if (i < enUnitNames.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println();
        System.out.println("* exit - выход, about - о программе, help - помощь (выводится данное сообщение)");
        System.out.println("**********************************************************************************");
    }

    private void showAbout() {
        System.out.println("******************************* О программе **************************************");
        System.out.println("*       Программа для перевода температуры и энергии, М.А.Ильницкий, 2017.");
        System.out.println("**********************************************************************************");
    }

    @Override
    public void onValueConverted(double[] results) {

        if (results.length != valueNames.length) {
            throw new IllegalArgumentException("results.length != valueNames.length");
        }

        for (int i = 0; i < valueNames.length; i++) {
            for (int j = 0; j < groupNames.length; j++) {
                if (groupStartIndex[j] == i) {
                    System.out.println(">>>> " + groupNames[j]);
                    break;
                }
            }
            System.out.println("> " + valueNames[i] + results[i] + " " + enUnitNames[i]);
        }
        System.out.println();
    }

    @Override
    public void close() throws Exception {

    }
}
