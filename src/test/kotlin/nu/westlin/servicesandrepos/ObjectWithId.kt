package nu.westlin.servicesandrepos

data class ObjectWithId<T>(val id: Int, val theObject: T) {

    companion object
}
