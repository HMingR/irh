package top.imuster;

/**
 * @ClassName: test
 * @Description: test
 * @author: hmr
 * @date: 2020/2/20 10:22
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = IrhFileApplication.class)
public class test {
    public static void main(String[] args) {
        String s = hexStringToString("\\x16\\x03\\x01\\x02\\x00\\x01\\x00\\x01\\xFC\\x03\\x03\\x17G\\x8E\\xB4\\x03\\x1F\\xE0^&K\\xD85\\xF4\\xB4$O\\xCD\\x8B\\xA2\\xF3k\\xFC\\x9Cr\\x0F\\xF8\\xFDt\\x11N\\xB1j \\xEA\\x0B\\x8B\\xBBC]\\x06r\\xEF\\x93\\x9B\\xD3\\xA7Sme\\xDF\\x08\\xB5\\xF0e\\xC39\\xD0\\xDB\\x18\\xE3\\xFD \\xEF^]\\x00\\x22\\x1A\\x1A\\x13\\x01\\x13\\x02\\x13\\x03\\xC0+\\xC0/\\xC0,\\xC00\\xCC\\xA9\\xCC\\xA8\\xC0\\x13\\xC0\\x14\\x00\\x9C\\x00\\x9D\\x00/\\x005\\x00");
        System.out.println(s);
    }

    /**
     * 16进制转换成为string类型字符串
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
}
