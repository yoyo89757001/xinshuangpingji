package megvii.testfacepass.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * Created by chenjun on 2017/5/17.
 */

public class FileUtil {
    public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static String getSerialNumber(Context context){

        String serial = null;

        try {

            serial = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return serial;

    }


    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }



    /**
     * 获取手机IMSI号
     */
    public static String getIMSI(){


        return "355" +
                Build.BOARD.length()%10 +
                Build.BRAND.length()%10 +
                Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 +
                Build.HOST.length()%10 +
                Build.ID.length()%10 +
                Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 +
                Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 +
                Build.TYPE.length()%10 +
                Build.USER.length()%10;
    }


    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * CBC解密
     * @param key 密钥
     * @param keyiv IV
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

    //MD5编码
    public static String sToMD5(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    /**
     * Returns the screen/display size
     */
    public static Point getDisplaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    //兼容 sdk>=19 版本 图片选择获取路径问题
    public static String getPath(final Context context, final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                //   if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
                //     }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = MediaStore.Images.Media.DATA;
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static final String PATH = "ruitongPD";

    /**
     * 是否支持SDCard
     */
    public static boolean isSupportSDCard() {
        return Environment.getExternalStorageDirectory().exists();
    }
    /**
     * 获取所有存储卡挂载路径
     * @return
     */
    public static List<String> getMountPathList() {
        List<String> pathList = new ArrayList<String>();
        final String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();//取得当前JVM的运行时环境
        try {
            Process p = run.exec(cmd);//执行命令
            BufferedInputStream inputStream = new BufferedInputStream(p.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // 获得命令执行后在控制台的输出信息
                // Log.d("",line);
                //输出信息内容：  /data/media /storage/emulated/0 sdcardfs rw,nosuid,nodev,relatime,uid=1023,gid=1023 0 0
                String[] temp = TextUtils.split(line, " ");
                //分析内容可看出第二个空格后面是路径
                String result = temp[1];
                File file = new File(result);
                //类型为目录、可读、可写，就算是一条挂载路径
                if (file.isDirectory() && file.canRead() && file.canWrite()) {
                    // Logger.d("add --> "+file.getAbsolutePath());
                    pathList.add(result);
                }

                // 检查命令是否执行失败
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0表示正常结束，1：非正常结束
                    Log.d("","命令执行失败!");
                }
            }
            bufferedReader.close();
            inputStream.close();
        } catch (Exception e) {
            Log.d("",e.toString()+"f");
            //命令执行异常，就添加默认的路径
            pathList.add(Environment.getExternalStorageDirectory().getAbsolutePath());
        }
        return pathList;
    }

    //  往SD卡写入文件的方法
    public static void savaFileToSD(String filename, String filecontent) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;

            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename,true);
            output.write(filecontent.getBytes());
            //将String字符串以字节流的形式写入到输出流中
            output.close();
            //关闭输出流
        }
    }


    public static void Unzip(String zipFile, String targetDir) {
        int BUFFER = 1024*100; //这里缓冲区我们使用4KB，
        String strEntry; //保存每个zip的条目名称

        try {
            BufferedOutputStream dest = null; //缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry; //每个zip条目的实例

            while ((entry = zis.getNextEntry()) != null) {

                try {
                  //  Log.i("Unzip: ","="+ entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();
                   // Log.d("FileUtil", strEntry);
                    File entryFile = new File(targetDir + File.separator+ strEntry);
                 //   Log.d("FileUtil", "entryFile.isFile():" + entryFile.isFile());

//                    File entryDir = new File(entryFile.getParent());
//                    if (!entryDir.exists()) {
//                        entryDir.mkdirs();
//                    }

                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                 //   Log.d("FileUtil", "5555");
                } catch (Exception ex) {
                    Log.d("FileUtil", ex.getMessage()+"解压文件异常");
                }
            }
            zis.close();
        } catch (Exception cwj) {
            Log.d("FileUtil", cwj.getMessage()+"解压文件异常");
        }
    }

    public interface ZipListener {
        /** 开始解压 */
        void zipStart();

        /** 解压成功 */
        void zipSuccess();

        /** 解压进度 */
        void zipProgress(int progress);

        /** 解压失败 */
        void zipFail();
    }

    /**
     * 获取压缩包解压后的内存大小
     *
     * @param filePath
     *            文件路径
     * @return 返回内存long类型的值
     */
    public static long getZipTrueSize(String filePath) {
        long size = 0;
        ZipFile f;
        try {
            f = new ZipFile(filePath);
            Enumeration<? extends ZipEntry> en = f.entries();
            while (en.hasMoreElements()) {
                size += en.nextElement().getSize();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }




    /**
     * [* 使用 org.apache.tools.zip.ZipFile 解压文件，它与 java 类库中的
     * java.util.zip.ZipFile 使用方式是一新的，只不过多了设置编码方式的 接口。
     * 注，apache 没有提供 ZipInputStream 类，所以只能使用它提供的ZipFile 来读取压缩文件。]<BR>
     * @param archive 压缩包路径
     * @param decompressDir 解压路径
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ZipException
     */
    public static void readByApacheZipFile(String archive, String decompressDir, ZipListener listener)
            throws IOException, FileNotFoundException, ZipException {

        long sumLength = 0;
        long ziplength = getZipTrueSize(archive);
        Log.e("gggg", "readByApacheZipFile"+ziplength);
       // BufferedInputStream bi;
        ZipFile zf = new ZipFile(archive);// 支持中文
        Enumeration e = zf.entries();
        InputStream is = null;
        int count=0;

        while(e.hasMoreElements()) {

            ZipEntry entry= (ZipEntry) e.nextElement();

            is= zf.getInputStream(entry);

            File dstFile = new File(decompressDir+"/"+entry.getName());
            if (dstFile.exists()){
                is.close();
                continue;
            }
            FileOutputStream fos= new FileOutputStream(dstFile);

            byte[]buffer = new byte[10240];

            while((count = is.read(buffer, 0, buffer.length)) != -1){
                sumLength += count;
                int progress = (int) ((sumLength * 100) / ziplength);
                updateProgress(progress, listener);
                fos.write(buffer,0,count);
            }
            fos.close();
            is.close();
        }

//        while (e.hasMoreElements()) {
//            ZipEntry ze2 = (ZipEntry) e.nextElement();
//            String entryName = ze2.getName();
//            Log.e(TAG, entryName);
//            String path = decompressDir + "/" + entryName;
//            if (ze2.isDirectory()) {
//                Log.e(TAG, "正在创建解压目录 - " + entryName);
//                File decompressDirFile = new File(path);
//                if (!decompressDirFile.exists()) {
//                    decompressDirFile.mkdirs();
//                }
//            } else {
//
//
//                Log.e(TAG, "正在创建解压文件 - " + entryName);
//                String fileDir = path.substring(0, path.lastIndexOf("/"));
//                File fileDirFile = new File(fileDir);
//                if (!fileDirFile.exists()) {
//                    fileDirFile.mkdirs();
//                }
//                BufferedOutputStream bos = new BufferedOutputStream(
//                        new FileOutputStream(decompressDir + "/" + entryName));
//                bi = new BufferedInputStream(zf.getInputStream(ze2));
//                byte[] readContent = new byte[10240];
//                int readCount = bi.read(readContent);
//                while (readCount != -1) {
//                    sumLength += readCount;
//                    int progress = (int) ((sumLength * 100) / ziplength);
//                    updateProgress(progress, listener);
//
//                    bos.write(readContent, 0, readCount);
//                    readCount = bi.read(readContent);
//                }
//                bos.close();
//            }
//        }
        zf.close();
    }

   private static int lastProgress = 0;
    private static void updateProgress(int progress, ZipListener listener2) {
        /** 因为会频繁的刷新,这里我只是进度>1%的时候才去显示 */
        if (progress > lastProgress) {
            lastProgress = progress;
            listener2.zipProgress(progress);
        }
    }

    /**
     * 获取指定目录内所有文件路径
     * @param dirPath 需要查询的文件目录
     * @param
     */
    public static List<String> getAllFileXml(String dirPath, List<String> fileList) {
        File f = new File(dirPath);
        Log.d("FileUtilXML", "f.exists():" + f.exists()+dirPath);
        if (!f.exists()) {//判断路径是否存在
            return null;
        }

        File[] files = f.listFiles();
        // Log.d("FileUtil", "files.length:" + files.length);
        if(files==null){//判断权限
            return null;
        }

        Log.d("FileUtilXml", "文件夹个数" + files.length);

        for (File _file : files) {//遍历目录
            if(_file.isFile() && (_file.getName().endsWith("xml") )){
              //  String _name=_file.getName();
                String filePath = _file.getAbsolutePath();//获取文件路径
                //  String fileName = _file.getName().substring(0,_name.length()-4);//获取文件名
                //  Log.d("LOGCAT","fileName:"+fileName);
                // Log.d("LOGCAT","filePath:"+filePath);
                try {
                    fileList.add(filePath);

                }catch (Exception e){
                    Log.d("FileUtil", e.getMessage()+"获取usb文件异常");
                }
            } else if(_file.isDirectory()){//查询子目录
                getAllFileXml(_file.getAbsolutePath(),fileList);
            }
        }
        Log.d("FileUtil", "返回的xml个数:" + fileList.size());
        return fileList;
    }



    /**
     * 获取指定目录内所有文件路径
     * @param dirPath 需要查询的文件目录
     * @param
     */
    public static List<String> getAllFiles(String dirPath, List<String> fileList) {
        File f = new File(dirPath);
        Log.d("FileUtil", "f.exists():" + f.exists()+dirPath);
        if (!f.exists()) {//判断路径是否存在
            return null;
        }

        File[] files = f.listFiles();
       // Log.d("FileUtil", "files.length:" + files.length);
        if(files==null){//判断权限
            return null;
        }

        Log.d("FileUtil", "文件夹个数" + files.length);

        for (File _file : files) {//遍历目录
            if(_file.isFile() && ((_file.getName().endsWith("jpg") )||(_file.getName().endsWith("png") || (_file.getName().endsWith("jpeg") )))){
                String _name=_file.getName();
                String filePath = _file.getAbsolutePath();//获取文件路径
                //  String fileName = _file.getName().substring(0,_name.length()-4);//获取文件名
                //  Log.d("LOGCAT","fileName:"+fileName);
                // Log.d("LOGCAT","filePath:"+filePath);
                try {
                    fileList.add(filePath);

                }catch (Exception e){
                    Log.d("FileUtil", e.getMessage()+"获取usb文件异常");
                }
            } else if(_file.isDirectory()){//查询子目录
                getAllFiles(_file.getAbsolutePath(),fileList);
            }
        }
        Log.d("FileUtil", "返回的jsonArray:" + fileList.size());
        return fileList;
    }

    /**
     * 检测文件或者路径是否存在
     * <p>
     * 可以给值为Null，如果给值null,判断路径是否存在
     */

    public static boolean isExists(String path, String fileName) {
        if (null == path && null == fileName) {
            return false;
        }
        String name;
        name = SDPATH + File.separator + path;
        File file = new File(name);
        if (!file.exists()) {
            file.mkdirs();
        }
        File fileNmae = new File(name, fileName);
        return fileNmae.exists();
    }

    public static boolean isExists(String path) {
        if (null == path) {
            return false;
        }
        String name;

        name = SDPATH + File.separator + path;

        File file = new File(name);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.exists();
    }

    /**
     * 检查SD卡是否可用
     */
    public static boolean isAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }


    /***
     *保存bitmap对象到文件中
     * @param bm
     * @param path
     * @param quality
     * @return
     */
    public static boolean saveBitmap2File(Bitmap bm, String path, int quality) {
        if (null == bm || bm.isRecycled()) {
            return false;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != bm) {
                if (!bm.isRecycled()) {
                    bm.recycle();
                }
                bm = null;
            }
        }
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap
     *            传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }
}
