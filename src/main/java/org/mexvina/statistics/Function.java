package org.mexvina.statistics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * 存放一些用到的小功能
 */
public class Function {
	
	/**
	 * 获取n个长度的随机字符字符串
	 * 传入:要获取的字符串的长度
	 */
	public static String getRandomString(int length){
		String char62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	     Random random=new Random();
	     StringBuffer sb=new StringBuffer();
	     for(int i=0;i<length;i++){
	       int number=random.nextInt(62);
	       sb.append(char62.charAt(number));
	     }
	     return sb.toString();
	 }
	
	/**
	 * url转二维码
	 * 传入 url 
	 * 传出 二维码数组
	 */
	public static byte[] createQrCode(String url) {
		Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix;
        try {
			bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
        BufferedImage image = toBufferedImage(bitMatrix);
        byte[] img = null;
		try {
			img = getImageByte(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}
	private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        final int BLACK = new Color(0,0,0).getRGB();
        final int WHITE = new Color(255,255,255).getRGB();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
	private static byte[] getImageByte(BufferedImage bufferedImage) throws Exception {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ImageIO.write(bufferedImage, "png", out);
	    return out.toByteArray();
	}
}
