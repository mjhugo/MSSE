def files = findSourceFiles()

File targetDir = new File('target')
targetDir.mkdir()

files.each { File sourceFile ->
    String fileBaseName = sourceFile.name - '.md'
    String command = "landslide ${sourceFile.absolutePath} -d target/${fileBaseName}.html --relative --copy-theme -t landslide/mssetheme/"

    def proc = command.execute()
    proc.waitFor()
    if (proc.err.text) {println "ERROR:\n ${proc.err.text}"}
    println proc.in.text
}

new File('theme').renameTo(new File(targetDir, 'theme'));

List<File> findSourceFiles() {
    List files = []
    File baseDir = new File('.')

    def directoryTraversal

    directoryTraversal = {
        it.eachDir(directoryTraversal);
        it.eachFile {
            if (it.name.endsWith('.md')) {
                files << it
            }
        }
    }

    directoryTraversal(baseDir)

    return files
}