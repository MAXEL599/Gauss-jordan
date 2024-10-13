package solvergui;

/**
 * Interfaz que define el método numérico para resolver sistemas de ecuaciones lineales.
 */
interface MetodoNumerico {
    double[] solve(double[][] A, double[] b);
}

