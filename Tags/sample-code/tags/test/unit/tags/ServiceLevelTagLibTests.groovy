package tags

import advancedorm.ServiceLevel
import grails.test.mixin.TestFor
import org.junit.Test

@TestFor(ServiceLevelTagLib)
class ServiceLevelTagLibTests {

    @Test
    void renderClassNameInSpan() {
        ServiceLevel serviceLevel = new ServiceLevel(name: 'blue')
        String tagOutput =
            applyTemplate('<sl:renderWithColor serviceLevel="${serviceLevel}"/>',
                    [serviceLevel: serviceLevel])
        assert '<span class="servicelevel blue">blue</span>' == tagOutput
    }

    @Test
    void renderClassNameInSpan_handleCase() {
        ServiceLevel serviceLevel = new ServiceLevel(name: 'Blue')
        String tagOutput = applyTemplate('<sl:renderWithColor serviceLevel="${serviceLevel}"/>', [serviceLevel: serviceLevel])

        assert '<span class="servicelevel blue">Blue</span>' == tagOutput
    }

    @Test
    void renderClassNameInSpan_HandleSpace() {
        ServiceLevel serviceLevel = new ServiceLevel(name: 'Blue Level')
        String tagOutput = applyTemplate('<sl:renderWithColor serviceLevel="${serviceLevel}"/>', [serviceLevel: serviceLevel])

        assert '<span class="servicelevel bluelevel">Blue Level</span>' == tagOutput
    }

    @Test
    void renderClassNameInSpan_HandleNull() {
        String tagOutput = applyTemplate('<sl:renderWithColor serviceLevel="${serviceLevel}"/>', [serviceLevel: null])

        assert '' == tagOutput
    }

    @Test
    public void ifGold() {
        String body = 'Hello Gold!'
        String tagOutput = applyTemplate('<sl:ifGold serviceLevel="${serviceLevel}">' + body + '</sl:ifGold>',
                [serviceLevel: new ServiceLevel(name: 'Gold')])

        assert body == tagOutput

    }

}
