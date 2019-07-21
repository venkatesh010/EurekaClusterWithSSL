class Calculator {
	def shell = new GroovyShell()
	List<String> run(List<String> inputs){
		return inputs.collect {i ->
			shell.evaluate(i) as String
		}
	}
}
