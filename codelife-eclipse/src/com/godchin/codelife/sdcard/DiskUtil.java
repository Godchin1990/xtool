package com.godchin.codelife.sdcard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * 磁盘路径 有可能用户不存在SD卡 因此需要保持到手机内存中 必须实例化
 * 
 * @author Zhoucan
 * 
 */
@SuppressLint("NewApi")
public class DiskUtil {

	public static String DISKPATH;

	public DiskUtil(Context context) {
		DISKPATH = initDiskPath(context);
	}

	public String initDiskPath(Context context) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			DISKPATH = context.getCacheDir().getPath() + File.separator;
		} else {
			DISKPATH = context.getExternalCacheDir().getPath() + File.separator;
		}
		return DISKPATH;
	}

	// public static void downloadxmlfromservice(Context context,String url){
	// // url: http://127.0.0.1/nav/navs_configs.xml
	// try {
	// String filename = url.substring(url.lastIndexOf("/"));
	// String filepath = DISKPATH + "asdfadf/" + filename;
	//
	// BufferedReader in = new BufferedReader(new FileReader(url));
	// BufferedWriter out = new BufferedWriter(new FileWriter(filepath));
	//
	// String content = null;
	// while ((content = in.readLine()) != null) {
	// out.write(content);
	// out.newLine();
	// out.flush();
	// }
	// in.close();
	// out.close();
	// } catch (Exception e) {
	// ExceptionUtil.handleException(e);
	// }
	// }

	public static String getDISKPATH() {
		if (DISKPATH != null) {
			return DISKPATH;
		}
		return null;
	}

	/**
	 * 检查sd卡是否插入
	 */

	public static boolean isExitSDCard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable();
	}

	/**
	 * 创建目录
	 * 
	 * @param filepath
	 *            如navs
	 * @param FileName
	 *            如navs_configs.xml
	 */
	public File createDiskDir(String filepath) {
		File dir = new File(DISKPATH + filepath + File.separator);
		dir.mkdirs();
		return dir;
	}

	/**
	 * 创建文件到磁盘中
	 * 
	 * @param filepath
	 *            如navs
	 * @param FileName
	 *            如navs_configs.xml
	 */

	public static File createFile2Disk(String filepath, String fileName) {
		File file = new File(DISKPATH + filepath + File.separator + fileName);
		try {
			file.createNewFile();
		} catch (Exception e) {

		}
		return file;
	}

	/**
	 * 将一个InputStream字节流写入到磁盘中
	 * 
	 * @param filepath
	 *            如navs
	 * @param FileName
	 *            如navs_configs.xml
	 */
	public File write2DiskFromInputStream(String filepath, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;

		try {
			Log.i("syso", "======================" + input.available());
			Log.i("syso", "======================" + filepath);
			createDiskDir(filepath); // 根据传入的路径创建目录
			file = createFile2Disk(filepath, fileName); // 根据传入的文件名创建
			output = new FileOutputStream(file);
			byte[] buffer = new byte[4 * 1024]; // 每次读取8K
			int num = 0; // 需要根据读取的字节大小写入文件
			while ((num = (input.read(buffer))) != -1) {
				output.write(buffer, 0, num);
			}
			output.flush(); // 清空缓存
		} catch (Exception e) {
		} finally {
			try {
				input.close();
				output.close();
			} catch (Exception e) {
			}
		}
		return file;
	}

	/**
	 * 把传入的字符流写入到磁盘中
	 * 
	 * @param filepath
	 *            如navs
	 * @param FileName
	 *            如navs_configs.xml
	 * @param input
	 * @return
	 */
	@SuppressWarnings("resource")
	public File write2DiskFromBufferedReader(String filepath, String fileName,
			final BufferedReader input) {
		File file = null;
		try {
			FileWriter output = null; // 创建一个写入字符流对象
			createDiskDir(filepath); // 根据传入的路径创建目录
			file = createFile2Disk(filepath, fileName); // 根据传入的文件名创建
			output = new FileWriter(file);
			final BufferedWriter bufw = new BufferedWriter(output);
			new Thread() {
				@Override
				public void run() {
					try {
						String line = null;
						while ((line = (input.readLine())) != null) {
							bufw.write(line);
							bufw.newLine();
						}
						bufw.flush(); // 清空缓存
					} catch (Exception e) {
					} finally {
						try {
							bufw.close();
						} catch (Exception e) {
						}
					}
				};
			}.start();
		} catch (Exception e) {
		}
		return file;
	}

	/**
	 * 判断文件是否存在磁盘上面
	 * 
	 * @param filepath
	 *            如navs
	 * @param FileName
	 *            如navs_configs.xml
	 */

	public boolean isFileExitOnDisk(String filepath, String fileName) {
		File file = new File(DISKPATH + filepath + File.separator + fileName);
		return file.exists();
	}

	/**
	 * 查找某目录下面的所有filetype类型的文件
	 * 
	 * @param filepath
	 *            如navs
	 * @param FileName
	 *            如navs_configs.xml
	 */

	public static File[] findFileByFileType(String filepath,
			final String fileType) {
		File dir = new File(DISKPATH + filepath + File.separator);
		if (dir.isDirectory()) {
			File[] files = dir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					return (filename.endsWith(fileType));
				}
			});
			Arrays.sort(files, new Comparator<File>() {
				@Override
				public int compare(File str1, File str2) {
					return str2.getName().compareTo(str1.getName());
				}
			});
			return files;
		}
		return null;
	}
}