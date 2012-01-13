# Object Relational Mapping in Grails

- Grails Object Relational Mapping
- Constraints/Validation
- Relationships

------------------------

# Domain Class Location Convention

<pre class="brush: text; highlight:[6]">
	|___grails-app
	| |___conf
	| | |___hibernate
	| | |___spring
	| |___controllers
	| |___domain
	| |___i18n
	| |___services
	| |___taglib
	| |___utils
	| |___views
</pre>	

The **domain** subdirectory is for any class that you want to be persistent. 
These classes are automatically mapped to the DB through Hibernate (or other GORM implementation)

----------

# Database Creation Magic

<pre class="brush: groovy; highlight:[12]">
	dataSource {
		pooled = false
		driverClassName = "org.hsqldb.jdbcDriver"
		username = "sa"
		password = ""
	    loggingSql = true
	}
	
	environments {
		development {
			dataSource {
				dbCreate = "create-drop"
				//...
			}
		}
		test {
		//...
</pre>
	
----------

# DB creation options 

- create-drop
	- Drop and re-create the database when Grails is run
- create
	- Create the database if it doesn't exist, but don't modify it if it does
- update
	- Create the database if it doesn't exist, and modify it if it does exist
- not defined
	- do nothing to the database
	
---------------

# DB Console

TODO

-----------


<span class="notes">class name becomes table name</span>

<pre class="brush: groovy; highlight:[1]">
	class Customer {
		String name
		String accountNumber
	}
</pre>

<hr>

<pre class="brush: text; highlight:[1]">
	mysql> describe customer;
	+----------------+--------------+------+-----+---------+----------------+
	| Field          | Type         | Null | Key | Default | Extra          |
	+----------------+--------------+------+-----+---------+----------------+
	| id             | bigint(20)   | NO   | PRI | NULL    | auto_increment | 
	| version        | bigint(20)   | NO   |     | NULL    |                | 
	| account_number | varchar(255) | NO   |     | NULL    |                | 
	| name           | varchar(255) | NO   |     | NULL    |                | 
	+----------------+--------------+------+-----+---------+----------------+
	4 rows in set (0.02 sec)
</pre>


--------

<span class="notes">attribute names are converted into column names</span>

<pre class="brush: groovy; highlight:[2,3]">
	class Customer {
		String name
		String accountNumber
	}
</pre>

<hr>

<pre class="brush: text; highlight:[7,8]">
	mysql> describe customer;
	+----------------+--------------+------+-----+---------+----------------+
	| Field          | Type         | Null | Key | Default | Extra          |
	+----------------+--------------+------+-----+---------+----------------+
	| id             | bigint(20)   | NO   | PRI | NULL    | auto_increment | 
	| version        | bigint(20)   | NO   |     | NULL    |                | 
	| account_number | varchar(255) | NO   |     | NULL    |                | 
	| name           | varchar(255) | NO   |     | NULL    |                | 
	+----------------+--------------+------+-----+---------+----------------+
	4 rows in set (0.02 sec)
</pre>

--------

<span class="notes">id (PK) and version (optimistic locking) columns are added to DB, but don’t need to be explicitly specified in code</span>

<pre class="brush: groovy">
	class Customer {
		String name
		String accountNumber
	}
</pre>

<hr>

<pre class="brush: text; highlight:[5,6]">
	mysql> describe customer;
	+----------------+--------------+------+-----+---------+----------------+
	| Field          | Type         | Null | Key | Default | Extra          |
	+----------------+--------------+------+-----+---------+----------------+
	| id             | bigint(20)   | NO   | PRI | NULL    | auto_increment | 
	| version        | bigint(20)   | NO   |     | NULL    |                | 
	| account_number | varchar(255) | NO   |     | NULL    |                | 
	| name           | varchar(255) | NO   |     | NULL    |                | 
	+----------------+--------------+------+-----+---------+----------------+
	4 rows in set (0.02 sec)
</pre>

--------

# ID and Version - invisible attributes?

- ID and Version were added to the DB table for Customer
- Where did they come from?

---

# javap

<pre class="brush: text; highlight:[7,8]">
	javap Customer
	Compiled from "Customer.groovy"
	public class calltrack.Customer 
		extends java.lang.Object 
		implements groovy.lang.GroovyObject{
	    
		java.lang.Long id;
	    java.lang.Long version;

	    private java.lang.String name;
	    private java.lang.String accountNumer;
	
		//... getters, setters, etc
	
	}
</pre>