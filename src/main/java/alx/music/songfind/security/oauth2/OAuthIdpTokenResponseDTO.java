package alx.music.songfind.security.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.UUID;

public class OAuthIdpTokenResponseDTO implements Serializable {

  @JsonProperty("token_type")
  private String tokenType;

  private String scope;

  @JsonProperty("expires_in")
  private Long expiresIn;

  @JsonProperty("ext_expires_in")
  private Long extExpiresIn;

  @JsonProperty("expires_on")
  private Long expiresOn;

  @JsonProperty("not-before-policy")
  private Long notBefore;

  private UUID resource;

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("id_token")
  private String idToken;

  @JsonProperty("session_state")
  private String sessionState;

  @JsonProperty("refresh_expires_in")
  private String refreshExpiresIn;

  public OAuthIdpTokenResponseDTO() {
    // Empty constructor for serialization.
  }

  public String getRefreshExpiresIn() {
    return this.refreshExpiresIn;
  }

  public void setRefreshExpiresIn(String refreshExpiresIn) {
    this.refreshExpiresIn = refreshExpiresIn;
  }

  public String getSessionState() {
    return this.sessionState;
  }

  public void setSessionState(String sessionState) {
    this.sessionState = sessionState;
  }

  public String getTokenType() {
    return this.tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getScope() {
    return this.scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public Long getExpiresIn() {
    return this.expiresIn;
  }

  public void setExpiresIn(Long expiresIn) {
    this.expiresIn = expiresIn;
  }

  public Long getExtExpiresIn() {
    return this.extExpiresIn;
  }

  public void setExtExpiresIn(Long extExpiresIn) {
    this.extExpiresIn = extExpiresIn;
  }

  public Long getExpiresOn() {
    return this.expiresOn;
  }

  public void setExpiresOn(Long expiresOn) {
    this.expiresOn = expiresOn;
  }

  public Long getNotBefore() {
    return this.notBefore;
  }

  public void setNotBefore(Long notBefore) {
    this.notBefore = notBefore;
  }

  public UUID getResource() {
    return this.resource;
  }

  public void setResource(UUID resource) {
    this.resource = resource;
  }

  public String getAccessToken() {
    return this.accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return this.refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getIdToken() {
    return this.idToken;
  }

  public void setIdToken(String idToken) {
    this.idToken = idToken;
  }
}
