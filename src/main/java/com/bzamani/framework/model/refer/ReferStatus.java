package com.bzamani.framework.model.refer;

public enum ReferStatus {
    initialSaved(0, "initialSaved", "پذیرش در مطب(ارجاع نشده)"),
    referred(1, "referred", "ارجاع شده به کلینیک"),
    initialReception(2, "initialReception", "پذیرش اولیه در کلینیک"),
    finishedWork(3, "finishedWork", "پایان کار بیمار در کلینیک"),
    settlementDone(4, "settlementDone", "تکمیل شده"),
    revoked(5, "revoked", "ابطال شده");

    private final Integer index;
    private final String title;
    private final String persianTitle;

    ReferStatus(Integer index, String title, String persianTitle) {
        this.index = index;
        this.title = title;
        this.persianTitle = persianTitle;
    }

    public Integer getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }

    public String getPersianTitle() {
        return persianTitle;
    }

    public static ReferStatus valueOfIndex(Integer index) {
        for (ReferStatus enu : values()) {
            if (enu.getIndex() == index) {
                return enu;
            }
        }
        throw new IllegalArgumentException(
                "status cannot be resolved for code " + index);
    }

}
