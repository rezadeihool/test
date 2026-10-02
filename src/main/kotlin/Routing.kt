package ir.andy

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bbottema.javasocksproxyserver.SocksServer
import org.bbottema.javasocksproxyserver.auth.UsernamePasswordAuthenticator
import java.net.InetAddress
import java.net.ServerSocket
import javax.net.ServerSocketFactory

fun Application.configureRouting() {
    routing {
        get("/socks") {

            call.respondText("the server is running")

            if (DataHolder.isSocksRunning) return@get

            val socksServer = SocksServer(4112).setFactory(object : ServerSocketFactory() {
                override fun createServerSocket(port: Int): ServerSocket? {
                    println("First called")
                    return ServerSocket(port)
                }

                override fun createServerSocket(port: Int, backlog: Int): ServerSocket? {
                    println("Second called")
                    return ServerSocket(port, backlog)
                }


                override fun createServerSocket(
                    port: Int,
                    backlog: Int,
                    ifAddress: InetAddress?
                ): ServerSocket? {
                    println("Third called")
                    return ServerSocket(port, backlog, InetAddress.getByName("0.0.0.0"))
                }

            })
            socksServer.setAuthenticator(object : UsernamePasswordAuthenticator(false) {
                override fun validate(username: String?, password: String?): Boolean {
                    return (username == "andy" && password == "andy")
                }
            })
            socksServer.start()
            println("The proxy server is started successfully >> ${socksServer.listenPort} :))")
            DataHolder.isSocksRunning = true
        }



    }
}

