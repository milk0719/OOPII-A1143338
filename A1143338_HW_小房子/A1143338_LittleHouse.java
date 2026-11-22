import java.awt.*;
import javax.swing.*;

public class A1143338_LittleHouse extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. 天空與草地：用 fillRect 把畫面上半部塗成天藍色、下半部塗成綠色
        g.setColor(new Color(135, 206, 235)); // 天藍色
        g.fillRect(0, 0, 500, 320);

        g.setColor(new Color(76, 175, 80));   // 草地綠色
        g.fillRect(0, 320, 500, 80);

        // 2. 太陽：用 fillOval 畫黃色正圓，並用 drawLine 畫至少 8 條光芒
        int sunCenterX = 420;
        int sunCenterY = 75;
        int sunRadius = 40;

        g.setColor(Color.YELLOW);
        g.fillOval(sunCenterX - sunRadius, sunCenterY - sunRadius, sunRadius * 2, sunRadius * 2);

        int innerR = 50;
        int outerR = 70;
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45);
            int x1 = (int) (sunCenterX + innerR * Math.cos(angle));
            int y1 = (int) (sunCenterY + innerR * Math.sin(angle));
            int x2 = (int) (sunCenterX + outerR * Math.cos(angle));
            int y2 = (int) (sunCenterY + outerR * Math.sin(angle));
            g.drawLine(x1, y1, x2, y2);
        }

        // 3. 雲朵：用至少 3 個互相重疊的白色 fillOval 組成
        g.setColor(Color.WHITE);
        g.fillOval(60, 55, 80, 50);
        g.fillOval(100, 40, 80, 60);
        g.fillOval(140, 55, 80, 50);

        // 4. 房屋主體：用 fillRect + drawRect
        int houseX = 150;
        int houseY = 190;
        int houseW = 200;
        int houseH = 160;

        g.setColor(new Color(255, 228, 181));
        g.fillRect(houseX, houseY, houseW, houseH);
        g.setColor(Color.BLACK);
        g.drawRect(houseX, houseY, houseW, houseH);

        // 5. 屋頂：只能用 drawLine 畫出三角形（三條線段）
        g.setColor(Color.RED);
        int topX = 250, topY = 95;
        int leftX = 130, leftY = 190;
        int rightX = 370, rightY = 190;

        g.drawLine(topX, topY, leftX, leftY);
        g.drawLine(topX, topY, rightX, rightY);
        g.drawLine(leftX, leftY, rightX, rightY);

        // 6. 門：用 fillRoundRect，門把用小的 fillOval
        g.setColor(new Color(139, 69, 19));
        g.fillRoundRect(225, 265, 50, 85, 20, 20);

        g.setColor(Color.YELLOW);
        g.fillOval(262, 305, 8, 8);

        // 7. 窗戶：至少 2 扇，用 fillRect + drawRect，並用 drawLine 畫十字窗框
        drawWindow(g, 170, 220, 50, 50);
        drawWindow(g, 280, 220, 50, 50);

        // 【新增元素】屋子旁邊的小人（位於屋子右側草地上）
        drawPerson(g, 415, 275);

        // 8. 用 g.drawString("學號 姓名", x, y) 在畫面角落簽名
        g.setColor(Color.WHITE);
        g.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        g.drawString("A1143338 許祐銓", 15, 385);
    }

    // 輔助方法：繪製單扇十字窗戶
    private void drawWindow(Graphics g, int x, int y, int w, int h) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, w, h);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, w, h);
        g.drawLine(x + w / 2, y, x + w / 2, y + h);
        g.drawLine(x, y + h / 2, x + w, y + h / 2);
    }

    // 輔助方法：繪製屋旁小人 (headX, headY 為頭部左上角座標)
    private void drawPerson(Graphics g, int headX, int headY) {
        int headSize = 26;
        int centerX = headX + headSize / 2; // 身體中線 X = 428

        // 頭部（膚色填滿 + 黑色外框）
        g.setColor(new Color(255, 224, 189));
        g.fillOval(headX, headY, headSize, headSize);
        g.setColor(Color.BLACK);
        g.drawOval(headX, headY, headSize, headSize);

        // 眼睛與微笑
        g.fillOval(headX + 7, headY + 8, 3, 3);
        g.fillOval(headX + 16, headY + 8, 3, 3);
        g.drawArc(headX + 7, headY + 12, 12, 8, 180, 180);

        // 身體（用藍色圓角矩形當作衣服）
        g.setColor(new Color(33, 150, 243));
        g.fillRoundRect(centerX - 10, headY + headSize, 20, 32, 8, 8);
        g.setColor(Color.BLACK);
        g.drawRoundRect(centerX - 10, headY + headSize, 20, 32, 8, 8);

        // 雙手（左手自然下垂，右手舉起揮手）
        int shoulderY = headY + headSize + 6;
        g.drawLine(centerX - 10, shoulderY, centerX - 22, shoulderY + 16); // 左手
        g.drawLine(centerX + 10, shoulderY, centerX + 24, shoulderY - 12); // 右手（揮手）

        // 雙腿（站在 y = 355 的草地上，與屋子底部對齊）
        int hipY = headY + headSize + 32;
        g.drawLine(centerX - 5, hipY, centerX - 10, hipY + 22); // 左腳
        g.drawLine(centerX + 5, hipY, centerX + 10, hipY + 22); // 右腳
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("我的小房子");
       A1143338_LittleHouse panel = new A1143338_LittleHouse();
        panel.setPreferredSize(new Dimension(500, 400));

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}