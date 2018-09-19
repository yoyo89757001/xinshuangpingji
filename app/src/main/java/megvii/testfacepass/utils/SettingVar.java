package megvii.testfacepass.utils;

import megvii.facepass.types.FacePassImageRotation;

/**
 * Created by wangzhiqiang on 2017/11/22.
 */

public class SettingVar {
    public static boolean cameraFacingFront = true;
    public static int faceRotation = FacePassImageRotation.DEG270;
    public static boolean isSettingAvailable = false;
    public static int cameraPreviewRotation = 90;
    public static boolean isCross = false;
    public static String SharedPrefrence = "user";
    public static int mHeight;
    public static int mWidth;
    public static boolean cameraSettingOk = false;
    public static boolean iscameraNeedConfig = false;
    public static boolean isButtonInvisible = false;
}
