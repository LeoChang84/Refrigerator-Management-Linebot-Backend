package service.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.nio.charset.Charset;
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
import com.google.zxing.DecodeHintType;


public class ReadQRCode {

 	static private Logger logger = LoggerFactory.getLogger(ReadQRCode.class.getName());

	public ReadQRCode() { }

	public String scanQRcode(String path) throws IOException, NotFoundException {
 		System.out.println("----------Try to scan QR code--------------");
		File QRfile = new File(path);
		BufferedImage bufferedImg = ImageIO.read(QRfile);
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImg);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
 		System.out.println("----------Scan QR code OK--------------");
		Map<DecodeHintType, Object> hints = new LinkedHashMap<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET,"utf-8");
		hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
		Result result = new MultiFormatReader().decode(bitmap, hints);
		logger.info("Get result");
		String reply = "";
		if (null == result) {
			logger.info("-----------Scan QR code null--------------");
		} else {
			System.out.println("Barcode Format: " + result.getBarcodeFormat());
			System.out.println("Content: " + result.getText());
			reply = result.getText();
		}
		return reply;
	}
}