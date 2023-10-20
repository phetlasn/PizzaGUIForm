import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PizzaGUIFrame extends JFrame {
    JPanel mainPanel;
    JPanel buttonPanel;
    JPanel comboPanel;

    JRadioButton thin;
    JRadioButton regular;
    JRadioButton deepDish;

    JComboBox<String> comboBox;
    JCheckBox pepperoni;
    JCheckBox cheese;
    JCheckBox sausage;
    JCheckBox olives;
    JCheckBox mushroom;
    JCheckBox pineapple;

    JPanel topPanel;
    JPanel receiptPanel;
    JPanel controlPanel;
    JPanel northPanel;

    JTextArea receipt;
    JScrollPane scroller;
    JButton orderButton;
    JButton clearButton;
    JButton quitButton;

    boolean orderPlaced = false;
    public PizzaGUIFrame() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        createComboPanel();
        northPanel.add(comboPanel, BorderLayout.WEST);

        createCrustPanel();
        northPanel.add(buttonPanel, BorderLayout.CENTER);

        createTopPanel();
        northPanel.add(topPanel, BorderLayout.EAST);

        createReceiptPanel();
        mainPanel.add(receiptPanel, BorderLayout.CENTER);

        createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        mainPanel.add(northPanel, BorderLayout.NORTH);

        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Souriya's Order Form");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(3*(screenWidth / 4), 3*(screenHeight / 4));
        setLocationRelativeTo(null);
    }

    private void createCrustPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Crust (Choose your crust)"));

        thin = new JRadioButton("Thin");
        regular = new JRadioButton("Regular");
        deepDish = new JRadioButton("Deep Dish");

        buttonPanel.add(thin);
        buttonPanel.add(regular);
        buttonPanel.add(deepDish);
        regular.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(thin);
        group.add(regular);
        group.add(deepDish);

    }

    private void createComboPanel() {
        comboPanel = new JPanel();
        comboPanel.setLayout(new GridLayout());
        comboPanel.setBorder(new TitledBorder(new EtchedBorder(), "Size (Choose the size of your pizza)"));
        comboBox = new JComboBox<>();
        comboBox.addItem("Small ($8.00)");
        comboBox.addItem("Medium ($12.00)");
        comboBox.addItem("Large ($16.00)");
        comboBox.addItem("Super ($20.00)");
        comboPanel.add(comboBox);
    }

    private void createReceiptPanel() {
        receiptPanel = new JPanel();
        receiptPanel.setLayout(new BorderLayout());
        receiptPanel.setBorder(new TitledBorder(new EtchedBorder(), "Order Details"));

        receipt = new JTextArea(20, 30);
        scroller = new JScrollPane(receipt);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        receipt.setEditable(false);
        receiptPanel.add(scroller);
    }

    private void createTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 4));
        topPanel.setBorder(new TitledBorder(new EtchedBorder(), "Toppings ($1 per Topping"));

        pepperoni = new JCheckBox("Pepperoni");
        cheese = new JCheckBox("Cheese");
        sausage = new JCheckBox("Sausage");
        olives = new JCheckBox("Olives");
        mushroom = new JCheckBox("Mushrooms");
        pineapple = new JCheckBox("Pineapples");

        topPanel.add(pepperoni);
        topPanel.add(cheese);
        topPanel.add(sausage);
        topPanel.add(olives);
        topPanel.add(mushroom);
        topPanel.add(pineapple);
    }

    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 3));
        orderButton = new JButton("PLACE ORDER");
        orderButton.addActionListener((ActionEvent ae) -> placeOrder());

        clearButton = new JButton("CLEAR");
        clearButton.addActionListener((ActionEvent ae) -> {
            pepperoni.setSelected(false);
            cheese.setSelected(false);
            sausage.setSelected(false);
            olives.setSelected(false);
            mushroom.setSelected(false);
            pineapple.setSelected(false);
            receipt.setText(null);
            comboBox.setSelectedIndex(0);
            regular.setSelected(true);
            orderPlaced = false;
        });

        quitButton = new JButton("QUIT");
        quitButton.addActionListener((ActionEvent ae) -> {
            int choice = JOptionPane.showConfirmDialog(quitButton, "You sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);}});

        controlPanel.add(orderButton);
        controlPanel.add(clearButton);
        controlPanel.add(quitButton);
            }

            private double subTotal;
    public void placeOrder() {
        if (!orderPlaced)
        {
            subTotal = 0;
            addDividers();
            checkCrustAndSize();
            checkToppings();
            receipt.append("\n\n\n");
            calculatePrice();
            addDividers();
            orderPlaced = true;
        }
    }

    public void addDividers () {
        receipt.append("================================================================================ \n");
    }
    public void calculatePrice () {
        double tax = subTotal * .07;
        double totalPrice = subTotal + tax;

        receipt.append("Sub Total:      $" + String.format("%.2f", subTotal) + "\n");
        receipt.append("Tax:                 $" + String.format("%.2f", tax) + "\n");
        receipt.append("-------------------------------------------------------------------------------------------------------------------------------------------- \n");
        receipt.append("Total Price:    $" + String.format("%.2f", totalPrice) + "\n");
    }

    public void checkToppings() {
        ArrayList<JCheckBox> toppingButtons = new ArrayList<>(Arrays.asList(pepperoni, cheese, sausage, olives, mushroom, pineapple));
        ArrayList<String> selectedToppings = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (toppingButtons.get(i).isSelected()) {
                selectedToppings.add(toppingButtons.get(i).getText());
            }

        }
        receipt.append("Toppings:  ");
        for (int l = 0 ; l < selectedToppings.size() ; l++) {
            subTotal += 1.0;
            if (l < selectedToppings.size() - 1)
                receipt.append(selectedToppings.get(l) + ", ");
            else
                receipt.append(selectedToppings.get(l));
        }
        receipt.append("    $" + selectedToppings.size() + ".00 \n");
    }
    public void checkCrustAndSize () {
        String chosenCrust = "";
        if (thin.isSelected()) {
            chosenCrust = "Thin";
        }

        if (regular.isSelected()) {
            chosenCrust = "Regular";
        }

        if (deepDish.isSelected()) {
            chosenCrust = "Deep-Dish";
        }

        String chosenSize = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
        if (Objects.equals(chosenSize, "Small ($8.00)")) {
            receipt.append("Size and Crust Type :   Small, " + chosenCrust + "     $8.00 \n");
            subTotal += 8.0;
        }

        if (Objects.equals(chosenSize, "Medium ($12.00)")) {
            receipt.append("Size and Crust Type :   Medium, " + chosenCrust + "     $12.00 \n");
            subTotal += 12.0;
        }

        if (Objects.equals(chosenSize, "Large ($16.00)")) {
            receipt.append("Size and Crust Type :   Large, " + chosenCrust + "     $16.00 \n");
            subTotal += 16.0;
        }

        if (Objects.equals(chosenSize, "Super ($20.00)")) {
            receipt.append("Size and Crust Type :   Super, " + chosenCrust + "     $20.00 \n");
            subTotal += 20.0;
        }
    }
}
