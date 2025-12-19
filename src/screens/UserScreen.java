package screens;

import components.NavBarPanel;
import components.NavButton;

import javax.swing.*;
import java.awt.*;

public class UserScreen extends JFrame {
    public UserScreen() {
        setTitle("Modern Navigation Bar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 150);
        setLocationRelativeTo(null);

        JPanel navBarPanel = new NavBarPanel();

        JPanel navWrapper = new JPanel(new BorderLayout());
        navWrapper.setOpaque(false);
        navWrapper.add(navBarPanel, BorderLayout.CENTER);

        JLabel companyName = new JLabel("Generate New Invoice for Peter Loves Carl Co.");
        companyName.setFont(new Font("Arial", Font.BOLD, 48));
        companyName.setHorizontalAlignment(SwingConstants.CENTER);
        companyName.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 20, 10, 20),
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(130, 170, 255)) // Bottom border only
        ));
        navWrapper.add(companyName, BorderLayout.SOUTH);


        // Main contents
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel informationInputContainer = getJPanel();

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.WHITE);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        containerPanel.add(navWrapper, BorderLayout.NORTH);
        containerPanel.add(informationInputContainer, BorderLayout.SOUTH);
        add(containerPanel);
        setVisible(true);
    }

    private JPanel getJPanel() {
        JPanel informationInputContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        informationInputContainer.setOpaque(false);

        JPanel nameContainer = new JPanel(new BorderLayout());
        nameContainer.setOpaque(false);
        JLabel nameLabel = new JLabel("Name:    ");
        JTextField nameInput = getInputField();
        nameContainer.add(nameLabel, BorderLayout.WEST);
        nameContainer.add(nameInput, BorderLayout.EAST);

        JPanel contactContainer = new JPanel(new BorderLayout());
        contactContainer.setOpaque(false);
        JLabel contactLabel = new JLabel("Contact No.:    ");
        JTextField contactInput = getInputField();
        contactContainer.add(contactLabel, BorderLayout.WEST);
        contactContainer.add(contactInput, BorderLayout.EAST);

        JPanel addressContainer = new JPanel(new BorderLayout());
        addressContainer.setOpaque(false);
        JLabel addressLabel = new JLabel("Address:    ");
        JTextField addressInput = getInputField();
        addressContainer.add(addressLabel, BorderLayout.WEST);
        addressContainer.add(addressInput, BorderLayout.EAST);

        informationInputContainer.add(nameContainer);
        informationInputContainer.add(contactContainer);
        informationInputContainer.add(addressContainer);
        return informationInputContainer;
    }

    private static JTextField getInputField() {
        JTextField inputField = new JTextField("", 20);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setForeground(Color.GRAY);
//        inputField.setBackground(new Color(200, 220, 255));
//        inputField.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(180, 210, 255), 1),
//                BorderFactory.createEmptyBorder(8, 12, 8, 12)
//        ));

        // Round the search field corners
        inputField.setOpaque(false);
        inputField.setUI(new javax.swing.plaf.basic.BasicTextFieldUI() {
            @Override
            protected void paintBackground(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(inputField.getBackground());
                g2.fillRoundRect(0, 0, inputField.getWidth(), inputField.getHeight(), 40, 40);
                g2.dispose();
            }
        });
        return inputField;
    }

    public static void main(String[] args) {
        new UserScreen();
    }
}
