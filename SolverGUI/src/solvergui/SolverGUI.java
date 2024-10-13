package solvergui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa la GUI para la resolución de sistemas de ecuaciones
 * utilizando métodos numéricos.
 */
public class SolverGUI extends JFrame {

    // Definimos los componentes de la interfaz gráfica
    private JTextField[][] matrixFields;
    private JComboBox<String>[][] signComboBoxes; // Para elegir el signo (+ o -)
    private JTextField[] vectorFields;
    private JComboBox<String> methodComboBox;
    private JTextArea resultArea;

    public SolverGUI() {
        // Configuramos la ventana principal
        setTitle("Métodos Numéricos");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para la entrada de datos
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

        matrixFields = new JTextField[3][3];  // Matriz 3x3 para ejemplo
        signComboBoxes = new JComboBox[3][3]; // Para los signos de cada coeficiente
        vectorFields = new JTextField[3];     // Vector de 3 elementos

        // Inicializamos los campos de la matriz A y elige el signo
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                signComboBoxes[i][j] = new JComboBox<>(new String[]{"+", "-"});
                matrixFields[i][j] = new JTextField(5);

                gbc.gridx = j * 2;
                gbc.gridy = i;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                inputPanel.add(signComboBoxes[i][j], gbc);

                gbc.gridx = j * 2 + 1;
                inputPanel.add(matrixFields[i][j], gbc);
            }
            vectorFields[i] = new JTextField(5);

            gbc.gridx = 6;
            gbc.gridy = i;
            inputPanel.add(new JLabel("="), gbc);

            gbc.gridx = 7;
            inputPanel.add(vectorFields[i], gbc);
        }

        // Panel para seleccionar el método
        JPanel methodPanel = new JPanel();
        methodComboBox = new JComboBox<>(new String[]{"Eliminación Gaussiana", "Gauss-Jordan", "Inversión de Matrices"});
        methodPanel.add(new JLabel("Seleccionar Método:"));
        methodPanel.add(methodComboBox);

        // Agrupar inputPanel y methodPanel en un solo panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Layout vertical
        topPanel.add(inputPanel); // Añadir el panel de entrada de datos
        topPanel.add(methodPanel); // Añadir el panel de selección de método

        // Botón para resolver
        JButton solveButton = new JButton("Resolver");
        solveButton.addActionListener(new SolveButtonListener());

        // Área de resultados
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Añadir paneles a la ventana principal
        add(topPanel, BorderLayout.NORTH);   // Panel combinado en la parte superior
        add(solveButton, BorderLayout.SOUTH); // Botón "Resolver" en la parte inferior
        add(scrollPane, BorderLayout.CENTER); // Área de resultados en el centro

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);

        setVisible(true);
    }

    // Acción del botón "Resolver"
    private class SolveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Aquí es donde se conectan los métodos numéricos con la interfaz gráfica.
            String selectedMethod = (String) methodComboBox.getSelectedItem();
            resultArea.setText("Has seleccionado el método: " + selectedMethod + "\n");

            // Leer los datos ingresados por el usuario
            double[][] A = new double[3][3];
            double[] b = new double[3];

            // Leer la matriz A
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    String sign = (String) signComboBoxes[i][j].getSelectedItem();
                    double value = Double.parseDouble(matrixFields[i][j].getText());
                    A[i][j] = sign.equals("+") ? value : -value; // Aplicar el signo
                }
            }

            // Leer el vector b
            for (int i = 0; i < 3; i++) {
                b[i] = Double.parseDouble(vectorFields[i].getText());
            }

            // Resolver el sistema dependiendo del método seleccionado
            if ("Eliminación Gaussiana".equals(selectedMethod)) {
                EliminacionGaussiana gauss = new EliminacionGaussiana();
                double[] resultado = gauss.solve(A, b);

                // Mostrar el resultado
                resultArea.append("Soluciones:\n");
                resultArea.append("x = " + resultado[0] + "\n");
                resultArea.append("y = " + resultado[1] + "\n");
                resultArea.append("z = " + resultado[2] + "\n");
            }
            if ("Gauss-Jordan".equals(selectedMethod)) {
                GaussJordan gaussJordan = new GaussJordan();
                double[] resultado = gaussJordan.solve(A, b);

                // Mostrar el resultado
                resultArea.append("Soluciones:\n");
                resultArea.append("x = " + resultado[0] + "\n");
                resultArea.append("y = " + resultado[1] + "\n");
                resultArea.append("z = " + resultado[2] + "\n");
            }

            if ("Inversión de Matrices".equals(selectedMethod)) {
                InversionDeMatrices inversion = new InversionDeMatrices();
                double[] resultado = inversion.solve(A, b);

                // Mostrar el resultado
                resultArea.append("Soluciones usando Inversión de Matrices:\n");
                resultArea.append("x = " + resultado[0] + "\n");
                resultArea.append("y = " + resultado[1] + "\n");
                resultArea.append("z = " + resultado[2] + "\n");
            }
        }
    }

    public static void main(String[] args) {
        // Iniciar la aplicación
        new SolverGUI();
    }
}
