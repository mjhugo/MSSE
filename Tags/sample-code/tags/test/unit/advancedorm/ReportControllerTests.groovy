package advancedorm

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.junit.Test
import grails.buildtestdata.mixin.Build

@TestFor(ReportController)
@Mock([Customer, ServiceLevel, Product, Incident])
@Build(Incident)
class ReportControllerTests {

    @Test
    void testCounts() {
        controller.counts()
        
        assert view == '/report/counts'
        assert model.counts.Incident == 0
        assert model.counts.Customer == 0
        assert model.counts.Product == 0
        assert model.counts.ServiceLevel == 0
    }

    @Test
    void countIncidentsByYear(){
        Incident.build(dateReported:Date.parse('yyyy', '2009'))
        Incident.build(dateReported:Date.parse('yyyy', '2009'))
        Incident.build(dateReported:Date.parse('yyyy', '2010'))

        params.year = '2009'
        
        controller.countIncidentsByYear()

        assert view == '/report/countsByYear'

        assert model.year == params.year
        assert model.total == 2
    }
}
