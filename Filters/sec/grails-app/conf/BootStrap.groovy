import sec.User

class BootStrap {

    def init = { servletContext ->

        User.findOrSaveWhere([username:'mike', password: 'password'])

    }
    def destroy = {
    }
}
