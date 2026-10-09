import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class LoginWindow1{
    public static void main(String[] args) {
        // 在 UI 執行緒中建立視窗
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("使用者登入");
            frame.setSize(380, 220);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // 視窗顯示於螢幕中央

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // 帳號標籤與輸入框
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("帳號："), gbc);

            gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
            JTextField userText = new JTextField(15);
            panel.add(userText, gbc);

            // 密碼標籤與輸入框
            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
            panel.add(new JLabel("密碼："), gbc);

            gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
            JPasswordField passwordText = new JPasswordField(15);
            panel.add(passwordText, gbc);

            // 按鈕區域
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            JButton cancelButton = new JButton("取消");
            JButton loginButton = new JButton("登入");

            buttonPanel.add(cancelButton);
            buttonPanel.add(loginButton);

            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            panel.add(buttonPanel, gbc);

            // 事件處理
            loginButton.addActionListener((ActionEvent e) -> {
                String username = userText.getText().trim();
                String password = new String(passwordText.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "請輸入帳號與密碼！", "提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "登入成功，歡迎 " + username + "！", "訊息", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            cancelButton.addActionListener((ActionEvent e) -> {
                userText.setText("");
                passwordText.setText("");
            });

            frame.add(panel);
            frame.setVisible(true);
        });
    }
}