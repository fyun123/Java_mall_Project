package web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1、创建一对象，在内存中图片（验证码图片对象）
        int width = 100;
        int height = 34;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //2、美化图片
        //2.1填充背景色
        Graphics graphics = bufferedImage.getGraphics();//画笔对象
        graphics.setColor(Color.PINK);//设置画笔颜色
        graphics.fillRect(0, 0, width, height);
        //2.2画边框
        graphics.setColor(Color.blue);
        graphics.drawRect(0, 0, width - 1, height - 1);
        //2.3写验证码
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnoppqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < 5; i++) {
            Random random = new Random();
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            //将生成的验证码，存入session
            stringBuilder.append(c);
            String checkCodeSession = stringBuilder.toString();
            req.getSession().setAttribute("checkCodeSession", checkCodeSession);
            graphics.drawString(c + "", width / 5 * i, height / 2);
        }
        //2.4画干扰线
        for (int j = 0; j < 5; j++){
            int x1 = new Random().nextInt(width);
            int x2 = new Random().nextInt(width);
            int y1 = new Random().nextInt(height);
            int y2 = new Random().nextInt(height);
            graphics.drawLine(x1, y1, x2, y2);
        }
        //3、将图片输出到页面展示
        ImageIO.write(bufferedImage, "jpg", resp.getOutputStream());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}

