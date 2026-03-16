package consulo.java.messageFormat.impl.parser;

public class JdkMessageFormatParser extends MessageFormatParser {
    // JDK parser uses the base parser behavior.
    // Choice format style is handled by the base parseStyleContent() which
    // already handles nested braces and all tokens within the style section.
    // The lexer handles the choice-specific tokens (PIPE, LESS, etc.) in the
    // AFTER_SECOND_COMMA state.
}
