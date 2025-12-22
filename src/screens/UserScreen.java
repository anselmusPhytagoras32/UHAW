package screens;

import components.NavBarPanel;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.MainActivity;

public class UserScreen extends JPanel {

    private Map<String, Double> itemValueMap;
    private Map<String, String> itemCategoryMap;
    private Map<String, Integer> itemQuantityMap;
    private Set<String> selectedCategories;
    private JPanel itemsPanel;
    private JLabel overallTotalLabel;
    private JLabel totalItemsLabel;
    private java.util.List<JSpinner> quantitySpinners;
    private Map<JSpinner, String> spinnerToItemMap;

    private JTextField nameInput;
    private JTextField contactInput;
    private JTextField addressInput;

    public UserScreen() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 1. Initialize Data Structures
        itemValueMap = new HashMap<>();
        itemCategoryMap = new HashMap<>();
        itemQuantityMap = new HashMap<>();
        selectedCategories = new HashSet<>();
        quantitySpinners = new java.util.ArrayList<>();
        spinnerToItemMap = new HashMap<>();

        // 2. Load Data
        loadInventoryData();
        selectedCategories.addAll(Arrays.asList("1", "2", "3"));

        // 3. Main container with padding
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 4. Navigation Bar
        JPanel navBarPanel = new NavBarPanel("USER");

        // 5. Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        JLabel titleLabel = new JLabel("Create Purchase");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // 6. Main Content Panels
        JPanel informationInputContainer = getCustomerInfoPanel();
        JPanel invoiceTablePanel = getInvoiceTablePanel();
        JPanel bottomPanel = getBottomPanel();

        // 7. Content panel assembly
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setOpaque(false);
        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(informationInputContainer, BorderLayout.BEFORE_FIRST_LINE);
        contentPanel.add(invoiceTablePanel, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 8. Assemble main container
        mainContainer.add(navBarPanel, BorderLayout.NORTH);
        mainContainer.add(contentPanel, BorderLayout.CENTER);

        add(mainContainer);
    }

    public void refreshData() {
        loadInventoryData();
        refreshTableRows();
    }

    private void loadInventoryData() {
        try {
            File file = null;
            String[] possiblePaths = { "src/items/inventory.json", "items/inventory.json", "../items/inventory.json" };
            for (String path : possiblePaths) {
                File testFile = new File(path);
                if (testFile.exists()) { file = testFile; break; }
            }
            if (file != null && file.exists()) {
                try (Scanner scanner = new Scanner(file)) {
                    StringBuilder content = new StringBuilder();
                    while (scanner.hasNextLine()) content.append(scanner.nextLine());
                    parseInventoryJson(content.toString());
                }
            } else {
                loadSampleData();
            }
        } catch (Exception e) {
            loadSampleData();
        }
    }

    private void loadSampleData() {
        itemValueMap.put("Product A", 150.00); itemCategoryMap.put("Product A", "1"); itemQuantityMap.put("Product A", 10);
    }

    private void parseInventoryJson(String jsonContent) {
        try {
            String jsonStr = jsonContent.replaceAll("\\s+", " ");
            int arrayStart = jsonStr.indexOf('[');
            int arrayEnd = jsonStr.lastIndexOf(']');
            if (arrayStart != -1 && arrayEnd != -1) {
                jsonStr = jsonStr.substring(arrayStart + 1, arrayEnd);
                String[] items = jsonStr.split("\\},\\s*\\{");
                for (String item : items) {
                    item = item.trim().replace("{", "").replace("}", "");
                    String itemName = extractJsonValue(item, "itemName");
                    String valueStr = extractJsonValue(item, "value");
                    String category = extractJsonValue(item, "category");
                    String qtyStr = extractJsonValue(item, "quantity");

                    if (itemName != null && valueStr != null && category != null) {
                        try {
                            double value = Double.parseDouble(valueStr.replaceAll(",.*", "").trim());
                            int qty = (qtyStr != null) ? Integer.parseInt(qtyStr.trim()) : 0;
                            itemValueMap.put(itemName, value);
                            itemCategoryMap.put(itemName, category);
                            itemQuantityMap.put(itemName, qty);
                        } catch (Exception e) {}
                    }
                }
            }
        } catch (Exception e) {}
    }

