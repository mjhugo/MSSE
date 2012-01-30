import grails.util.Environment

class BootStrap {

    def dataService

    def init = { servletContext ->
        println "loading lookup data"
        dataService.loadLookup()

        if (Environment.current == Environment.DEVELOPMENT) {
            println "loading sample data"
            dataService.load()
        }
    }
    def destroy = {
    }
}
