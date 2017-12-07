package com.cloud.core;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.cloud.core.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2017/10/29
 * Description:虚拟机判断
 * Modifier:
 * ModifyContent:
 */
public class EmulatorJudge {

    private static String[] known_qemu_drivers = {"goldfish"};
    private static String[] known_numbers = {"15555215554", "15555215556",
            "15555215558", "15555215560", "15555215562", "15555215564",
            "15555215566", "15555215568", "15555215570", "15555215572",
            "15555215574", "15555215576", "15555215578", "15555215580",
            "15555215582", "15555215584",};
    private static String[] known_device_ids = {"000000000000000", "000000000000001c",
            "0000000000000024", "0000000000000020", "000000000000002c",
            "0000000000000028"};
    private static String[] known_imsi_ids = {"310260000000000"};

    private static boolean isQEmuDriverFile() {
        try {
            File driver_file = new File("/proc/tty/drivers");
            if (driver_file.exists() && driver_file.canRead()) {
                byte[] data = new byte[(int) driver_file.length()];
                try {
                    InputStream inStream = new FileInputStream(driver_file);
                    inStream.read(data);
                    inStream.close();
                } catch (FileNotFoundException e) {
                    Logger.L.error(e);
                } catch (IOException e) {
                    Logger.L.error(e);
                }
                String driver_data = new String(data);
                for (String known_qemu_driver : known_qemu_drivers) {
                    if (driver_data.indexOf(known_qemu_driver) != -1) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return false;
    }

    private static boolean checkPhoneNumber(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String phonenumber = telephonyManager.getLine1Number();
            for (String number : known_numbers) {
                if (number.equalsIgnoreCase(phonenumber)) {
                    return true;
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return false;
    }

    private static boolean checkDeviceIDS(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_ids = telephonyManager.getDeviceId();
            for (String know_deviceid : known_device_ids) {
                if (know_deviceid.equalsIgnoreCase(device_ids)) {
                    return true;
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return false;
    }

    private static boolean CheckImsiIDS(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            String imsi_ids = telephonyManager.getSubscriberId();
            for (String know_imsi : known_imsi_ids) {
                if (know_imsi.equalsIgnoreCase(imsi_ids)) {
                    return true;
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return false;
    }

    private static boolean checkEmulatorBuild(Context context) {
        try {
            String BOARD = android.os.Build.BOARD;
            String BOOTLOADER = android.os.Build.BOOTLOADER;
            String BRAND = android.os.Build.BRAND;
            String DEVICE = android.os.Build.DEVICE;
            String HARDWARE = android.os.Build.HARDWARE;
            String MODEL = android.os.Build.MODEL;
            String PRODUCT = android.os.Build.PRODUCT;
            if (BOARD == "unknown" || BOOTLOADER == "unknown"
                    || BRAND == "generic" || DEVICE == "generic"
                    || MODEL == "sdk" || PRODUCT == "sdk"
                    || HARDWARE == "goldfish") {
                return true;
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return false;
    }

    private static boolean checkOperatorNameAndroid(Context context) {
        try {
            String szOperatorName = ((TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperatorName();
            if (szOperatorName.toLowerCase().equals("android") == true) {
                return true;
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return false;
    }

    public static boolean isEmulator(Context context) {
        if (isQEmuDriverFile() ||
                checkPhoneNumber(context) ||
                checkDeviceIDS(context) ||
                CheckImsiIDS(context) ||
                checkEmulatorBuild(context) ||
                checkOperatorNameAndroid(context)) {
            return true;
        } else {
            return false;
        }
    }
}
