package consulo.java.messageFormat.impl.parsing;

import consulo.java.messageFormat.MessageFormatFileType;
import consulo.java.messageFormat.impl.MessageFormatVersionKind;
import consulo.java.messageFormat.impl.MessageFormatBaseParsingTest;
import consulo.java.messageFormat.impl.SetMessageFormatVersion;
import org.junit.jupiter.api.Test;

public class JdkMessageFormatParsingTest extends MessageFormatBaseParsingTest {
    public JdkMessageFormatParsingTest() {
        super("parsing/jdk", MessageFormatFileType.JDK_EXTENSION);
    }

    @Test
    @SetMessageFormatVersion(MessageFormatVersionKind.JDK)
    public void testSimple(Context context) throws Exception {
        doTest(context, null);
    }

    @Test
    @SetMessageFormatVersion(MessageFormatVersionKind.JDK)
    public void testNumberFormat(Context context) throws Exception {
        doTest(context, null);
    }

    @Test
    @SetMessageFormatVersion(MessageFormatVersionKind.JDK)
    public void testChoiceFormat(Context context) throws Exception {
        doTest(context, null);
    }

    @Test
    @SetMessageFormatVersion(MessageFormatVersionKind.JDK)
    public void testQuotedString(Context context) throws Exception {
        doTest(context, null);
    }
}
