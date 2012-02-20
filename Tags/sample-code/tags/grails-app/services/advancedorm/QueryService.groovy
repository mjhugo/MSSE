package advancedorm

class QueryService {

    static boolean transactional = false

    def goldWithFiveDynamic() {
        // this is a very bad, lazy way to do this
        // it can be done much cleaner with HQL or Criteria (see other methods below)
        def gold = ServiceLevel.findByName('Gold')
        def goldCustomers = Customer.findAllByServiceLevel(gold)
        def moreThanFive = goldCustomers.findAll {cust ->
            cust.incidents.size() > 5
        }
        return moreThanFive
    }

    def goldWithFiveHqlOne() {
        // just to illustrate the difference between .find and .findAll
        // using .find puts a "limit 1" on the end of the query and only returns one record
        Customer.find(
                "from Customer as c inner join fetch c.address where c.serviceLevel.name = ? and size(c.incidents) > 5",
                ['Gold'])
    }

    def goldWithFiveHql() {
        // using .findAll returns all matching records
        Customer.findAll(
                "from Customer as c inner join fetch c.address where c.serviceLevel.name = ? and size(c.incidents) > 5",
                ['Gold'])
    }

    def goldWithFiveWhere(){
        String level = 'Gold'

        Customer.where{
            serviceLevel.name == level && incidents.size() > 5
        }.list()
    }

    def countByState() {
        Address.executeQuery("select distinct state, count(*) from Address group by state")
    }

    def upgradeCustomers() {
        Customer.executeUpdate(
                "update Customer c set c.serviceLevel = :newSl where c.serviceLevel = :oldSl",
                [newSl: ServiceLevel.findByName('SuperAwesome'), oldSl: ServiceLevel.findByName('Gold')])
    }

    def downgradeCustomers() {
        Customer.executeUpdate(
                "update Customer c set c.serviceLevel = :newSl where c.serviceLevel = :oldSl",
                [newSl: ServiceLevel.findByName('Gold'), oldSl: ServiceLevel.findByName('SuperAwesome')])
    }

    def customerByName(nameToFind, isCacheQuery = false) {
        def c = Customer.createCriteria()
        return c.list() {
            eq('name', nameToFind)
            cache (isCacheQuery)
        }
    }

    def searchForCustomer (accountNumber, name, state){
        def c = Customer.createCriteria()
        println state

        c.list (){
            or {
                if (name){
                    ilike('name', name + '%')
                }
                like('accountNumber', accountNumber)
            }
            if (state){
                address {
                    eq ('state', state)
                }
            }
        }
    }

    def goldWithFiveCriteria(cacheQuery = false) {
        def c = Customer.createCriteria()
        return c.list() {
            serviceLevel {
                eq('name', 'Gold')
            }
            sizeGt('incidents', 5)
            cache (cacheQuery)
        }
    }

    def search(accountNumber, name, state) {
        def c = Customer.createCriteria()
        return c.list {
            or {
                if (accountNumber) {
                    ilike('accountNumber', "${accountNumber}%")
                }
                if (name) {
                    ilike('name', "${name}%")
                }
            }
            if (state && state != 'null') {
                address {
                    eq('state', state)
                }
            }
        }
    }


    List chainDynamicFinder(accountNumber, name, theState){
        Customer.where{address.state == theState}.findAllByAccountNumberAndName(accountNumber, name)
    }
    

    List goldWithFiveWhereChain() {

        String gold = 'Gold'

        def serviceLevel = Customer.where{
            serviceLevel.name == gold
        }
        
        def greaterThanFive = serviceLevel.where() {
            incidents.size() > 5
        }

        return greaterThanFive.list()
    }
}
