package hcil.umd.edu.Android_calc;


public final class CalcUtils {
    private CalcUtils(){

    }

    public static boolean isOperator(char c){
        return c == '/' ||
                c == '÷' ||
                c == '*' ||
                c == '-' ||
                c == '+';
    }

    public static double evaluate(final String str){
        class Parser {
            int pos = -1, c;

            void eatChar() {
                c = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            void eatSpace() {
                while (Character.isWhitespace(c)) eatChar();
            }

            double parse() {
                eatChar();
                double v = parseExpression();
                if (c != -1) throw new RuntimeException("Unexpected: " + (char)c);
                return v;
            }


            double parseExpression() {
                double v = parseTerm();
                for (;;) {
                    eatSpace();
                    if (c == '+') {
                        eatChar();
                        v += parseTerm();
                    } else if (c == '-') {
                        eatChar();
                        v -= parseTerm();
                    } else {
                        return v;
                    }
                }
            }

            double parseTerm() {
                double v = parseFactor();
                for (;;) {
                    eatSpace();
                    if (c == '/' || c == '÷') {
                        eatChar();
                        v /= parseFactor();
                    } else if (c == '*' || c == '(') {
                        if (c == '*') eatChar();
                        v *= parseFactor();
                    } else {
                        return v;
                    }
                }
            }

            double parseFactor() {
                double v;
                boolean negate = false;
                eatSpace();
                if (c == '+' || c == '-') {
                    negate = c == '-';
                    eatChar();
                    eatSpace();
                }
                if (c == '(') {
                    eatChar();
                    v = parseExpression();
                    if (c == ')') eatChar();
                } else {
                    int startIndex = this.pos;
                    while ((c >= '0' && c <= '9') || c == '.') eatChar();
                    if (pos == startIndex) throw new RuntimeException("Unexpected: " + (char)c);
                    v = Double.parseDouble(str.substring(startIndex, pos));
                }

                eatSpace();
                if (c == '^') {
                    eatChar();
                    v = Math.pow(v, parseFactor());
                }
                if (negate) v = -v;
                return v;
            }
        }
        return new Parser().parse();
    }
}
