package advancedorm

class ReportController {

    def index() { }

    def counts(){
        Map model = [counts:[:]]
        model.counts.Incident = Incident.count()
        model.counts.Product = Product.count()
        model.counts.Customer = Customer.count()
        model.counts.ServiceLevel = ServiceLevel.count()

        render (view:'counts', model:model)
    }

    def countIncidentsByYear() {
        Date begin = Date.parse('yyyy', params.year)
        Date end = Date.parse('yyyy', "${params.int('year') + 1}")

        int count = Incident.countByDateReportedGreaterThanEqualsAndDateReportedLessThan(begin, end)

        render (view:'countsByYear', model:[total:count, year:params.year])

    }
}
