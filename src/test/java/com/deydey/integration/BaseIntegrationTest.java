package com.deydey.integration;


import com.deydey.Oauth2JwtAuthenticationApplication;
import org.junit.ClassRule;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("integration")
@SpringBootTest(classes = Oauth2JwtAuthenticationApplication.class)
@Category(IntegrationTest.class)
public class BaseIntegrationTest {

	static {
		System.setProperty("AUTH_SECRET", "foo");
	}
}
