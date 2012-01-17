package calltrack

class CustomerIntegrationTests {

    void testCreate() {
        def c = new Customer(name: 'Mike', accountNumber: '123');
        c.save();

        assert c == Customer.get(c.id)
    }

    void testRead() {
        assert 0 == Customer.count()

        def c = new Customer(name: 'Mike', accountNumber: '123');
        c.save();

        //read only version
        assert c == Customer.read(c.id)
    }

    void testGet() {
        assert 0 == Customer.count()

        def c = new Customer(name: 'Mike', accountNumber: '123');
        c.save();

        //read only version 
        assert c == Customer.get(c.id)
    }

    void testUpdate() {
        //setup
        def c = new Customer(name: 'Mike',
                accountNumber: '123').save()

        //update
        Customer customer = Customer.get(c.id)
        customer.name = 'new value'
        customer.save()
    }

    void testUpdateImplicit() {
        assert 0 == Customer.count()

        def c = new Customer(name: 'Mike', accountNumber: '123');
        c.save();

        c.name = 'Jim'
        c.save()

        assert c.name == Customer.get(c.id).name

        // beware the implicit update
        c.name = 'John'

        // even though c.save() hasn't been called,
        // the new name 'John' has been persisted!
        assert 'John' == Customer.get(c.id).name

    }

    void testDelete() {
        //setup
        def c = new Customer(name: 'Mike',
                accountNumber: '123').save()
        //delete
        Customer customer = Customer.get(c.id)
        customer.delete(flush: true)

        customer = Customer.get(c.id)
    }

    void testLock() {
        //setup
        def c = new Customer(name: 'Mike',
                accountNumber: '123').save()

        Customer.withTransaction{

            Customer customer = Customer.lock(c.id)
            customer.name = 'John'
            customer.save(flush: true)

        }
        assert 'John' == Customer.get(c.id).name
    }


}
