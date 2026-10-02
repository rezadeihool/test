package ir.andy

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.andy.DataHolder.Companion.SOCKS_PORT
import org.bbottema.javasocksproxyserver.SocksServer
import org.bbottema.javasocksproxyserver.auth.UsernamePasswordAuthenticator
import org.littleshoot.proxy.ProxyAuthenticator
import org.littleshoot.proxy.impl.DefaultHttpProxyServer
import java.net.InetAddress
import java.net.InetSocketAddress
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

        get("/http") {
            println("the server is running")
            if (DataHolder.isHttpRunning) return@get
            val server = DefaultHttpProxyServer.bootstrap()
                .withAddress(InetSocketAddress(("0.0.0.0"), DataHolder.HTTP_PORT))
                .withProxyAuthenticator(object : ProxyAuthenticator {
                    override fun authenticate(userName: String?, password: String?): Boolean {
                        return (userName == "andy" && password == "andy")
                    }

                    override fun getRealm(): String? = null
                })
                .start()

            DataHolder.isHttpRunning = true


        }


    }
}

