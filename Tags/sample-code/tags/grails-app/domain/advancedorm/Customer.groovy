package advancedorm

class Customer {
    String name
    String accountNumber
    String phone
    Date dateCreated
    boolean active = true

    static hasMany = [incidents: Incident,
            products: Product]

    ServiceLevel serviceLevel
    Address address

    def beforeInsert(){
        log.info 'beforeInsert'
    }
    def beforeUpdate(){
        log.info 'beforeUpdate'
    }
    def beforeDelete(){
        log.info 'beforeDelete'
    }
    def beforeValidate(){
        log.info 'beforeValidate'
    }
    def afterInsert(){
        log.info 'afterInsert'
    }
    def afterUpdate(){
        log.info 'afterUpdate'
    }
    def afterDelete(){

        log.info 'afterDelete'
    }
    def onLoad(){
        log.info 'onLoad'
    }

    static mapping = {
//        address fetch: 'join'
        cache true
    }
    static namedQueries = {
        goldLevelMoreThanFiveIncidents {
            byServiceLevelName('Gold')
            moreThanFiveIncidents()
        }

        moreThanFiveIncidents {
            sizeGt('incidents', 5)
        }

        byServiceLevelName { serviceLevelName ->
            serviceLevel {
                eq('name', serviceLevelName)
            }
        }

        orderByName {
            order("name", "asc")
        }

        inState {String stateCode ->
            address {
                eq('state', stateCode)
            }
        }

    }

    static constraints = {
        // name must be at least 2 characters, but no more than 100
        name(nullable: false, blank: false, size: 2..100)
        //account number min length of 1 and have max of 5 characters
        accountNumber(nullable: true, blank: false, size: 1..5)
        //phone number must be 10 digits
        phone(nullable: true, matches: /(\d{10})?/)
    }

    String toString() {
        "${name} (${accountNumber})"
    }


    boolean equals(o) {
        if (this.is(o)) return true;

        if (!o || getClass() != o.class) return false;

        Customer customer = (Customer) o;

        if (active != customer.active) return false;
        if (accountNumber ? !accountNumber.equals(customer.accountNumber) : customer.accountNumber != null) return false;
        if (address ? !address.equals(customer.address) : customer.address != null) return false;
        if (dateCreated ? !dateCreated.equals(customer.dateCreated) : customer.dateCreated != null) return false;
        if (name ? !name.equals(customer.name) : customer.name != null) return false;
        if (phone ? !phone.equals(customer.phone) : customer.phone != null) return false;
        if (serviceLevel ? !serviceLevel.equals(customer.serviceLevel) : customer.serviceLevel != null) return false;

        return true;
    }

    int hashCode() {

        int result;

        result = (name ? name.hashCode() : 0);
        result = 31 * result + (accountNumber ? accountNumber.hashCode() : 0);
        result = 31 * result + (phone ? phone.hashCode() : 0);
        result = 31 * result + (dateCreated ? dateCreated.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (serviceLevel ? serviceLevel.hashCode() : 0);
        result = 31 * result + (address ? address.hashCode() : 0);
        return result;
    }
}
