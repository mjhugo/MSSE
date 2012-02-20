package tags

class ServiceLevelTagLib {

    static namespace = "sl"

    def renderWithColor = {attrs ->
        if (attrs.serviceLevel){
            out << """<span class="servicelevel ${attrs.serviceLevel.name.toLowerCase().replaceAll(' ','')}">${attrs.serviceLevel.name.encodeAsHTML()}</span>"""
        }
    }

    def ifGold = {attrs, body ->
        if (attrs.serviceLevel.name == 'Gold'){
            out << body()
        }
    }

}
