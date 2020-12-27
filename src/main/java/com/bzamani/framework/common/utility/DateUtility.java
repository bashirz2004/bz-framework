package com.bzamani.framework.common.utility;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class DateUtility {

    public static long getDiffSeconds(Date from, Date to) {
        return (to.getTime() - from.getTime()) / 1000;
    }

}
