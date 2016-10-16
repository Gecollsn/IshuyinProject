package com.ishuyin.gecollsn.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gecollsn on 2016/3/24 16:27.
 * <p/>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p/>
 * Declare: 处理安全相关的工具类, 例如MD5加密、获取SHA1等
 * 需要在Application中初始化context
 */
public final class SecureUtil {

    /** 设置一个统一的上下文环境 */
    private static Context context;
    public static void initContext(Context context) { SecureUtil.context = context; }

    /**
     * 将字符串通过MD5加密
     *
     * @param strNeedMD5
     * @return
     */
    public static String md5(String strNeedMD5) {
        String str;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(strNeedMD5.getBytes());
            byte b[] = md.digest();
            StringBuilder buf = new StringBuilder(0);
            for (int offset = 0, i; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
            return str;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取应用的md5值
     *
     * @return
     */
    public static String md5() {
        PackageInfo pi;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature signatures = pi.signatures[0];
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(signatures.toByteArray());
            byte[] digest = md.digest();
            return toHexString(digest);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取应用的SHA1
     *
     * @return
     */
    public static String getSHA1() {
        PackageManager pm = context.getPackageManager();
        // 获取当前要获取SHA1值的包名，也可以用其他的包名，但需要注意，
        // 在用其他包名的前提是，此方法传递的参数Context应该是对应包的上下文。
        String packageName = context.getPackageName();
        // 返回包括在包中的签名信息
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            // 获得包的所有内容信息类
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 签名信息
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        // 将签名转换为字节数组流
        InputStream input = new ByteArrayInputStream(cert);
        // 证书工厂类，这个类实现了出厂合格证算法的功能
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        // X509证书，X.509是一种非常通用的证书格式
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            // 加密算法的类，这里的参数可以使MD4,MD5等加密算法
            MessageDigest md = MessageDigest.getInstance("SHA1");
            // 获得公钥
            byte[] publicKey = md.digest(c.getEncoded());
            // 字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    /**
     * 获取请求的sign
     *
     * @param params
     * @param token
     * @param password
     * @return
     */
    public static String getRequestSign(Map<String, String> params, String token, String password, String time) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = params.keySet().iterator();
        try {
            while (it.hasNext()) {
                String v = it.next();
                if (!"sign".equals(v)) {
                    sb.append(v);
                    sb.append("=");
                    sb.append(java.net.URLDecoder.decode(params.get(v), "utf-8"));
                    sb.append("&");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("time=");
        sb.append(time);
        sb.append("|");
        String token_pwd = md5(token + password);
        sb.append(token_pwd);
        String sign = md5(sb.toString());
        return sign;
    }



/*------------------------------   以下为私有方法，仅供本类使用    ---------------------------------------------------*/
    /** 这里是将获取到得编码进行16进制转换 */
    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1) h = "0" + h;
            if (l > 2) h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1)) str.append(':');
        }
        return str.toString();
    }

    private static String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        int len = block.length;
        for (int i = 0; i < len; i++) {
            byte2hex(block[i], buf);
            if (i < len - 1) buf.append(":");
        }
        return buf.toString();
    }

    private static void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

}
