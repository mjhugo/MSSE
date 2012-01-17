package calltrack

class Customer {
    static hasMany = [incidents: Incident]

    static belongsTo = Incident
    String name
    String accountNumber

    Date dateCreated

//    static mapping = {
//        table 't_cust'
//    }

    String toUpper() {
        name.toUpperCase()
    }

    static constraints = {
        name(blank: false, nullable: false)
    }
}
