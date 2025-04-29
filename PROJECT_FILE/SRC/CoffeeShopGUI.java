import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

class Coffee {
    String name;
    double basePrice;

    Coffee(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public double getPriceBySize(String size) {
        switch (size) {
            case "Small":
                return basePrice;
            case "Medium":
                return basePrice + 50; // Adding ₹50 for Medium
            case "Large":
                return basePrice + 100; // Adding ₹100 for Large
            default:
                return basePrice;
        }
    }
}

public class CoffeeShopGUI extends JFrame {
    private JTextArea orderSummary;
    private double totalBill;
    private HashMap<String, Double> orders; // To track individual orders
    private JCheckBox whippedCreamCheckBox;
    private JComboBox<String> sizeComboBox;

    private final Coffee[] coffees = {
            new Coffee("Caffè Americano", 250),
            new Coffee("Caramel Macchiato", 375),
            new Coffee("Caffè Latte", 300),
            new Coffee("Cappuccino", 280),
            new Coffee("Espresso", 200),
            new Coffee("Flat White", 320),
            new Coffee("Cold Brew", 350),
            new Coffee("Vanilla Latte", 350),
            new Coffee("Mocha", 300),
            new Coffee("Pumpkin Spice Latte", 450),
            new Coffee("Nitro Cold Brew", 450),
            new Coffee("Iced White Chocolate Mocha", 425)
    };

    public CoffeeShopGUI() {
        orders = new HashMap<>();

        setTitle("Starbucks Coffee Shop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Set background color to a warm coffee-themed color
        Color panelColor = new Color(222, 184, 135);
        getContentPane().setBackground(panelColor);

        // Add café name label at the top with a stylish look
        JLabel cafeNameLabel = new JLabel("STARBUCKS COFFEE", SwingConstants.CENTER);
        cafeNameLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 36)); // Increased font size
        cafeNameLabel.setForeground(new Color(139, 69, 19));
        add(cafeNameLabel, BorderLayout.NORTH);

        // Coffee selection panel
        JPanel coffeePanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 columns
        coffeePanel.setBackground(panelColor);

        for (Coffee coffee : coffees) {
            createCoffeeComponent(coffeePanel, coffee);
        }

        // Additional components for extras
        whippedCreamCheckBox = new JCheckBox("Add Whipped Cream (₹50)");
        whippedCreamCheckBox.setBackground(panelColor);
        whippedCreamCheckBox.setFont(new Font("Serif", Font.PLAIN, 18)); // Increased font size
        coffeePanel.add(whippedCreamCheckBox);

        // Combo box for selecting coffee size
        String[] sizes = {"Small", "Medium", "Large"};
        sizeComboBox = new JComboBox<>(sizes);
        sizeComboBox.setBackground(Color.WHITE);
        coffeePanel.add(new JLabel("Select Size:"));
        coffeePanel.add(sizeComboBox);

        add(coffeePanel, BorderLayout.CENTER); // Add coffee panel to the center

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton clearButton = new JButton("Clear Order");
        clearButton.setBackground(new Color(139, 69, 19));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Serif", Font.PLAIN, 18)); // Increased font size
        clearButton.addActionListener(e -> clearOrder());
        buttonPanel.add(clearButton);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBackground(new Color(139, 69, 19));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setFont(new Font("Serif", Font.PLAIN, 18)); // Increased font size
        checkoutButton.addActionListener(e -> showFinalBill());
        buttonPanel.add(checkoutButton);

        add(buttonPanel, BorderLayout.SOUTH); // Add button panel at the bottom

        // Order summary area
        orderSummary = new JTextArea(10, 30);
        orderSummary.setEditable(false);
        orderSummary.setFont(new Font("Serif", Font.PLAIN, 18)); // Increased font size
        JScrollPane scrollPane = new JScrollPane(orderSummary);
        add(scrollPane, BorderLayout.EAST); // Add order summary to the right

        pack();
        setVisible(true);
    }

    private void createCoffeeComponent(JPanel panel, Coffee coffee) {
        JButton orderButton = new JButton(coffee.name + " (Base ₹" + coffee.basePrice + ")");
        orderButton.setBackground(new Color(139, 69, 19));
        orderButton.setForeground(Color.WHITE);
        orderButton.setFont(new Font("Serif", Font.PLAIN, 18)); // Increased font size
        orderButton.setToolTipText("Order " + coffee.name);
        orderButton.addActionListener(e -> {
            String size = (String) sizeComboBox.getSelectedItem();
            double coffeePrice = coffee.getPriceBySize(size);
            totalBill += coffeePrice;

            // Add to orders
            String orderKey = coffee.name + " (" + size + ")";
            orders.put(orderKey, coffeePrice);

            if (whippedCreamCheckBox.isSelected()) {
                totalBill += 50; // Add extra for whipped cream
            }

            updateOrderSummary();
        });
        panel.add(orderButton);
    }

    private void updateOrderSummary() {
        orderSummary.setText(""); // Clear the summary
        StringBuilder summary = new StringBuilder();
        for (String order : orders.keySet()) {
            summary.append(order).append(" = ₹").append(orders.get(order)).append("\n");
        }
        if (whippedCreamCheckBox.isSelected()) {
            summary.append("\nExtras: Whipped Cream - ₹50\n");
        }
        summary.append("\nTotal Bill: ₹").append(totalBill);
        orderSummary.setText(summary.toString());
    }

    private void clearOrder() {
        orderSummary.setText("");
        totalBill = 0.0;
        orders.clear(); // Clear the orders
        whippedCreamCheckBox.setSelected(false);
        sizeComboBox.setSelectedIndex(0);
    }

    private void showFinalBill() {
        StringBuilder finalBill = new StringBuilder("Your final bill:\n");
        double finalTotal = totalBill; // Already calculated total

        for (String order : orders.keySet()) {
            finalBill.append(order).append(" = ₹").append(orders.get(order)).append("\n");
        }

        if (whippedCreamCheckBox.isSelected()) {
            finalBill.append("Extras: Whipped Cream - ₹50\n");
            finalTotal += 50; // Add to total for whipped cream
        }

        finalBill.append("\nTotal Amount Due: ₹").append(finalTotal);
        JOptionPane.showMessageDialog(this, finalBill.toString(), "Final Bill", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new CoffeeShopGUI();
    }
}