package alx.music.songfind.security.account;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * A user.
 */

@Data
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String login;

  private String firstName;

  private String lastName;

  private String email;

  private boolean activated;

  private String langKey;

  private String imageUrl;

  private Set<Authority> authorities = new HashSet<>();


  // Lowercase the login before saving it in database
  public void setLogin(String login) {
    this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
  }

}
