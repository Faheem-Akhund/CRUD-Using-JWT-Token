package io.javabrains.springsecurityjpa;

import io.javabrains.springsecurityjpa.Beans.GlobalCache;
import io.javabrains.springsecurityjpa.Beans.GlobalToken;
import io.javabrains.springsecurityjpa.Exceptions.CustomException;
import io.javabrains.springsecurityjpa.User.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableCaching
@EnableScheduling
public class SpringSecurityJpaApplication {

	@Scheduled(fixedRate = 10000)
	public void performTask() {

		if(GlobalCache.token.isEmpty())
		{
			System.out.println("null");
		}

		else
		{
			for(GlobalToken globalToken: GlobalCache.token )
			{
				if(LocalDateTime.now().isAfter(globalToken.getDate()))
				{
					GlobalCache.token.remove(globalToken);

				}

				System.out.println("Time to Expire"+globalToken.getDate());
				System.out.println("Time Now"+LocalDateTime.now());


			}

		}

	}



	public static void main(String[] args) {

		SpringApplication.run(SpringSecurityJpaApplication.class, args);
	}

}
