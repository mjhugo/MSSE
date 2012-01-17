#￼Advanced GORM
     
- Dynamic finders
- Hibernate Query Language (HQL) 
- Criteria Builder
- Named Queries
- Where Queries
- Events / Automatic timestamping Caching

---

# Dynamic Finders
 
- Synthesized at runtime (not compiled) 
- Made of up to two properties, boolean operators and comparators
- Additional parameters can be passed for pagination and sorting

--- 

# Dynamic Finders
- findAllBy
	- returns list of objects
- findBy
	- returns single object

---

# Dynamic Finders
<pre class="brush:groovy; highlight:[]; class-name:doubleline">
	findByName('mike')
	findAllByName('mike')
	findAllByNameAndAccountNumber('mike', '123')
	findAllByNameOrAccountNumber('mike', '789')
	findAllByNameLike('J%')
	findAllByNameIlike('j%', [sort:'name'])
	findAllByNameIlike('j%', [order:'desc',sort:'name'])
	findAllByNameIlike('j%', 
		[order:'desc', sort:'name', max:10, offset:0])
</pre>

--- 

#￼Meta Programming

--- 

# modify runtime behavior

---

￼class Person {
    String name
    Integer age
}
Person.findByName('Mike')
￼metaClass
￼def fakeDb = [new Person(name:'Mike', gender:'M'),
    new Person(name:'Robin', gender:'F')]
class Person {
    String name
    String gender
    String toString() {name}
}
// define a metaClass method on Person Person.metaClass.static.findByName = { String name->
    return fakeDb.findAll {it.name == name}
}
Person.findByName('Robin')

---

#￼invokeMethod

---
￼
class AddressBook {
   def people = [new Person(name:'Mike', gender:'M'),
       new Person(name:'Robin', gender:'F')]
   def invokeMethod(String name, args) {
        def propToFind = name - 'findBy'
        people.findAll {
            it[propToFind.toLowerCase()] == args[0]
        }
}
}
def ab = new AddressBook() ab.findByName('Robin') ab.findByGender('F')

---

#￼methodMissing

---

class AddressBook {
    static people = [new Person(name:'Mike', gender:'M'),
       new Person(name:'Robin', gender:'F')]
    def methodMissing(String name, args) {
} }
println 'inside methodMissing'
def impl = { Object[] theArgs ->
    def propToFind = name - 'findBy'
    people.findAll {
        it[propToFind.toLowerCase()] == args[0]
    }
}
AddressBook.metaClass."${name}" = impl
return impl(args)
def ab = new AddressBook()
ab.findByName('Robin')

---

￼class AddressBook {
    static people = [new Person(name:'Mike', gender:'M'),
       new Person(name:'Robin', gender:'F')]
    def methodMissing(String name, args) {
println 'inside methodMissing'
def impl = { Object[] theArgs ->
    def propToFind = name - 'findBy'
    people.findAll {
        it[propToFind.toLowerCase()] == args[0]
    }
}
AddressBook.metaClass."${name}" = impl
return impl(args)
￼￼} }
def ab = new AddressBook()
ab.findByName('Robin')

intercept

---

￼class AddressBook {
    static people = [new Person(name:'Mike', gender:'M'),
       new Person(name:'Robin', gender:'F')]
    def methodMissing(String name, args) {
        println 'inside methodMissing'
        def impl = { Object[] theArgs ->
            def propToFind = name - 'findBy'
            people.findAll {
                it[propToFind.toLowerCase()] == args[0]
            }
}
        AddressBook.metaClass."${name}" = impl
￼        return impl(args)
    }
}
def ab = new AddressBook() ab.findByName('Robin')

cache

---

￼class AddressBook {
    static people = [new Person(name:'Mike', gender:'M'),
       new Person(name:'Robin', gender:'F')]
    def methodMissing(String name, args) {
        println 'inside methodMissing'
        def impl = { Object[] theArgs ->
            def propToFind = name - 'findBy'
            people.findAll {
                it[propToFind.toLowerCase()] == args[0]
            }
}
        AddressBook.metaClass."${name}" = impl
return impl(args)
￼￼} }
invoke
def ab = new AddressBook()
ab.findByName('Robin')

