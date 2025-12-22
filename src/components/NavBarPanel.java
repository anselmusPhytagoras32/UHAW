package components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.function.Consumer;
import main.MainActivity;

public class NavBarPanel extends JPanel {
    private String activeScreen;
    private Consumer<String> searchListener; // Listener for search text

    public NavBarPanel(String activeScreen) {
        this.activeScreen = activeScreen;
        setLayout(new BorderLayout());
        setBackground(new Color(130, 170, 255));
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel leftPanel = getJPanel();

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);

        JButton mainMenuButton = new NavButton("Main Menu");
        mainMenuButton.addActionListener(e -> {
            if (MainActivity.getInstance() != null) {
                MainActivity.getInstance().showScreen(MainActivity.MAIN_MENU_SCREEN);
            }
        });

        JTextField searchField = getSearchField();

        rightPanel.add(mainMenuButton);
        rightPanel.add(searchField);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Sets a listener that triggers whenever the search text changes.
     */
    public void setSearchListener(Consumer<String> listener) {
        this.searchListener = listener;
    }

    private JTextField getSearchField() {
        String placeholder = "Search";
        JTextField searchField = new JTextField(20);
        searchField.setText(placeholder);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setForeground(Color.GRAY);
        searchField.setBackground(new Color(200, 220, 255));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 210, 255), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText(placeholder);
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        // Add DocumentListener to detect typing
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { onChange(); }
            public void removeUpdate(DocumentEvent e) { onChange(); }
            public void changedUpdate(DocumentEvent e) { onChange(); }

            private void onChange() {
                if (searchListener != null) {
                    // Ignore placeholder text
                    if (searchField.getForeground() == Color.GRAY) {
                        searchListener.accept("");
                    } else {
                        searchListener.accept(searchField.getText());
                    }
                }
            }
        });

        searchField.setOpaque(false);
        searchField.setUI(new javax.swing.plaf.basic.BasicTextFieldUI() {
            @Override
            protected void paintBackground(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(searchField.getBackground());
                g2.fillRoundRect(0, 0, searchField.getWidth(), searchField.getHeight(), 20, 20);
                g2.dispose();
            }
        });
        return searchField;
    }

    private JPanel getJPanel() {
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        leftPanel.setOpaque(false);

        JLabel userLabel = new JLabel(activeScreen);
        userLabel.setFont(new Font("Arial", Font.BOLD, 28));
        userLabel.setForeground(Color.BLACK);

        JButton homeButton = new NavButton("Home");
        homeButton.addActionListener(e -> {
            if (MainActivity.getInstance() != null) {
                MainActivity.getInstance().showScreen(MainActivity.USER_SCREEN);
            }
        });

        JButton purchaseHistoryButton = new NavButton("Purchase History");
        purchaseHistoryButton.addActionListener(e -> {
            if (MainActivity.getInstance() != null) {
                MainActivity.getInstance().showScreen(MainActivity.INVOICE_SCREEN);
            }
        });

        leftPanel.add(userLabel);
        leftPanel.add(homeButton);
        leftPanel.add(purchaseHistoryButton);

        return leftPanel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.dispose();
    }

    // Inner class for styled navigation buttons
    private static class NavButton extends JButton {
        public NavButton(String text) {
            super(text);
            setFont(new Font("Arial", Font.PLAIN, 16));
            setForeground(Color.BLACK); // Set text to BLACK as requested
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    setForeground(Color.WHITE); // Hover effect
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    setForeground(Color.BLACK); // Back to Black
                }
            });
        }
    }
}