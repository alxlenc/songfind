package alx.music.songfind.account;

import java.io.Serializable;
import lombok.Data;

/**
 * An authority (a security role) used by Spring Security.
 */

@Data
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

}