---

-￼First Time
	- ab.findByName(‘Robin’)
	- invokeMethod
	- methodMissing
	- method cached
	- method invoked

- Second Time
	- ab.findByName(‘Robin’)
	- invokeMethod
	- method invoked

---
 
Problem
 
- Something more advanced than a dynamic finder
- Dynamic finders are great, but can lead to programmer laziness and performance issues
- example, find all customers with gold service level and more than 5 incidents

---

def gold = ServiceLevel.findByName('Gold')
List goldCustomers = Customer.findAllByServiceLevel(gold)
List moreThanFive = goldCustomers.findAll { cust->
    cust.incidents.size() > 5
}

---

select
from service_level where name = ?
select from customer where service_level_id = ?
￼￼def gold = ServiceLevel.findByName('Gold')
List goldCustomers = Customer.findAllByServiceLevel(gold)
List moreThanFive = goldCustomers.findAll { cust->
    cust.incidents.size() > 5
}
select
from incident
where customer_id = ?
(for each customer)
iterates through list of customers

---
￼ 
# HQL
- Hibernate Query Language
- SQL-like query language using domain names rather than DB names

---

#￼HQL
Customer.findAll(
! "from Customer as c where c.name = 'Mike'")
Customer.findAll(
! "from Customer as c where c.name = ?", ! ['Mike'])
Customer.findAll(
! "from Customer as c where c.name = :name",
    [name:'mike'])

---

# methods
 
- find
- findAll 
- executeQuery 
- executeUpdate

---

#￼find / findAll
 
- find: returns a single object (first found)
- findAll: returns a list of objects

---

Customer.findAll(
  "from Customer as c
   inner join fetch c.address
   where c.serviceLevel.name = ?
   and size(c.incidents) > 5",
￼￼￼￼￼['Gold'])
￼parameter
properties

---

#￼executeQuery

- doesn’t return a domain class 
- good for getting a subset of data
	- loading a single column
	- count, min, max, etc

---

