package feature.JDBC_T;

import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(format = {"json:target/surefire-reports/jdbc-test.json"})
@RunWith(Karate.class)
public class JDBC_TTest {
}
