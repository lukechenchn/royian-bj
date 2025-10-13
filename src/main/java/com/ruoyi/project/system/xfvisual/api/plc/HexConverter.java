package com.ruoyi.project.system.xfvisual.api.plc;

/**
 * 十六进制转换工具类
 */
public class HexConverter {
    
    /**
     * 字节数组转十六进制字符串
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
    
    /**
     * 十六进制字符串转字节数组
     * @param hex 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }
    
    /**
     * 字节转十六进制字符串
     * @param b 字节
     * @return 十六进制字符串
     */
    public static String byteToHex(byte b) {
        return String.format("%02X", b);
    }
    
    /**
     * 十六进制字符串转字节
     * @param hex 十六进制字符串
     * @return 字节
     */
    public static byte hexToByte(String hex) {
        return (byte) ((Character.digit(hex.charAt(0), 16) << 4)
                + Character.digit(hex.charAt(1), 16));
    }
    
    /**
     * 十进制转十六进制
     * @param decimal 十进制数
     * @return 十六进制字符串
     */
    public static String decimalToHex(int decimal) {
        return Integer.toHexString(decimal).toUpperCase();
    }
    
    /**
     * 十六进制转十进制
     * @param hex 十六进制字符串
     * @return 十进制数
     */
    public static int hexToDecimal(String hex) {
        return Integer.parseInt(hex, 16);
    }
}
