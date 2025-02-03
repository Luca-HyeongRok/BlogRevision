package shop.mtcoding.blog.core.error;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import shop.mtcoding.blog.core.error.ex.Exception400;

@Component // IOC컨테이너에 자동으로 빈 등록되도록?
@Aspect // 관점지향프로그래밍(AOP)를 사용하기 위해서
public class GlobalValidationHandler {
    //    @Before("@annotation(shop.mtcoding.blog.core.Hello)")
//    public void hello() {
//        System.out.println("aop hello 호출");
//    @Around("@annotation(shop.mtcoding.blog.core.Hello)")
//    public Object hello(ProceedingJoinPoint jp) throws Throwable {
//        System.out.println("aop Hello before 호출");
//        Object proceed = jp.proceed();
//        System.out.println("aop Hello after 호출됨");
//        System.out.println(proceed);
//        return proceed;
    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void vaildCheck(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;
                if (errors.hasErrors()) {
                    for (FieldError error : errors.getFieldErrors()) {
                        throw new Exception400(error.getDefaultMessage() + " : " + error.getField());
                    }
                }
            }
        }
    }
}
