package metrics

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.dao.DataIntegrityViolationException

@TestFor(BookController)
@Mock(Book)
class BookControllerTests {


    def populateValidParams(params) {
      assert params != null
      params["title"] = 'someValidTitle'
    }

    void testIndex() {
        controller.index()
        assert "/book/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.bookInstanceList.size() == 0
        assert model.bookInstanceTotal == 0
    }

    void testList_maxParams() {
        params.max = 10

        (1..20).each {
            new Book(title:it).save(failOnError: true)
        }

        def model = controller.list()

        assert model.bookInstanceList.size() == params.max
        assert model.bookInstanceTotal == Book.count()
    }

    void testCreate() {
       def model = controller.create()

       assert model.bookInstance != null
    }

    void testSave() {
        controller.save()

        assert model.bookInstance != null
        assert view == '/book/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/book/show/1'
        assert controller.flash.message != null
        assert Book.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/book/list'


        populateValidParams(params)
        def book = new Book(params)

        assert book.save() != null

        params.id = book.id

        def model = controller.show()

        assert model.bookInstance == book
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/book/list'


        populateValidParams(params)
        def book = new Book(params)

        assert book.save() != null

        params.id = book.id

        def model = controller.edit()

        assert model.bookInstance == book
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/book/list'

        response.reset()

        populateValidParams(params)
        def book = new Book(params)
        assert book.save() != null
        book.version = 1
        assert book.save() != null


        // test invalid parameters in update
        params.id = book.id
        params.title = null

        controller.update()

        assert view == "/book/edit"
        assert model.bookInstance != null

        book.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/book/show/$book.id"
        assert flash.message != null

        // test with valid version number
        assert book.version == 1
        response.reset()
        book.clearErrors()

        populateValidParams(params)
        params.version = book.version
        controller.update()

        assert response.redirectedUrl == "/book/show/$book.id"
        assert flash.message != null


        //test outdated version number
        response.reset()
        book.clearErrors()

        populateValidParams(params)
        params.id = book.id
        params.version = -1
        controller.update()

        assert view == "/book/edit"
        assert model.bookInstance != null
        assert model.bookInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/book/list'

        response.reset()

        populateValidParams(params)
        def book = new Book(params)

        assert book.save() != null
        assert Book.count() == 1

        params.id = book.id

        controller.delete()

        assert Book.count() == 0
        assert Book.get(book.id) == null
        assert response.redirectedUrl == '/book/list'
    }

    void testDelete_mockDataIntegrityConstriant() {
        Book.metaClass.'static'.delete = {Map args->
            throw new DataIntegrityViolationException('BOOM!')
        }

        populateValidParams(params)
        def book = new Book(params)

        assert book.save() != null
        assert Book.count() == 1

        params.id = book.id
        controller.delete()
        assert response.redirectedUrl == "/book/show/$book.id"
        assert flash.message != null

    }


}
