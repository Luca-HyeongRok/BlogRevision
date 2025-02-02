package shop.mtcoding.blog.core.util;

public class Script {
    public static String back(String msg) {
        String errorMsg = """
                <script>
                    alert('$msg');
                """.replace("$msg", msg);
        return errorMsg;
    }

    public static String href(String msg, String url) {
        String errMsg = """
                <script>
                    alert('$msg');
                    location.href = '$url';
                </script>
                """.replace("$msg", msg)
                .replace("$url", url);
        return errMsg;
    }
}
