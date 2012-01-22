@Grab(group='org.pegdown', module='pegdown', version='1.1.0') 


File targetDir = new File('target')
targetDir.mkdir()

boolean continuousBuild = true

if (continuousBuild){
	def start = new Date().time
	while (true){
	    Thread.sleep(500)
		def files = findSourceFiles(start)

		files.keySet().each { File sourceFile ->
			generateHtmlSlides(sourceFile, targetDir)
			if (files[sourceFile] > start){
				start = files[sourceFile]
			}
		}
	}
} else {
	def files = findSourceFiles()

	files.keySet().each { File sourceFile ->
		generateHtmlSlides(sourceFile, targetDir)
	}
}


void generateHtmlSlides(File sourceFile, File targetDir){
	println "generating slides for ${sourceFile.name} at ${new Date()}"
	String fileBaseName = sourceFile.name - '.md'
	File parentDirectory = sourceFile.parentFile

new File("${fileBaseName}-pegdown.html").text = new org.pegdown.PegDownProcessor().markdownToHtml(sourceFile.text)

	new File("target/${parentDirectory.name}").mkdirs()
	
    String command = "landslide ${sourceFile.name} -d ../target/${parentDirectory.name}/${fileBaseName}.html --relative --copy-theme -t ../landslide/mssetheme/ "
	println command

    def proc = command.execute(null, parentDirectory)
    proc.waitFor()
    if (proc.err.text) {println "ERROR:\n ${proc.err.text}"}
    println proc.in.text

	File outputFile = new File("target/${parentDirectory.name}/${fileBaseName}.html")
	outputFile.text = outputFile.text.replaceAll('theme/', '../theme/')

	new File(parentDirectory, 'theme').renameTo(new File(targetDir, "theme"));

	parentDirectory.eachFile {supportFile ->
		if (supportFile.isDirectory() && supportFile.name == 'images'){
			File targetSupportDir = new File("target/${parentDirectory.name}/${supportFile.name}")
			targetSupportDir.mkdirs()
			new AntBuilder().copy( todir:targetSupportDir.absolutePath ) {
			  fileset( dir:supportFile.absolutePath)
			}
		}		
	}
}


Map<File, Long> findSourceFiles(Long sinceLastModified = null) {
    Map files = [:]
    File baseDir = new File('.')

    def directoryTraversal

    directoryTraversal = {
        it.eachDir(directoryTraversal);
        it.eachFile {
            if (it.name.endsWith('.md') && (sinceLastModified == null || it.lastModified() > sinceLastModified)) {
                files[ it ] = it.lastModified()
            }
        }
    }

    directoryTraversal(baseDir)

    return files
}