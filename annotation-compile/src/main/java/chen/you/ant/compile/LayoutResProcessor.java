package chen.you.ant.compile;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import chen.you.ant.layoutres.BindLayout;

/**
 * Created by You on 2018/2/1.
 */
@AutoService(Processor.class)
public final class LayoutResProcessor extends AbstractProcessor {
    /**
     * package包名目录
     */
    static final String PACKAGE = "package chen.you.ant.layoutres;\n\n";
    /**
     * import导入
     */
    static final String IMPORT = "import java.util.HashMap;\n\n";
    /**
     * 包名完整路径
     */
    static final String CLASS = "chen.you.ant.layoutres.LayoutResIds";
    /**
     * class类的前部与尾部
     */
    static final String PUBLIC_CLASS = "public final class LayoutResIds {\n\n" +
            "    private LayoutResIds() {\n" +
            "        throw new AssertionError(\"No LayoutRes instances.\");\n" +
            "    }\n\n" +
            "    private static HashMap<String, Integer> lrMap = new HashMap<>();\n" +
            "    private static HashMap<String, Integer> abMap = new HashMap<>();";

//    static final String CLASS_END = "\n\n    public static int layoutId(Class clazz) {\n" +
//            "        Integer value = lrMap.get(ResUtils.encry(clazz.getName()));\n" +
//            "        if (value == null) return 0;\n" +
//            "        return value;\n" +
//            "    }\n\n" +
//            "    public static int actionbarId(Class clazz) {\n" +
//            "        Integer value = abMap.get(ResUtils.encry(clazz.getName()));\n" +
//            "        if (value == null) return 0;\n" +
//            "        return value;\n" +
//            "    }\n\n}";

    static final String CLASS_END = "\n\n    public static int layoutId(Class clazz) {\n" +
            "        if (clazz == null) return 0;\n" +
            "        Integer value = lrMap.get(ResUtils.encry(clazz.getName()));\n" +
            "        if (value == null) return layoutId(clazz.getSuperclass());\n" +
            "        return value;\n" +
            "    }\n" +
            "\n" +
            "    public static int actionbarId(Class clazz) {\n" +
            "        if (clazz == null) return 0;\n" +
            "        Integer value = abMap.get(ResUtils.encry(clazz.getName()));\n" +
            "        if (value == null) return actionbarId(clazz.getSuperclass());\n" +
            "        return value;\n" +
            "    }\n\n}";
    /**
     * 表态代码块
     */
    static final String STATIC_START = "\n\n    static {";
    static final String STATIC_END = "\n    }";
    /**
     * 表态代码块
     */
    static final String LAYOUT_MAP_FORMAT = "\n        lrMap.put(\"%s\", %d);";
    static final String ACTIONBAR_MAP_FORMAT = "\n        abMap.put(\"%s\", %d);";

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> annotations = new HashSet<>();
        annotations.add(BindLayout.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        StringBuffer sb = new StringBuffer(PACKAGE).append(IMPORT).append(PUBLIC_CLASS);

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindLayout.class);
        if (elements != null || elements.size() > 0) {
            sb.append(STATIC_START);
            for (Element e : elements) {
                String putKey = ResUtils.encry(e.toString());
                BindLayout inject = e.getAnnotation(BindLayout.class);
                int layoutResId = inject.layoutResId();
                if (layoutResId != 0) {
                    sb.append(String.format(LAYOUT_MAP_FORMAT, putKey, layoutResId));
                }
                //actionbar...
                int actionbarId = inject.actionBarResId();
                if (actionbarId != 0) {
                    sb.append(String.format(ACTIONBAR_MAP_FORMAT, putKey, actionbarId));
                }
            }
            sb.append(STATIC_END);
        }

        sb.append(CLASS_END);
        //System.err.println(sb.toString());
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(CLASS);
            Writer writer = sourceFile.openWriter();
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
