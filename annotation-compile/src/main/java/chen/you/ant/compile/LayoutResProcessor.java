package chen.you.ant.compile;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import chen.you.ant.layoutres.BindLayout;

/**
 * Created by You on 2018/2/1.
 */
@AutoService(Processor.class)
public final class LayoutResProcessor extends AbstractProcessor {
    static final String PACKAGE = "chen.you.ant.layoutres";
    static final String CLASS = "LayoutResIds";
    /**
     * 静态代码
     */
    static final String LAYOUT_MAP_FORMAT = "lrMap.put(\"%s\", %d);\n";
    static final String ACTIONBAR_MAP_FORMAT = "abMap.put(\"%s\", %d);\n";

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
        ClassName hashMapName = ClassName.get(HashMap.class);
        ParameterizedTypeName mapName = ParameterizedTypeName.get(hashMapName, ClassName.get(String.class), ClassName.get(Integer.class));

        FieldSpec layoutSpec = FieldSpec.builder(mapName, "lrMap", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T<>()", hashMapName).build();
        FieldSpec actionbarSpec = FieldSpec.builder(mapName, "abMap", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T<>()", hashMapName).build();
        FieldSpec initializedSpec = FieldSpec.builder(TypeName.BOOLEAN, "initialized", Modifier.PRIVATE, Modifier.STATIC)
                .build();

        MethodSpec construct = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE)
                .addCode("throw new AssertionError(\"No LayoutResIds instances.\");\n")
                .build();

        MethodSpec layoutMethod = MethodSpec.methodBuilder("layoutId")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(Class.class, "clazz")
                .returns(TypeName.INT)
                .addCode("if (clazz == null) return 0;\n" +
                        "Integer value = lrMap.get(ResUtils.encry(clazz.getName()));\n" +
                        "if (value == null) return layoutId(clazz.getSuperclass());\n" +
                        "return value;\n")
                .build();

        MethodSpec actionbarMethod = MethodSpec.methodBuilder("actionbarId")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(Class.class, "clazz")
                .returns(TypeName.INT)
                .addCode("if (clazz == null) return 0;\n" +
                        "Integer value = abMap.get(ResUtils.encry(clazz.getName()));\n" +
                        "if (value == null) return actionbarId(clazz.getSuperclass());\n" +
                        "return value;\n")
                .build();

        MethodSpec.Builder bindMethodBuilder = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addCode("if (initialized) return;\ninitialized = true;\n")
                .returns(TypeName.VOID);

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindLayout.class);
        if (elements != null || elements.size() > 0) {
            for (Element e : elements) {
                String putKey = ResUtils.encry(e.toString());
                BindLayout inject = e.getAnnotation(BindLayout.class);
                int layoutResId = inject.layout();
                if (layoutResId != 0) {
                    bindMethodBuilder.addCode(String.format(LAYOUT_MAP_FORMAT, putKey, layoutResId));
                }
                //actionbar...
                int actionbarId = inject.actionBar();
                if (actionbarId != 0) {
                    bindMethodBuilder.addCode(String.format(ACTIONBAR_MAP_FORMAT, putKey, actionbarId));
                }
            }
        }

        TypeSpec typeSpec = TypeSpec.classBuilder(CLASS).addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(layoutSpec).addField(actionbarSpec).addField(initializedSpec)
                .addMethod(construct).addMethod(bindMethodBuilder.build())
                .addMethod(layoutMethod).addMethod(actionbarMethod)
                .build();

        try {
            JavaFile javaFile = JavaFile.builder(PACKAGE, typeSpec).build();
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
