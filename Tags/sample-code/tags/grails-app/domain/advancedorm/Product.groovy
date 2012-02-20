package advancedorm

class Product {
    String name

    static hasMany = [customers: Customer]

    static belongsTo = Customer

    static constraints = {
        name(blank: false, size: 2..100)
    }

    static mapping = {
        cache true
    }

    String toString() {
        name
    }

    boolean equals(o) {
        if (this.is(o)) return true;

        if (!o || getClass() != o.class) return false;

        Product product = (Product) o;

        if (name ? !name.equals(product.name) : product.name != null) return false;

        return true;
    }

    int hashCode() {
        return (name ? name.hashCode() : 0);
    }
}