￼Address.executeQuery(
  "select distinct state, count(*)
  from Address
  group by state")
Returns a list of results, where each result is a list itself, e.g.:
[ [MN, 5], [WI, 10] ]

---

#￼executeUpdate

- Data Manipulation
	- UPDATE
	- DELETE
- e.g. update all silver statuses to platinum
- delete all accounts with no activity in the 90 days

---

￼Customer.executeUpdate(
  "update Customer c
 set c.serviceLevel = :newSl
 where c.serviceLevel = :oldSl",
[newSl:ServiceLevel.findByName('SuperAwesome'),
 oldSl: ServiceLevel.findByName('Gold')])
named parameters
named parameters

---

#￼Criteria

- Grails provides a Hibernate Criteria Builder
- Useful for forming dynamic queries 
- Two methods:
	- createCriteria
	- withCriteria - inline criteria builder

---

#￼restrictions
      
- `eq` - equal
- `ilike` - case insensitive like (wildcard: %) like - case sensitive like (wildcard: %)
- `in` - in a list
- `isNull`
- `lt`, `le` - less than; less than or equal
- `gt`, `ge` - greater than; greater than or equal

---

#￼query methods

- list - (default) returns all matching rows 
- listDistinct - return a distinct set of results 
- get - retrieve one row (useful for projections)
- scroll - returns a scrollable result set

---

#￼Criteria
==
￼Customer.withCriteria {
    eq('name', 'mike')
}
￼def c = Customer.createCriteria() c{
    eq('name', 'mike')
}
￼def c = Customer.createCriteria() c{
    or {
        eq('name', 'mike')
        eq('accountNumber', '789')
} }
==
￼￼findAllByNameOrAccountNumber('mike', '789')

---

￼def customerByName(nameToFind) {
    def c = Customer.createCriteria()
    return c.list() {
        eq('name', nameToFind)
    }
}
// same as:
def customerByName(nameToFind) {
    return Customer.withCriteria {
        eq('name', nameToFind)
    }
}

---

#￼Example
 
- simple: find all customers by name
- find all customers with gold service level and more than 5 incidents
- Advanced Search function
	- name
	- account number
	- state

---

￼def goldWithFiveCriteria() {
def c = Customer.createCriteria() return c.list() {
        serviceLevel {
            eq('name', 'Gold')
}
        sizeGt('incidents', 5)
    }
}

---

￼def search(accountNumber, name, state) { def c = Customer.createCriteria() return c.list {
        or {
            if (accountNumber){
                ilike('accountNumber', "${accountNumber}%")
            }
            if (name){
                ilike('name', "${name}%")
} }
        if (state && state != 'null'){
            address {
} }
}
    eq('state', state)
}

---

￼def c = Customer.createCriteria() def results = c.scroll() {
    address {
        eq('state', 'MN')
} }
results.first() println results.get() results.next() println results.get() results.last() println results.get() results.next() println results.get()
// yields:
[Victoria Benjamin (5)]
[Sybill Cline (50)]
[Len Small (157)]
null

---

￼def c = Customer.createCriteria() def result = c.get() {
    projections {
        countDistinct('serviceLevel')
} }
println result
// yields:
3

---

#￼Named Queries
 
- Since Grails 1.2 http://grails.org/doc/latest/ref/Domain %20Classes/namedQueries.html
- Allow you to define criteria queries as part of the domain class
- Can be chained together
- Can be combined with dynamic finders Break down queries into components

---

#￼Auto Timestamping

- Two special properties for domain classes
	- dateCreated
	- lastUpdated
- Must be nullable
- Will be set automatically when object is persisted to the database

---
  
# Events

- beforeInsert - Executed before an object is initially persisted to the database
- beforeUpdate - Executed before an object is updated 
- beforeDelete - Executed before an object is deleted 
- beforeValidate - Executed before an object is validated 
- afterInsert - Executed after an object is persisted to the database
- afterUpdate - Executed after an object has been updated 
- afterDelete - Executed after an object has been deleted 
- onLoad - Executed when an object is loaded from the database

---

#￼Caching
  
- Domain objects
- Queries
- Overriding default cache settings

---

# Hibernate Cache
  
- Hibernate provides a second level cache for domain objects
- Good for “read mostly” or “read only” type of objects
- Different Cache Providers:
	- EHCache (default in Grails)
	- OSCache
	- SwarmCache
	- JBoss Cache
   
---

#￼Cache Strategies
- Transactional
	- (JTA environment only)
- Read-write
	- read committed
- Nonstrict-read-write
	- occasional updates
	- unlikely that two transactions update same item simultaneously
- Read-only

---

#￼equals / hashCode

- Define equality using “business key” - NOT primary key of object (exclude collections) 
- Needed for anything stored in a set or 2nd level cache
- Can use Intellij to generate
	- Code > Generate... > equals and hashCode
- Or implement a framework like :
	- http://burtbeckwith.com/blog/?p=53
- Or use Groovy @Equals and @HashCode annotations


---

#￼Caching in Grails  

- in DataSource.groovy
hibernate {
cache.use_second_level_cache=true
cache.use_query_cache=true cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}

- In domain class
static mapping = {
    cache true
}

---

#￼Query Caching

- Requires that the domain objects being returned have caching turned on
- Can be done for queries written with
	- Criteria
	- Dynamic Finders
	- where queries????? TODO
- Any save of a domain class will clear the query cache
- Not as useful as you might expect
- Do your own performance testing!

---

#￼Criteria
def customerByName(nameToFind) {
    def c = Customer.createCriteria()
    return c.list() {
} }
eq('name', nameToFind)
cache (true)

--- 

#￼Dynamic Finder
def customerByName(nameToFind) {
   Customer.findAllByName(nameToFind,
}
[cache:true])

--- 

#￼Where Query

TODO

--- 

# Clearing Cache
 
- sessionFactory.evictQueries()
- sessionFactory.evict(DomainClass.class)