    private String extractJsonValue(String jsonStr, String key) {
        String searchStr = "\"" + key + "\"";
        int keyIndex = jsonStr.indexOf(searchStr);
        if (keyIndex == -1) return null;
        int colonIndex = jsonStr.indexOf(":", keyIndex);
        if (colonIndex == -1) return null;
        int valueStart = colonIndex + 1;
        while (valueStart < jsonStr.length() && Character.isWhitespace(jsonStr.charAt(valueStart))) valueStart++;
        if (valueStart >= jsonStr.length()) return null;
        if (jsonStr.charAt(valueStart) == '"') {
            int valueEnd = jsonStr.indexOf('"', valueStart + 1);
            return (valueEnd != -1) ? jsonStr.substring(valueStart + 1, valueEnd) : null;
        } else {
            int valueEnd = valueStart;
            while (valueEnd < jsonStr.length() && jsonStr.charAt(valueEnd) != ',' && jsonStr.charAt(valueEnd) != '}') valueEnd++;
            return jsonStr.substring(valueStart, valueEnd).trim();
        }
    }

    private JPanel getCustomerInfoPanel() {
        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        container.setOpaque(false);

        JPanel p1 = new JPanel(new BorderLayout(5, 0)); p1.setOpaque(false);
        p1.add(new JLabel("Name:"), BorderLayout.WEST); nameInput = getInputField(); p1.add(nameInput, BorderLayout.CENTER);

        JPanel p2 = new JPanel(new BorderLayout(5, 0)); p2.setOpaque(false);
        p2.add(new JLabel("Contact No.:"), BorderLayout.WEST); contactInput = getInputField(); p2.add(contactInput, BorderLayout.CENTER);

        JPanel p3 = new JPanel(new BorderLayout(5, 0)); p3.setOpaque(false);
        p3.add(new JLabel("Address:"), BorderLayout.WEST); addressInput = getInputField(); p3.add(addressInput, BorderLayout.CENTER);

        container.add(p1); container.add(p2); container.add(p3);
        return container;
    }

    private JPanel getInvoiceTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel categoryPanel = createCategoryTogglePanel();

