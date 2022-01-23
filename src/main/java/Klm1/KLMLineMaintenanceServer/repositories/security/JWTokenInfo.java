package Klm1.KLMLineMaintenanceServer.repositories.security;

public class JWTokenInfo {
  public static final String KEY = "tokenInfo";

  private String id;
  private String name;
  private String status;
  private String role;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
