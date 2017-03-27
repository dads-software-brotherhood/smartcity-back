package mx.infotec.smartcity.backend.utils;

public enum TemplatesEnum {
  
  RECOVERY_PASSWORD_EMAIL("recovery_password_email.ftl"),
  MAIL_SAMPLE("mailSample.html");
  
  private String value;
  
  private TemplatesEnum(String value) {
    this.value = value;
  }
  
  public String value() {
    return value;
  }
}
