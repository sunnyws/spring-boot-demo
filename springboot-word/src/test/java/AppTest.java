//import com.sunnyws.word.constant.WordType;
//import org.junit.Test;
//
//import java.util.Collections;
//
///**
// * 单元测试用例
// *
// * @author minghu.zhang
// */
//public class AppTest {
//
//    /**
//     * 词库上下文环境
//     */
//    private final WordContext context = new WordContext();
//    private final WordFilter filter = new WordFilter(context);
//
//    /**
//     * 测试替换敏感词
//     */
//    @Test
//    public void replace() {
//        String text = "利于上游行业发展的政策逐渐发布";
//        System.out.println(filter.replace(text));   //利于上游行业发展的政策逐渐发布
//        context.removeWord(Collections.singletonList("上游行业"), WordType.WHITE);
//        System.out.println(filter.replace(text));   //利于上**业发展的政策逐渐发布
//    }
//
//    /**
//     * 测试是否包含敏感词
//     */
//    @Test
//    public void include() {
//        String text = "利于上游行业发展的政策逐渐发布";
//        System.out.println(filter.include(text));   //false
//        context.removeWord(Collections.singletonList("上游行业"), WordType.WHITE);
//        System.out.println(filter.include(text));   //true
//    }
//
//    /**
//     * 获取敏感词数
//     */
//    @Test
//    public void wordCount() {
//        String text = "利于上游行业发展的政策逐渐发布";
//        System.out.println(filter.wordCount(text));   //0
//        context.removeWord(Collections.singletonList("上游行业"), WordType.WHITE);
//        System.out.println(filter.wordCount(text));   //1
//    }
//
//    /**
//     * 获取敏感词列表
//     */
//    @Test
//    public void wordList() {
//        String text = "利于上游1行业发展的政策逐渐发布";
//        System.out.println(filter.wordList(text));   //[]]
//        context.removeWord(Collections.singletonList("上游行业"), WordType.WHITE);
//        System.out.println(filter.wordList(text));   //[游行]
//    }
//
//}
