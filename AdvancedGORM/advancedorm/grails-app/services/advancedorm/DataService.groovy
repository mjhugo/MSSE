package advancedorm

import java.text.SimpleDateFormat
import org.springframework.core.io.ClassPathResource

class DataService {

    static transactional = false

    def loadLookup() {
        ['Gold', 'Silver', 'Bronze', 'SuperAwesome'].eachWithIndex {level, i ->
            if (!ServiceLevel.findByName(level)) {
                save(new ServiceLevel(name: level, priority: i + 1))
            }
        }


        ['Printer', 'Monitor', 'CPU', 'Scanner', 'Speakers', 'TV', 'DVD', 'Stereo', 'CD', 'MP3 Player', 'Guitar', 'Washer', 'Dryer'].each {
            if (!Product.findByName(it)) {
                save(new Product(name: it))
            }
        }
    }

    def save(object){
        if (!object.save()){
            println("ERRORS saving ${object.class.name}: ${object.errors}")
        }
    }


    def load() {
        if (Customer.count() < 100) {
            //name|phone|account|products|servicelevel|city|street|state
            def customerFile = new ClassPathResource('randomCustomerData.csv').file
            def incidentFile = new ClassPathResource('randomIncident.csv').file
            def incidents = incidentFile.readLines()
            def incidentIterator = incidents.iterator()
            incidentIterator.next()
            customerFile.eachLine {line ->
                if (!line.startsWith('name')) {
                    def columns = line.split('\\|')
                    println columns
                    Customer customer = new Customer()
                    customer.name = columns[0]
                    customer.phone = columns[1]
                    customer.accountNumber = columns[2]

                    if (columns[3]){
                        def products = columns[3].split(', ')
                        products.each { id->
                            customer.addToProducts (Product.get(Long.valueOf(id)))
                        }
                    }

                    customer.serviceLevel = ServiceLevel.get(Long.valueOf(columns[4]))
                    customer.address = new Address(city: columns[5], street: columns[6], state: columns[7])

                    customer.save()
                    if (customer.hasErrors()) {
                        println customer.errors
                    }

                    def randomNumber = new Random().nextInt(25)
                    for (i in 0..randomNumber) {
                        def incidentColumns
                        try {
                            incidentColumns = incidentIterator.next().split('\\|')
                        } catch (NoSuchElementException e) {
                            // start over
                            incidentIterator = incidents.iterator()
                            incidentIterator.next()
                            incidentColumns = incidentIterator.next().split('\\|')
                        }
                        //println incidentColumns
                        def incidentStatus = null
                        if (incidentColumns[1]) {
                            incidentStatus = Incident.INCIDENT_STATUSES.get(Integer.valueOf(incidentColumns[1]) - 1)
                        }
                        def incident = new Incident(customer: customer, description: incidentColumns[0])
                        if (incidentStatus) {
                            incident.status = incidentStatus
                        }
                        incident.dateReported = new SimpleDateFormat("MM/dd/yyyy").parse(incidentColumns[2])
                        incident.save()
                        if (incident.hasErrors()) {
                            println incident.errors
                        }
                    }

                }
            }

        }

    }
}