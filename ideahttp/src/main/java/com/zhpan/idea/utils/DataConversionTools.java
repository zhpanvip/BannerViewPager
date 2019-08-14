package com.zhpan.idea.utils;

import java.nio.charset.Charset;
import java.util.Calendar;

/**
 * Created by jokerlee on 16-6-23.
 * 这是一个用于将不同数据类型转换为byte数组或者解码byte组为其它数据的工具类
 * Slc 设备基本采用高位在前的数据传输
 */
public class DataConversionTools {

    public static byte[] intArrayToByteArray(int[] data){
        if( data == null ) return null;
        byte[] result = new byte[data.length];
        for( int i = 0; i < data.length; i++ ){
            result[i] = (byte) data[i];
        }
        return result;
    }

    /**
     * 校验一段byte数组的crc，前bits位的和，取低8位
     * @param data
     * @return
     */
    public static boolean validCrc(byte[] data){
        if(data == null){
            return false;
        }
        byte crc = getCrc(data, data.length - 1);
        return crc == data[data.length - 1];
    }

    /**
     * 计算一段byte数组的crc，前bits位的和，取低8位
     * @param data
     * @param bits
     * @return
     */
    public static byte getCrc(byte[] data, int bits){
        int result = 0;
        for( int i = 0 ; i < bits ; i ++ ){
            byte b = data[i];
            result += b & 0xff;
        }
        return lowUint16((short) result);
    }

    /**
     * 获取低8位的byte数据
     * @param v
     * @return
     */
    public static byte lowUint16(short v) {
        return (byte) (v & 0xFF);
    }

    /**
     * 获取高8位的byte数据
     * @param v
     * @return
     */
    public static byte highUint16(short v) {
        return (byte) (v >> 8);
    }

    /**
     * 根据高低8位数据构造一个2 bytes的short数据
     * @param hi
     * @param lo
     * @return
     */
    public static short buildUint16(byte hi, byte lo) {
        return (short) ((hi << 8) + (lo & 0xff));
    }


    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)
     * @param src
     *            byte数组
     * @param offset
     *            从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (src[offset] & 0xFF)
                | ((src[offset+1] & 0xFF)<<8)
                | ((src[offset+2] & 0xFF)<<16)
                | ((src[offset+3] & 0xFF)<<24);
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序
     */
    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF);
        return value;
    }

    /**
     * Encode the string to byte[], use the given charset.
     * The common charset:
     * <ul>
     * <li>ISO-8859-1
     * <li>US-ASCII
     * <li>UTF-16
     * <li>UTF-16BE
     * <li>UTF-16LE
     * <li>UTF-8
     * </ul>
     * @param value
     * @param charset
     * @return
     */
    public static byte[] stringToBytes(String value, Charset charset){
        if( value != null && !value.isEmpty()) {
            return value.getBytes(charset);
        }
        return null;
    }

    /**
     * Encode the string to byte[], use the default utf-8
     * @param value
     * @return
     */
    public static byte[] stringToBytes(String value){
        if( value != null && !value.isEmpty()) {
            return value.getBytes(Charset.forName("UTF-8"));
        }
        return null;
    }

    /**
     * Decode the byte[] to string, use the utf-8 charset.
     * @param data
     * @return
     */
    public static String bytesToString(byte[] data){
        if(data!=null && data.length!=0){
            return new String(data,Charset.forName("UTF-8"));
        }
        return null;
    }

    /**
     * 将byte数组转换为十六进制的字符串
     * @param data
     * @return
     */
    public static String bytesToHexString(byte[] data){
        String result = "";
        for( byte b : data ){
            result += String.format("%02x",b);
        }
        return  result;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            result[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return result;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    /**
     * 将byte数组转换为acsii字符串
     * @param data
     * @return
     */
    public static String bytesToAsciiString(byte[] data){
        String result = "";
        for( byte b : data ){
            char c = (char) b;
            result += c;
        }
        return  result;
    }

    /**
     * 将5508规则的时间7个bytes数组转换为时间戳timestamp
     * 数据如：2016-10-31-21:09:55则写入07 e0 10 31 21 09 55
     * @param data
     * @return
     */
    public static long bytesToTimestamp(byte[] data){
        if( data==null || data.length != 7 ) return -1;
        //头两个bytes为年份，高8位在前，低8位在后
        int year = buildUint16(data[0], data[1]);
        int month = data[2] - 1; //默认月份为0-11故而取值之后 -1
        int day = data[3];
        int hour = data[4];
        int minute = data[5];
        int second = data[6];
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day,hour,minute,second);

//        PrintTools.printLogs("时间："
//                + CommonUtil.getStringDateTime(calendar.getTimeInMillis()));
        return calendar.getTimeInMillis();
    }

    public static byte[] getDeviceNameData(String name){
        //默认15bytes名字长度，不允许用户填写空格故而使用空格填充
        byte[] data = new byte[15];
        byte[] temp = DataConversionTools.stringToBytes(name);
        System.arraycopy(temp,0,data,0,temp.length > 15 ? 15 : temp.length);
        return data;
    }

}