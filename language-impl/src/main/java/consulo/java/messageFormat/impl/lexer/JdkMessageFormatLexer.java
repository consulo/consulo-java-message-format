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
