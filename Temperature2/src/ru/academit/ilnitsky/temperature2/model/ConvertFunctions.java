package ru.academit.ilnitsky.temperature2.model;

/**
 * Библиотека функций преобразования температуры
 * Created by Mike on 27.01.2017.
 */
public class ConvertFunctions {
    private ConvertFunctions() {
    }

    public static final double zeroC = 273.15;          // [K]
    public static final double e = 1.6021766208e-19;    // [C]
    public static final double k = 1.38064852e-23;      // [J/K]
    public static final double h = 6.62607004e-34;      // [J*s]
    public static final double c = 299792458;           // [m/s]
    public static final double lambdaTm = 0.0028999;    // [m*K]
    public static final double lambdaTum = 2899.9;      // [um*K]

    public static double to_K_from_C(double c) {
        return c + zeroC;
    }

    public static double to_C_from_K(double k) {
        return k - zeroC;
    }

    public static double to_C_from_F(double f) {
        return (f - 32) * 5.0 / 9.0;
    }

    public static double to_F_from_C(double c) {
        return c * 9.0 / 5.0 + 32;
    }

    public static double to_K_from_F(double f) {
        return to_K_from_C(to_C_from_F(f));
    }

    public static double to_F_from_K(double k) {
        return to_F_from_C(to_C_from_K(k));
    }

    public static double to_K_from_J(double energy) {
        return energy / k;
    }

    public static double to_J_from_K(double t) {
        return t * k;
    }

    public static double to_K_from_eV(double energy) {
        return to_K_from_J(energy * e);
    }

    public static double to_eV_from_K(double t) {
        return to_J_from_K(t) / e;
    }

    public static double to_K_from_Erg(double energy) {
        return to_K_from_J(energy * 1e-7);
    }

    public static double to_Erg_from_K(double t) {
        return to_J_from_K(t) / 1e-7;
    }

    public static double to_K_from_LambdaTm(double lambda) {
        return lambdaTm / lambda;
    }

    public static double to_LambdaTm_from_K(double t) {
        return lambdaTm / t;
    }

    public static double to_K_from_LambdaTum(double lambda) {
        return lambdaTum / lambda;
    }

    public static double to_LambdaTum_from_K(double t) {
        return lambdaTum / t;
    }

    public static double to_K_from_FrequencyEHz(double hz) {
        return to_K_from_J(hz * h);
    }

    public static double to_FrequencyEHz_from_K(double t) {
        return to_J_from_K(t) / h;
    }

    public static double to_K_from_LambdaEm(double lambda) {
        return to_K_from_J(h * c / lambda);
    }

    public static double to_LambdaEm_from_K(double t) {
        return h * c / to_J_from_K(t);
    }

    public static double to_K_from_LambdaEum(double lambda) {
        return to_K_from_LambdaEm(lambda / 1e6);
    }

    public static double to_LambdaEum_from_K(double t) {
        return to_LambdaEm_from_K(t) * 1e6;
    }
}
