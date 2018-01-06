package qianlima.test2.example3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具类
 * 
 * @author komojoemary
 * @version [版本号, 2010-3-16]
 */
public class FileManagerUtil {

	/**
	 * 按行读取文件内容
	 * 
	 * @param fileName
	 * 
	 *            文件全路径
	 * @return List<String> -每行文件内容字符串
	 */

	static List<String> getContentFromSystemByLine(String fileName) {
		List<String> lineOf = new ArrayList<String>();
		if (isExist(fileName, false)) {
			String line = null;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						new File(fileName)));
				while ((line = reader.readLine()) != null) {
					lineOf.add(line);
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lineOf;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * 
	 *            文件全路径
	 * @param create
	 * 
	 *            如果不存在是否创建目录
	 * @return boolean 是否存在
	 */

	static boolean isExist(String fileName, boolean create) {
		File file = new File(fileName);
		boolean exist = file.exists();
		if (!exist && create) {
			file.mkdirs();
			// 只需要创建文件夹即可，文件删除
			if (fileName.indexOf(".") != -1) {
				file.delete();
			}
		}
		return exist;
	}

	/**
	 * 判断文件夹是否存在
	 * 
	 * @param filePath
	 * 
	 *            文件夹路径
	 * @param create
	 * 
	 *            如果不存在是否创建目录
	 * @return boolean 是否存在
	 */

	static boolean isExistFileDir(String filePath, boolean create) {
		File file = new File(filePath);
		boolean exist = file.exists();
		if (!exist && create) {
			file.mkdirs();
		}
		return exist;
	}
}
