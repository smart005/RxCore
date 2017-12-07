package com.cloud.core.encrypts;

import android.text.TextUtils;

import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;

import java.util.Arrays;
import java.util.Random;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2017/10/17
 * Description:密文编码
 * Modifier:
 * ModifyContent:
 */
public class CipherTextCode {

    private static char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'i', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'I', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static String[] civ = {"□:≡", "※", "눈눈"};

    /**
     * 密文编码
     *
     * @param text 密文
     *             return
     */
    public static String codingCiphertext(String text) {
        try {
            if (TextUtils.isEmpty(text)) {
                return "";
            }
            Random random = new Random();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.isDigit(c)) {
                    sb.append("0" + Arrays.binarySearch(nums, c));
                    int index = random.nextInt(chars.length);
                    sb.append(chars[index] + "0" + civ[random.nextInt(civ.length)]);
                } else {
                    int pos = Arrays.binarySearch(chars, c);
                    if (pos == -1) {
                        sb.append(c);
                        sb.append("1" + civ[random.nextInt(civ.length)]);
                    } else {
                        if (pos < 10) {
                            sb.append("0" + pos);
                        } else {
                            sb.append(pos);
                        }
                        int index = random.nextInt(nums.length);
                        sb.append(nums[index] + "0" + civ[random.nextInt(civ.length)]);
                    }
                }
            }
            int rpos = random.nextInt(sb.length());
            return sb.substring(rpos) + sb.substring(0, rpos) + "▥▥" + rpos;
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return "";
    }

    public static String decodingCiphertext(String text) {
        try {
            if (TextUtils.isEmpty(text)) {
                return "";
            }
            String[] slist = text.split("▥▥");
            if (slist.length != 2) {
                return "";
            }
            if (TextUtils.isEmpty(slist[0])) {
                return "";
            }
            int rpos = slist[0].length() - ConvertUtils.toInt(slist[1]);
            String result = slist[0].substring(rpos) + slist[0].substring(0, rpos);
            String splits = "";
            for (String splitItem : civ) {
                splits += String.format("|%s", splitItem);
            }
            if (splits.length() > 0) {
                splits = splits.substring(1);
            }
            String[] stringItems = result.split(splits);
            StringBuffer sb = new StringBuffer();
            for (String item : stringItems) {
                int placePos = ConvertUtils.toInt(item.substring(item.length() - 1));
                String m = item.substring(0, item.length() - 1);
                if (placePos == 1) {
                    sb.append(m);
                } else {
                    int pos = ConvertUtils.toInt(m.substring(0, 2));
                    if (Character.isDigit(m.charAt(2))) {
                        sb.append(chars[pos]);
                    } else {
                        sb.append(nums[pos]);
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return "";
    }
}
