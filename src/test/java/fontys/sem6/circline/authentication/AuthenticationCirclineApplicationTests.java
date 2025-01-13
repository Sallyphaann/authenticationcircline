package fontys.sem6.circline.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest

class AuthenticationCirclineApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	public void testDatabaseConnection() {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/authenticationcc", "root", "Halan2021!")) {
			assertNotNull(connection);
		} catch (SQLException e) {
			fail("Database connection failed: " + e.getMessage());
		}
	}

}
