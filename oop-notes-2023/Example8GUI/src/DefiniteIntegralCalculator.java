import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class DefiniteIntegralCalculator extends JFrame {
	
	private static final int NUM_THREADS = 4; // Number of threads to use
	private JComboBox<String> functionComboBox;
    private JLabel intervalLabel;
    private JTextField lowerBoundField;
    private JTextField upperBoundField;
    private JButton calculateButton;
    private JLabel resultLabel;
	
    public DefiniteIntegralCalculator() {
        setTitle("Integral Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the font to Arial
        Font font = new Font("Arial", Font.PLAIN, 12);
        setFont(font);

        // Main panel with GridLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 1));
        mainPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        // Title label
        JLabel titleLabel = new JLabel("Integral Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel);

        // Function panel
        JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel functionLabel = new JLabel("Function:");
        functionPanel.setBackground(new Color(173, 216, 230)); // Light blue background
        functionComboBox = new JComboBox<>();
        functionComboBox.addItem("f(x) = x^2");
        functionComboBox.addItem("f(x) = sin(x)");
        functionComboBox.addItem("f(x) = cos(x)");
        functionPanel.add(functionLabel);
        functionPanel.add(functionComboBox);
        mainPanel.add(functionPanel);

        // Interval label
        intervalLabel = new JLabel("Interval:");
        intervalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(intervalLabel);

        // Lower bound and upper bound panel
        JPanel boundPanel = new JPanel(new GridLayout(1, 2));
        boundPanel.setBackground(new Color(173, 216, 230));
        lowerBoundField = new JTextField(10);
        upperBoundField = new JTextField(10);
        boundPanel.add(lowerBoundField);
        boundPanel.add(upperBoundField);
        mainPanel.add(boundPanel);

        // Calculate button
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    calculateFiniteIntegral();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainPanel.add(calculateButton);

        // Result label
        resultLabel = new JLabel();
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(resultLabel);

        setContentPane(mainPanel);
        pack(); // Calculate preferred size of the window to generate according to components
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void calculateFiniteIntegral() throws InterruptedException {
        // Get selected function
        String selectedFunction = (String) functionComboBox.getSelectedItem();


        // Check if both lower and upper bounds are missing
        if (lowerBoundField.getText().isEmpty() || upperBoundField.getText().isEmpty()) {
            showErrorDialog("Please enter values for both lower and upper bounds.");
            return;
        }
        
        double lowerBound=0.0;
        double upperBound=0.0;
        
        // Get lower and upper bounds
         try {
	       lowerBound = Double.parseDouble(lowerBoundField.getText());
	       upperBound = Double.parseDouble(upperBoundField.getText());
         }
	     catch (NumberFormatException nfe)
	     {
	    	 showErrorDialog("Please insert two numbers, not other characters!");
	    	 return;
	     }
       
        // Check if lower bound is bigger than upper bound
        if (lowerBound > upperBound) {
            showErrorDialog("Lower bound cannot be greater than upper bound.");
            return;
        }
        
        // Compute finite integral based on selected function and interval
        double result = computeIntegral(selectedFunction, lowerBound, upperBound);

        // Display the result
        resultLabel.setText(String.format("%.2f", result));
    }

    private double computeIntegral(String function, double lowerBound, double upperBound) throws InterruptedException {
        // Define the selected function using the trapezoidal approximation
        Function<Double, Double> selectedFunction;
        switch (function) {
            case "f(x) = x^2":
                selectedFunction = x -> Math.pow(x, 2);
                break;
            case "f(x) = sin(x)":
                selectedFunction = Math::sin;
                break;
            case "f(x) = cos(x)":
                selectedFunction = Math::cos;
                break;
            default:
                throw new IllegalArgumentException("Invalid function selected");
        }
        return computeDefiniteIntegral(selectedFunction, lowerBound, upperBound);
    }
    
    private static double computeIndefiniteIntegral(Function<Double, Double> function, double x) {
        // Compute the indefinite integral F(x) at a given x using numerical approximation
        double epsilon = 0.00001; // Small value for numerical approximation
        double h = 0.00001; // Step size for numerical approximation

        double integral = 0.0;
        double previousValue = 0.0;

        for (double t = 0.0; t < x; t += h) {
            double currentValue = function.apply(t);
            integral += (currentValue + previousValue) * h / 2.0;
            previousValue = currentValue;
        }

        return integral;
    }

    private static double computeDefiniteIntegral(Function<Double, Double> function, double intervalStart, double intervalEnd) throws InterruptedException {
        double partitionSize = (intervalEnd - intervalStart) / NUM_THREADS;

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        AtomicReference<Double>[] partialSums = new AtomicReference[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            partialSums[i] = new AtomicReference<>(0.0);
            int partitionNumber = i;
            executor.submit(() -> {
                double start = intervalStart + partitionNumber * partitionSize;
                double end = start + partitionSize;
                double partitionIntegral = computeIndefiniteIntegral(function, end) - computeIndefiniteIntegral(function, start);
                partialSums[partitionNumber].updateAndGet(sum -> sum + partitionIntegral);
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        double sum = 0.0;
        for (AtomicReference<Double> partialSum : partialSums) {
            sum += partialSum.get();
        }

        return sum;
    }
    
    private void showErrorDialog(String message) {
        
    	JDialog dialog = new JDialog(this, "Error", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(173, 216, 230));
        dialog.setLayout(new BorderLayout());
        

        JLabel errorLabel = new JLabel(message);
        Font font=new Font("Arial", Font.PLAIN, 14);
        errorLabel.setFont(font);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(errorLabel, BorderLayout.CENTER);
        
        // Calculate the preferred size of the dialog based on the length of the message
        FontMetrics fontMetrics = dialog.getFontMetrics(font);
        int messageWidth = fontMetrics.stringWidth(message);
        int messageHeight = fontMetrics.getHeight();
        int dialogWidth = messageWidth + 40; // Add some padding
        int dialogHeight = messageHeight + 80; // Add some padding
        dialog.setSize(dialogWidth, dialogHeight);
        

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(173, 216, 230));
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        
        dialog.setVisible(true);
    }

    
}