package com.godchin.codelife.assets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.util.Log;

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
	
	
	private void copyBigDataToSD(String fileInRaw,String strOutFileName,Context context) throws IOException 
    {  
        InputStream myInput;  
        OutputStream myOutput = new FileOutputStream(strOutFileName);  
        myInput = context.getAssets().open(fileInRaw);  
        byte[] buffer = new byte[1024];  
        int length = myInput.read(buffer);
        while(length > 0)
        {
            myOutput.write(buffer, 0, length); 
            length = myInput.read(buffer);
        }
        myOutput.flush();  
        myInput.close();  
        myOutput.close();        
    } 
	
	
    //调用代码  
   // CopyAssets("/sdcard/xxx/", "xxx.txt");  
    private void CopyAssets(Context context,String dir, String fileName){  
            //String[] files;  
            File mWorkingPath = new File(dir);  
            if (!mWorkingPath.exists()) {  
                if (!mWorkingPath.mkdirs()) {  
                    Log.e("--CopyAssets--", "cannot create directory.");  
                }  
            }  
            try {  
                InputStream in = context.getResources().getAssets().open(fileName);  
                System.err.println("");  
                File outFile = new File(mWorkingPath, fileName);  
                OutputStream out = new FileOutputStream(outFile);  
                // Transfer bytes from in to out  
                byte[] buf = new byte[1024];  
                int len;  
                while ((len = in.read(buf)) > 0) {  
                    out.write(buf, 0, len);  
                }  
                in.close();  
                out.close();  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
        }  

}
