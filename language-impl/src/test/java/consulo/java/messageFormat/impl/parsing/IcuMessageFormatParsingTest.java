package consulo.java.messageFormat.impl.parsing;

import consulo.java.messageFormat.impl.MessageFormatVersionKind;
import consulo.java.messageFormat.impl.MessageFormatBaseParsingTest;
import consulo.java.messageFormat.impl.SetMessageFormatVersion;
import org.junit.jupiter.api.Test;

public class IcuMessageFormatParsingTest extends MessageFormatBaseParsingTest {
    public IcuMessageFormatParsingTest() {
        super("parsing/icu");
    }

    @Test
    @SetMessageFormatVersion(MessageFormatVersionKind.ICU4J)
    public void testSimple(Context context) throws Exception {
        doTest(context, null);
    }

    @Test
    @SetMessageFormatVersion(MessageFormatVersionKind.ICU4J)
    public void testPlural(Context context) throws Exception {
        doTest(context, null);
    }

    @Test
    @SetMessageFormatVersion(MessageFormatVersionKind.ICU4J)
    public void testSelect(Context context) throws Exception {
        doTest(context, null);
    }

    @Test
    @SetMessageFormatVersion(MessageFormatVersionKind.ICU4J)
    public void testNested(Context context) throws Exception {
        doTest(context, null);
    }
}
