package solvergui;

/**
 * Clase que implementa el método de Inversión de Matrices
 * utilizando el método de Gauss-Jordan para encontrar la inversa.
 */
public class InversionDeMatrices implements MetodoNumerico {

    @Override
    public double[] solve(double[][] A, double[] b) {
        int n = A.length;
        double[][] augmentedMatrix = new double[n][n * 2];

        // Crear la matriz aumentada A | I (donde I es la identidad)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = A[i][j]; // Matriz A
            }
            for (int j = n; j < 2 * n; j++) {
                augmentedMatrix[i][j] = (i == j - n) ? 1 : 0; // Matriz identidad
            }
        }

        // Aplicar Gauss-Jordan para convertir A en la identidad
        for (int p = 0; p < n; p++) {
            // Seleccionar el pivote
            double pivot = augmentedMatrix[p][p];
            if (pivot == 0) {
                throw new ArithmeticException("La matriz no es invertible.");
            }

            // Dividir toda la fila p por el valor del pivote para hacer el pivote 1
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[p][j] /= pivot;
            }

            // Hacer ceros en las otras filas
            for (int i = 0; i < n; i++) {
                if (i != p) {
                    double alpha = augmentedMatrix[i][p];
                    for (int j = 0; j < 2 * n; j++) {
                        augmentedMatrix[i][j] -= alpha * augmentedMatrix[p][j];
                    }
                }
            }
        }

        // La inversa de A ahora está en la segunda mitad de augmentedMatrix
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = augmentedMatrix[i][j + n];
            }
        }

        // Multiplicar la inversa por el vector b para obtener la solución
        double[] solution = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                solution[i] += inverse[i][j] * b[j];
            }
        }

        return solution;
    }
}
