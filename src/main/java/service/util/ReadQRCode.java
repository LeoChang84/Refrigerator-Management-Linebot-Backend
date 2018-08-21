package service.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.EncodeHintType;

public class ReadQRCode {

 	static private Logger logger = LoggerFactory.getLogger(ReadQRCode.class.getName());

	public ReadQRCode() { }

	public void scanQRcode(String path) throws IOException, NotFoundException {
 		System.out.println("----------Try to scan QR code--------------");
		File QRfile = new File(path);
		BufferedImage bufferedImg = ImageIO.read(QRfile);
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImg);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
 		System.out.println("----------Scan QR code OK--------------");
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
		Result result = new MultiFormatReader().decode(bitmap, hints);
		logger.info("Get result");
		if (null == result) {
			logger.info("-----------Scan QR code null--------------");
		} else {
			System.out.println("Barcode Format: " + result.getBarcodeFormat());
			System.out.println("Content: " + result.getText());
		}
	}
}