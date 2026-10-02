package ir.andy

import org.bbottema.javasocksproxyserver.SocksServer

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
    val socksServer = SocksServer(4112)
    socksServer.start()
}
