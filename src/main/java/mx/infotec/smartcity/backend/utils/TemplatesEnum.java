package mx.infotec.smartcity.backend.utils;

public enum TemplatesEnum {

    RECOVERY_PASSWORD_EMAIL("recovery_password_email.ftl", "notification"),
    MAIL_SAMPLE("mailSample.html", "notification"),
    MAIL_SAMPLE2("mailSample.html", "notification"),
    DELETE_ACCOUNT_MAIL("cancelAccountMail.ftl", "Cancel account");

    private final String value;
    private final String title;

    private TemplatesEnum(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String value() {
        return value;
    }

    public String getTitle() {
        return title;
    }
}
