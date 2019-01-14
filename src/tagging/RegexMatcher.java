package tagging;
import java.util.regex.*;

public class RegexMatcher {
	public boolean matches(String pattern, String input)
	{
		return Pattern.matches(pattern, input);
	}
}
