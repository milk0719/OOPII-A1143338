import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("登入視窗");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("帳號:"), gbc);

        gbc.gridx = 1;
        JTextField userText = new JTextField(15);
        panel.add(userText, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("密碼:"), gbc);

        gbc.gridx = 1;
        JPasswordField passwordText = new JPasswordField(15);
        panel.add(passwordText, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("登入");
        panel.add(loginButton, gbc);

        
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "請輸入帳號與密碼！", "警告", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "登入成功！\n帳號: " + username, "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.add(panel);
        frame.pack(); 
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }
}