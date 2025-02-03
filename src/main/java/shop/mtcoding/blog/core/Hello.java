package shop.mtcoding.blog.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 유지 기간을 지정하는 메타 어노테이션 컴파일 후에도 런타임까지유지되어서 API접근가능해진다.
public @interface Hello {
}
