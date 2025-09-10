package inventoryapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private final ProductTableModel tableModel;
    private final JTable productTable;
    private final JTextField codeField;
    private final JTextField nameField;
    private final JTextField qtyField;
    private final JTextField priceField;

    public Main() {
        tableModel = new ProductTableModel();
        productTable = new JTable(tableModel);

        setTitle("Inventory App (Memory Only)");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        codeField = new JTextField();
        nameField = new JTextField();
        qtyField = new JTextField();
        priceField = new JTextField();

        formPanel.add(new JLabel("Kode:"));
        formPanel.add(codeField);
        formPanel.add(new JLabel("Nama:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Qty:"));
        formPanel.add(qtyField);
        formPanel.add(new JLabel("Harga:"));
        formPanel.add(priceField);

        JButton addButton = new JButton("Tambah");
        JPanel addBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addBtnPanel.add(addButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(addBtnPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Produk"));
        add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Hapus Produk Terpilih");
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedProduct();
            }
        });

        setLocationRelativeTo(null);
    }

    private void addProduct() {
        try {
            String code = codeField.getText();
            String name = nameField.getText();
            int qty = Integer.parseInt(qtyField.getText());
            double price = Double.parseDouble(priceField.getText());

            if (code.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kode dan Nama tidak boleh kosong.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ProductModel product = new ProductModel(code, name, qty, price);
            tableModel.addProduct(product);

            codeField.setText("");
            nameField.setText("");
            qtyField.setText("");
            priceField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Qty harus berupa angka dan Harga harus berupa angka desimal.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = productTable.convertRowIndexToModel(selectedRow);
            tableModel.removeProduct(modelRow);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih produk yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
