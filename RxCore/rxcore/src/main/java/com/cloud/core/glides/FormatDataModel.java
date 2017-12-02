package com.cloud.core.glides;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.cloud.core.RxCoreUtils;
import com.cloud.core.config.RxConfig;
import com.cloud.core.enums.ImgRuleType;
import com.cloud.core.enums.PlatformType;
import com.cloud.core.enums.RuleParams;
import com.cloud.core.utils.ValidUtils;

import java.text.MessageFormat;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/30
 * @Description:jpg图片数据源
 * @Modifier:
 * @ModifyContent:
 */
public class FormatDataModel {

    public static String getUrl(Context context,
                                String dataModelUrl,
                                ImgRuleType ruleType,
                                int imgWidth,
                                int imgHeight,
                                int imgCorners) {

        String resultUrl = dataModelUrl;
        RxConfig config = RxCoreUtils.getInstance().getConfig(context);
        if (config.getPlatformType() == PlatformType.Alibaba) {
            resultUrl = aliImgProcessRule(context,
                    dataModelUrl,
                    ruleType,
                    imgWidth,
                    imgHeight,
                    imgCorners);
        } else if (config.getPlatformType() == PlatformType.Qiniu) {
            resultUrl = qiniuImgProcessRule(context,
                    dataModelUrl,
                    ruleType,
                    imgWidth,
                    imgHeight,
                    imgCorners);
        }
        return resultUrl;
    }

    private static String qiniuImgProcessRule(Context context,
                                              String url,
                                              ImgRuleType ruleType,
                                              int imgWidth,
                                              int imgHeight,
                                              int imgCorners) {
        if (ruleType == ImgRuleType.GeometricForWidth) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth));
        } else if (ruleType == ImgRuleType.GeometricRoundedCornersForWidth) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth), String.valueOf(imgCorners > 0 ? imgCorners : 12));
        } else if (ruleType == ImgRuleType.TailoringCircle) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth / 2));
        } else if (ruleType == ImgRuleType.TailoringWHRectangular) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth), String.valueOf(imgHeight));
        } else if (ruleType == ImgRuleType.TailoringWHRectangularRoundedCorners) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth), String.valueOf(imgHeight), String.valueOf(imgCorners));
        } else if (ruleType == ImgRuleType.GeometricCircleForWidth) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth), String.valueOf(imgCorners));
        }
        return url;
    }

    private static String aliImgProcessRule(Context context,
                                            String url,
                                            ImgRuleType ruleType,
                                            int imgWidth,
                                            int imgHeight,
                                            int imgCorners) {
        if (url.contains("cstart") && url.contains("cend")) {
            String verify = String.format(RuleParams.MatchTagBetweenContent.getValue(), "cstart", "cend");
            String paramsBase64 = ValidUtils.matche(verify, url);
            if (!TextUtils.isEmpty(paramsBase64)) {
                String params = new String(Base64.decode(paramsBase64, Base64.NO_WRAP));
                if (!TextUtils.isEmpty(params)) {
                    verify = String.format(RuleParams.MatchTagBetweenContent.getValue(), "#", "#");
                    params = ValidUtils.matche(verify, params);
                    params = params.replace("-", ",");
                    url = url + "?x-oss-process=image/" + params;
                    return url;
                } else {
                    return buildSuffix(context, url, ruleType, imgWidth, imgHeight, imgCorners);
                }
            } else {
                return buildSuffix(context, url, ruleType, imgWidth, imgHeight, imgCorners);
            }
        } else {
            return buildSuffix(context, url, ruleType, imgWidth, imgHeight, imgCorners);
        }
    }

    private static String buildSuffix(Context context,
                                      String url,
                                      ImgRuleType ruleType,
                                      int imgWidth,
                                      int imgHeight,
                                      int imgCorners) {
        if (ruleType == ImgRuleType.GeometricForWidth) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth));
        } else if (ruleType == ImgRuleType.GeometricRoundedCornersForWidth) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth), String.valueOf(imgCorners > 0 ? imgCorners : 12));
        } else if (ruleType == ImgRuleType.TailoringCircle) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth / 2));
        } else if (ruleType == ImgRuleType.TailoringWHRectangular) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth), String.valueOf(imgHeight));
        } else if (ruleType == ImgRuleType.TailoringWHRectangularRoundedCorners) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth), String.valueOf(imgHeight), String.valueOf(imgCorners));
        } else if (ruleType == ImgRuleType.GeometricCircleForWidth) {
            return url + MessageFormat.format(ruleType.getRule(context), String.valueOf(imgWidth), String.valueOf(imgCorners));
        }
        return url;
    }
}
