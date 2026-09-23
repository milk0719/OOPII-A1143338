import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class a1143338HW1 {

    static JFrame frm = new JFrame("骰子模擬器");
    static JPanel pne = new JPanel();
    static JButton btn = new JButton("擲骰子"); 
    static JLabel lab = new JLabel("點擊按鈕開始擲骰子",SwingConstants.CENTER); 
    static int count = 0;
    static int total=0;
    static double avg=0.0;

    public static void main(String[] args) {
        pne.setLayout(new BorderLayout(10, 10));

        
        lab.setFont(new Font("微軟正黑體", Font.BOLD, 60));
        btn.setFont(new Font("微軟正黑體", Font.PLAIN, 60));

        
        pne.add(lab, BorderLayout.CENTER);
        pne.add(btn, BorderLayout.SOUTH);

       
        frm.add(pne);

        
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                count++;
                Random rand = new Random();
                int point = rand.nextInt(6) + 1;
                total+=point;
                avg = (double) total / count;

                if (point == 6) {
                    lab.setForeground(Color.RED); 
                } else if (point == 1) {
                    lab.setForeground(Color.GREEN);
                } else {
                    lab.setForeground(Color.BLACK);
                }
                lab.setText("<html><center>" +
                            "骰子點數：" + point + "<br>" +
                            "投擲次數：" + count + "<br>" +
                            "累計總分：" + total + "<br>" +
                            "平均點數：" + avg+
                            "</center></html>");
            }
        });



        frm.setSize(400, 320);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
}
