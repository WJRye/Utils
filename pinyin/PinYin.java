

import java.util.ArrayList;

public class PinYin {
    //汉字返回拼音，字母原样返回，都转换为小写
    public static String getPinYin(String input) {
        ArrayList<Token> tokens = HanziToPinyin.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (Token token : tokens) {
                if (Token.PINYIN == token.type) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    //汉字返回拼音，字母原样返回，都转换为小写
    public static String[] getPinYinArray(String input) {
        ArrayList<Token> tokens = HanziToPinyin.getInstance().get(input);
        String[] strings = null;
        if (tokens != null && tokens.size() > 0) {
            strings = new String[tokens.size()];
            for (int i = 0; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                if (Token.PINYIN == token.type) {
                    strings[i] = token.target;
                } else {
                    strings[i] = token.source;
                }
            }
        }
        return strings;
    }
}
