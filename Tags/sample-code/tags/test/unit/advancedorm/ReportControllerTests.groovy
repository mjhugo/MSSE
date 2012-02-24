package advancedorm

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.junit.Test

@TestFor(ReportController)
@Mock([Customer, ServiceLevel, Product, Incident])
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
}
