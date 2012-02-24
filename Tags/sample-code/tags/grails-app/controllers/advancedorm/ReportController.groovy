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
}
