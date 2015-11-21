package com.godchin.codelife.assets;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class AssetsUtil {
	/**
	 * 判断assets下是否存在某文件（判断完后，会关闭IO流)
	 * 
	 * @param assetPath
	 * @return
	 */
	public static boolean isAssetExistent(Context context, String filename) {
		InputStream is = null;
		try {
			is = context.getAssets().open(filename);
			return is != null;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception e) {
			}
		}
	}

}
