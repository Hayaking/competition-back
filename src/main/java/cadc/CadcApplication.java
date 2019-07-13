package cadc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("cadc.mapper")
@SpringBootApplication
public class CadcApplication {

    public static void main(String[] args) {
        SpringApplication.run(CadcApplication.class, args);
    }

}
