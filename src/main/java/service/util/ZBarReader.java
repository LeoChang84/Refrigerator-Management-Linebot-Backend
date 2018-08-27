//package service.util;
//
//import com.dynamsoft.utils.BaseReader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class ZBarReader extends BaseReader {
//
//    static private Logger logger = LoggerFactory.getLogger(ZBarReader.class.getName());
//
//    static {
//        System.loadLibrary("zbarjni");
//    }
//
//    public void testZBar(String fileName) {
//        long start = System.nanoTime();
//        ZBarReader reader =  new ZBarReader();
//        ZBarResult[] results = (ZBarResult[])reader.decode(fileName);
//        logger.info("ZBAR: " + "TIME_COST " + ((System.nanoTime() - start) / 1000000) + "MS");
//
//        if (results != null && results.length > 0) {
////            mCount += 1;
//            for (ZBarResult result : results) {
//                logger.info("ZBAR: " + "TYPE: " + result.mType + "VALUE: " + result.mValue);
//            }
//        }
//    }
//
//    @Override
//    public int getCount() {
//        // TODO Auto-generated method stub
//        return super.getCount();
//    }
//
//    public native Object[] decode(String fileName);
//}