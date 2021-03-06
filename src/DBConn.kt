import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.io.BufferedReader
import java.io.InputStreamReader
import java.sql.ResultSet
import java.util.*
import javax.xml.transform.Result

class DBConn(private val hostUser: String = "pi", private val hostPassword: String = "enumaEli_s", private val hostURL: String = "www.musicmanager.duckdns.org", private val dbUser: String = "root", private val dbPassword: String = "yagamiLigh_t", private val dbName: String = "QuizApplication")
{
    init { connect() }
    private var connected = false
    private lateinit var session: Session
    private lateinit var config: Properties

    fun connect()
    {
        if (!connected)
        {
            session = JSch().getSession(hostUser, hostURL, 22)
            session.setPassword(hostPassword)
            config = Properties()
            //config.put("PreferredAuthentications", hostPassword)
            config.put("StrictHostKeyChecking", "no")
            println(config.toString())
            session.setConfig(config)
            session.connect()
        }
        connected = true

    }

    private fun outputFromCommand(command: String): String
    {
        if (!connected) { connect() }
        val channel = session.openChannel("exec")
        val instream = BufferedReader(InputStreamReader(channel.inputStream))

        (channel as ChannelExec).setCommand(command)
        channel.connect()

        var output: String?
        var totalOutput = ""
        do
        {
            output = instream.readLine()
            totalOutput += if (output != null) { output } else { "" }
        } while (output != null)

        channel.disconnect()
        return totalOutput
    }

    fun linesFromQuery(query: String): Array<String>
    {
        val resultsAsLines = outputFromCommand("mysql --user=$dbUser --password=$dbPassword -D $dbName -e \"$query\" > foo.txt && perl -pi -e 's/\n/#/g' foo.txt && cat foo.txt").split("#")
        return if (resultsAsLines.isNotEmpty()) resultsAsLines.subList(1, resultsAsLines.size).filter { it.isNotBlank() }.toTypedArray() // first element is the column headings
        else resultsAsLines.toTypedArray() // which is empty
    }

    fun runQuery(query: String) = outputFromCommand("mysql --user=$dbUser --password=$dbPassword -D $dbName -e \"$query\"")

    fun close() = session.disconnect()

}