package com.zcpure.foreign.trade.utils;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author ethan
 * @create_time 2018/11/1 10:39
 */
public class FileUtil {

	public static void SaveFileFromInputStream(InputStream stream, String path, String filename) {
		try {
			FileOutputStream fs = new FileOutputStream(path + "/" + filename);
			byte[] buffer = new byte[1024 * 1024];
			int byteSum = 0;
			int byteRead;
			while ((byteRead = stream.read(buffer)) != -1) {
				byteSum += byteRead;
				fs.write(buffer, 0, byteRead);
				fs.flush();
			}
			fs.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
