import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.*;

public class A1143338_HW3 extends JFrame {

    // 介面元件
    private JComboBox<String> typeBox;       // NORTH：換算類型
    private JTextField inputField;           // CENTER 第一列：輸入數值
    private JComboBox<String> fromUnitBox;   // CENTER 第一列：來源單位
    private JTextField outputField;          // CENTER 第二列：輸出結果
    private JComboBox<String> toUnitBox;     // CENTER 第二列：目標單位
    private JButton convertBtn;              // SOUTH：換算按鈕

    // 各類型的單位選項陣列
    private final String[] lengthUnits = {"公尺", "公分", "英吋", "英尺"};
    private final String[] weightUnits = {"公斤", "公克", "磅", "盎司"};
    private final String[] tempUnits   = {"攝氏", "華氏", "克氏"};

    public  A1143338_HW3() {
        setTitle("單位換算器");
        setSize(480, 280);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font uiFont = new Font("微軟正黑體", Font.PLAIN, 15);

        // 1. NORTH：JComboBox 選換算類型（長度 / 重量 / 溫度）
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        JLabel typeLabel = new JLabel("換算類型：");
        typeLabel.setFont(uiFont);
        typeBox = new JComboBox<>(new String[]{"長度", "重量", "溫度"});
        typeBox.setFont(uiFont);
        typeBox.setPreferredSize(new Dimension(150, 30));
        northPanel.add(typeLabel);
        northPanel.add(typeBox);
        add(northPanel, BorderLayout.NORTH);

        // 2. CENTER：兩列，每列一個 JTextField + 一個 JComboBox（來源單位、目標單位）
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));

        // 第一列（來源）
        JPanel row1 = new JPanel(new BorderLayout(10, 0));
        inputField = new JTextField();
        inputField.setFont(uiFont);
        fromUnitBox = new JComboBox<>(lengthUnits);
        fromUnitBox.setFont(uiFont);
        fromUnitBox.setPreferredSize(new Dimension(120, 32));
        row1.add(inputField, BorderLayout.CENTER);
        row1.add(fromUnitBox, BorderLayout.EAST);

        // 第二列（目標）
        JPanel row2 = new JPanel(new BorderLayout(10, 0));
        outputField = new JTextField();
        outputField.setFont(uiFont);
        outputField.setEditable(false);
        outputField.setBackground(Color.WHITE);
        toUnitBox = new JComboBox<>(lengthUnits);
        toUnitBox.setFont(uiFont);
        toUnitBox.setPreferredSize(new Dimension(120, 32));
        toUnitBox.setSelectedIndex(1); // 預設選第二個單位（公分）
        row2.add(outputField, BorderLayout.CENTER);
        row2.add(toUnitBox, BorderLayout.EAST);

        centerPanel.add(row1);
        centerPanel.add(row2);
        add(centerPanel, BorderLayout.CENTER);

        // 3. SOUTH：「換算」按鈕，結果顯示在第二個 JTextField
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        convertBtn = new JButton("換算");
        convertBtn.setFont(uiFont);
        convertBtn.setPreferredSize(new Dimension(120, 34));
        southPanel.add(convertBtn);
        add(southPanel, BorderLayout.SOUTH);

        // 事件 1：切換換算類型時，更新兩個單位選單並清空舊結果
        typeBox.addActionListener(e -> updateUnitBoxes());

        // 事件 2（修正 BUG 5）：切換來源或目標單位時，同步清空舊的換算結果
        fromUnitBox.addActionListener(e -> outputField.setText(""));
        toUnitBox.addActionListener(e -> outputField.setText(""));

        // 事件 3：點擊「換算」按鈕或於輸入框按 Enter 執行計算
        ActionListener doConvert = e -> calculateResult();
        convertBtn.addActionListener(doConvert);
        inputField.addActionListener(doConvert);
    }

    // 更新來源與目標單位的下拉選單
    private void updateUnitBoxes() {
        String selectedType = (String) typeBox.getSelectedItem();
        String[] units;

        if ("重量".equals(selectedType)) {
            units = weightUnits;
        } else if ("溫度".equals(selectedType)) {
            units = tempUnits;
        } else {
            units = lengthUnits;
        }

        fromUnitBox.setModel(new DefaultComboBoxModel<>(units));
        toUnitBox.setModel(new DefaultComboBoxModel<>(units));
        if (units.length > 1) {
            toUnitBox.setSelectedIndex(1);
        }
        outputField.setText("");
    }

    // 執行單位換算與防呆驗證
    private void calculateResult() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請先輸入要換算的數值！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double value = Double.parseDouble(text);

            // 修正 BUG 3：阻擋 NaN 與 Infinity 繞過格式檢查
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                JOptionPane.showMessageDialog(this, "請輸入有限的實際數值！", "格式錯誤", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String type = (String) typeBox.getSelectedItem();
            String from = (String) fromUnitBox.getSelectedItem();
            String to = (String) toUnitBox.getSelectedItem();

            // 修正 BUG 4：檢查物理下限（負數長度/重量、低於絕對零度）
            if (!checkPhysicalBounds(type, from, value)) {
                return;
            }

            // 修正 BUG 1：來源與目標單位相同時直接回傳原值，避免浮點數誤差
            double result;
            if (from.equals(to)) {
                result = value;
            } else if ("長度".equals(type)) {
                result = convertLength(value, from, to);
            } else if ("重量".equals(type)) {
                result = convertWeight(value, from, to);
            } else {
                result = convertTemperature(value, from, to);
            }

            // 防止極大數值運算後溢位成 Infinity
            if (Double.isNaN(result) || Double.isInfinite(result)) {
                JOptionPane.showMessageDialog(this, "計算結果超出數值上限！", "溢位錯誤", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 修正 BUG 2：消除 -0.0，並使用 BigDecimal 格式化輸出（保留最多 6 位小數且不使用科學記號）
            if (Math.abs(result) < 1e-12) {
                result = 0.0;
            }
            String formatted = BigDecimal.valueOf(result)
                    .setScale(6, RoundingMode.HALF_UP)
                    .stripTrailingZeros()
                    .toPlainString();

            outputField.setText(formatted);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "請輸入有效的數字！", "格式錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 檢查輸入數值是否符合基本物理限制
    private boolean checkPhysicalBounds(String type, String fromUnit, double value) {
        if (("長度".equals(type) || "重量".equals(type)) && value < 0) {
            JOptionPane.showMessageDialog(this, type + "不能為負數！", "數值不合理", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if ("溫度".equals(type)) {
            if ("攝氏".equals(fromUnit) && value < -273.15) {
                JOptionPane.showMessageDialog(this, "攝氏溫度不能低於絕對零度（-273.15°C）！", "數值不合理", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if ("華氏".equals(fromUnit) && value < -459.67) {
                JOptionPane.showMessageDialog(this, "華氏溫度不能低於絕對零度（-459.67°F）！", "數值不合理", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if ("克氏".equals(fromUnit) && value < 0) {
                JOptionPane.showMessageDialog(this, "克氏溫度（K）不能為負數！", "數值不合理", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }

    // 長度換算（以「公尺」為基準單位）
    private double convertLength(double val, String from, String to) {
        double meters;
        switch (from) {
            case "公分": meters = val * 0.01; break;
            case "英吋": meters = val * 0.0254; break;
            case "英尺": meters = val * 0.3048; break;
            default:     meters = val; // 公尺
        }
        switch (to) {
            case "公分": return meters / 0.01;
            case "英吋": return meters / 0.0254;
            case "英尺": return meters / 0.3048;
            default:     return meters;
        }
    }

    // 重量換算（以「公斤」為基準單位）
    private double convertWeight(double val, String from, String to) {
        double kg;
        switch (from) {
            case "公克": kg = val * 0.001; break;
            case "磅":   kg = val * 0.45359237; break;
            case "盎司": kg = val * 0.0283495231; break;
            default:     kg = val; // 公斤
        }
        switch (to) {
            case "公克": return kg / 0.001;
            case "磅":   return kg / 0.45359237;
            case "盎司": return kg / 0.0283495231;
            default:     return kg;
        }
    }

    // 溫度換算（以「攝氏」為基準單位）
    private double convertTemperature(double val, String from, String to) {
        double celsius;
        switch (from) {
            case "華氏": celsius = (val - 32) * 5.0 / 9.0; break;
            case "克氏": celsius = val - 273.15; break;
            default:     celsius = val; // 攝氏
        }
        switch (to) {
            case "華氏": return celsius * 9.0 / 5.0 + 32;
            case "克氏": return celsius + 273.15;
            default:     return celsius;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new  A1143338_HW3().setVisible(true);
        });
    }
}