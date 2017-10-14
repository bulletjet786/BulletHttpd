import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 验证某些想法
 *
 * @author Bullet
 * @time 2017-08-21 13:51
 */
public class Test {

  public static void main(String[] args) throws IOException {
    ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("GMT"));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z")
        .withLocale(
            Locale.ENGLISH);
    System.out.println(dateTime.format(formatter));

  }

}
