package feature.JDBC;

import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(format={"json:target/surefire-reports/JDBC.json"})
//@CucumberOptions(tags = {"~@ignore"})
//@CucumberOptions()
//@RunWith(Karate.class)
public class JDBCTest {
}
