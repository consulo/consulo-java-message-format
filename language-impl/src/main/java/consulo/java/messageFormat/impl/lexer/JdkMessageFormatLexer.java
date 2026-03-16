package consulo.java.messageFormat.impl.lexer;

public class JdkMessageFormatLexer extends _JdkMessageFormatLexer {
    private static final int STATE_MASK = 0xFFFF;
    private static final int DEPTH_SHIFT = 16;

    @Override
    public int getState() {
        int state = super.getState();
        return state | (getBraceDepth() << DEPTH_SHIFT);
    }

    @Override
    public void start(CharSequence buffer, int startOffset, int endOffset, int initialState) {
        super.start(buffer, startOffset, endOffset, initialState & STATE_MASK);
        setBraceDepth(initialState >> DEPTH_SHIFT);
    }
}
