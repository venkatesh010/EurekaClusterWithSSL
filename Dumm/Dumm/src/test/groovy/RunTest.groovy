import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber)
@CucumberOptions(
		strict=true
//		features=["src/test/cucumber"],
//		glue = ["src/test/steps"]
)
public class RunTest{

}