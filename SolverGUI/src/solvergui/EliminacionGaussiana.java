/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solvergui;

/**
 * Clase que implementa el método de Eliminación Gaussiana
 * para resolver sistemas de ecuaciones lineales.
 * Implementa la interfaz MetodoNumerico.
 */
public class EliminacionGaussiana implements MetodoNumerico {

    @Override
    public double[] solve(double[][] A, double[] b) {
        int n = b.length;

        // Convertimos el sistema a una forma triangular superior
        for (int p = 0; p < n; p++) {
            // Encontrar el mayor valor en la columna p
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

            // Hacer ceros debajo de A[p][p]
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // Resolución por sustitución hacia atrás
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }
}
