package cache.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitDataUtils {
    private static final Logger log = LoggerFactory.getLogger(InitDataUtils.class);

    public static boolean checkBooleanAnswer(String answer) {
        if ("y".equalsIgnoreCase(answer) || "n".equalsIgnoreCase(answer)) {
            return "y".equalsIgnoreCase(answer);
        }
        throw new RuntimeException("Unknown answer: '" + answer + "'");
    }

    public static int checkIntegerAnswer(String answer) {
        try {
            return Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Unknown number: '" + answer + "'");
        }
    }
}
