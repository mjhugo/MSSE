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