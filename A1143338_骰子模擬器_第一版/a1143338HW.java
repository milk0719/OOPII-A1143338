import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class a1143338HW{

    static JFrame frm = new JFrame("骰子模擬器");
    static JPanel pne = new JPanel();
    static JButton btn = new JButton("擲骰子");

    public static void main(String[] args) {
        pne.setLayout(new BorderLayout(10, 10));
        btn.setFont(new Font("微軟正黑體", Font.PLAIN, 60));
        pne.add(btn, BorderLayout.SOUTH);

        frm.add(pne);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 按鈕點擊事件觸發區
            }
        });

        frm.setSize(400, 320);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
}