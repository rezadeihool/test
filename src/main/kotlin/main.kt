package ir.andy

import io.ktor.network.sockets.InetSocketAddress
import org.bbottema.javasocksproxyserver.SocksServer
import java.net.InetAddress
import java.net.ServerSocket
import javax.net.ServerSocketFactory

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
    val socksServer = SocksServer().setFactory(object : ServerSocketFactory(){
        val num = 4112
        override fun createServerSocket(port: Int): ServerSocket? {
            println("First called")
            return ServerSocket(4112)
        }

        override fun createServerSocket(port: Int, backlog: Int): ServerSocket? {
            println("Second called")
            return ServerSocket(4112, backlog)
        }



        override fun createServerSocket(
            port: Int,
            backlog: Int,
            ifAddress: InetAddress?
        ): ServerSocket? {
            println("Third called")
            return ServerSocket(4112, backlog, InetAddress.getByName("0.0.0.0"))
        }

    })
    socksServer.start()
    println("The proxy server is started successfully >> ${socksServer.listenPort} :))")
}
