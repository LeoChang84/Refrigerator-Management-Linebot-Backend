package service.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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

	public void scanQRcode() throws IOException, NotFoundException {
 		System.out.println("----------before get path--------------");
		File QRfile = new File("./src/main/resources/qrcode.png");
 		System.out.println("----------get path , before read--------------");
		BufferedImage bufferedImg = ImageIO.read(QRfile);
 		System.out.println("----------!!!--------------");
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImg);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
 		System.out.println("----------!!!--------------");
		Result result = new MultiFormatReader().decode(bitmap);
		System.out.println("Barcode Format: " + result.getBarcodeFormat());
		System.out.println("Content: " + result.getText());
	}
}