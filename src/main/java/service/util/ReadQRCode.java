package service.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
 
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
 
public class ReadQRCode {
 	
	public ReadQRCode() { }

	public void scanQRcode(String path) throws IOException, NotFoundException {
 		System.out.println("----------Try to scan QR code--------------");
		File QRfile = new File(path);
		BufferedImage bufferedImg = ImageIO.read(QRfile);
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImg);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
 		System.out.println("----------Scan QR code OK--------------");
		Result result = new MultiFormatReader().decode(bitmap);
		System.out.println("Barcode Format: " + result.getBarcodeFormat());
		System.out.println("Content: " + result.getText());
	}
}