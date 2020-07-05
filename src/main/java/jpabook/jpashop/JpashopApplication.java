package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

//	// OrderSimpleApiController에서 Entity 노출시 해결방안
//	@Bean
//	Hibernate5Module hibernate5Module() {
//		Hibernate5Module hibernate5Module = new Hibernate5Module();
//		//hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
//		return hibernate5Module;
//	}
}
