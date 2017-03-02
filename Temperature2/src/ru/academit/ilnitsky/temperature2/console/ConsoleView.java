package ru.academit.ilnitsky.temperature2.console;

import ru.academit.ilnitsky.temperature2.common.*;

import java.util.regex.Pattern;

/**
 * Created by UserLabView on 02.03.17.
 */
public class ConsoleView implements View {
    private ViewListener core;

    private String[] groupNames;
    private String[] valueNames;
    private String[] enUnitNames;
    private String[] ruUnitNames;

    private double[] values;

    private String unitMask;
    private Pattern pattern;

    private static final String separator = ":   ";

    public ConsoleView(UnitConverter converter) {
        ConvertUnit[] converters = converter.getConverters();
        UnitGroup[] groups = converter.getGroups();

        groupNames = new String[groups.length];
        for (int i = 0; i < groups.length; i++) {
            groupNames[i] = groups[i].getName();
        }

        StringBuilder sb = new StringBuilder();

        values = new double[converters.length];
        valueNames = new String[converters.length];
        enUnitNames = new String[converters.length];
        ruUnitNames = new String[converters.length];

        for (int i = 0; i < converters.length; i++) {
            valueNames[i] = converters[i].toString() + separator;
            enUnitNames[i] = converters[i].getUnit().getEnName();
            ruUnitNames[i] = converters[i].getUnit().getRuName();

            sb.append(enUnitNames[i]).append("|").append(ruUnitNames[i]);
            if (i < converters.length - 1) {
                sb.append("|");
            }
        }

        unitMask = sb.toString();
        pattern = Pattern.compile("^((\\d+)(" + unitMask + "))|([Ee][Xx][Ii][Tt])$");
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

    }

    @Override
    public void onValueConverted(double[] results) {

    }

    @Override
    public void close() throws Exception {

    }
}
