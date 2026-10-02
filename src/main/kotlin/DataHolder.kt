package ir.andy

class DataHolder {
    companion object {
        @Volatile var isSocksRunning: Boolean = false
        @Volatile var isHttpRunning: Boolean = false
        const val SOCKS_PORT = 4112
        const val HTTP_PORT = 5311
    }
}