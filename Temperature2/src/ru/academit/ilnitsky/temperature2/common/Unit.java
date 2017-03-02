package ru.academit.ilnitsky.temperature2.common;

/**
 * Используемые единицы измерения
 * Created by UserLabView on 27.01.17.
 */
public enum Unit {
    K("K", "К"), C("C", "С"), F("F", ""),
    J("J", "Дж"), ERG("erg", "эрг"), EV("eV", "эВ"),
    WAVELENGTH_E_M("m", "м"), WAVELENGTH_E_UM("um", "мкм"), FREQUENCY_E_HZ("Hz", "Гц"),
    WAVELENGTH_T_M("m", "м"), WAVELENGTH_T_UM("um", "мкм"), FREQUENCY_T_HZ("Hz", "Гц");

    private final String enName;
    private final String ruName;

    Unit(String enName, String ruName) {
        this.enName = enName;
        this.ruName = ruName;
    }

    public String getEnName() {
        return enName;
    }

    public String getRuName() {
        return ruName;
    }
}
