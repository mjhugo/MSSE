package advancedorm

class Address {

    static belongsTo = Customer

    String street
    String city
    String state

    static mapping = {
        cache true
    }

    static constraints = {
        street(size:2..100, blank:true, nullable:true)
        city(size:2..100, blank:false, nullable:true)
        state(size:2..100, blank:true, nullable:true)
    }

    boolean equals(o) {
        if (this.is(o)) return true;

        if (!o || getClass() != o.class) return false;

        Address address = (Address) o;

        if (city ? !city.equals(address.city) : address.city != null) return false;
        if (state ? !state.equals(address.state) : address.state != null) return false;
        if (street ? !street.equals(address.street) : address.street != null) return false;

        return true;
    }

    int hashCode() {
        int result;

        result = (street ? street.hashCode() : 0);
        result = 31 * result + (city ? city.hashCode() : 0);
        result = 31 * result + (state ? state.hashCode() : 0);
        return result;
    }
}