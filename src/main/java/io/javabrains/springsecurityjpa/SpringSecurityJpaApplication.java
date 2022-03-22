package io.javabrains.springsecurityjpa;

import io.javabrains.springsecurityjpa.Beans.GlobalCache;
import io.javabrains.springsecurityjpa.Beans.GlobalToken;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableCaching
@EnableScheduling



public class SpringSecurityJpaApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}



	@Scheduled(fixedRate = 1000*60*15)
	public void performTask() {

		if(!GlobalCache.token.isEmpty())
		{
			for(GlobalToken globalToken: GlobalCache.token )
			{
				if(LocalDateTime.now().isAfter(globalToken.getDate()))
				{
					GlobalCache.token.remove(globalToken);

				}


			}
		}




	}



	public static void main(String[] args) {

		SpringApplication.run(SpringSecurityJpaApplication.class, args);
	}

}
