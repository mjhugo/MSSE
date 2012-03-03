package sec

class SecurityFilters {

    def filters = {
        all(controller:'login', invert:true) {
            before = {
                if (!session.user){
                    redirect(controller: "login", action: "index")
                    return false
                }
            }
        }
    }
}
