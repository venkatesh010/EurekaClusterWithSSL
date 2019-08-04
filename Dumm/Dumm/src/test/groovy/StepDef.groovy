import cucumber.api.DataTable
import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)




Given(~/^the input$/) { DataTable values ->
	calculator = new Calculator()
	List<Map<String, String>> params = values.asMaps(String.class, String.class)
	inputs = params.collect {it ->
		it.get("input")
	}
}

When(~/^Calculator is run$/) {  ->
	println inputs
	result = calculator.run(inputs)
}

Then(~/^the output should be$/) { DataTable output ->

	//println result*.class.name
	def answer = output.asMaps(String.class, String.class).collect{it->it.get("output")}
	//println answer*.class.name

	assert result == answer

}

