/*
 * Copyright 2013-2026 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
