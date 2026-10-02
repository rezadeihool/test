package ir.andy

class DataHolder {
    companion object {
        @Volatile var isRunning: Boolean = false
    }
}