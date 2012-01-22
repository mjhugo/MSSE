String fileName = 'orm.md'

def lastModified = new File(fileName).lastModified()
while (true){
    Thread.sleep(500)
    def currentModified =  new File(fileName).lastModified()
    if (currentModified > lastModified){
        println "reloading ${fileName}"
		def proc = './build.sh'.execute()
		proc.waitFor()  		
		if (proc.err.text){println "ERROR:\n ${proc.err.text}"}
		println proc.in.text
		lastModified = currentModified
    }
}