package com.stonedt.intelligence.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

public class QRCodeUtil {

    private final static int BLACK = 0xFF000000;
    private final static int WHITE = 0xFFFFFFFF;

    /**
     * 生成二维码
     *
     * @param url
     * @param size
     * @param ops
     * @throws WriterException
     * @throws IOException
     */
    public static void encodeQR(String url, int size, OutputStream ops) throws WriterException, IOException {
        BufferedImage qrImage = encodeQr(url, size);
        ImageIO.write(qrImage, "png", ops);
    }

    /**
     * 生成中间有logo的二维码
     */
    public static void encodeQR(String url, int size, OutputStream ops, InputStream logo) throws WriterException, IOException {
        BufferedImage qrImage = encodeQr(url, size);
        // 插入logo
        insertLogo(qrImage, logo);
        ImageIO.write(qrImage, "png", ops);
    }

    /**
     * 插入logo
     */
    private static void insertLogo(BufferedImage qrImage, InputStream logo) throws IOException {
        BufferedImage logoImage = ImageIO.read(logo);
        int logoWidth = logoImage.getWidth();
        int logoHeight = logoImage.getHeight();
        // 插入logo
        Graphics2D graphics = qrImage.createGraphics();
        graphics.drawImage(logoImage, qrImage.getWidth() / 2 - logoWidth / 2, qrImage.getHeight() / 2 - logoHeight / 2, logoWidth, logoHeight, null);
        graphics.dispose();
        logoImage.flush();
    }


    private static BufferedImage encodeQr(String content, int size) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        // 设置纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 设置白边
        hints.put(EncodeHintType.MARGIN, 0);

        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
        // 生成二维码
        BufferedImage qrImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                qrImage.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }

        return qrImage;
    }

}

