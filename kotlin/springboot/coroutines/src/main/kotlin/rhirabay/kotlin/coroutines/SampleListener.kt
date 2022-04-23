package rhirabay.kotlin.coroutines

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import javax.servlet.ServletRequestEvent
import javax.servlet.ServletRequestListener
import javax.servlet.http.HttpServletRequest

@Component
class SampleListener : ServletRequestListener {
    override fun requestInitialized(sre: ServletRequestEvent) {
        log.info("request receipt @SampleListener")
        val start = System.currentTimeMillis()
        sre.servletRequest.setAttribute("xx-start", start)
    }

    override fun requestDestroyed (sre: ServletRequestEvent) {
        log.info("request destroyed @SampleListener")
        val end = System.currentTimeMillis()
        val start = sre.servletRequest.getAttribute("xx-start") as Long
        val hsr = sre.servletRequest as HttpServletRequest
        log.info("${hsr.requestURI} ${hsr.method} in ${end - start}")
    }

    companion object{
        private val log = LogManager.getLogger(ServletRequestListener::class.java)
    }
}