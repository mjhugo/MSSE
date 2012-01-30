package advancedorm

public class QueryServiceTest extends GroovyTestCase {

    def dataService
    def queryService
    def sessionFactory

    private Customer customerWithMoreThanFive

    protected void setUp() {
        super.setUp();
        dataService.loadLookup()
        customerWithMoreThanFive = buildCustomer('Mike', '123', 'MN', 'Gold', 6)
        customerWithMoreThanFive.save()

        Customer custSilver = buildCustomer('John', '456', 'MN', 'Silver', 0)
        custSilver.save()

        Customer custWithLessThanFive = buildCustomer('Sarah', '789', 'WI', 'Gold', 4)
        custWithLessThanFive.save()

        // flush all changes to the DB
        sessionFactory.currentSession.flush()
        sessionFactory.currentSession.clear()

        assertEquals(3, Customer.count())
    }

    void testGoldWithFiveDynamic() {
        def results = queryService.goldWithFiveDynamic()
        assertEquals 1, results.size()
        assertEquals customerWithMoreThanFive.id, results[0].id
    }

    void testGoldWithFiveWhere() {
        def results = queryService.goldWithFiveWhere()
        assertEquals 1, results.size()
        assertEquals customerWithMoreThanFive.id, results[0].id
    }

    void testGoldWithFiveWhereChain() {
        def results = queryService.goldWithFiveWhereChain()
        assertEquals 1, results.size()
        assertEquals customerWithMoreThanFive.id, results[0].id
    }

    void testGoldWithFiveHqlOne() {
        assertEquals customerWithMoreThanFive.id, queryService.goldWithFiveHqlOne().id
    }

    void testGoldWithFiveHql() {
        def results = queryService.goldWithFiveHql()
        assertEquals 1, results.size()
        assertEquals customerWithMoreThanFive.id, results[0].id
    }

    void testGoldWithFiveCriteria() {
        def results = queryService.goldWithFiveCriteria()
        assertEquals 1, results.size()
        assertEquals customerWithMoreThanFive.id, results[0].id
    }

    void testCountByState() {
        def results = queryService.countByState()
        assertEquals 2, results.size()
        assertEquals 2, results.find {it[0] == 'MN'}[1]
        assertEquals 1, results.find {it[0] == 'WI'}[1]
    }

    void testCustomerByName() {
        def expectedResults = Customer.findAllByName(customerWithMoreThanFive.name)
        def results = queryService.customerByName(customerWithMoreThanFive.name)

        assertEquals(expectedResults, results)
    }

    void testChainDynamicFinder(){
        assert 1 == queryService.chainDynamicFinder('123', 'Mike', 'MN').size()
    }
    
    // the .build() method is provided by the build-test-data plugin which is required for this test to run
    // it should be installed automatically, but if not, run grails install-plugin build-test-data
    private Customer buildCustomer(name, accountNumber, state, serviceLevel, numberOfIncidents = 0) {
        def address = Address.buildWithoutSave([state: state])
        def customer = Customer.build([name: name, accountNumber: accountNumber, address: address])
        customer.serviceLevel = ServiceLevel.findByName(serviceLevel)
        numberOfIncidents.times {
            Incident.build(customer:customer)
        }
        return customer
    }

}