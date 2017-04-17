package mx.infotec.smartcity.backend.utils;

public enum TemplatesEnum {

    RECOVERY_PASSWORD_EMAIL("recovery_password.html", "Notification"),
    CREATE_USER_BY_ADMIN("create_user_admin.html", "Notification"),
    DELETE_SIMPLE_ACCOUNT("cancel_account.html", "Cancel account"),
    DELETE_ACCOUNT_MAIL("cancel_account_admin.html", "Cancel account"),
    CREATE_SIMPLE_ACCOUNT("create_user.html","Notification");

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
