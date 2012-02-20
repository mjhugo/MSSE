package advancedorm

class ServiceLevel {
    Integer priority
    String name

    static constraints = {
        name(nullable: false, size: 2..32)
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

        ServiceLevel that = (ServiceLevel) o;

        if (name ? !name.equals(that.name) : that.name != null) return false;
        if (priority ? !priority.equals(that.priority) : that.priority != null) return false;

        return true;
    }

    int hashCode() {
        int result;

        result = (priority ? priority.hashCode() : 0);
        result = 31 * result + (name ? name.hashCode() : 0);
        return result;
    }
}