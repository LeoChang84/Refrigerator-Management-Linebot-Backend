package service.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.EnumSet;
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
import com.google.zxing.BarcodeFormat;


public class ReadQRCode {

 	static private Logger logger = LoggerFactory.getLogger(ReadQRCode.class.getName());

	public ReadQRCode() { }

	public String scanQRcode(String path) throws IOException, NotFoundException {
 		logger.info("----------Try to scan QR code--------------");
 		logger.info(path);
		File qrFile = new File(path);
		FileInputStream qrFileInputStream = new FileInputStream(qrFile);
      	if (qrFile.isFile() && qrFile.canRead()){
        	logger.info("File can be read!");
      	} else { logger.info("File be readed fail.");}

		logger.info("new File " + qrFile.getPath());
		BufferedImage bufferedImg = ImageIO.read(qrFileInputStream);
		if (bufferedImg == null) {
			logger.info("bufferedImg is null");
		} else { logger.info("ImageIO read: " + bufferedImg.getHeight() + " " + bufferedImg.getWidth()); }

		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImg);
		if (source == null) { logger.info("BufferedImageLuminanceSource is null"); }
		logger.info("BufferedImageLuminanceSource source");
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
 		logger.info("----------Scan QR code OK--------------");
		Map<DecodeHintType, Object> hints = new LinkedHashMap<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET,"utf-8");
		hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		hints.put(DecodeHintType.POSSIBLE_FORMATS,EnumSet.allOf(BarcodeFormat.class));
        hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        logger.info("try to decode with MultiFormatReader");
        try {
			Result result = new MultiFormatReader().decode(bitmap, hints);
			System.out.println("Barcode Format: " + result.getBarcodeFormat());
			System.out.println("Content: " + result.getText());
			return result.getText();
        } catch (Exception e) {
        	return "fail";
        }
	}
}