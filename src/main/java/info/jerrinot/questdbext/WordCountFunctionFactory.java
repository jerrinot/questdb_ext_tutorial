package info.jerrinot.questdbext;

import io.questdb.cairo.CairoConfiguration;
import io.questdb.cairo.sql.Function;
import io.questdb.cairo.sql.Record;
import io.questdb.griffin.FunctionFactory;
import io.questdb.griffin.SqlException;
import io.questdb.griffin.SqlExecutionContext;
import io.questdb.griffin.engine.functions.IntFunction;
import io.questdb.griffin.engine.functions.UnaryFunction;
import io.questdb.std.IntList;
import io.questdb.std.ObjList;

public final class WordCountFunctionFactory implements FunctionFactory {
    @Override
    public String getSignature() {
// see https://github.com/questdb/questdb/blob/5c8eaf67bc9be0181a6d7db7b60af123944c0ecf/core/src/main/java/io/questdb/griffin/FunctionFactory.java#L34
// for the signature syntax
        return "wordcount(S)";
    }

    @Override
    public Function newInstance(int i, ObjList<Function> objList, IntList intList, CairoConfiguration cairoConfiguration, SqlExecutionContext sqlExecutionContext) throws SqlException {
        return new WordCountFunction(objList.getQuick(0));
    }

    private static class WordCountFunction extends IntFunction implements UnaryFunction {
        private final Function arg;

        private WordCountFunction(Function arg) {
            this.arg = arg;
        }

        @Override
        public Function getArg() {
            return arg;
        }

        @Override
        public int getInt(Record record) {
            CharSequence str = arg.getStr(record);
            return countWords(str);
        }

        private static int countWords(CharSequence str) {
            int count = 0;
            boolean inWord = false;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isWhitespace(c)) {
                    inWord = false;
                } else {
                    if (!inWord) {
                        count++;
                        inWord = true;
                    }
                }
            }
            return count;
        }
    }
}
