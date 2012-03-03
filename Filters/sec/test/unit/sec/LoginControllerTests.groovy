package sec

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(LoginController)
@Mock([User, SecurityFilters])
class LoginControllerTests {

    void testLogin() {
        request.method = 'GET'
        withFilters(action: 'index') {
            controller.index()
        }
        assert view == '/login/index'
        assert !model.message
    }

    void testLogin_valid() {
        User user = new User(username: 'mike',
                password: 'pass').save(failOnError: true)
        params.username = user.username
        params.password = user.password

        withFilters(action: 'index') {
            controller.index()
        }
        assert response.redirectedUrl == '/user'
    }

    void testLogin_invalid() {
        User user = new User(username: 'mike',
                password: 'pass').save(failOnError: true)
        params.username = user.username
        params.password = ''

        withFilters(action: 'index') {
            controller.index()
        }
        assert view == '/login/index'
        assert model.message == 'Invalid Login'
    }


}
