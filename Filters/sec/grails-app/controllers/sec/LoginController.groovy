package sec

class LoginController {

    def index(String username, String password) {

        if (request.get){
            render view: 'index'
            return
        }
        User user =  User.findByUsernameAndPassword(username, password)
        if (user) {
            session.user = user.username
            redirect(controller: 'user')
        } else {
            render(view: 'index', model: [message: 'Invalid Login'])
        }
    }
}
