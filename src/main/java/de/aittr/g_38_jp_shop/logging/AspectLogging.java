package de.aittr.g_38_jp_shop.logging;

import de.aittr.g_38_jp_shop.domain.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;

@Aspect
@Component
@Slf4j
public class AspectLogging {

//    public Logger logger = LoggerFactory.getLogger(AspectLogging.class);

    @Pointcut("execution(* de.aittr.g_38_jp_shop.service.ProductServiceImpl.save(..))")
    public void saveProduct(){}

    @Before("saveProduct()")
    public void beforeSavingProduct(JoinPoint joinPoint)  {
        Object[] args =joinPoint.getArgs();
        log.info("Method save of the class ProductServiceImp was called with parameter {}", args[0]);
    }

    @After("saveProduct()")
    public void afterSavingProduct()  {
           log.info("Method save of the class ProductServiceImp finish worked");
    }

    @Pointcut("execution(* de.aittr.g_38_jp_shop.service.ProductServiceImpl.getById(..))")
    public void getProductById(){}

    @AfterReturning(pointcut = "getProductById()", returning = "result")
    public void afterReturningPorductById(Object result){
        log.info("Method getById of the class ProductServiceImpl successfully returned result: {}", result);
    }

    @AfterThrowing(pointcut = "getProductById()", throwing = "e")
    public void afterThrowingAnExceptionWhileGettingProductById(Exception e){
        log.info("Method getById of the class ProductServiceImpl threw an exception: {}", e.getMessage());
    }

    @Pointcut("execution(* de.aittr.g_38_jp_shop.service.ProductServiceImpl.getAll(..))")
    public void getAllProducts() {}

    @Around("getAllProducts()")
    public Object aroundGettingAllProducts(ProceedingJoinPoint joinPoint) {
        log.info("Method getAll of the class ProductServiceImpl called");

        List<ProductDto> result = null;

        try {
            result = ((List<ProductDto>) joinPoint.proceed())
                    .stream()
                    .filter(x -> x.getPrice().compareTo(new BigDecimal(100)) > 0)
                    .toList();
        } catch (Throwable e) {
            log.error("Method getAll of the class ProductServiceImpl threw an exception: {}", e.getMessage());
        }

        log.info("Method getAll of the class ProductServiceImpl finished its work");
        return result;
    }

}
