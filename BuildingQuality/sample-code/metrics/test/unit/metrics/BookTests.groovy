package metrics



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Book)
class BookTests {

    void testSomething() {
        Book b = new Book(title: 'title')
        b.save()
        assert !b.hasErrors()
    }
}
