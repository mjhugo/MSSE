package sec



import grails.test.mixin.*

@TestFor(UserController)
@Mock([User,SecurityFilters])
class SecurityFiltersTests {

    void testRedirectToLogin() {
        withFilters(action:'list'){
            controller.list()
        }

        assert response.redirectedUrl == '/login/index'
    }

    void testLoggedInUserGoesToList() {
        session.user = 'mike'
        withFilters(action:'index'){
            controller.index()
        }

        assert response.redirectedUrl == '/user/list'
    }

}
