package advancedorm

import java.text.SimpleDateFormat

class Incident {

    static final INCIDENT_STATUSES = ['Created', 'In Progress', 'Resolved']

    static mapping = {
        cache true
    }

    static belongsTo = [customer:Customer]

    Date dateReported
    String description
    String resolution
    String status = 'Created'

    static constraints = {
        status(inList: INCIDENT_STATUSES)
        description(blank:false, size:2..500)
        resolution(nullable:true, blank:true, size:2..5000)
    }

    String toString(){
        "${dateReported ? new SimpleDateFormat('yyyy-MM-dd HH:mm').format(dateReported) : ''} [${status}] - ${description}"
    }

    boolean equals(o) {
        if (this.is(o)) return true;

        if (!o || getClass() != o.class) return false;

        Incident incident = (Incident) o;

        if (dateReported ? !dateReported.equals(incident.dateReported) : incident.dateReported != null) return false;
        if (description ? !description.equals(incident.description) : incident.description != null) return false;
        if (resolution ? !resolution.equals(incident.resolution) : incident.resolution != null) return false;
        if (status ? !status.equals(incident.status) : incident.status != null) return false;

        return true;
    }

    int hashCode() {
        int result;

        result = (dateReported ? dateReported.hashCode() : 0);
        result = 31 * result + (description ? description.hashCode() : 0);
        result = 31 * result + (resolution ? resolution.hashCode() : 0);
        result = 31 * result + (status ? status.hashCode() : 0);
        return result;
    }
}