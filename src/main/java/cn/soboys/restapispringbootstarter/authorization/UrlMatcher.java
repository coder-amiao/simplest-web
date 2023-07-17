package cn.soboys.restapispringbootstarter.authorization;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kenx
 * @version 1.0
 * @date 2020/11/12
 * url 匹配认证
 */
public class UrlMatcher {
    private static final String TMP_PLACEHOLDER = "@@@@@#####$$$$$";
    private List<Pattern> includePatterns;
    private List<Pattern> excludePatterns;

    public UrlMatcher(String includes, String excludes) {
        this.includePatterns = valueToPatterns(includes);
        this.excludePatterns = valueToPatterns(excludes);
    }

    private List<Pattern> valueToPatterns(String value) {
        List<Pattern> patterns = new ArrayList<>();
        if (value == null) {
            return patterns;
        }

        String[] patternItems = value.split(",");
        for (String patternItem : patternItems) {
            patternItem = patternItem.trim();
            if ("".equals(patternItem)) {
                continue;
            }

            patternItem = patternItem.replace("**", TMP_PLACEHOLDER);
            patternItem = patternItem.replace("*", "[^/]*?");//替换*
            patternItem = patternItem.replace(TMP_PLACEHOLDER, "**");
            patternItem = patternItem.replace("**", ".*?");//替换**
            patterns.add(Pattern.compile(patternItem));
        }

        return patterns;
    }

    public boolean matches(String url) {
        return matches(includePatterns, url) && !matches(excludePatterns, url);
    }

    private boolean matches(List<Pattern> patterns, String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        UrlMatcher matcher = new UrlMatcher("/**", "");
        System.out.println(matcher.matches("/v3/api-docs"));
    }
}