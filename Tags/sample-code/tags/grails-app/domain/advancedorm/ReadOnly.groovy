package advancedorm

public class ReadOnly {

    String name
    Integer myNum

    static constraints = {
        myNum (min:1)
        name(nullable:false)
    }

    String toString(){
        return name
    }
}