        JPanel headerPanel = new JPanel(new GridLayout(1, 4, 5, 0));
        headerPanel.setBackground(new Color(200, 200, 200));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] headers = {"Item Name", "Qty", "Value", "Total"};
        for (String header : headers) {
            JLabel headerLabel = new JLabel(header, SwingConstants.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            headerPanel.add(headerLabel);
        }

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setOpaque(false);
        refreshTableRows();

        JPanel scrollContent = new JPanel(new BorderLayout());
        scrollContent.setOpaque(false);
        scrollContent.add(itemsPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 350));

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.add(headerPanel, BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);

        tablePanel.add(categoryPanel, BorderLayout.NORTH);
        tablePanel.add(content, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createCategoryTogglePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setOpaque(false);
        panel.add(new JLabel("Categories:"));
        for (String cat : Arrays.asList("1", "2", "3")) {
            JToggleButton btn = new JToggleButton(cat);
            btn.setSelected(true);
            btn.setPreferredSize(new Dimension(50, 30));
            btn.setBackground(new Color(130, 170, 255));
            btn.setForeground(Color.WHITE);
            btn.addActionListener(e -> {
                if (btn.isSelected()) {
                    selectedCategories.add(cat);
                    btn.setBackground(new Color(130, 170, 255));
                    btn.setForeground(Color.WHITE);
                } else {
                    selectedCategories.remove(cat);
                    btn.setBackground(new Color(200, 200, 200));
                    btn.setForeground(Color.BLACK);
                }
                refreshTableRows();
            });
            panel.add(btn);
        }
        return panel;
    }

    private void refreshTableRows() {
        itemsPanel.removeAll();
        quantitySpinners.clear();
        spinnerToItemMap.clear();
        java.util.List<String> sorted = new ArrayList<>(itemValueMap.keySet());
        Collections.sort(sorted);

        for (String item : sorted) {
            if (selectedCategories.contains(itemCategoryMap.get(item))) {
                itemsPanel.add(createItemRow(item));
            }
        }
        itemsPanel.revalidate();
        itemsPanel.repaint();
        updateOverallTotals();
    }

    private JPanel createItemRow(String itemName) {
        JPanel row = new JPanel(new GridLayout(1, 4, 5, 0));
        row.setOpaque(false);
        row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        double val = itemValueMap.get(itemName);
        int qtyAvailable = itemQuantityMap.getOrDefault(itemName, 0);

        JLabel nameLbl = getStyledLabel(itemName); nameLbl.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel totalLbl = getStyledLabel("0.00");
        JLabel valLbl = getStyledLabel(String.format("%.2f", val));

        JComponent qtyComp;
        if (qtyAvailable > 0) {
            JSpinner s = new JSpinner(new SpinnerNumberModel(0, 0, qtyAvailable, 1));
            s.setFont(new Font("Arial", Font.PLAIN, 13));
            quantitySpinners.add(s);
            spinnerToItemMap.put(s, itemName);
            s.addChangeListener(e -> {
                updateTotalFromLabel(s, valLbl, totalLbl);
                updateOverallTotals();
            });
            qtyComp = s;
        } else {
            JLabel out = new JLabel("Out of Stock");
            out.setForeground(Color.RED);
            out.setFont(new Font("Arial", Font.BOLD, 12));
            out.setHorizontalAlignment(SwingConstants.CENTER);
            qtyComp = out;
            totalLbl.setText("-");
        }

        row.add(nameLbl); row.add(qtyComp); row.add(valLbl); row.add(totalLbl);
        return row;
    }

    private void updateTotalFromLabel(JSpinner s, JLabel vLbl, JLabel tLbl) {
        try {
            int q = (Integer) s.getValue();
            double v = Double.parseDouble(vLbl.getText());
            tLbl.setText(String.format("%.2f", q * v));
        } catch (Exception e) { tLbl.setText("0.00"); }
    }

    private JPanel getBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel totals = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        totals.setOpaque(false);
        totals.add(new JLabel("Overall Total:"));
        overallTotalLabel = getStyledLabel("0.00");
        totals.add(overallTotalLabel);
        totals.add(new JLabel("Total Items:"));
        totalItemsLabel = getStyledLabel("0");
        totals.add(totalItemsLabel);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttons.setOpaque(false);

        // REMOVED PURCHASE HISTORY BUTTON FROM HERE

        JButton checkoutButton = createActionButton("Checkout", new Color(34, 139, 34));
        checkoutButton.addActionListener(e -> generateInvoice());

        buttons.add(checkoutButton);

        bottomPanel.add(totals, BorderLayout.WEST);
        bottomPanel.add(buttons, BorderLayout.EAST);
        return bottomPanel;
    }

    private void updateOverallTotals() {
        if (overallTotalLabel == null) return;
        double total = 0; int count = 0;
        for (JSpinner s : quantitySpinners) {
            int q = (Integer) s.getValue();
            if (q > 0) {
                total += q * itemValueMap.get(spinnerToItemMap.get(s));
                count += q;
            }
        }
        overallTotalLabel.setText(String.format("%.2f", total));
        totalItemsLabel.setText(String.valueOf(count));
    }

    private JButton createActionButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private static JTextField getInputField() {
        JTextField f = new JTextField("", 15);
        f.setFont(new Font("Arial", Font.PLAIN, 14));
        f.setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(15, new Color(200, 200, 200)), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return f;
    }

    private static JLabel getStyledLabel(String text) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.PLAIN, 13));
        l.setBackground(new Color(245, 245, 245));
        l.setOpaque(true);
        l.setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(12, new Color(180, 180, 180)), BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        l.setPreferredSize(new Dimension(100, 30));
        return l;
    }

    private void generateInvoice() {
        String name = nameInput.getText().trim();
        String contact = contactInput.getText().trim();
        String addr = addressInput.getText().trim();

        if (name.isEmpty() || contact.isEmpty() || addr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all info.", "Missing Info", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.util.List<InvoiceItem> items = new ArrayList<>();
        Map<String, Integer> decrements = new HashMap<>();
        double total = 0;

        for (JSpinner s : quantitySpinners) {
            int q = (Integer) s.getValue();
            if (q > 0) {
                String item = spinnerToItemMap.get(s);
                double p = itemValueMap.get(item);
                items.add(new InvoiceItem(item, q, p, q * p));
                decrements.put(item, q);
                total += q * p;
            }
        }

        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select at least one item.", "Empty Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (updateInventoryFile(decrements)) {
            writeInvoiceToFile(name, contact, addr, items, total);
            JOptionPane.showMessageDialog(this, "Invoice Generated!", "Success", JOptionPane.INFORMATION_MESSAGE);
            nameInput.setText(""); contactInput.setText(""); addressInput.setText("");

            if (MainActivity.getInstance() != null) {
                MainActivity.getInstance().refreshAllScreens();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Inventory Error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean updateInventoryFile(Map<String, Integer> decrements) {
        try {
            File f = new File("src/items/inventory.json");
            if (!f.exists()) f = new File("items/inventory.json");
            if (!f.exists()) return false;

            java.util.List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String l; while ((l = br.readLine()) != null) lines.add(l);
            }

            for (Map.Entry<String, Integer> e : decrements.entrySet()) {
                String target = e.getKey(); int sub = e.getValue();
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).contains("\"itemName\": \"" + target + "\"")) {
                        for (int j = i; j < lines.size(); j++) {
                            if (lines.get(j).contains("\"quantity\":")) {
                                String qLine = lines.get(j);
                                int cur = Integer.parseInt(qLine.split(":")[1].trim().replace(",", ""));
                                String comma = qLine.trim().endsWith(",") ? "," : "";
                                lines.set(j, "    \"quantity\": " + Math.max(0, cur - sub) + comma);
                                break;
                            }
                            if (lines.get(j).contains("}")) break;
                        }
                        break;
                    }
                }
            }
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) { for (String l : lines) pw.println(l); }
            return true;
        } catch (Exception ex) { return false; }
    }

    private void writeInvoiceToFile(String n, String c, String a, java.util.List<InvoiceItem> items, double t) {
        try {
            String inv = "INV" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            File d = new File("invoices"); if (!d.exists()) d.mkdir();
            File f = new File(d, inv + ".txt");
            try (PrintWriter w = new PrintWriter(new FileWriter(f))) {
                w.println("=".repeat(80));
                w.println("\n  " + inv + "\n");
                w.println("  Due date" + " ".repeat(36) + "Subject");
                String date = new SimpleDateFormat("dd MMMM yyyy").format(new Date());
                w.println("  " + date + " ".repeat(30 - date.length()) + "Service for " + date);
                w.println("\n  Billed to" + " ".repeat(37) + "Currency");
                w.println("  " + n + " ".repeat(Math.max(1, 30 - n.length())) + "PHP - Philippine Pesos");
                w.println("  " + c);
                w.println("  " + a);
                w.println("\n  " + "-".repeat(76) + "\n");
                w.println("  DESCRIPTION" + " ".repeat(30) + "QTY" + " ".repeat(8) + "UNIT PRICE" + " ".repeat(8) + "AMOUNT\n");
                for (InvoiceItem i : items) {
                    w.printf("  %-40s %3d %,15.3f PHP %,15.3f PHP%n", (i.description.length()>35?i.description.substring(0,32)+"...":i.description), i.qty, i.unitPrice, i.amount);
                }
                w.println("\n" + " ".repeat(55) + "Amount due" + String.format("%,16.3f PHP", t));
                w.println("\n" + "=".repeat(80));
            }
        } catch (Exception e) {}
    }

    private static class InvoiceItem { String description; int qty; double unitPrice, amount; InvoiceItem(String d, int q, double p, double a){this.description=d;this.qty=q;this.unitPrice=p;this.amount=a;}}
    static class RoundedBorder extends javax.swing.border.AbstractBorder {
        int r; Color c; RoundedBorder(int r, Color c){this.r=r;this.c=c;}
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h){
            Graphics2D g2=(Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.c); g2.drawRoundRect(x,y,w-1,h-1,r,r); g2.dispose();
        }
        public Insets getBorderInsets(Component c){return new Insets(2,2,2,2);}
        public Insets getBorderInsets(Component c, Insets i){i.left=i.right=i.bottom=i.top=2;return i;}
    }
}