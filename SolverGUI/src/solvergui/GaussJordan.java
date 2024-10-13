/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solvergui;

/**
 * Clase que implementa el método Gauss-Jordan
 * para resolver sistemas de ecuaciones lineales.
 */
public class GaussJordan implements MetodoNumerico {

    @Override
    public double[] solve(double[][] A, double[] b) {
        int n = b.length;

        // Aplicar Gauss-Jordan
        for (int p = 0; p < n; p++) {
            // Escoger pivote
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }

            // Intercambiar filas en A y en b
            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;

            double t = b[p];
            b[p] = b[max];
            b[max] = t;

            // Normalizar pivote (hacer A[p][p] = 1)
            double pivot = A[p][p];
            for (int j = 0; j < n; j++) {
                A[p][j] /= pivot;
            }
            b[p] /= pivot;

            // Hacer ceros en las demás filas
            for (int i = 0; i < n; i++) {
                if (i != p) {
                    double alpha = A[i][p];
                    for (int j = 0; j < n; j++) {
                        A[i][j] -= alpha * A[p][j];
                    }
                    b[i] -= alpha * b[p];
                }
            }
        }
        return b; // Las soluciones están en el vector b
    }
}
