package com.cloud.core.encrypts;

import android.text.TextUtils;

import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;

import java.util.Arrays;
import java.util.Random;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/17
 * @Description:密文编码
 * @Modifier:
 * @ModifyContent:
 */
public class CipherTextCode {

    private static char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'i', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '-'};

    /**
     * 密文编码
     *
     * @param text 密文
     * @return
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
                    sb.append(chars[index]);
                } else {
                    int pos = Arrays.binarySearch(chars, c);
                    if (pos < 10) {
                        sb.append("0" + pos);
                    } else {
                        sb.append(pos);
                    }
                    int index = random.nextInt(nums.length);
                    sb.append(nums[index]);
                }
            }
            int rpos = random.nextInt(sb.length());
            return sb.substring(rpos) + sb.substring(0, rpos) + "/" + rpos;
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
            String[] slist = text.split("/");
            if (slist.length != 2) {
                return "";
            }
            if (slist[0].length() % 3 != 0) {
                return "";
            }
            int rpos = slist[0].length() - ConvertUtils.toInt(slist[1]);
            String result = slist[0].substring(rpos) + slist[0].substring(0, rpos);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length(); i += 3) {
                String m = result.substring(i, i + 3);
                int pos = ConvertUtils.toInt(m.substring(0, 2));
                char suf = m.charAt(m.length() - 1);
                if (Character.isDigit(suf)) {
                    sb.append(chars[pos]);
                } else {
                    sb.append(nums[pos]);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return "";
    }
}
